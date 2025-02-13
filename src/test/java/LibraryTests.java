import org.example.model.Asset;
import org.example.model.Library;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LibraryTests {
    @Test
    public void addBook_basicTest(){
        Library library = new Library();
        Asset asset1 = new Asset("asset") {
            @Override
            public String display() {
                return "";
            }

        };

        assertEquals(0, library.getAllAssets().size());
        library.addAsset(asset1);
        assertEquals(1, library.getAllAssets().size());
    }
    @Test
    public void addBook_whenAssetExists_shouldReturnError(){
        Library library = new Library();
        Asset asset1 = new Asset("sameTitle") {
            @Override
            public String display() {
                return "";
            }
        };

        Asset asset2 = new Asset("sameTitle") {

            @Override
            public String display() {
                return "";
            }

        };
        library.addAsset(asset1);
        library.addAsset(asset2);

        assertEquals(1, library.getAllAssets().size());
    }

    @Test
    public void removeBook_basicTest(){
        Library library = new Library();
        Asset asset1 = new Asset("asset1") {
            @Override
            public String display() {
                return "";
            }
        };

        library.addAsset(asset1);
        assertEquals(1, library.getAllAssets().size());

        library.removeAssetById(asset1.getId());
        assertEquals(0, library.getAllAssets().size());
    }

    @Test
    public void queryAssets_basicTest(){
        Library library = new Library();
        Asset asset1 = new Asset("asset one is here") {
            @Override
            public String display() {
                return "";
            }
        };
        Asset asset2 = new Asset("asset two is here") {
            @Override
            public String display() {
                return "";
            }
        };

        library.addAsset(asset1);
        library.addAsset(asset2);

        assertEquals(2, library.queryAssets("is here").size());
        assertEquals(1, library.queryAssets("one").size());
        assertEquals(0, library.queryAssets("one two here").size());
        assertEquals(0, library.queryAssets("three").size());
    }
}
