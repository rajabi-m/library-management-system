package org.example.repository;

import org.example.model.Asset;

import java.util.List;

public interface AssetRepository {
    void addAsset(Asset asset);

    Asset getAssetById(String assetId);

    void removeAsset(String assetId);

    boolean containsAsset(String assetId);

    boolean containsAsset(Asset asset);

    List<Asset> getAllAssets();

    List<Asset> getAssetsByType(String assetType);

    List<Asset> getAssetsByTitle(String assetTitle);

    void updateAsset(Asset asset);
}
