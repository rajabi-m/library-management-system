package org.example.manager;

import org.example.model.*;

public class MagazineManager {
    private final Library library;

    public MagazineManager(Library library) {
        this.library = library;
    }

    public String addMagazine(String title, String publisher, String releaseDate) {
        var magazine = new Magazine(title, publisher, releaseDate, AssetStatus.Exist);
        if (library.getAssets().contains(magazine)) {
            return "Magazine already exists in the library!";
        }
        library.addAsset(magazine);
        return "Magazine added successfully!";
    }

    public String removeMagazine(String title, String publisher, String releaseDate) {
        var magazine = findMagazine(title, publisher, releaseDate);
        if (magazine == null) {
            return "Magazine does not exist in the library!";
        }
        library.removeAsset(magazine);
        return "Magazine removed successfully!";
    }

    private Magazine findMagazine(String title, String publisher, String releaseDate) {
        for (var asset : library.getAssets()) {
            if (!(asset instanceof Magazine magazine)) {
                continue;
            }

            if (magazine.getTitle().equals(title) && magazine.getPublisher().equals(publisher) && magazine.getReleaseDate().equals(releaseDate)) {
                return magazine;
            }
        }
        return null;
    }

    public String updateMagazineStatus(String title, String publisher, String releaseDate, AssetStatus status) {
        var magazine = findMagazine(title, publisher, releaseDate);
        if (magazine == null) {
            return "Magazine does not exist in the library!";
        }
        library.updateAssetStatus(magazine, status);
        return "The magazine status was successfully updated!";
    }

    public LinkedList<Magazine> getAllMagazines() {
        var output = new LinkedList<Magazine>();
        for (var asset : library.getAssets()) {
            if (asset instanceof Magazine magazine) {
                output.add(magazine);
            }
        }
        return output;
    }
}
