package space.geek.ktassets.api.asset

import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import space.geek.ktassets.application.asset.AssetService
import space.geek.ktassets.domain.asset.Asset
import space.geek.ktassets.domain.asset.AssetCreationCommand

private val log = KotlinLogging.logger {}

@RestController
internal class AssetsController(
    private val assetService: AssetService
) {

    @PostMapping(
        path = ["/assets/upload"],
        consumes = [MediaType.MULTIPART_FORM_DATA_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun upload(@ModelAttribute request: AssetRequest): AssetResponse =
        AssetResponse.fromAsset(assetService.createAsset(request.toAssetCreationCommand()))
}

data class AssetRequest(
    val key: String,
    val file: MultipartFile
) {
    fun toAssetCreationCommand(): AssetCreationCommand =
        AssetCreationCommand(
            key = key,
            url = null,
        )
}

data class AssetResponse(
    val key: String,
    val url: String?
) {

    companion object {
        fun fromAsset(asset: Asset): AssetResponse =
            AssetResponse(
                key = asset.key,
                url = asset.url
            )
    }
}
