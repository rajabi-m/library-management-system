package org.example.model.dto;

import org.example.model.Asset;
import org.example.model.AssetStatus;
import org.example.model.BorrowableAsset;

import java.util.function.Predicate;

public record AssetDTO(String id, String type, String description, boolean borrowable) {
    public static AssetDTO of(Asset asset) {
        boolean borrowable = isAssetBorrowable(asset);
        return new AssetDTO(asset.getId(), asset.getType(), asset.toString(), borrowable);
    }

    private static boolean isAssetBorrowable(Asset asset) {
        Predicate<Asset> isBorrowable = BorrowableAsset.class::isInstance;
        Predicate<BorrowableAsset> isExist = borrowableAsset -> borrowableAsset.getStatus() == AssetStatus.Exist;
        return isBorrowable.test(asset) && isExist.test((BorrowableAsset) asset);
    }
}
