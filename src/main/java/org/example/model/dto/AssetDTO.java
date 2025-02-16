package org.example.model.dto;

import org.example.model.Asset;
import org.example.model.AssetStatus;
import org.example.model.BorrowableAsset;

public record AssetDTO(String id, String type, String description, boolean borrowable) {
    public static AssetDTO of(Asset asset) {
        boolean borrowable = isAssetBorrowable(asset);
        return new AssetDTO(asset.getId(), asset.getType(), asset.toString(), borrowable);
    }

    private static boolean isAssetBorrowable(Asset asset) {
        if (!(asset instanceof BorrowableAsset borrowableAsset)) {
            return false;
        }
        return borrowableAsset.getStatus() == AssetStatus.Exist;
    }
}
