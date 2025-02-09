package org.example.view.factories;

import org.example.model.Asset;
import org.example.model.Magazine;

import java.util.Scanner;

public class MagazineFactory implements AssetFactory{
    @Override
    public Asset createAsset(Scanner scanner) {
        System.out.print("Please enter the magazine title: ");
        String magazineTitle = scanner.nextLine();
        System.out.print("Please enter the magazine publisher: ");
        String magazinePublisher = scanner.nextLine();
        System.out.print("Please enter the release Date: ");
        String releaseDate = scanner.nextLine();

        return new Magazine(magazineTitle, magazinePublisher, releaseDate);
    }
}
