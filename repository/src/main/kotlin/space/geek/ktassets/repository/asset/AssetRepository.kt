package space.geek.ktassets.repository.asset

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component
import space.geek.ktassets.domain.asset.Asset
import space.geek.ktassets.domain.asset.AssetCreationCommand
import space.geek.ktassets.domain.asset.AssetRepositoryAdapter

interface AssetRepository : JpaRepository<AssetEntity, String> {

    fun findByKey(key: String): AssetEntity?
}

@Component
internal class AssetRepositoryAdapterImpl(
    private val repository: AssetRepository
) : AssetRepositoryAdapter {

    override fun findByKey(key: String): Asset? =
        repository.findByKey(key)?.to()

    override fun createAsset(command: AssetCreationCommand): Asset =
        repository.save(AssetEntity.from(command)).to()
}
