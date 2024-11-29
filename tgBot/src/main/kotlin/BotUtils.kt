import dev.inmo.tgbotapi.extensions.api.send.sendTextMessage
import dev.inmo.tgbotapi.extensions.behaviour_builder.DefaultBehaviourContextWithFSM
import dev.inmo.tgbotapi.extensions.behaviour_builder.triggers_handling.command

object BotUtils {
    suspend fun DefaultBehaviourContextWithFSM<BotState>.mode() {
        command("mode", false) {
            if (it.content.text.length < 6) {
                sendTextMessage(it.chat.id, getErrorMessage("no value passed"))
                return@command
            }
            val text = it.content.text.drop(6)

            val message = if (text.contains("Handle")) {
                "Handle"
            } else if (text.contains("Auto")) {
                "Auto"
            } else {
                ""
            }

            if (message.isNotBlank()) {
                mqtt.updateTopic(MqttTopics.ActuatorModeTopic, message)
                sendTextMessage(it.chat.id, "State updated to $message")
            } else {
                sendTextMessage(it.chat.id, getErrorMessage("can't find mode state"))
            }
        }
    }


    suspend fun DefaultBehaviourContextWithFSM<BotState>.command() {
        command("command", false) {
            if (it.content.text.length < 9) {
                sendTextMessage(it.chat.id, getErrorMessage("no value passed"))
                return@command
            }

            val text = it.content.text.drop(9)

            val message = if (text.contains("Hot")) {
                "Hot"
            } else if (text.contains("Cold")) {
                "Cold"
            } else if (text.contains("None")) {
                "None"
            } else {
                ""
            }

            if (message.isNotBlank()) {
                mqtt.updateTopic(MqttTopics.ActuatorModeTopic, message)
                sendTextMessage(it.chat.id, "Actuator temperature state updated to $message")
            } else {
                sendTextMessage(it.chat.id, getErrorMessage("can't find command state"))
            }
        }
    }

    private fun getErrorMessage(description: String) = "Error command: $description"
}