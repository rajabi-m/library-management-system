import org.example.model.Asset;
import org.example.model.Book;
import org.example.model.Library;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LibraryTests {
    @Test
    public void addBook_basicTest() {
        Library library = new Library();
        Asset asset1 = new Book("asset1", "author1", 2021);

        assertEquals(0, library.getAllAssets().size());
        library.addAsset(asset1);
        assertEquals(1, library.getAllAssets().size());
    }

    @Test
    public void addBook_whenAssetExists_shouldReturnError() {
        Library library = new Library();
        Asset asset1 = new Book("sameTitle", "sameAuthor", 2000);
        Asset asset2 = new Book("sameTitle", "sameAuthor", 2000);

        library.addAsset(asset1);
        library.addAsset(asset2);

        assertEquals(1, library.getAllAssets().size());
    }

    @Test
    public void removeBook_basicTest() {
        Library library = new Library();
        Asset asset1 = new Book("asset1", "author1", 2021);

        library.addAsset(asset1);
        assertEquals(1, library.getAllAssets().size());

        library.removeAssetById(asset1.getId());
        assertEquals(0, library.getAllAssets().size());
    }

    @Test
    public void queryAssets_basicTest() {
        Library library = new Library();
        Asset asset1 = new Book("asset one is here", "author1", 2021);
        Asset asset2 = new Book("asset two is here", "author1", 2021);

        library.addAsset(asset1);
        library.addAsset(asset2);

        assertEquals(2, library.queryAssets("is here").size());
        assertEquals(1, library.queryAssets("one").size());
        assertEquals(0, library.queryAssets("one two here").size());
        assertEquals(0, library.queryAssets("three").size());
    }
}
