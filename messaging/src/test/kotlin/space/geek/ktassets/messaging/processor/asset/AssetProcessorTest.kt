package space.geek.ktassets.messaging.processor.asset

import io.mockk.clearMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import space.geek.ktassets.application.asset.AssetService
import space.geek.ktassets.messaging.Fixtures.ASSET
import space.geek.ktassets.messaging.Fixtures.ASSET_MESSAGE
import space.geek.ktassets.messaging.MessageType

internal class AssetProcessorTest {

    private val assetService: AssetService = mockk()
    private lateinit var assetProcessor: AssetProcessor

    @BeforeEach
    fun setup() {
        clearMocks(assetService)
        assetProcessor = AssetProcessor(assetService)
    }

    @Test
    fun `create asset`() {
        every { assetService.createAsset(any()) }.returns(ASSET)

        assetProcessor.onMessage(ASSET_MESSAGE, MessageType.CREATE_ASSET)

        verify(exactly = 1) { assetService.createAsset(ASSET_MESSAGE.toCreationCommand()) }
    }
}
