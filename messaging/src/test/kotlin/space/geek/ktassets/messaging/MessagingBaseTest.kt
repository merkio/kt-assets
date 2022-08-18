package space.geek.ktassets.messaging

import com.ninjasquad.springmockk.MockkBean
import com.ninjasquad.springmockk.MockkClear
import com.ninjasquad.springmockk.clear
import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import space.geek.ktassets.messaging.Fixtures.CONSUMER_GROUP_ID
import space.geek.ktassets.messaging.Fixtures.CONSUMER_TOPIC
import space.geek.ktassets.messaging.processor.AssetProcessor

@SpringBootTest
@Testcontainers
internal class MessagingBaseTest {

    @MockkBean
    protected lateinit var assetProcessor: AssetProcessor

    @BeforeEach
    fun setup() {
        assetProcessor.clear(MockkClear.BEFORE)
    }

    companion object {

        @Container
        val container = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.0.5")).apply {
            withReuse(true)
            withEnv("KAFKA_AUTO_OFFSET_RESET", "earliest")
            withEnv("KAFKA_MAX_POLL_RECORDS", "1");
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            // Kafka properties
            registry.add("spring.kafka.consumer.bootstrap-servers", container::getBootstrapServers)
            registry.add("spring.kafka.producer.bootstrap-servers", container::getBootstrapServers)
            registry.add("spring.kafka.assets-group-id") { CONSUMER_GROUP_ID }
            registry.add("spring.kafka.assets-topic") { CONSUMER_TOPIC }

            registry.add("spring.kafka.consumer.key-deserializer") { "org.apache.kafka.common.serialization.StringDeserializer" }
            registry.add("spring.kafka.consumer.value-deserializer") { "org.springframework.kafka.support.serializer.JsonDeserializer" }
            registry.add("spring.kafka.producer.key-serializer") { "org.apache.kafka.common.serialization.StringSerializer" }
            registry.add("spring.kafka.producer.value-serializer") { "org.springframework.kafka.support.serializer.JsonSerializer" }
            registry.add("spring.kafka.properties.${JsonDeserializer.TRUSTED_PACKAGES}") { "*" }

            // Flyway properties
            registry.add("spring.flyway.default-schema") { "assets" }
        }
    }
}

@SpringBootApplication
internal class TestApplication