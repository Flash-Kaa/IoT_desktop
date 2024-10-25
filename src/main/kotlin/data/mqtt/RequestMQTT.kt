package data.mqtt

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
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage

class RequestMQTT(
    temperatureActuator: TemperatureActuator,

    getDelayUseCase: GetDelayUseCase,
    getTemperatureActuatorStateUseCase: GetTemperatureActuatorStateUseCase,
    getActuatorStateUseCase: GetActuatorStateUseCase,
    getInsideTemperatureUseCase: GetTemperatureUseCase,
    getOutsideTemperatureUseCase: GetTemperatureUseCase,
) {
    private val brokerUrl = "tcp://localhost:1883"
    private val client = MqttClient(brokerUrl, "KotlinClient").apply {
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
    }

    private fun updateTopic(topic: MqttTopics, value: String) {
        val message = MqttMessage(value.toByteArray()).apply { qos = 1 }
        client.publish(topic.value, message)
    }
}