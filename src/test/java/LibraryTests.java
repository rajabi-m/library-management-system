import org.example.model.Book;
import org.example.model.BookStatus;
import org.example.model.Library;
import org.junit.Test;

import static org.junit.Assert.*;

public class LibraryTests {
    @Test
    public void testAddBook(){
        Library library = new Library();
        Book book = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, BookStatus.Exist);

        library.addBook(book);

        var books = library.getBooksByTitle("The Lord of the Rings");
        assertNotNull(books.getHead());
    }

    @Test
    public void testRemoveBook(){
        Library library = new Library();
        Book book = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, BookStatus.Exist);
        library.addBook(book);

        library.removeBook(book);

        var books = library.getBooksByTitle("The Lord of the Rings");
        assertNull(books.getHead());
    }

    @Test
    public void testUpdateBookStatus(){
        Library library = new Library();
        Book book = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, BookStatus.Exist);
        library.addBook(book);

        library.updateBookStatus(book, BookStatus.Borrowed);

        var books = library.getBooksByTitle("The Lord of the Rings");
        assertEquals(BookStatus.Borrowed, books.getHead().getStatus());
    }

    @Test
    public void testGetBooksByTitle(){
        Library library = new Library();
        Book book1 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, BookStatus.Exist);
        Book book2 = new Book("The Hobbit", "J.R.R. Tolkien", 1937, BookStatus.Exist);
        library.addBook(book1);
        library.addBook(book2);

        var books = library.getBooksByTitle("The Lord of the Rings");
        assertEquals("The Lord of the Rings", books.getHead().getTitle());
        assertFalse(books.contains(book2));
    }

    @Test
    public void testGetBooksByAuthor(){
        Library library = new Library();
        Book book1 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, BookStatus.Exist);
        Book book2 = new Book("The Hobbit", "J.R.R. Tolkien 2", 1937, BookStatus.Exist);
        library.addBook(book1);
        library.addBook(book2);

        var books = library.getBooksByAuthor("J.R.R. Tolkien");
        assertEquals("J.R.R. Tolkien", books.getHead().getAuthor());
        assertFalse(books.contains(book2));
    }

    @Test
    public void testSortBooksByReleaseYear(){
        Library library = new Library();
        Book book1 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, BookStatus.Exist);
        Book book2 = new Book("The Hobbit", "J.R.R. Tolkien", 1937, BookStatus.Exist);
        library.addBook(book1);
        library.addBook(book2);

        library.sortBooksByReleaseYear();

        var books = library.getBooks();
        assertEquals("The Hobbit", books.get(0).getTitle());
        assertEquals("The Lord of the Rings", books.get(1).getTitle());
    }

}
