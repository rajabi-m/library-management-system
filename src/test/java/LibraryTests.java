import org.example.model.Book;
import org.example.model.AssetStatus;
import org.example.model.Library;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTests {
//    @Test
//    public void testAddBook(){
//        Library library = new Library();
//        Book book = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, AssetStatus.Exist);
//
//        library.addBook(book);
//
//        var books = library.getBooksByTitle("The Lord of the Rings");
//        assertNotNull(books.getHead());
//    }
//
//    @Test
//    public void testRemoveBook(){
//        Library library = new Library();
//        Book book = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, AssetStatus.Exist);
//        library.addBook(book);
//
//        library.removeBook("The Lord of the Rings", "J.R.R. Tolkien", 1954);
//
//        var books = library.getBooksByTitle("The Lord of the Rings");
//        assertNull(books.getHead());
//    }
//
//    @Test
//    public void testUpdateBookStatus(){
//        Library library = new Library();
//        Book book = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, AssetStatus.Exist);
//        library.addBook(book);
//
//        library.updateBookStatus("The Lord of the Rings", "J.R.R. Tolkien", 1954, AssetStatus.Borrowed);
//
//        var books = library.getBooksByTitle("The Lord of the Rings");
//        assertEquals(AssetStatus.Borrowed, books.getHead().getStatus());
//    }
//
//    @Test
//    public void testGetBooksByTitle(){
//        Library library = new Library();
//        Book book1 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, AssetStatus.Exist);
//        Book book2 = new Book("The Hobbit", "J.R.R. Tolkien", 1937, AssetStatus.Exist);
//        library.addBook(book1);
//        library.addBook(book2);
//
//        var books = library.getBooksByTitle("The Lord of the Rings");
//        assertEquals("The Lord of the Rings", books.getHead().getTitle());
//        assertFalse(books.contains(book2));
//    }
//
//    @Test
//    public void testGetBooksByAuthor(){
//        Library library = new Library();
//        Book book1 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, AssetStatus.Exist);
//        Book book2 = new Book("The Hobbit", "J.R.R. Tolkien 2", 1937, AssetStatus.Exist);
//        library.addBook(book1);
//        library.addBook(book2);
//
//        var books = library.getBooksByAuthor("J.R.R. Tolkien");
//        assertEquals("J.R.R. Tolkien", books.getHead().getAuthor());
//        assertFalse(books.contains(book2));
//    }
//
//    @Test
//    public void testSortBooksByReleaseYear(){
//        Library library = new Library();
//        Book book1 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, AssetStatus.Exist);
//        Book book2 = new Book("The Hobbit", "J.R.R. Tolkien", 1937, AssetStatus.Exist);
//        library.addBook(book1);
//        library.addBook(book2);
//
//        library.sortBooksByReleaseYear();
//
//        var books = library.getAssets();
//        assertEquals("The Hobbit", books.get(0).getTitle());
//        assertEquals("The Lord of the Rings", books.get(1).getTitle());
//    }
//
//    @Test
//    public void testLibraryOperations() {
//        Library library = new Library();
//        Book book1 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, AssetStatus.Exist);
//        Book book2 = new Book("The Hobbit", "J.R.R. Tolkien", 1937, AssetStatus.Exist);
//        Book book3 = new Book("1984", "George Orwell", 1949, AssetStatus.Exist);
//
//        // Test addBook
//        assertEquals("The book was successfully added to the library!", library.addBook(book1));
//        assertEquals("The book was successfully added to the library!", library.addBook(book2));
//        assertEquals("The book was successfully added to the library!", library.addBook(book3));
//        assertEquals("This book already exists in the library!", library.addBook(book1));
//
//        // Test getBooksByTitle
//        var booksByTitle = library.getBooksByTitle("The Lord of the Rings");
//        assertEquals(1, booksByTitle.size());
//        assertEquals("The Lord of the Rings", booksByTitle.getHead().getTitle());
//
//        // Test getBooksByAuthor
//        var booksByAuthor = library.getBooksByAuthor("J.R.R. Tolkien");
//        assertEquals(2, booksByAuthor.size());
//        assertTrue(booksByAuthor.contains(book1));
//        assertTrue(booksByAuthor.contains(book2));
//
//        // Test updateBookStatus
//        assertEquals("The book status was successfully updated!", library.updateBookStatus("The Lord of the Rings", "J.R.R. Tolkien", 1954, AssetStatus.Borrowed));
//        assertEquals(AssetStatus.Borrowed, library.getBooksByTitle("The Lord of the Rings").getHead().getStatus());
//
//        // Test removeBook
//        assertEquals("The book was successfully removed from the library!", library.removeBook("1984", "George Orwell", 1949));
//        assertNull(library.getBooksByTitle("1984").getHead());
//
//        // Test sortBooksByReleaseYear
//        library.sortBooksByReleaseYear();
//        var sortedBooks = library.getAssets();
//        assertEquals("The Hobbit", sortedBooks.get(0).getTitle());
//        assertEquals("The Lord of the Rings", sortedBooks.get(1).getTitle());
//    }



}
