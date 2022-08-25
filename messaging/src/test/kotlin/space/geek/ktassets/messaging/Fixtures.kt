package space.geek.ktassets.messaging

import space.geek.ktassets.domain.asset.Asset
import space.geek.ktassets.messaging.processor.asset.AssetMessage
import java.time.OffsetDateTime

object Fixtures {

    val CONSUMER_GROUP_ID = "assets-group-id"
    val CONSUMER_TOPIC = "assets"

    val ASSET_MESSAGE = AssetMessage(
        key = "key",
        url = "http://localhost"
    )
    val ASSET = Asset(
        id = 1,
        key = "key",
        url = "http://localhost",
        username = "user",
        createdAt = OffsetDateTime.now(),
        updatedAt = OffsetDateTime.now(),
        tags = emptyMap(),
        metadata = emptyMap()
    )
}
