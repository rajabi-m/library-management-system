import org.example.model.Book;
import org.example.model.AssetStatus;
import org.example.model.BorrowableAsset;
import org.example.model.Library;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryTests {
    @Test
    public void addAsset_whenBookIsPresent_shouldAddAsset() {
        Library library = new Library();
        Book book = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954);

        library.addAsset(book);

        var assets = library.getAssetsByTitle("The Lord of the Rings");
        assertEquals(1, assets.size());
    }

    @Test
    public void removeAsset_whenBookIsPresent_shouldRemoveAsset() {
        Library library = new Library();
        Book book = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954);
        library.addAsset(book);

        library.removeAsset(book);

        var assets = library.getAssetsByTitle("The Lord of the Rings");
        assertEquals(1, assets.size());
    }

    @Test
    public void updateAssetStatus_whenBookIsPresent_shouldUpdateAssetStatus() {
        Library library = new Library();
        Book book = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954);
        library.addAsset(book);

        library.updateAssetStatus(book, AssetStatus.Borrowed);

        var assets = library.getAssetsByType("The Lord of the Rings");
        var borrowedAsset = (BorrowableAsset) assets.get(0);
        assertEquals(AssetStatus.Borrowed, borrowedAsset.getStatus());
    }

    @Test
    public void testGetBooksByTitle(){
        Library library = new Library();
        Book book1 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954);
        Book book2 = new Book("The Hobbit", "J.R.R. Tolkien", 1937);
        library.addAsset(book1);
        library.addAsset(book2);

        var assets = library.getAssetsByTitle("The Lord of the Rings");
        assertEquals("The Lord of the Rings", assets.get(0).getTitle());
        assertFalse(assets.contains(book2));
    }

    @Test
    public void testGetBooksByAuthor(){
        Library library = new Library();
        Book book1 = new Book("The Lord of the Rings", "J.R.R. Tolkien", 1954, AssetStatus.Exist);
        Book book2 = new Book("The Hobbit", "J.R.R. Tolkien 2", 1937, AssetStatus.Exist);
        library.addBook(book1);
        library.addBook(book2);

        var books = library.getBooksByAuthor("J.R.R. Tolkien");
        assertEquals("J.R.R. Tolkien", books.getHead().getAuthor());
        assertFalse(books.contains(book2));
    }
}
