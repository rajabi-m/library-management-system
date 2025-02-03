package org.example;

import org.example.model.Book;
import org.example.model.BookStatus;
import org.example.model.Library;
import org.example.model.LinkedList;

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
        Book bookToUpdate = library.getBooksByTitle("1984").getHead();
        library.updateBookStatus(bookToUpdate, BookStatus.Borrowed);
        Book bookToRemove = library.getBooksByTitle("The Great Gatsby").getHead();
        library.removeBook(bookToRemove);
        library.printBooks();


        // Testing linked list
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.remove(2);

        for (Integer integer : linkedList) {
            System.out.println("int:"+ integer);
        }

    }
}