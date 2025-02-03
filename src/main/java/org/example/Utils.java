package org.example;

import org.example.model.Book;
import org.example.model.BookStatus;
import org.example.model.Library;
import org.example.model.LinkedList;

import java.io.*;

public class Utils {
    public static <T> String convertLinkedListToHumanReadableString(LinkedList<T> list) {
        StringBuilder output = new StringBuilder();
        for (T item : list) {
            output.append(item).append("\n");
        }
        return output.toString();
    }

    public static void writeLibraryBooksToFile(Library library, String outputFile) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));

            for (Book book : library.getBooks()) {
                bufferedWriter.write(
                            book.getTitle() + "," +
                                book.getAuthor() + "," +
                                book.getReleaseYear() + "," +
                                book.getStatus()
                );
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void readBooksFromFileAndAddToLibrary(String booksFile, Library library) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(booksFile));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] parts = line.split(",");
                String title = parts[0];
                String author = parts[1];
                int releaseYear = Integer.parseInt(parts[2]);
                String status = parts[3];
                library.addBook(new Book(title, author, releaseYear, BookStatus.valueOf(status)));
            }
        } catch (FileNotFoundException e){
            System.out.println("File not found: " + booksFile + "\nCreating new file...");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
