package space.geek.ktassets.api.asset

import mu.KotlinLogging
import org.springframework.web.bind.annotation.RestController
import space.geek.ktassets.application.asset.AssetService

private val log = KotlinLogging.logger {}

@RestController
internal class AssetsController(
    private val assetService: AssetService
)
