package space.geek.ktassets.messaging

import io.mockk.every
import io.mockk.verify
import org.apache.kafka.clients.producer.ProducerRecord
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import space.geek.ktassets.messaging.Fixtures.CONSUMER_TOPIC
import space.geek.ktassets.messaging.processor.AssetMessage
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

internal class MessageListenerTest : MessagingBaseTest() {

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, AssetMessage>

    @Test
    fun `receive message`() {
        val event = AssetMessage(
            key = "file",
            url = null
        )
        val latch = CountDownLatch(1)
        every { assetProcessor.onMessage(any(), any()) }.returns(Unit).andThenAnswer { latch.countDown() }

        val record = ProducerRecord<String, AssetMessage>(CONSUMER_TOPIC, event)
        record.headers().add(TYPE, MessageType.CREATE_ASSET.name.toByteArray())
        kafkaTemplate.send(record)
        kafkaTemplate.flush()

        latch.await(3, TimeUnit.SECONDS)

        verify(exactly = 1) { assetProcessor.onMessage(any(), any()) }
    }
}