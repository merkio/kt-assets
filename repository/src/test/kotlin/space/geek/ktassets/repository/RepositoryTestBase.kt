package space.geek.ktassets.repository

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import space.geek.ktassets.repository.asset.AssetRepository

@SpringBootTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
internal class RepositoryTestBase {

    @Autowired
    private lateinit var assetRepository: AssetRepository

    @BeforeEach
    fun setup() {
        assetRepository.deleteAll()
    }

    @AfterEach
    fun tearDown() {
    }

    companion object {

        @Container
        val container = PostgreSQLContainer("postgres:14").apply {
            withReuse(true)
            withDatabaseName("testdb")
            withUsername("test")
            withPassword("test")
        }

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            // R2DBC repositories
            registry.add("spring.datasource.url", container::getJdbcUrl)
            registry.add("spring.datasource.username", container::getUsername)
            registry.add("spring.datasource.password", container::getPassword)

            // Flyway properties
            registry.add("spring.flyway.default-schema") { "assets" }
        }
    }
}

@EnableJpaAuditing
@SpringBootApplication
internal class TestApplication
