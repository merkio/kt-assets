package space.geek.ktassets.messaging

import mu.KotlinLogging
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component
import space.geek.ktassets.messaging.processor.asset.AssetMessage
import space.geek.ktassets.messaging.processor.asset.AssetProcessor

private val log = KotlinLogging.logger {}

@Component
internal class MessageListener(
    private val assetProcessor: AssetProcessor
) {

    @KafkaListener(groupId = "\${spring.kafka.assets-group-id}", topics = ["\${spring.kafka.assets-topic}"])
    fun listenToPartitionWithOffset(
        @Payload message: AssetMessage,
        @Header(name = TYPE, required = false) header: String?
    ) {
        log.info { "Received message: '$message' with type: $header" }
        when (val type = MessageType.byValue(header)) {
            MessageType.CREATE_ASSET,
            MessageType.UPDATE_ASSET,
            MessageType.DELETE_ASSET -> assetProcessor.onMessage(message, type)

            else -> log.warn { "Unknown message type: $type with message body: $message" }
        }
    }
}
