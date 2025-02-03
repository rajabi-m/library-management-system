package org.example;

import org.example.models.Book;
import org.example.models.BookStatus;
import org.example.models.Library;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Book book1 = new Book("The Great Gatsby", "F. Scott Fitzgerald", 1925, BookStatus.Exist);
        Book book2 = new Book("To Kill a Mockingbird", "Harper Lee", 1960, BookStatus.Exist);
        Book book3 = new Book("1984", "George Orwell", 1949, BookStatus.Exist);
        Book book4 = new Book("Animal Farm", "George Orwell", 1945, BookStatus.Exist);
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.sortBooksByReleaseYear();
        Book bookToUpdate = library.getBooksByTitle("1984").get(0);
        library.updateBookStatus(bookToUpdate, BookStatus.Borrowed);
        library.printBooks();
    }
}