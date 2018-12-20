package pl.jlabs.beren.test;

import pl.jlabs.beren.annotations.*;
import pl.jlabs.beren.model.OperationContext;
import pl.jlabs.beren.model.ValidationResults;
import pl.jlabs.beren.test.model.*;

//dodac moze jeszcze cos takiego jak register czyli np
//@Validator(register="pl.jlabs.beren.custom.Validators")
//@I tam bedziemy mieli zestaw @Idkow albo metod do zeskanowania jako validatorow
@Validator(breakingStrategy = BreakingStrategy.SUMMARIZE_ALL)
public abstract class TestValidatorAbstract {

    public TestValidatorAbstract() {
    }

    private TestValidatorAbstract(String dadas) {
    }

    public TestValidatorAbstract(int dasd, float kkk, String aaa, Object o) {
    }

    @Validate(nullable = true, value = {
            @Field(name = "source", operation = "neitherOf(SarumanGifts, MordorGmbH)"),
            @Field(name = "requestId", operation = "biggerThan(0)"),
            @Field(name = "orders", operation = "validateOrders")
    })
    abstract ValidationResults validateRequest(OrdersCreateRequest request);

    @Validate({
            @Field(name = "invoiceMap", operation = "#ForEachValue(invoiceValidation)")
    })
    abstract ValidationResults validateOrders(Orders orders);

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
    abstract ValidationResults validateEntryValue(Invoice invoice);

    @Id("myCustomInlineValidation")
    boolean customInlineValidator(String paymentForm, boolean paid) {
        return "cash".equals(paymentForm) && paid;
    }

    @Id("myCustomInlineValidation2")
    boolean customInlineValidator2(Invoice invoice, OperationContext operationContext) {
        Invoice validationObject = operationContext.getValidationObject(Invoice.class);
        operationContext.addPlaceHolder("myPlaceHolder", "It's all wrong!");
        return invoice.getPaymentDate() != null && validationObject.isPaid();
    }

    @Validate({
            @Field(names = {"firstName", "lastName", "title"}, operation = "allNotEmpty"),
            @Field(names = {"gender", "age"}, operation = "notEquals(UNKNOWN) && isNull", message = "${param0} must not occurs with ${field1}"),
            @Field(name = "address", operation = "addressIsValid", message = "Invalid address")
    })
    abstract ValidationResults validateCustomer(Customer customer);

    boolean addressIsValid(Address address) {
        return address.getAddressLine() != null && address.getCity() != null && address.getCountry() != null && address.getHouseNumber() > 0;
    }

    @Override
    public String toString() {
        return "TestValidatorAbstract{}";
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
