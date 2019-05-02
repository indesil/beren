package io.github.indesil.beren.compilator.configuration;

import io.github.indesil.beren.compilator.parser.PlaceHolders;
import io.github.indesil.beren.compilator.utils.OperationUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static io.github.indesil.beren.compilator.utils.OperationUtils.strapFromParams;
import static java.lang.String.format;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class ConfigurationLoader {
    private static final String OPERATIONS_CONFIG = "beren-operations-configuration.yaml";
    private static final String DEFAULT_VALIDATION_MESSAGES = "defaults/ValidationMessages.properties";
    private static final String CUSTOM_VALIDATION_MESSAGES = "ValidationMessages.properties";

    public static BerenConfig loadConfigurations() {
        try (InputStream inputStream = ConfigurationLoader.class
                .getClassLoader()
                .getResourceAsStream(OPERATIONS_CONFIG)) {
            if(inputStream != null) {
                return createConfiguration(new Yaml().loadAs(inputStream, RawConfiguration.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalArgumentException("Could not load beren configuration!");
    }

    private static BerenConfig createConfiguration(RawConfiguration rawConfiguration) {
        Map<String, OperationConfig> mappings = new HashMap<>(rawConfiguration.getOperationsMappings().size());
        Properties validationMessages = loadValidationMessages();
        for (Map.Entry<String, OperationConfig> rawConf : rawConfiguration.getOperationsMappings().entrySet()) {
            validateConfig(rawConf);
            String key = strapFromParams(rawConf.getKey());
            tryPutNewConfiguration(mappings, key, formatOperationConfig(rawConf, validationMessages));
        }
        return new BerenConfig().withOperationsMappings(mappings);
    }

    private static Properties loadValidationMessages() {
        try(InputStream defaultValidationMessagesStream = getResourcesAsStream(DEFAULT_VALIDATION_MESSAGES);
            InputStream customValidationMessagesStream = getResourcesAsStream(CUSTOM_VALIDATION_MESSAGES)) {

            Properties properties = new Properties();
            properties.load(defaultValidationMessagesStream);
            if(customValidationMessagesStream != null) {
                properties.load(customValidationMessagesStream);
            }
            return properties;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while loading validation messages", e);
        }
    }

    private static InputStream getResourcesAsStream(String resourceName) {
        return ConfigurationLoader.class.getClassLoader().getResourceAsStream(resourceName);
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

    private static OperationConfig formatOperationConfig(Map.Entry<String, OperationConfig> entry, Properties validationMessages) {
        return entry.getValue()
                .withDefaultMessage(determineDefaultMessage(entry, validationMessages))
                .withArgs(listOfArgs(entry));
    }

    private static String determineDefaultMessage(Map.Entry<String, OperationConfig> entry, Properties validationMessages) {
        String defaultMessage = entry.getValue().getDefaultMessage();
        String propertyKeyWithoutBraces = defaultMessage.substring(1, defaultMessage.length() - 1);
        return validationMessages.getProperty(propertyKeyWithoutBraces);
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

    private static void tryPutNewConfiguration(Map<String, OperationConfig> mappings, String key, OperationConfig operationConfig) {
        OperationConfig previousEntry = mappings.put(key, operationConfig);
        if(previousEntry != null) {
            throw new IllegalArgumentException(format("Duplicated mapping configuration for operation: %s", key));
        }
    }
}
