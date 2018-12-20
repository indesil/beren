package pl.jlabs.beren.compilator.configuration;

import org.yaml.snakeyaml.Yaml;
import pl.jlabs.beren.compilator.utils.OperationExtractorExtractor;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;

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
            if(inputStream != null) {
                return setupArgs(yaml.loadAs(inputStream, BerenConfig.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static BerenConfig setupArgs(BerenConfig berenConfig) {
        for (Map.Entry<String, OperationConfig> entry : berenConfig.getOperationsMappings().entrySet()) {
            entry.getValue().setArgs(listOfArgs(entry.getKey(), entry.getValue().getOperationCall()));
        }
        return berenConfig;
    }

    private static List<String> listOfArgs(String key, String operationCall) {
        List<String> keyParams = OperationExtractorExtractor.getParams(key);
        List<String> operationParams = OperationExtractorExtractor.getParams(operationCall);
        validateParams(key, operationCall, keyParams, operationParams);
        return operationParams;
    }

    private static void validateParams(String key, String operationCall, List<String> keyParams, List<String> operationParams) {
        if(!operationParams.containsAll(keyParams)) {
            throw new IllegalArgumentException(format("Invalid params mapping for key: %s and operation call: %s", key, operationCall));
        }

        if(!operationParams.contains("this")) {
            throw new IllegalArgumentException(format("Operation call: %s is not referring to validated parameter(this)!", operationCall));
        }

        int expectedNumberOfParams = operationParams.size() - 1;
        if(expectedNumberOfParams != keyParams.size()) {
            throw new IllegalArgumentException(format("Missing parameters between key: %s and %s operation call!", key, operationCall));
        }
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
