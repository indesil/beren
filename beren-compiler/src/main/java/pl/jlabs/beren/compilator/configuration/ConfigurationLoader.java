package pl.jlabs.beren.compilator.configuration;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;

public class ConfigurationLoader {
    private static final String DEFAULT_CONFIG = "beren-default-configuration.yaml";
    private static final String CUSTOM_CONFIG = "beren-configuration.yaml";

    public static BerenConfig loadConfigurations() {
        Yaml yaml = new Yaml();
        BerenConfig defaultConfig = loadConfig(yaml, DEFAULT_CONFIG);
        BerenConfig customConfig = loadConfig(yaml, CUSTOM_CONFIG);

        return mergeConfigs(defaultConfig, customConfig);
    }

    private static BerenConfig loadConfig(Yaml yaml, String fileName) {
        try (InputStream inputStream = ConfigurationLoader.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {
            return inputStream != null ? yaml.loadAs(inputStream, BerenConfig.class) : null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static BerenConfig mergeConfigs(BerenConfig defaultConfig, BerenConfig customConfig) {
        if(customConfig == null) {
            return defaultConfig;
        }

        BerenConfig berenConfig = new BerenConfig();

        berenConfig.getOperationsMappings().putAll(defaultConfig.getOperationsMappings());
        berenConfig.getOperationsMappings().putAll(customConfig.getOperationsMappings());

        return berenConfig;
    }
}
