import org.example.model.data_structure.InvertedIndexMap;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvertedIndexMapTests {
    @Test
    public void query_whenEverythingIsFine_shouldWorkCorrectly() {
        InvertedIndexMap<String, String> invertedIndexMap = new InvertedIndexMap<>();
        invertedIndexMap.add("key1", "value1");
        invertedIndexMap.add("key2", "value2");
        invertedIndexMap.add("key2", "value1");
        invertedIndexMap.add("key3", "value3");
        invertedIndexMap.add("key1", "value4");
        invertedIndexMap.add("key2", "value4");
        var keysToTest = List.of("key1", "key2");
        var expectedResult = List.of("value1", "value4");

        var result = invertedIndexMap.query(keysToTest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void query_whenThereAreNoIntersection_shouldReturnEmptyList() {
        InvertedIndexMap<String, String> invertedIndexMap = new InvertedIndexMap<>();
        invertedIndexMap.add("key1", "value1");
        invertedIndexMap.add("key2", "value2");
        invertedIndexMap.add("key2", "value3");
        invertedIndexMap.add("key3", "value3");
        var keysToTest = List.of("key1", "key2");
        var expectedResult = List.of();

        var result = invertedIndexMap.query(keysToTest);

        assertEquals(expectedResult, result);
    }

    @Test
    public void query_whenKeysDoesNotBelongToAnyValue_shouldReturnEmptyList() {
        InvertedIndexMap<String, String> invertedIndexMap = new InvertedIndexMap<>();
        invertedIndexMap.add("key1", "value1");
        invertedIndexMap.add("key2", "value2");
        invertedIndexMap.add("key3", "value3");
        var keysToTest = List.of("key4", "key5");
        var expectedResult = List.of();

        var result = invertedIndexMap.query(keysToTest);

        assertEquals(expectedResult, result);
    }
}
