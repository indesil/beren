package pl.jlabs.beren.test;

import pl.jlabs.beren.annotations.Field;
import pl.jlabs.beren.annotations.Id;
import pl.jlabs.beren.annotations.Validate;
import pl.jlabs.beren.annotations.Validator;
import pl.jlabs.beren.model.OperationContext;
import pl.jlabs.beren.test.model.*;

//dodac moze jeszcze cos takiego jak register czyli np
//@Validator(register="pl.jlabs.beren.custom.Validators")
//@I tam bedziemy mieli zestaw @Idkow albo metod do zeskanowania jako validatorow
//@Validator
public interface TestValidator {

    @Validate({
            @Field(name = "source", operation = "neitherOf(SarumanGifts, MordorGmbH)"),
            @Field(name = "requestId", operation = "biggerThan(0)", message = "requestId should be bigger than 0!"),
            @Field(name = "orders", operation = "validateOrders")
    })
    void validateRequest(OrdersCreateRequest request);

    //@Field(name = "invoiceMap", operation = "forEachValue/forEachKey/forEachEntry(invoiceValidation, this)")
    //to this ciekawy pomysł a te iteratory musza byc osobno zeby ich nikt nie zoverridował bo sie ich napsiać recznie nie da!
    //moze najlepiej dac jakas dyrektywe typu #forEachValue zeby bylo widac ze to jest inna operacja
    @Validate(nullable = true, value = {
            @Field(name = "invoiceMap", operation = "#forEachValue(invoiceValidation)")
    })
    default void validateOrders(Orders orders) {
        for (Invoice value : orders.getInvoiceMap().values()) {
            validateEntryValue(value);
        }
    }

    //poki co robimy ze jak np Field String i File name =strinField załapie oba to oba sie beda generowac
    //potem mozna dolozyc excludes albo override ale to w innej iteracji
    @Id("invoiceValidation")
    @Validate({
        @Field(type = String.class, operation = "notEmpty"),
        @Field(type = Double.class, operation = "greaterThan(0)"),
            //jezeli to names bylo by np type to wtedy musimy przyjac array[] nie wiadomo czy w przypadku typow tez?
            // a moze i to i to?
        //@Field(names = {"paymentForm", "paid"}, operation = "myCustomInlineValidation", message = "Cash was not paid! Please check variable ${path}"),
        @Field(name = "paymentForm", operation = "myCustomInlineValidation", message = "myCustomInlineValidation!"),
        @Field(name = "customer", operation = "validateCustomer")

    })
    void validateEntryValue(Invoice invoice);

    @Id("myCustomInlineValidation")
    default boolean customInlineValidator(String paymentForm) {
        return "cash".equals(paymentForm);
    }

    //eee trudne to bedzie bo jeszcze nie wiem co z tym operationContext ma byc wiec narazie skip
    @Id("myCustomInlineValidation2")
    default boolean customInlineValidator2(Invoice invoice, OperationContext operationContext) {
        Invoice validationObject = operationContext.getValidationObject(Invoice.class);
        operationContext.addPlaceHolder("myPlaceHolder", "It's all wrong!");
        return invoice.getPaymentDate() != null && validationObject.isPaid();
    }

    //@Field(names = {"gender", "age"}, operation = "notEquals(UNKNOWN) && isNull", message = "${arg1} must not occurs with ${field2}"),
    //mega trudne bo jak zrobi cparsowanie enumow lepiej narazie names traktowac jak kolektywch tak jak type czy pattern
    //nawet name mozna traktowac jako pojedynczy kolektyw
    @Validate({
            //@Field(names = {"firstName", "lastName", "title"}, operation = "notEmpty"),
            //@Field(names = {"gender", "age"}, operation = "notEquals(UNKNOWN) && isNull", message = "${arg1} must not occurs with ${field2}"),
            @Field(name = "address", operation = "addressIsValid", message = "Invalid address")
    })
    void validateCustomer(Customer customer);

    default boolean addressIsValid(Address address) {
        return address.getAddressLine() != null && address.getCity() != null && address.getCountry() != null && address.getHouseNumber() > 0;
    }

    //Być moze będzie sie tak dało zrobić ale w MVP3? Trzeba gdzies to zapisywać
    //Kolejny pomysł to override messegow dla poszczegolnych operacji?
    // tak zeby łatwo było zrobić np internacjonalizacje
    //@Validate({
    //          @Field(type = String.class, operation = "notNull"),
    //          @Field(name = "houseNumber", operation = "greaterThan(0)")
    //})
    //boolean addressIsValid(Address address)
}
