package org.example.model;

import java.util.ArrayList;
import java.util.Comparator;

public class Library {
    private final LinkedList<Asset> assets;

    // Constructor
    public Library() {
        this.assets = new LinkedList<>();
    }

    public Library(LinkedList<Asset> assets) {
        this.assets = assets;
    }

    // Methods
    public String addAsset(Asset asset) {
        if (assets.contains(asset)) {
            return "This asset already exists in the library!";
        }

        this.assets.add(asset);
        return "The asset was successfully added to the library!";
    }

    public String removeAsset(Asset asset) {
        if (this.assets.remove(asset))
            return "The asset was successfully removed from the library!";

        return "The asset was not found in the library!";
    }

    public String updateAssetStatus(Asset asset, AssetStatus status) {
        asset.setStatus(status);
        return "The asset status was successfully updated!";
    }

    public ArrayList<Asset> getAssetsByTitle(String title){
        var output = new ArrayList<Asset>();
        for (Asset asset : this.assets) {
            if (asset.getTitle().equals(title)) {
                output.add(asset);
            }
        }
        return output;
    }

    public LinkedList<Asset> getAssets() {
        return assets;
    }

    //    public ArrayList<Asset> getBooksByAuthor(String author){
//        var output = new ArrayList<Asset>();
//        for (Asset asset : this.assets) {
//            if (asset.getAuthor().equals(author)) {
//                output.add(asset);
//            }
//        }
//        return output;
//    }

//    // Every book is unique by title and author & release year
//    public Book findBook(String title, String author, int releaseYear){
//        for (Book book : this.assets) {
//            if (book.getTitle().equals(title) && book.getAuthor().equals(author) && book.getReleaseYear() == releaseYear) {
//                return book;
//            }
//        }
//        return null;
//    }

//    public void sortBooksByReleaseYear() {
//        this.assets.sort(Comparator.comparingInt(Book::getReleaseYear));
//    }

}
