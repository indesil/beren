package io.github.indesil.beren.compilator.configuration;

import io.github.indesil.beren.compilator.parser.PlaceHolders;
import org.yaml.snakeyaml.Yaml;
import io.github.indesil.beren.compilator.utils.OperationUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isEmpty;
import static io.github.indesil.beren.compilator.utils.OperationUtils.strapFromParams;

public class ConfigurationLoader {
    private static final String OPERATIONS_CONFIG = "beren-operations-configuration.yaml";
    private static final String CUSTOM_CONFIG = "beren-configuration.yaml";

    public static void main(String[] args) {
        BerenConfig berenConfig = loadConfigurations();
        System.out.println("");
    }

    public static BerenConfig loadConfigurations() {
        Yaml yaml = new Yaml();
        BerenConfig defaultConfig = loadConfig(yaml, OPERATIONS_CONFIG);
        BerenConfig customConfig = loadConfig(yaml, CUSTOM_CONFIG);

        return mergeConfigs(defaultConfig, customConfig);
    }

    private static BerenConfig loadConfig(Yaml yaml, String fileName) {
        try (InputStream inputStream = ConfigurationLoader.class
                .getClassLoader()
                .getResourceAsStream(fileName)) {
            if(inputStream != null) {
                return createConfiguration(yaml.loadAs(inputStream, RawConfiguration.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static BerenConfig createConfiguration(RawConfiguration rawConfiguration) {
        Map<String, OperationConfig> mappings = new HashMap<>(rawConfiguration.getOperationsMappings().size());

        for (Map.Entry<String, OperationConfig> rawConf : rawConfiguration.getOperationsMappings().entrySet()) {
            validateConfig(rawConf);
            String key = strapFromParams(rawConf.getKey());
            tryPutNewConfiguration(mappings, key, formatOperationConfig(rawConf));
        }
        return new BerenConfig().withOperationsMappings(mappings);
    }

    private static void validateConfig(Map.Entry<String, OperationConfig> operationConfig) {
        OperationConfig value = operationConfig.getValue();
        if(value == null) {
            throw new IllegalArgumentException(format("Empty operation config for entry %s!", operationConfig.getKey()));
        }
        if(isEmpty(value.getDefaultMessage()) && isEmpty(value.getOperationCall())) {
            throw new IllegalArgumentException("New custom operation config must contains both operationCall and defaultMessage!");
        }
    }

    private static void tryPutNewConfiguration(Map<String, OperationConfig> mappings, String key, OperationConfig operationConfig) {
        OperationConfig previousEntry = mappings.put(key, operationConfig);
        if(previousEntry != null) {
            throw new IllegalArgumentException(format("Duplicated mapping configuration for operation: %s", key));
        }
    }

    private static OperationConfig formatOperationConfig(Map.Entry<String, OperationConfig> entry) {
        return entry.getValue().withArgs(listOfArgs(entry));
    }

    private static List<String> listOfArgs(Map.Entry<String, OperationConfig> entry) {
        String key = entry.getKey();
        List<String> keyParams = OperationUtils.getParams(key);
        String operationCall = entry.getValue().getOperationCall();
        List<String> operationParams = OperationUtils.getParams(operationCall);
        validateParams(key, operationCall, keyParams, operationParams);
        return operationParams;
    }

    private static void validateParams(String key, String operationCall, List<String> keyParams, List<String> operationParams) {
        if(!operationParams.containsAll(keyParams)) {
            throw new IllegalArgumentException(format("Invalid params mapping for key: %s and operation call: %s", key, operationCall));
        }

        if(!operationParams.contains(PlaceHolders.THIS_)) {
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
        //!!! nie ma juz walidacji ze ktos zdefiniowal sobie 2 razy ten sam wpis!!!!
        //!!!! to znaczy ze between(a) w custom bedzie napdpisywal ten z efault between(a, b)
        berenConfig.getOperationsMappings().putAll(defaultConfig.getOperationsMappings());
        berenConfig.getOperationsMappings().putAll(customConfig.getOperationsMappings());

        return berenConfig;
    }
}
