package space.geek.ktassets.messaging.processor

import mu.KotlinLogging
import org.springframework.stereotype.Component
import space.geek.ktassets.appilcation.asset.AssetService
import space.geek.ktassets.messaging.MessageType

private val log = KotlinLogging.logger {}

@Component
internal class AssetProcessor(
    private val assetService: AssetService
) {

    fun onMessage(message: AssetMessage, type: MessageType) {
        when (type) {
            MessageType.CREATE_ASSET -> assetService.createAsset(message.toCreationCommand())
            else -> log.error { "Can't process message" }
        }
    }
}

