import BotUtils.command
import BotUtils.mode
import dev.inmo.tgbotapi.extensions.api.telegramBot
import dev.inmo.tgbotapi.extensions.behaviour_builder.buildBehaviourWithFSMAndStartLongPolling
import kotlinx.coroutines.runBlocking

val TELEGRAM_BOT_TOKEN = System.getenv("IoT_TG_BOT")
val mqtt = RequestMQTT()

fun main() {
    runBlocking {
        val bot = telegramBot(TELEGRAM_BOT_TOKEN)
        bot.buildBehaviourWithFSMAndStartLongPolling<BotState> {
            mode()
            command()
        }.join()
    }
}