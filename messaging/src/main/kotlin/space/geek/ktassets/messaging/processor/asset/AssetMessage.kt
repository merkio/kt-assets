package space.geek.ktassets.messaging.processor.asset

import space.geek.ktassets.domain.asset.AssetCreationCommand

data class AssetMessage(
    val key: String,
    val url: String?,
    val tags: Map<String, String> = emptyMap(),
    val metadata: Map<String, String> = emptyMap()
) {
    fun toCreationCommand(): AssetCreationCommand =
        AssetCreationCommand(
            key = key,
            url = url,
            tags = tags,
            metadata = metadata
        )
}
