package org.example.view.factories;

import org.example.model.Asset;
import org.example.model.Book;

import java.util.InputMismatchException;
import java.util.Scanner;

public class BookFactory implements AssetFactory {

    @Override
    public Asset createAsset(Scanner scanner) throws RuntimeException {
        System.out.print("Enter book title: ");
        var title = scanner.nextLine();
        System.out.print("Enter book author: ");
        var author = scanner.nextLine();
        System.out.print("Enter book release year: ");
        try {
            var year = scanner.nextInt();
            scanner.nextLine();
            return new Book(title, author, year);
        } catch (InputMismatchException e){
            scanner.nextLine(); // clear the buffer
            throw new RuntimeException("Invalid year format. Please enter a number.");
        }
    }
}
