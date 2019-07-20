package io.github.indesil.beren.test.features.model;

import java.util.Date;
import java.util.Calendar;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.chrono.HijrahDate;
import java.time.chrono.JapaneseDate;
import java.time.chrono.MinguoDate;
import java.time.chrono.ThaiBuddhistDate;

public class DateFeaturesModel extends FeaturesTestModel<DateFeaturesModel>{
    private Date date;
    private Calendar calendar;
    private Instant instant;
    private LocalDate localDate;
    private LocalDateTime localDateTime;
    private LocalTime localTime;
    private MonthDay monthDay;
    private OffsetDateTime offsetDateTime;
    private OffsetTime offsetTime;
    private Year year;
    private YearMonth yearMonth;
    private ZonedDateTime zonedDateTime;
    private HijrahDate hijrahDate;
    private JapaneseDate japaneseDate;
    private MinguoDate minguoDate;
    private ThaiBuddhistDate thaiBuddhistDate;

    public Date getDate() {
        return date;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public Instant getInstant() {
        return instant;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public LocalTime getLocalTime() {
        return localTime;
    }

    public MonthDay getMonthDay() {
        return monthDay;
    }

    public OffsetDateTime getOffsetDateTime() {
        return offsetDateTime;
    }

    public OffsetTime getOffsetTime() {
        return offsetTime;
    }

    public Year getYear() {
        return year;
    }

    public YearMonth getYearMonth() {
        return yearMonth;
    }

    public ZonedDateTime getZonedDateTime() {
        return zonedDateTime;
    }

    public HijrahDate getHijrahDate() {
        return hijrahDate;
    }

    public JapaneseDate getJapaneseDate() {
        return japaneseDate;
    }

    public MinguoDate getMinguoDate() {
        return minguoDate;
    }

    public ThaiBuddhistDate getThaiBuddhistDate() {
        return thaiBuddhistDate;
    }

    public DateFeaturesModel setDate(Date date) {
        this.date = date;
        setLastModifiedValueDescription("date", date);
        return this;
    }

    public DateFeaturesModel setCalendar(Calendar calendar) {
        this.calendar = calendar;
        setLastModifiedValueDescription("calendar", calendar);
        return this;
    }

    public DateFeaturesModel setInstant(Instant instant) {
        this.instant = instant;
        setLastModifiedValueDescription("instant", instant);
        return this;
    }

    public DateFeaturesModel setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
        setLastModifiedValueDescription("localDate", localDate);
        return this;
    }

    public DateFeaturesModel setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
        setLastModifiedValueDescription("localDateTime", localDateTime);
        return this;
    }

    public DateFeaturesModel setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
        setLastModifiedValueDescription("localTime", localTime);
        return this;
    }

    public DateFeaturesModel setMonthDay(MonthDay monthDay) {
        this.monthDay = monthDay;
        setLastModifiedValueDescription("monthDay", monthDay);
        return this;
    }

    public DateFeaturesModel setOffsetDateTime(OffsetDateTime offsetDateTime) {
        this.offsetDateTime = offsetDateTime;
        setLastModifiedValueDescription("offsetDateTime", offsetDateTime);
        return this;
    }

    public DateFeaturesModel setOffsetTime(OffsetTime offsetTime) {
        this.offsetTime = offsetTime;
        setLastModifiedValueDescription("offsetTime", offsetTime);
        return this;
    }

    public DateFeaturesModel setYear(Year year) {
        this.year = year;
        setLastModifiedValueDescription("year", year);
        return this;
    }

    public DateFeaturesModel setYearMonth(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
        setLastModifiedValueDescription("yearMonth", yearMonth);
        return this;
    }

    public DateFeaturesModel setZonedDateTime(ZonedDateTime zonedDateTime) {
        this.zonedDateTime = zonedDateTime;
        setLastModifiedValueDescription("zonedDateTime", zonedDateTime);
        return this;
    }

    public DateFeaturesModel setHijrahDate(HijrahDate hijrahDate) {
        this.hijrahDate = hijrahDate;
        setLastModifiedValueDescription("hijrahDate", hijrahDate);
        return this;
    }

    public DateFeaturesModel setJapaneseDate(JapaneseDate japaneseDate) {
        this.japaneseDate = japaneseDate;
        setLastModifiedValueDescription("japaneseDate", japaneseDate);
        return this;
    }

    public DateFeaturesModel setMinguoDate(MinguoDate minguoDate) {
        this.minguoDate = minguoDate;
        setLastModifiedValueDescription("minguoDate", minguoDate);
        return this;
    }

    public DateFeaturesModel setThaiBuddhistDate(ThaiBuddhistDate thaiBuddhistDate) {
        this.thaiBuddhistDate = thaiBuddhistDate;
        setLastModifiedValueDescription("thaiBuddhistDate", thaiBuddhistDate);
        return this;
    }
}
