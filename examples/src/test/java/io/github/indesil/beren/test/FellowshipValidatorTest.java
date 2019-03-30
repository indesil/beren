package io.github.indesil.beren.test;

import io.github.indesil.beren.Beren;
import io.github.indesil.beren.test.model.fellowship.FellowshipOfTheRing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FellowshipValidatorTest {

    private FellowshipValidator fellowshipValidator;

    @BeforeEach
    void init() {
        fellowshipValidator = Beren.loadValidator(FellowshipValidator.class);
    }

    @Test
    void shouldWork() {
        // given
        FellowshipOfTheRing fellowshipOfTheRing = new FellowshipOfTheRing();

        // when
        fellowshipValidator.checkFellowshipBeforeLeave(fellowshipOfTheRing);

        // then
    }
}