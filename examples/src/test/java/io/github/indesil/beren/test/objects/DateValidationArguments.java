package io.github.indesil.beren.test.objects;

import org.junit.jupiter.params.provider.Arguments;

import java.time.*;
import java.time.chrono.HijrahDate;
import java.time.chrono.JapaneseDate;
import java.time.chrono.MinguoDate;
import java.time.chrono.ThaiBuddhistDate;
import java.util.Date;
import java.util.stream.Stream;

import static io.github.indesil.beren.test.objects.FeaturesTestObjects.dateFeaturesModel;
import static io.github.indesil.beren.test.objects.ObjectUtils.*;

public class DateValidationArguments {

    public static Stream<Arguments> futureAndCurrentDates() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime futureDateTime = now
                .plusYears(1)
                .plusDays(1)
                .plusMinutes(30);
        return dates(futureDateTime, now);
    }

    public static Stream<Arguments> futureAndCurrentWithSmallOffsetDates() {
        LocalDateTime now = LocalDateTime.now().plusSeconds(5);
        LocalDateTime futureDateTime = now
                .plusYears(1)
                .plusDays(1)
                .plusMinutes(30);
        return dates(futureDateTime, now);
    }

    public static Stream<Arguments> invalidFutureDates() {
        LocalDateTime futureDateTime = LocalDateTime.now()
                .plusYears(1)
                .plusDays(1)
                .plusMinutes(30);
        LocalDateTime pastDateTime = LocalDateTime.now()
                .minusYears(1)
                .minusDays(1)
                .minusMinutes(30);
        return dates(futureDateTime, pastDateTime);
    }

    public static Stream<Arguments> pastAndCurrentWithSmallOffsetDates() {
        LocalDateTime now = LocalDateTime.now().plusSeconds(5);
        LocalDateTime pastDateTime = LocalDateTime.now()
                .minusYears(1)
                .minusDays(1)
                .minusMinutes(30);
        return dates(pastDateTime, now);
    }

    public static Stream<Arguments> invalidPastDates() {
        LocalDateTime futureDateTime = LocalDateTime.now()
                .plusYears(1)
                .plusDays(1)
                .plusMinutes(30);
        LocalDateTime pastDateTime = LocalDateTime.now()
                .minusYears(1)
                .minusDays(1)
                .minusMinutes(30);
        return dates(pastDateTime, futureDateTime);
    }

    public static Stream<Arguments> pastAndCurrentDates() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime pastDateTime = LocalDateTime.now()
                .minusYears(1)
                .minusDays(1)
                .minusMinutes(30);
        return dates(pastDateTime, now);
    }

    private static Stream<Arguments> dates(LocalDateTime baseDate, LocalDateTime dateToSet) {
        Instant dateToSetInstant = dateToSet.atZone(ZoneId.systemDefault()).toInstant();
        Date dateFromDateToSet = Date.from(dateToSetInstant);
        return Stream.of(
                Arguments.of(dateFeaturesModel(baseDate).setDate(dateFromDateToSet)),
                Arguments.of(dateFeaturesModel(baseDate).setDate(dateFromDateToSet)),
                Arguments.of(dateFeaturesModel(baseDate).setCalendar(toCalendar(dateFromDateToSet))),
                Arguments.of(dateFeaturesModel(baseDate).setInstant(dateToSetInstant)),
                Arguments.of(dateFeaturesModel(baseDate).setLocalDate(LocalDate.from(dateToSet))),
                Arguments.of(dateFeaturesModel(baseDate).setLocalDateTime(dateToSet)),
                Arguments.of(dateFeaturesModel(baseDate).setLocalTime(LocalTime.from(dateToSet))),
                Arguments.of(dateFeaturesModel(baseDate).setMonthDay(MonthDay.from(dateToSet))),
                Arguments.of(dateFeaturesModel(baseDate).setOffsetDateTime(toOffsetDateTime(dateToSet))),
                Arguments.of(dateFeaturesModel(baseDate).setOffsetTime(toOffsetTime(dateToSet))),
                Arguments.of(dateFeaturesModel(baseDate).setYear(Year.from(dateToSet))),
                Arguments.of(dateFeaturesModel(baseDate).setYearMonth(YearMonth.from(dateToSet))),
                Arguments.of(dateFeaturesModel(baseDate).setZonedDateTime(toZonedDateTime(dateToSet))),
                Arguments.of(dateFeaturesModel(baseDate).setHijrahDate(HijrahDate.from(dateToSet))),
                Arguments.of(dateFeaturesModel(baseDate).setJapaneseDate(JapaneseDate.from(dateToSet))),
                Arguments.of(dateFeaturesModel(baseDate).setMinguoDate(MinguoDate.from(dateToSet))),
                Arguments.of(dateFeaturesModel(baseDate).setThaiBuddhistDate(ThaiBuddhistDate.from(dateToSet)))
        );
    }
}
