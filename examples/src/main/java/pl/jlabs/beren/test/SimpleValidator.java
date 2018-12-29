package pl.jlabs.beren.test;


import pl.jlabs.beren.annotations.*;
import pl.jlabs.beren.test.model.*;
import pl.jlabs.beren.test.model.customer.Gender;

import java.util.Set;

@Validator(breakingStrategy = BreakingStrategy.THROW_ON_FIRST)
public abstract class SimpleValidator {

    public SimpleValidator(int a, int b) {

    }

    public SimpleValidator(int a, String b) {

    }

    public SimpleValidator(Integer i, Object o) {

    }

    @Validate({
            @Field(name = "source", operation = "neitherOf(['Saruman,Gifts', 'MordorGmbH'])"),
            @Field(name = "requestId", operation = "greaterThan(0)", message = "requestId should be bigger than 0!"),
            @Field(name = "addresses", operation = "#forEachValue(addressIsValid)", message = "%{paramName} invalid address!"),
            @Field(name = "orders", operation = "validateOrders")
    })
    abstract void validateRequest(OrdersCreateRequest request);

    @Validate(nullable = true, value = {
            @Field(name = "invoiceMap", operation = "#forEachValue(invoiceValidation)"),
            @Field(name = "additionalData", operation = "notEmpty"),
            @Field(name = "additionalData", operation = "isAdditionalDataValid", message = "invalid data was given")
    })
    abstract void validateOrders(Orders orders);

    boolean isAdditionalDataValid(Set<Object> data) {
        return data != null;
    }

    @Id("invoiceValidation")
    @Validate({
            //to jest chyba invalid...
            //@Field(type = void.class, operation = "notEmpty"),
            @Field(type = String.class, operation = "notEmpty"),
            @Field(type = Double.class, operation = "greaterThan(0.0)"),
            @Field(name = "paymentForm", operation = "myCustomInlineValidation", message = "myCustomInlineValidation!"),
            @Field(name = "customer", operation = "validateCustomer")

    })
    abstract void validateEntryValue(Invoice invoice);

    @Id("myCustomInlineValidation")
    boolean customInlineValidator(String paymentForm) {
        return "cash".equals(paymentForm);
    }

    @Validate(
            nullable = true,
            value = {
            @Field(names = {"firstName", "lastName", "title"}, operation = "notEmpty"),
            @Field(pattern = ".*Name", operation = "notNull"),
            @Field(type = Enum.class, operation = "cosiestanie", message = "dasda"),
            @Field(name = "address", operation = "addressIsValid", message = "Invalid address")
    })
    abstract void validateCustomer(Customer customer);


    boolean addressIsValid(Address address) {
        return address.getAddressLine() != null && address.getCity() != null && address.getCountry() != null && address.getHouseNumber() > 0;
    }

    boolean cosiestanie(Gender gender) {
        return true;
    }
       // TO DO

    //jezeli to names bylo by np type to wtedy musimy przyjac array[] nie wiadomo czy w przypadku typow tez?
    // a moze i to i to?
    //@Field(names = {"paymentForm", "paid"}, operation = "myCustomInlineValidation", message = "Cash was not paid! Please check variable ${path}"),

    //dalbym ze takie complex operation dziala tylko na pojedynczym polu a nie na wszystkich
    //no sorry ale takie cos to jest zbyt wiele
    //@Field(names = {"gender", "age"}, operation = "notEquals(UNKNOWN) && isNull", message = "${arg1} must not occurs with ${field2}"),

    //fajnie by bylo miec kontrole nad tym co bedzioe w message ale to jest jakies MVP 5
    //default boolean addressIsValid(Address address)

    //rozwazyc taki case bo to jest trudne
    //generalnie dla THROW_ON_FIRST nic sie nie zmienia
    // ale dla summarize jest problem z przekazywaniem validation resutls
    // przemyslec to razem z ValidationContext bo teraz to jest duzy problem jezeli ktos
    // wywola ze swojej metody metode walidacyjna to napisze sobie ValidationResults i bedzie mial 0!
   /*default void mojaWalidacja(Invoice invoice, ValidationResults validationResults) {
        validateEntryValue(invoice);

    }*/

    //no chyba ze tak
       /*default ValidationResults mojaWalidacja(Invoice invoice) {
        ValidationResults validationResults = validateEntryValue(invoice);
        // tutaj cosik sobie z tym zrobi
        return validationResults;
    }*/

    //dodac moze jeszcze cos takiego jak register czyli np
    //@Validator(register="pl.jlabs.beren.custom.Validators")
    //I tam bedziemy mieli zestaw @Idkow albo metod do zeskanowania jako validatorow

    //dolozyc excludes dla @Field(type i pattern)

    //@Field(name = "this", operation = "complexValidation")
    //    })
    //    void validateRequest(OrdersCreateRequest request);
    //
    //    default boolean complexValidation(OrdersCreateRequest request) {
    //        return request.getOrders() != null && request.getSource() != null;
    //    }

    //eee trudne to bedzie bo jeszcze nie wiem co z tym operationContext ma byc wiec narazie skip
    /*@Id("myCustomInlineValidation2")
    default boolean customInlineValidator2(Invoice invoice, OperationContext operationContext) {
        Invoice validationObject = operationContext.getValidationObject(Invoice.class);
        operationContext.addPlaceHolder("myPlaceHolder", "It's all wrong!");
        return invoice.getPaymentDate() != null && validationObject.isPaid();
    }*/
}
