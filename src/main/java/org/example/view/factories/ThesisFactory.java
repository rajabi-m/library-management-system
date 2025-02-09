package org.example.view.factories;

import org.example.model.Asset;
import org.example.model.Thesis;

import java.util.Scanner;

public class ThesisFactory implements AssetFactory {
    @Override
    public Asset createAsset(Scanner scanner) {
        System.out.print("Please enter the thesis name: ");
        String name = scanner.nextLine();
        System.out.print("Please enter the thesis author: ");
        String author = scanner.nextLine();
        System.out.print("Please enter the thesis supervisor: ");
        String supervisor = scanner.nextLine();
        System.out.print("Please enter the thesis department name: ");
        String department = scanner.nextLine();
        System.out.print("Please enter the thesis publishDate: ");
        String publishDate = scanner.nextLine();

        return new Thesis(name, author, supervisor, department, publishDate);
    }
}
