package org.example;

import org.example.model.Book;
import org.example.model.AssetStatus;
import org.example.model.LinkedList;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BooksDatabase {
    private final String filePath;

    public BooksDatabase(String filePath) {
        this.filePath = filePath;
    }

    public void writeBooksToFile(Iterable<Book> books) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));

            for (Book book : books) {
                bufferedWriter.write(
                            book.getTitle() + "," +
                                book.getAuthor() + "," +
                                book.getReleaseYear() + "," +
                                book.getStatus() + "\n");
            }

            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public LinkedList<Book> readBooksFromFile() {
        LinkedList<Book> books = new LinkedList<>();
        try {
            FileReader fileReader = new FileReader(filePath);
            Scanner scanner = new Scanner(fileReader);

            while (scanner.hasNextLine()) {
                String[] bookData = scanner.nextLine().split(",");
                books.add(
                        new Book(bookData[0],
                                bookData[1],
                                Integer.parseInt(bookData[2]),
                                AssetStatus.valueOf(bookData[3])
                        )
                );
            }

            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

}
