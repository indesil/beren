package pl.jlabs.beren.compilator.configuration;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationLoaderTest {

    @Test
    void test() {
        String[] split = StringUtils.split("", ",");
        String[] split2 = StringUtils.split("asda", ",");
        BerenConfig berenConfig = ConfigurationLoader.loadConfigurations();
    }
}