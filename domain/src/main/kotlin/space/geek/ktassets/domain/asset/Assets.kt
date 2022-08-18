package space.geek.ktassets.domain.asset

import java.time.OffsetDateTime

data class Asset(
    val id: Long,
    val key: String,
    val url: String?,
    val tags: Map<String, String>,
    val username: String?,
    val metadata: Map<String, String>,
    val createdAt: OffsetDateTime,
    val updatedAt: OffsetDateTime
)

data class AssetCreationCommand(
    val key: String,
    val url: String?,
    val tags: Map<String, String> = emptyMap(),
    val username: String? = null,
    val metadata: Map<String, String> = emptyMap(),
)

data class AssetUpdateCommand(
    val key: String,
    val url: String?,
    val tags: Map<String, String> = emptyMap(),
    val username: String,
    val metadata: Map<String, String> = emptyMap(),
)

data class AssetDeleteCommand(
    val key: String,
    val username: String
)

