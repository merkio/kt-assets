package space.geek.ktassets.api.asset

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import space.geek.ktassets.application.asset.AssetService
import space.geek.ktassets.domain.asset.Asset
import java.time.OffsetDateTime

@WebMvcTest(controllers = [AssetsController::class])
internal class AssetsControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockkBean
    private lateinit var assetService: AssetService

    @Test
    fun `upload new asset`() {
        every { assetService.createAsset(any()) }.returns(
            Asset(
                id = 1,
                key = "test",
                url = null,
                tags = emptyMap(),
                username = null,
                metadata = emptyMap(),
                createdAt = OffsetDateTime.now(),
                updatedAt = OffsetDateTime.now()
            )
        )
        val file = MockMultipartFile(
            "file",
            "Test.txt",
            "text/plain",
            "file content".encodeToByteArray()
        )

        mvc.perform(
            multipart("/assets/upload")
                .file(file)
                .param("key", "test")
        )
            .andExpect(status().isOk)
    }
}
