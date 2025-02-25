package org.example.service;

import org.example.model.Asset;
import org.example.model.AssetStatus;
import org.example.model.BorrowableAsset;
import org.example.model.Library;
import org.example.model.dto.AssetDTO;
import org.example.model.observer.Subscribable;
import org.example.model.observer.Subscriber;
import org.example.model.strategy.ContainsAllKeysStrategy;
import org.example.model.strategy.InvertedIndexSearchStrategy;

import java.time.LocalDate;
import java.util.List;

public class LibraryService {
    private final Library library = new Library();
    private InvertedIndexSearchStrategy<String, String> invertedIndexSearchStrategy = new ContainsAllKeysStrategy<>();

    private static class InstanceHolder {
        private static final LibraryService instance = new LibraryService();
    }

    public static LibraryService getInstance() {
        return InstanceHolder.instance;
    }

    private LibraryService() {
    }

    public void setInvertedIndexSearchStrategy(InvertedIndexSearchStrategy<String, String> invertedIndexSearchStrategy) {
        this.invertedIndexSearchStrategy = invertedIndexSearchStrategy;
    }

    public void loadAssets(List<Asset> assets) {
        synchronized (library) {
            for (Asset asset : assets) {
                if (library.containsAsset(asset)) {
                    continue;
                }
                library.addAsset(asset);
            }
        }
    }

    public String addAsset(Asset asset) {
        synchronized (library) {
            if (library.containsAsset(asset)) {
                return "Asset already exists in the library!";
            }

            library.addAsset(asset);
            return "Asset added successfully!";
        }
    }

    public String removeAssetById(String assetId) {
        synchronized (library) {
            if (!library.containsAsset(assetId)) {
                return "Asset does not exist in the library!";
            }

            library.removeAsset(assetId);
            return "Asset removed successfully!";
        }
    }

    public List<AssetDTO> getAllAssets() {
        List<Asset> assets = library.getAllAssets();
        return assets.stream().map(AssetDTO::of).toList();
    }

    public List<AssetDTO> getAssetsByTitle(String title) {
        List<Asset> assets = library.getAllAssets();
        return assets.parallelStream()
                .filter(asset -> asset.getTitle().equals(title))
                .map(AssetDTO::of)
                .toList();
    }

    public List<AssetDTO> getAllBorrowableAssets() {
        List<Asset> assets = library.getAllAssets();
        return assets.parallelStream()
                .filter(asset -> asset instanceof BorrowableAsset)
                .map(AssetDTO::of)
                .toList();
    }

    public List<AssetDTO> getAssetsByType(String type) {
        List<Asset> assets = library.getAllAssets();
        return assets.parallelStream()
                .filter(asset -> asset.getType().equals(type))
                .map(AssetDTO::of)
                .toList();
    }

    public List<AssetDTO> queryAssets(String query) {
        List<Asset> assets = library.queryAssets(query, invertedIndexSearchStrategy);
        return assets.stream().map(AssetDTO::of).toList();
    }

    public String borrowAssetById(String assetId, LocalDate returnDate) {
        synchronized (library) {
            if (!library.containsAsset(assetId)) {
                return "Asset does not exist in the library!";
            }

            Asset asset = library.getAssetById(assetId);

            if (!(asset instanceof BorrowableAsset borrowableAsset) || borrowableAsset.getStatus() != AssetStatus.Exist) {
                return "This asset is not available for borrowing";
            }

            borrowableAsset.setStatus(AssetStatus.Borrowed);
            borrowableAsset.setReturnDate(returnDate);
            return "Asset successfully borrowed";
        }
    }

    public String returnAssetById(String assetId) {
        synchronized (library) {
            if (!library.containsAsset(assetId)) {
                return "Asset does not exist in the library!";
            }

            Asset asset = library.getAssetById(assetId);
            if (!(asset instanceof BorrowableAsset borrowableAsset) || borrowableAsset.getStatus() != AssetStatus.Borrowed) {
                return "This asset is not borrowed";
            }

            borrowableAsset.notifySubscribers(asset.getTitle() + " is now available for borrowing");

            borrowableAsset.setStatus(AssetStatus.Exist);
            borrowableAsset.setReturnDate(BorrowableAsset.defaultReturnDate);
            return "Asset successfully brought back";
        }
    }

    public String updateAssetById(String assetId, Asset updatedAsset) {
        synchronized (library) {
            if (!library.containsAsset(assetId)) {
                return "Asset does not exist in the library!";
            }

            Asset currentAsset = library.getAssetById(assetId);

            if (currentAsset.getClass() != updatedAsset.getClass()) {
                return "Asset types do not match";
            }

            if (currentAsset.equals(updatedAsset)) {
                return "No changes detected";
            }

            boolean titleChanged = !currentAsset.getTitle().equals(updatedAsset.getTitle());

            currentAsset.update(updatedAsset);

            if (titleChanged) {
                library.indexAssetTitle(currentAsset);
            }

            return "Asset updated successfully";
        }
    }

    public AssetDTO getAssetById(String assetId) {
        var asset = library.getAssetById(assetId);
        return AssetDTO.of(asset);
    }

    public String subscribeToAssetById(String assetId, Subscriber subscriber) {
        synchronized (library) {
            if (!library.containsAsset(assetId)) {
                return "Asset does not exist in the library!";
            }
            Asset asset = library.getAssetById(assetId);

            if (!(asset instanceof Subscribable subscribable)) {
                return "Asset is not subscribable!";
            }

            subscribable.subscribe(subscriber);
            return "Subscribed successfully!";
        }
    }

    public List<Asset> getAllAssetObjects() {
        return library.getAllAssets();
    }
}
