package space.geek.ktassets.domain.asset

interface AssetRepositoryAdapter {

    fun findByKey(key: String): Asset?

    fun createAsset(command: AssetCreationCommand): Asset
}