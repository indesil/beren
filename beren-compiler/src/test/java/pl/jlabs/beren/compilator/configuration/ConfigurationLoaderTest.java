package pl.jlabs.beren.compilator.configuration;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationLoaderTest {

    @Test
    void test() {
        BerenConfig berenConfig = ConfigurationLoader.loadConfigurations();
    }
}