package data.mqtt

import data.DetectorDataFirebase
import domain.entity.ActuatorState
import domain.entity.TemperatureActuatorState
import domain.interfaces.actuator.TemperatureActuator
import domain.usecases.GetActuatorStateUseCase
import domain.usecases.GetDelayUseCase
import domain.usecases.GetTemperatureActuatorStateUseCase
import domain.usecases.GetTemperatureUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.net.HttpURLConnection
import java.net.URL
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.Float

class RequestMQTT(
    temperatureActuator: TemperatureActuator,

    getDelayUseCase: GetDelayUseCase,
    getTemperatureActuatorStateUseCase: GetTemperatureActuatorStateUseCase,
    getActuatorStateUseCase: GetActuatorStateUseCase,
    getInsideTemperatureUseCase: GetTemperatureUseCase,
    getOutsideTemperatureUseCase: GetTemperatureUseCase,
) {
    private val firebaseDBUri = "https://internetofthings-58825-default-rtdb.asia-southeast1.firebasedatabase.app"
    private val brokerUrl = "tcp://dev.rightech.io:1883"
    private val client = MqttClient(brokerUrl, "temperature_object").apply {
        connect(MqttConnectOptions().apply { isCleanSession = true })

        setCallback(
            object : MqttCallback {
                override fun connectionLost(p0: Throwable?) {}

                override fun deliveryComplete(p0: IMqttDeliveryToken?) {}

                // Get data from server
                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    message?.toString()?.let { message ->
                        println(message)

                        // Check topic type
                        when (MqttTopics.deserialize(topic)) {
                            MqttTopics.ActuatorModeTopic -> {
                                ActuatorState.deserialize(message)?.let { temperatureActuator.actuatorState = it }
                            }

                            MqttTopics.ActuatorCommandTopic -> {
                                TemperatureActuatorState.deserialize(message)
                                    ?.let { temperatureActuator.temperatureChangerState = it }
                            }

                            else -> {}
                        }
                    }
                }
            }
        )

        // Subscribe to server
        MqttTopics.entries.forEach {
            subscribe(it.value, 1)
        }
    }
    // Send data in topic with delay
    init {
        CoroutineScope(SupervisorJob() + CoroutineExceptionHandler { _, _ -> }).launch {
            launch {
                while (true) {
                    // Send data
                    updateTopic(MqttTopics.ActuatorModeTopic, getActuatorStateUseCase().name)
                    updateTopic(MqttTopics.ActuatorCommandTopic, getTemperatureActuatorStateUseCase().name)
                    updateTopic(MqttTopics.TemperatureInsideTopic, getInsideTemperatureUseCase().toString())
                    updateTopic(MqttTopics.TemperatureOutsideTopic, getOutsideTemperatureUseCase().toString())

                    // Delay coroutine
                    delay(getDelayUseCase())
                }
            }

            launch {
                while (true) {
                    val data = DetectorDataFirebase(
                        insideTemperature = getInsideTemperatureUseCase(),
                        outsideTemperature = getOutsideTemperatureUseCase(),
                        actuatorState = getActuatorStateUseCase().name,
                        temperatureActuatorState = getTemperatureActuatorStateUseCase().name,
                        time = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC),
                    )

                    writeToFirebase(Json.encodeToString(data))
                    readStateFirebase()?.let {
                        temperatureActuator.temperatureChangerState = it
                    }

                    delay(getDelayUseCase())
                }
            }
        }
    }

    fun writeToFirebase(data: String) {
        val firebaseUrl = URL("$firebaseDBUri/dataobjects.json")
        val connection = firebaseUrl.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json; utf-8")
        connection.doOutput = true

        connection.outputStream.use { it.write(data.toByteArray(Charsets.UTF_8)) }

        val responseCode = connection.responseCode

        if (responseCode != HttpURLConnection.HTTP_OK) {
            println("ERROR: ${connection.responseMessage}")
        }

        connection.disconnect()
    }

    fun readStateFirebase(): TemperatureActuatorState? {
        val firebaseUrl = URL("$firebaseDBUri/tas.json")
        val connection = (firebaseUrl.openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"
            setRequestProperty("Content-Type", "application/json; utf-8")
            doOutput = true
        }

        val responseCode = connection.responseCode

        if (responseCode != HttpURLConnection.HTTP_OK) {
            println("ERROR: ${connection.responseMessage}")
        }



        return connection.inputStream.bufferedReader().use {
            TemperatureActuatorState.deserialize(it.readText() )
        }.also {
            connection.disconnect()
        }
    }

    private fun updateTopic(topic: MqttTopics, value: String) {
        val message = MqttMessage(value.toByteArray()).apply { qos = 1 }
        client.publish(topic.value, message)
    }
}