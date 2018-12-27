package pl.jlabs.beren.test;


import pl.jlabs.beren.annotations.*;
import pl.jlabs.beren.model.ValidationResults;
import pl.jlabs.beren.test.model.Address;
import pl.jlabs.beren.test.model.Customer;
import pl.jlabs.beren.test.model.Invoice;
import pl.jlabs.beren.test.model.OrdersCreateRequest;

@Validator(breakingStrategy = BreakingStrategy.SUMMARIZE_ALL)
public interface SimpleValidator {

    @Validate({
            @Field(name = "source", operation = "neitherOf(['Saruman,Gifts', 'MordorGmbH'])"),
            @Field(name = "requestId", operation = "greaterThan(0)", message = "requestId should be bigger than 0!"),
            //@Field(name = "orders", operation = "validateOrders")
    })
    ValidationResults validateRequest(OrdersCreateRequest request);

    @Id("invoiceValidation")
    @Validate({
            @Field(type = String.class, operation = "notEmpty"),
            //double.class nie jest traktowana tak samo jak Double.class!
            @Field(type = double.class, operation = "greaterThan(0.0)"),
            //jezeli to names bylo by np type to wtedy musimy przyjac array[] nie wiadomo czy w przypadku typow tez?
            // a moze i to i to?
            //@Field(names = {"paymentForm", "paid"}, operation = "myCustomInlineValidation", message = "Cash was not paid! Please check variable ${path}"),
            @Field(name = "paymentForm", operation = "myCustomInlineValidation", message = "myCustomInlineValidation!"),
            @Field(name = "customer", operation = "validateCustomer")

    })
    ValidationResults validateEntryValue(Invoice invoice);

    @Id("myCustomInlineValidation")
    default boolean customInlineValidator(String paymentForm) {
        return "cash".equals(paymentForm);
    }

    @Validate(
            nullable = true,
            value = {
            @Field(names = {"firstName", "lastName", "title"}, operation = "notEmpty"),
            @Field(pattern = ".*name", operation = "notNull"),
            //dalbym ze takie complex operation dziala tylko na pojedynczym polu a nie na wszystkich
            //no sorry ale takie cos to jest zbyt wiele
            //@Field(names = {"gender", "age"}, operation = "notEquals(UNKNOWN) && isNull", message = "${arg1} must not occurs with ${field2}"),
            @Field(name = "address", operation = "addressIsValid", message = "Invalid address")
    })
    ValidationResults validateCustomer(Customer customer);

    default boolean addressIsValid(Address address) {
        return address.getAddressLine() != null && address.getCity() != null && address.getCountry() != null && address.getHouseNumber() > 0;
    }

    //rozwazyc taki case bo to jest trudne
    //generalnie dla THROW_ON_FIRST nic sie nie zmienia
    // ale dla summarize jest problem z przekazywaniem validation resutls
    // przemyslec to razem z ValidationContext bo teraz to jest duzy problem jezeli ktos
    // wywola ze swojej metody metode walidacyjna to napisze sobie ValidationResults i bedzie mial 0!
   /*default void mojaWalidacja(Invoice invoice, ValidationResults validationResults) {
        validateEntryValue(invoice);

    }*/

   //no chyba ze tak
    //ciekawe te casy
       /*default ValidationResults mojaWalidacja(Invoice invoice) {
        ValidationResults validationResults = validateEntryValue(invoice);
        // tutaj cosik sobie z tym zrobi
        return validationResults;
    }*/
}
