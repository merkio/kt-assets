package space.geek.ktassets.repository.asset

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.test.context.support.WithAnonymousUser
import space.geek.ktassets.domain.asset.AssetCreationCommand
import space.geek.ktassets.domain.asset.AssetRepositoryAdapter
import space.geek.ktassets.repository.RepositoryTestBase
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

internal class AssetRepositoryTest : RepositoryTestBase() {

    @Autowired
    private lateinit var repository: AssetRepositoryAdapter

    @Test
    @WithAnonymousUser
    fun `create asset`() {
        val command = AssetCreationCommand(
            key = "key",
            url = "http://localhost/1"
        )

        val asset = repository.createAsset(command)

        assertEquals(command.key, asset.key)
        assertEquals(emptyMap(), asset.tags)
        assertEquals(emptyMap(), asset.metadata)
        assertNotNull(asset.username)
        assertNotNull(asset.createdAt)
        assertNotNull(asset.updatedAt)
    }
}