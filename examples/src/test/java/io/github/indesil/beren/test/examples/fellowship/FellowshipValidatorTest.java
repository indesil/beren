package io.github.indesil.beren.test.examples.fellowship;

import io.github.indesil.beren.Beren;
import io.github.indesil.beren.exceptions.ValidationException;
import io.github.indesil.beren.test.examples.fellowship.model.FellowshipOfTheRing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FellowshipValidatorTest {

    private FellowshipValidator fellowshipValidator;

    @BeforeEach
    void init() {
        fellowshipValidator = Beren.loadValidator(FellowshipValidator.class);
    }

    @Test
    void shouldFailWhenHeroesAreEmpty() {
        // given
        FellowshipOfTheRing fellowshipOfTheRing = new FellowshipOfTheRing();

        // when // then
        assertThatThrownBy(() -> fellowshipValidator.checkFellowshipBeforeLeave(fellowshipOfTheRing))
                .isInstanceOf(ValidationException.class)
                .hasMessage("My own default message for empty collection error - heroes must not be empty!");
    }
}