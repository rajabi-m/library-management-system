package org.example.view.factories;

import org.example.model.Asset;

import java.util.Scanner;

public interface AssetFactory {
    Asset createAsset(Scanner scanner) throws RuntimeException;
}
