package org.example.service;

import org.example.model.Asset;
import org.example.model.AssetStatus;
import org.example.model.BorrowableAsset;
import org.example.model.dto.AssetDTO;
import org.example.model.observer.Subscribable;
import org.example.model.observer.Subscriber;
import org.example.model.strategy.ContainsAllKeysStrategy;
import org.example.model.strategy.InvertedIndexSearchStrategy;
import org.example.repository.AssetRepository;
import org.example.repository.DefaultAssetRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private final AssetSearchService assetSearchService = new AssetSearchService();
    private final AssetRepository assetRepository = DefaultAssetRepository.getInstance();
    private InvertedIndexSearchStrategy<String, String> invertedIndexSearchStrategy = new ContainsAllKeysStrategy<>();

    private static class InstanceHolder {
        private static final LibraryService instance = new LibraryService();
    }

    public static LibraryService getInstance() {
        return InstanceHolder.instance;
    }

    private LibraryService() {
        indexAssets();
    }

    public void setInvertedIndexSearchStrategy(InvertedIndexSearchStrategy<String, String> invertedIndexSearchStrategy) {
        this.invertedIndexSearchStrategy = invertedIndexSearchStrategy;
    }

    private void indexAssets() {
        List<Asset> allAssets = assetRepository.getAllAssets();
        allAssets.forEach(assetSearchService::indexAssetTitle);
    }

    public String addAsset(Asset asset) {
        if (assetRepository.containsAsset(asset)) {
            return "Asset already exists in the library!";
        }

        assetRepository.addAsset(asset);
        return "Asset added successfully!";
    }

    public String removeAssetById(String assetId) {
        if (!assetRepository.containsAsset(assetId)) {
            return "Asset does not exist in the library!";
        }

        assetRepository.removeAsset(assetId);
        return "Asset removed successfully!";
    }

    public List<AssetDTO> getAllAssets() {
        List<Asset> assets = assetRepository.getAllAssets();
        return assets.stream().map(AssetDTO::of).toList();
    }

    public List<AssetDTO> getAssetsByTitle(String title) {
        List<Asset> assets = assetRepository.getAssetsByTitle(title);
        return assets.parallelStream()
                .map(AssetDTO::of)
                .toList();
    }

    public List<AssetDTO> getAllBorrowableAssets() {
        List<Asset> assets = assetRepository.getAllAssets();
        return assets.parallelStream()
                .filter(asset -> asset instanceof BorrowableAsset)
                .map(AssetDTO::of)
                .toList();
    }

    public List<AssetDTO> getAssetsByType(String type) {
        List<Asset> assets = assetRepository.getAssetsByType(type);
        return assets.parallelStream()
                .map(AssetDTO::of)
                .toList();
    }

    public List<AssetDTO> queryAssets(String query) {
        List<String> assetIDs = assetSearchService.queryAssets(query, invertedIndexSearchStrategy);
        List<AssetDTO> output = new ArrayList<>();
        for (String assetID : assetIDs) {
            Asset asset = assetRepository.getAssetById(assetID);
            if (asset == null) {
                continue;
            }
            output.add(AssetDTO.of(asset));
        }
        return output;
    }

    public String borrowAssetById(String assetId, LocalDate returnDate) {
        if (!assetRepository.containsAsset(assetId)) {
            return "Asset does not exist in the library!";
        }

        Asset asset = assetRepository.getAssetById(assetId);

        if (!(asset instanceof BorrowableAsset borrowableAsset) || borrowableAsset.getStatus() != AssetStatus.Exist) {
            return "This asset is not available for borrowing";
        }

        borrowableAsset.setStatus(AssetStatus.Borrowed);
        borrowableAsset.setReturnDate(returnDate);
        assetRepository.updateAsset(borrowableAsset);
        return "Asset successfully borrowed";
    }

    public String returnAssetById(String assetId) {
        if (!assetRepository.containsAsset(assetId)) {
            return "Asset does not exist in the library!";
        }

        Asset asset = assetRepository.getAssetById(assetId);
        if (!(asset instanceof BorrowableAsset borrowableAsset) || borrowableAsset.getStatus() != AssetStatus.Borrowed) {
            return "This asset is not borrowed";
        }

        borrowableAsset.notifySubscribers(asset.getTitle() + " is now available for borrowing");

        borrowableAsset.setStatus(AssetStatus.Exist);
        borrowableAsset.setReturnDate(BorrowableAsset.defaultReturnDate);
        assetRepository.updateAsset(borrowableAsset);
        return "Asset successfully brought back";
    }

    public String updateAssetById(String assetId, Asset updatedAsset) {
        if (!assetRepository.containsAsset(assetId)) {
            return "Asset does not exist in the library!";
        }

        Asset currentAsset = assetRepository.getAssetById(assetId);

        if (currentAsset.getClass() != updatedAsset.getClass()) {
            return "Asset types do not match";
        }

        if (currentAsset.equals(updatedAsset)) {
            return "No changes detected";
        }

        boolean titleChanged = !currentAsset.getTitle().equals(updatedAsset.getTitle());

        currentAsset.update(updatedAsset);

        if (titleChanged) {
            assetSearchService.indexAssetTitle(currentAsset);
        }

        assetRepository.updateAsset(currentAsset);
        return "Asset updated successfully";
    }

    public AssetDTO getAssetById(String assetId) {
        var asset = assetRepository.getAssetById(assetId);
        return AssetDTO.of(asset);
    }

    public String subscribeToAssetById(String assetId, Subscriber subscriber) {
        if (!assetRepository.containsAsset(assetId)) {
            return "Asset does not exist in the library!";
        }
        Asset asset = assetRepository.getAssetById(assetId);

        if (!(asset instanceof Subscribable subscribable)) {
            return "Asset is not subscribable!";
        }

        subscribable.subscribe(subscriber);
        assetRepository.updateAsset(asset);
        return "Subscribed successfully!";
    }

    public List<Asset> getAllAssetObjects() {
        return assetRepository.getAllAssets();
    }
}
