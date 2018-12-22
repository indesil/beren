package pl.jlabs.beren.compilator.configuration;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

class ConfigurationLoaderTest {

    @Test
    void test() {
        String[] split = StringUtils.split("", ",");
        String[] split2 = StringUtils.split("asda", ",");
        BerenConfig berenConfig = ConfigurationLoader.loadConfigurations();
    }

    //neitherOf(String, String)
    //neitherOf(String[])
    //neitherOf(String...)
    //neitherOf(Collection<String>)
    /*@Validate({
            @Field(name = "source", operation = "neitherOf(1, 'SarumanGifts', 'MordorGmbH')"),
            @Field(type = String.class, operation = "notEmpty"),
            @Field(name="hordcore", operation = "somethingDifficult(1, 5.0, ['a', 'b'] "),
            @Field(type = Double.class, operation = "greaterThan(0.0)"),
            @Field(type = Enum.class, operation = "mustEquals(Enum.FIELD)"),
            //jezeli to names bylo by np type to wtedy musimy przyjac array[] nie wiadomo czy w przypadku typow tez?
            // a moze i to i to?
            //@Field(names = {"paymentForm", "paid"}, operation = "myCustomInlineValidation", message = "Cash was not paid! Please check variable ${path}"),
            @Field(name = "paymentForm", operation = "myCustomInlineValidation", message = "myCustomInlineValidation!"),
            @Field(name = "customer", operation = "validateCustomer")

    })*/
    /*
    operationsMappings:
    neitherOf(a):
    operationCall: pl.jlabs.beren.operations.Operations.neitherOf(this, a)
    defaultMessage: "${fieldName} must be neither of ${a}"
    biggerThan(var):
    operationCall: pl.jlabs.beren.operations.Operations.biggerThan(this, var)
    defaultMessage: "${fieldName} must be bigger than ${var}"
    between(a,b):
    operationCall: pl.jlabs.beren.operations.Operations.between(this, a, b)
    defaultMessage: "${fieldName} must be between ${a} and ${b}"
    notNull:
    operationCall: java.util.Objects.notNull(this)
    defaultMessage: "${fieldName} must not be null!"
    notEmpty:
    operationCall: pl.jlabs.beren.operations.Operations.notEmpty(this)
    defaultMessage: "${fieldName} must not be empty"*/
}