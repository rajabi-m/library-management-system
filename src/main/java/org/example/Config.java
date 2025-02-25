package org.example;

public record Config(
        String outputFilePath,
        String assetsFilePath,
        boolean saveOutputToFile,
        SearchStrategy defaultSearchStrategy,
        SerializationFormat assetSerializationFormat,
        String logLevel,
        String databaseUrl,
        String databaseUser,
        String databasePassword
) {

    private static Config instance;

    public static Config getInstance() {
        if (instance == null) {
            throw new RuntimeException("Configs not initialized");
        }
        return instance;
    }

    public static void setInstance(Config instance) {
        Config.instance = instance;
    }

    public enum SearchStrategy {
        contains_at_least_one_word,
        contains_all_words
    }

    public enum SerializationFormat {
        JSON,
        ProtoBuf
    }
}
