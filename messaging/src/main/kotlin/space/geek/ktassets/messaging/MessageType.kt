package space.geek.ktassets.messaging

const val TYPE = "TYPE"

enum class MessageType {
    CREATE_ASSET,
    UPDATE_ASSET,
    DELETE_ASSET;

    companion object {
        fun byValue(value: String?): MessageType? = value?.let { valueOf(it.uppercase()) }
    }
}