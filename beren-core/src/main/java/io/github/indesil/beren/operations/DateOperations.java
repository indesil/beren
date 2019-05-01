package io.github.indesil.beren.operations;

import java.time.*;
import java.time.chrono.HijrahDate;
import java.time.chrono.JapaneseDate;
import java.time.chrono.MinguoDate;
import java.time.chrono.ThaiBuddhistDate;
import java.util.Calendar;
import java.util.Date;

public class DateOperations {

    public static boolean future(Date date) {
        return date == null || future(date.toInstant());
    }

    public static boolean futureOrPresent(Date date) {
        return date == null || futureOrPresent(date.toInstant());
    }

    public static boolean past(Date date) {
        return date == null || past(date.toInstant());
    }

    public static boolean pastOrPresent(Date date) {
        return date == null || pastOrPresent(date.toInstant());
    }

    public static boolean future(Calendar date) {
        return date == null || future(date.toInstant());
    }

    public static boolean futureOrPresent(Calendar date) {
        return date == null || futureOrPresent(date.toInstant());
    }

    public static boolean past(Calendar date) {
        return date == null || past(date.toInstant());
    }

    public static boolean pastOrPresent(Calendar date) {
        return date == null || pastOrPresent(date.toInstant());
    }

    public static boolean future(Instant date) {
        return date == null || date.compareTo(Clock.systemDefaultZone().instant()) > 0;
    }

    public static boolean futureOrPresent(Instant date) {
        return date == null || date.compareTo(Clock.systemDefaultZone().instant()) >= 0;
    }

    public static boolean past(Instant date) {
        return date == null || date.compareTo(Clock.systemDefaultZone().instant()) < 0;
    }

    public static boolean pastOrPresent(Instant date) {
        return date == null || date.compareTo(Clock.systemDefaultZone().instant()) <= 0;
    }

    public static boolean future(LocalDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(LocalDate.now(clock)) > 0;
    }

    public static boolean futureOrPresent(LocalDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(LocalDate.now(clock)) >= 0;
    }

    public static boolean past(LocalDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(LocalDate.now(clock)) < 0;
    }

    public static boolean pastOrPresent(LocalDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(LocalDate.now(clock)) <= 0;
    }

    public static boolean future(LocalDateTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(LocalDateTime.now(clock)) > 0;
    }

    public static boolean futureOrPresent(LocalDateTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(LocalDateTime.now(clock)) >= 0;
    }

    public static boolean past(LocalDateTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(LocalDateTime.now(clock)) < 0;
    }

    public static boolean pastOrPresent(LocalDateTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(LocalDateTime.now(clock)) <= 0;
    }

    public static boolean future(LocalTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(LocalTime.now(clock)) > 0;
    }

    public static boolean futureOrPresent(LocalTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(LocalTime.now(clock)) >= 0;
    }

    public static boolean past(LocalTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(LocalTime.now(clock)) < 0;
    }

    public static boolean pastOrPresent(LocalTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(LocalTime.now(clock)) <= 0;
    }

    public static boolean future(MonthDay date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(MonthDay.now(clock)) > 0;
    }

    public static boolean futureOrPresent(MonthDay date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(MonthDay.now(clock)) >= 0;
    }

    public static boolean past(MonthDay date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(MonthDay.now(clock)) < 0;
    }

    public static boolean pastOrPresent(MonthDay date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(MonthDay.now(clock)) <= 0;
    }

    public static boolean future(OffsetDateTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(OffsetDateTime.now(clock)) > 0;
    }

    public static boolean futureOrPresent(OffsetDateTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(OffsetDateTime.now(clock)) >= 0;
    }

    public static boolean past(OffsetDateTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(OffsetDateTime.now(clock)) < 0;
    }

    public static boolean pastOrPresent(OffsetDateTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(OffsetDateTime.now(clock)) <= 0;
    }

    public static boolean future(OffsetTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(OffsetTime.now(clock)) > 0;
    }

    public static boolean futureOrPresent(OffsetTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(OffsetTime.now(clock)) >= 0;
    }

    public static boolean past(OffsetTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(OffsetTime.now(clock)) < 0;
    }

    public static boolean pastOrPresent(OffsetTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(OffsetTime.now(clock)) <= 0;
    }

    public static boolean future(Year date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(Year.now(clock)) > 0;
    }

    public static boolean futureOrPresent(Year date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(Year.now(clock)) >= 0;
    }

    public static boolean past(Year date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(Year.now(clock)) < 0;
    }

    public static boolean pastOrPresent(Year date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(Year.now(clock)) <= 0;
    }

    public static boolean future(YearMonth date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(YearMonth.now(clock)) > 0;
    }

    public static boolean futureOrPresent(YearMonth date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(YearMonth.now(clock)) >= 0;
    }

    public static boolean past(YearMonth date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(YearMonth.now(clock)) < 0;
    }

    public static boolean pastOrPresent(YearMonth date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(YearMonth.now(clock)) <= 0;
    }

    public static boolean future(ZonedDateTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(ZonedDateTime.now(clock)) > 0;
    }

    public static boolean futureOrPresent(ZonedDateTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(ZonedDateTime.now(clock)) >= 0;
    }

    public static boolean past(ZonedDateTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(ZonedDateTime.now(clock)) < 0;
    }

    public static boolean pastOrPresent(ZonedDateTime date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(ZonedDateTime.now(clock)) <= 0;
    }

    public static boolean future(HijrahDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(HijrahDate.now(clock)) > 0;
    }

    public static boolean futureOrPresent(HijrahDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(HijrahDate.now(clock)) >= 0;
    }

    public static boolean past(HijrahDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(HijrahDate.now(clock)) < 0;
    }

    public static boolean pastOrPresent(HijrahDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(HijrahDate.now(clock)) <= 0;
    }

    public static boolean future(JapaneseDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(JapaneseDate.now(clock)) > 0;
    }

    public static boolean futureOrPresent(JapaneseDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(JapaneseDate.now(clock)) >= 0;
    }

    public static boolean past(JapaneseDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(JapaneseDate.now(clock)) < 0;
    }

    public static boolean pastOrPresent(JapaneseDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(JapaneseDate.now(clock)) <= 0;
    }

    public static boolean future(MinguoDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(MinguoDate.now(clock)) > 0;
    }

    public static boolean futureOrPresent(MinguoDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(MinguoDate.now(clock)) >= 0;
    }

    public static boolean past(MinguoDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(MinguoDate.now(clock)) < 0;
    }

    public static boolean pastOrPresent(MinguoDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(MinguoDate.now(clock)) <= 0;
    }

    public static boolean future(ThaiBuddhistDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(ThaiBuddhistDate.now(clock)) > 0;
    }

    public static boolean futureOrPresent(ThaiBuddhistDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(ThaiBuddhistDate.now(clock)) >= 0;
    }

    public static boolean past(ThaiBuddhistDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(ThaiBuddhistDate.now(clock)) < 0;
    }

    public static boolean pastOrPresent(ThaiBuddhistDate date) {
        Clock clock = Clock.systemDefaultZone();
        return date == null || date.compareTo(ThaiBuddhistDate.now(clock)) <= 0;
    }
}