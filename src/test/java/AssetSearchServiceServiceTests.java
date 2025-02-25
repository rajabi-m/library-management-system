import org.example.model.Asset;
import org.example.model.AssetStatus;
import org.example.model.Book;
import org.example.model.BorrowableAsset;
import org.example.model.strategy.ContainsAllKeysStrategy;
import org.example.service.LibraryService;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AssetSearchServiceServiceTests {
    public LibraryService libraryService = createLibraryService();

    public static LibraryService createLibraryService() {
        try {
            Constructor<LibraryService> constructor = LibraryService.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            System.out.println(constructor);
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void addBook_basicTest() {
        Asset asset1 = new Book("asset1", "author1", 2021);

        assertEquals(0, libraryService.getAllAssets().size());
        libraryService.addAsset(asset1);
        assertEquals(1, libraryService.getAllAssets().size());
    }

    @Test
    public void addBook_whenAssetExists_shouldReturnError() {
        Asset asset1 = new Book("sameTitle", "sameAuthor", 2000);
        Asset asset2 = new Book("sameTitle", "sameAuthor", 2000);

        libraryService.addAsset(asset1);
        libraryService.addAsset(asset2);

        assertEquals(1, libraryService.getAllAssets().size());
    }

    @Test
    public void removeBook_basicTest() {
        Asset asset1 = new Book("asset1", "author1", 2021);

        libraryService.addAsset(asset1);
        assertEquals(1, libraryService.getAllAssets().size());

        libraryService.removeAssetById(asset1.getId());
        assertEquals(0, libraryService.getAllAssets().size());
    }

    @Test
    public void queryAssets_basicTest() {
        libraryService.setInvertedIndexSearchStrategy(new ContainsAllKeysStrategy<>());
        Asset asset1 = new Book("asset one is here", "author1", 2021);
        Asset asset2 = new Book("asset two is here", "author1", 2021);

        libraryService.addAsset(asset1);
        libraryService.addAsset(asset2);

        assertEquals(2, libraryService.queryAssets("is here").size());
        assertEquals(1, libraryService.queryAssets("one").size());
        assertEquals(0, libraryService.queryAssets("one two here").size());
        assertEquals(0, libraryService.queryAssets("three").size());
    }

    @Test
    public void borrowBook_whenAssetIsAvailable_shouldReturnSuccess() {
        BorrowableAsset asset1 = new Book("asset1", "author1", 2021);

        libraryService.addAsset(asset1);
        assertEquals(1, libraryService.getAllAssets().size());

        libraryService.borrowAssetById(asset1.getId(), LocalDate.now());
        assertEquals(AssetStatus.Borrowed, asset1.getStatus());
    }

    @Test
    public void returnBook_whenAssetIsBorrowed_shouldReturnSuccess() {
        BorrowableAsset asset1 = new Book("asset1", "author1", 2021);

        libraryService.addAsset(asset1);
        assertEquals(1, libraryService.getAllAssets().size());

        libraryService.borrowAssetById(asset1.getId(), LocalDate.now());
        assertEquals(AssetStatus.Borrowed, asset1.getStatus());

        libraryService.returnAssetById(asset1.getId());
        assertEquals(AssetStatus.Exist, asset1.getStatus());
    }
}
