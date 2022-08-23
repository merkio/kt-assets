package space.geek.ktassets.api.asset

import org.springframework.web.bind.annotation.RestController
import space.geek.ktassets.appilcation.asset.AssetService

import mu.KotlinLogging

private val log = KotlinLogging.logger {}

@RestController
internal class AssetsController(
    private val assetService: AssetService
)