package pl.jlabs.beren.test;

import pl.jlabs.beren.annotations.Field;
import pl.jlabs.beren.annotations.Id;
import pl.jlabs.beren.annotations.Validate;
import pl.jlabs.beren.model.OperationContext;
import pl.jlabs.beren.model.ValidationResults;
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
    ValidationResults validateRequest(OrdersCreateRequest request);

    @Validate({
            @Field(name = "invoiceMap", operation = "onEveryEntryValue(invoiceValidation)")
    })
    ValidationResults validateOrders(Orders orders);

    @Id("invoiceValidation")
    @Validate({
        @Field(type = String.class, operation = "notEmpty"),
        @Field(type = Double.class, operation = "greaterThan(0)"),
            //jezeli to names bylo by np type to wtedy musimy przyjac array[] nie wiadomo czy w przypadku typow tez?
            // a moze i to i to?
        @Field(names = {"paymentForm", "paid"}, operation = "myCustomInlineValidation", message = "Cash was not paid! Please check variable ${path}"),
        @Field(operation = "myCustomInlineValidation", message = "This validator failed because ${myPlaceHolder}"),
        @Field(name = "customer", operation = "validateCustomer")

    })
    ValidationResults validateEntryValue(Invoice invoice);

    @Id("myCustomInlineValidation")
    default boolean customInlineValidator(String paymentForm, boolean paid) {
        return "cash".equals(paymentForm) && paid;
    }

    //eee trudne to bedzie bo jeszcze nie wiem co z tym operationContext ma byc wiec narazie skip
    @Id("myCustomInlineValidation2")
    default boolean customInlineValidator2(Invoice invoice, OperationContext operationContext) {
        Invoice validationObject = operationContext.getValidationObject(Invoice.class);
        operationContext.addPlaceHolder("myPlaceHolder", "It's all wrong!");
        return invoice.getPaymentDate() != null && validationObject.isPaid();
    }

    @Validate({
            @Field(names = {"firstName", "lastName", "title"}, operation = "allNotEmpty"),
            @Field(names = {"gender", "age"}, operation = "notEquals(UNKNOWN) && isNull", message = "${param0} must not occurs with ${field1}"),
            @Field(name = "address", operation = "addressIsValid", message = "Invalid address")
    })
    ValidationResults validateCustomer(Customer customer);

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
