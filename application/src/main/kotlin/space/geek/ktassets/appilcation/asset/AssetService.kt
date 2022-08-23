package space.geek.ktassets.appilcation.asset

import org.springframework.stereotype.Service
import space.geek.ktassets.domain.asset.Asset
import space.geek.ktassets.domain.asset.AssetCreationCommand
import space.geek.ktassets.domain.asset.AssetRepositoryAdapter

interface AssetService {

    fun createAsset(command: AssetCreationCommand): Asset
}

@Service
internal class AssetServiceImpl(
    private val assetRepositoryAdapter: AssetRepositoryAdapter
) : AssetService {

    override fun createAsset(command: AssetCreationCommand): Asset =
        assetRepositoryAdapter.createAsset(command)
}
