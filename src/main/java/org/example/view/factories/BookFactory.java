package org.example.view.factories;

import org.example.model.Asset;
import org.example.model.Book;

import java.util.Scanner;

public class BookFactory implements AssetFactory {

    @Override
    public Asset createAsset(Scanner scanner) {
        System.out.print("Enter book title: ");
        var title = scanner.nextLine();
        System.out.print("Enter book author: ");
        var author = scanner.nextLine();
        System.out.print("Enter book release year: ");
        var year = scanner.nextInt();
        scanner.nextLine();
        return new Book(title, author, year);
    }
}
