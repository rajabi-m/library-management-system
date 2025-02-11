package org.example.view.factories;

import org.example.model.Asset;

import java.util.Scanner;

public interface AssetFactory {
    public Asset createAsset(Scanner scanner) throws RuntimeException;
}
