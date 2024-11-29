import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage

class RequestMQTT(
) {
    private val brokerUrl = "tcp://localhost:1883"
    private val client = MqttClient(brokerUrl, "KotlinClient").apply {
        connect(MqttConnectOptions().apply { isCleanSession = true })
    }

    fun updateTopic(topic: MqttTopics, value: String) {
        val message = MqttMessage(value.toByteArray()).apply { qos = 1 }
        client.publish(topic.value, message)
    }
}