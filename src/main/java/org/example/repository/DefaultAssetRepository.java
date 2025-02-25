package org.example.repository;

import com.google.gson.Gson;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.exception.CannotCloseDatabaseConnectionException;
import org.example.exception.FailedSqlQueryException;
import org.example.model.Asset;
import org.example.serializer.GsonProvider;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefaultAssetRepository implements AssetRepository, Closeable {
    private Connection databaseConnection;
    private final Logger logger = LogManager.getLogger(DefaultAssetRepository.class.getSimpleName());
    private final Gson gson = GsonProvider.getGson();

    @Override
    public void close() throws CannotCloseDatabaseConnectionException {
        if (databaseConnection != null) {
            try {
                databaseConnection.close();
            } catch (SQLException e) {
                throw new CannotCloseDatabaseConnectionException();
            }
        }
    }

    private static final class InstanceHolder {
        private static final DefaultAssetRepository instance = new DefaultAssetRepository();
    }

    public static DefaultAssetRepository getInstance() {
        return InstanceHolder.instance;
    }

    public void connectToDatabase(String databaseUrl, String username, String password) throws SQLException {
        databaseConnection = DriverManager.getConnection(databaseUrl, username, password);
        logger.log(Level.INFO, "Connected to database: {}", databaseConnection.getCatalog());
    }

    @Override
    public void addAsset(Asset asset) {
        String query = "INSERT INTO asset (id, assetType, title, details) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = databaseConnection.prepareStatement(query)) {
            preparedStatement.setString(1, asset.getId());
            preparedStatement.setString(2, asset.getType());
            preparedStatement.setString(3, asset.getTitle());
            preparedStatement.setString(4, gson.toJson(asset));
            int effectedRows = preparedStatement.executeUpdate();
            if (effectedRows != 1) {
                logger.warn("Bad attempt to add asset: {} to the database. effectedRows: {}", asset, effectedRows);
            }
        } catch (SQLException e) {
            throw new FailedSqlQueryException(e.getMessage());
        }
    }

    @Override
    public Asset getAssetById(String assetId) {
        String query = "SELECT * FROM asset WHERE id = ?";
        try (PreparedStatement preparedStatement = databaseConnection.prepareStatement(query)) {
            preparedStatement.setString(1, assetId);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return gson.fromJson(resultSet.getString("details"), Asset.class);
            }
            if (resultSet.next()) {
                logger.warn("Multiple assets with the same id: {}", assetId);
            }
            return null;
        } catch (SQLException e) {
            throw new FailedSqlQueryException("Failed to get asset from the database");
        }
    }

    @Override
    public void removeAsset(String assetId) {
        String query = "DELETE FROM asset WHERE id = ?";
        try (PreparedStatement preparedStatement = databaseConnection.prepareStatement(query)) {
            preparedStatement.setString(1, assetId);
            int effectedRows = preparedStatement.executeUpdate();
            if (effectedRows != 1) {
                logger.warn("Bad attempt to remove asset with id: {} from the database. effectedRows: {}", assetId, effectedRows);
            }
        } catch (SQLException e) {
            throw new FailedSqlQueryException("Failed to remove asset from the database");
        }
    }

    @Override
    public boolean containsAsset(String assetId) {
        String query = "SELECT * FROM asset WHERE id = ?";
        try (PreparedStatement preparedStatement = databaseConnection.prepareStatement(query)) {
            preparedStatement.setString(1, assetId);
            var resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new FailedSqlQueryException("Failed to check if asset exists in the database");
        }
    }

    @Override
    public boolean containsAsset(Asset asset) {
        return getAllAssets().contains(asset); //TODO: temporary solution with bad performance
    }

    @Override
    public List<Asset> getAllAssets() {
        String query = "SELECT * FROM asset";
        try (PreparedStatement preparedStatement = databaseConnection.prepareStatement(query)) {
            var resultSet = preparedStatement.executeQuery();

            List<Asset> output = new ArrayList<>();
            while (resultSet.next()) {
                output.add(gson.fromJson(resultSet.getString("details"), Asset.class));
            }
            return output;
        } catch (SQLException e) {
            throw new FailedSqlQueryException(e.getMessage());
        }
    }

    @Override
    public List<Asset> getAssetsByType(String assetType) {
        String query = "SELECT * FROM asset WHERE assetType = ?";
        try (PreparedStatement preparedStatement = databaseConnection.prepareStatement(query)) {
            preparedStatement.setString(1, assetType);
            var resultSet = preparedStatement.executeQuery();

            List<Asset> output = new ArrayList<>();
            while (resultSet.next()) {
                output.add(gson.fromJson(resultSet.getString("details"), Asset.class));
            }
            return output;
        } catch (SQLException e) {
            throw new FailedSqlQueryException("Failed to get all assets from the database");
        }
    }

    @Override
    public List<Asset> getAssetsByTitle(String assetTitle) {
        String query = "SELECT * FROM asset WHERE title = ?";
        try (PreparedStatement preparedStatement = databaseConnection.prepareStatement(query)) {
            preparedStatement.setString(1, assetTitle);
            var resultSet = preparedStatement.executeQuery();

            List<Asset> output = new ArrayList<>();
            while (resultSet.next()) {
                output.add(gson.fromJson(resultSet.getString("details"), Asset.class));
            }
            return output;
        } catch (SQLException e) {
            throw new FailedSqlQueryException("Failed to get all assets from the database");
        }
    }

    @Override
    public void updateAsset(Asset asset) {
        String query = "UPDATE asset SET assetType = ?, title = ?, details = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = databaseConnection.prepareStatement(query)) {
            preparedStatement.setString(1, asset.getType());
            preparedStatement.setString(2, asset.getTitle());
            preparedStatement.setString(3, gson.toJson(asset));
            preparedStatement.setString(4, asset.getId());
            int effectedRows = preparedStatement.executeUpdate();
            if (effectedRows != 1) {
                logger.warn("Bad attempt to update asset: {} in the database. effectedRows: {}", asset, effectedRows);
            }
        } catch (SQLException e) {
            throw new FailedSqlQueryException(e.getMessage());
        }
    }
}
