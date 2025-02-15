package org.example.model.dto;

import org.example.model.Asset;

public record AssetDTO(String id, String type, String description){
    public static AssetDTO of (Asset asset){
        return new AssetDTO(asset.getId(), asset.getType(), asset.toString());
    }
}
