package com.paisley.todolist.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.time.chrono.Chronology;
import java.time.chrono.MinguoChronology;
import java.time.chrono.MinguoDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.DecimalStyle;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * DateUtil
 *
 * @author Sero
 * @since 2009/2/16
 **/
public final class DateUtil {

    private DateUtil() {}

    private static final Locale LOCAL = Locale.TAIWAN;

    /**
     * String轉Date
     *
     * @param str    str
     * @param format format
     * @return Date
     * @throws ParseException exception
     */
    public static Date parseDate(String str, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        if (StringUtil.isBlank(str)) return date;
        return sdf.parse(str);
    }

    /**
     * LocalDateTime轉Date
     *
     * @param localDateTime localDateTime
     * @return Date
     */
    public static Date parseDate(LocalDateTime localDateTime) {
        ZoneId zoneIdPlus8 = ZoneId.of("UTC+8");
        return Date.from(localDateTime.atZone(zoneIdPlus8).toInstant());
    }

    /**
     * String轉Timestamp
     *
     * @param str    str
     * @param format format
     * @return Timestamp
     * @throws ParseException exception
     */
    public static Timestamp parseTimestamp(String str, String format) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Timestamp timestamp = null;
        if (StringUtil.isBlank(str)) return timestamp;
        return new Timestamp(sdf.parse(str).getTime());
    }

    /**
     * LocalDateTime轉Timestamp
     *
     * @param localDateTime localDateTime
     * @return Timestamp
     */
    public static Timestamp parseTimestamp(LocalDateTime localDateTime) {
        Timestamp timestamp = null;
        if (nonNull(localDateTime)) {
            timestamp = Timestamp.valueOf(localDateTime);
        }
        return timestamp;
    }

    /**
     * String轉LocalDate
     *
     * @param time    time
     * @param pattern pattern
     * @return LocalDate
     */
    public static LocalDate parseLocalDate(String time, String pattern) {
        LocalDate localDate = null;
        if (checkPattern(time, pattern)) {
            localDate = LocalDate.parse(time, DateTimeFormatter.ofPattern(pattern, LOCAL));
        }
        return localDate;
    }

    /**
     * String轉LocalDateTime
     *
     * @param time    time
     * @param pattern pattern
     * @return LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(String time, String pattern) {
        LocalDateTime localDateTime = null;
        if (checkPattern(time, pattern)) {
            localDateTime = LocalDateTime.parse(time, DateTimeFormatter.ofPattern(pattern, LOCAL));
        }
        return localDateTime;
    }

    /**
     * Timestamp轉LocalDateTime
     *
     * @param timestamp timestamp
     * @return LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(Timestamp timestamp) {
        LocalDateTime localDateTime = null;
        if (nonNull(timestamp)) {
            localDateTime = timestamp.toLocalDateTime();
        }
        return localDateTime;
    }

    /**
     * Date轉String
     *
     * @param date    date
     * @param pattern pattern
     * @return String
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String str = null;
        if (nonNull(date)) {
            str = sdf.format(date);
        }
        return str;
    }

    /**
     * long毫秒數轉String
     *
     * @param timeMillis timeMillis
     * @param pattern    pattern
     * @return String
     */
    public static String format(long timeMillis, String pattern) {
        ZoneId zoneIdPlus8 = ZoneId.of("UTC+8");
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timeMillis), zoneIdPlus8);
        return format(localDateTime, pattern);
    }

    /**
     * LocalDateTime轉String
     *
     * @param localDateTime localDateTime
     * @param pattern       pattern
     * @return String
     */
    public static String format(LocalDateTime localDateTime, String pattern) {
        String str = null;
        if (nonNull(localDateTime)) {
            str = localDateTime.format(DateTimeFormatter.ofPattern(pattern, LOCAL));
        }
        return str;
    }

    /**
     * LocalDat轉String
     *
     * @param localDate localDate
     * @param pattern   pattern
     * @return String
     */
    public static String format(LocalDate localDate, String pattern) {
        String str = null;
        if (nonNull(localDate)) {
            str = localDate.format(DateTimeFormatter.ofPattern(pattern, LOCAL));
        }
        return str;
    }

    /**
     * Timestamp轉ROC String
     *
     * @param timestamp timestamp
     * @param pattern   pattern
     * @return String
     */
    public static String formatRoc(Timestamp timestamp, String pattern) {
        String result = null;
        LocalDateTime localDateTime = parseLocalDateTime(timestamp);
        if (nonNull(localDateTime)) {
            MinguoDate minguoDate = MinguoDate.from(localDateTime.toLocalDate());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern, LOCAL);
            ChronoLocalDateTime<MinguoDate> minguoDateTime = minguoDate.atTime(localDateTime.toLocalTime());
            result = minguoDateTime.format(dtf);
        }
        return result;
    }

    /**
     * LocalDate轉ROC String
     *
     * @param adDate     adDate
     * @param rocPattern rocPattern
     * @return String
     * @throws ParseException ParseException
     */
    public static String formatRoc(LocalDate adDate, String rocPattern) throws ParseException {
        return convertAd2Roc(format(adDate, "yyyy-MM-dd"), "yyyy-MM-dd", rocPattern);
    }

    /**
     * AD String轉ROC String
     *
     * @param adStr      adStr
     * @param adPattern  adPattern
     * @param rocPattern rocPattern
     * @return String
     * @throws ParseException exception
     */
    public static String convertAd2Roc(String adStr, String adPattern, String rocPattern) throws ParseException {
        if (StringUtil.isBlank(adStr)) {
            return null;
        }
        Timestamp timestamp = parseTimestamp(adStr, adPattern);
        return formatRoc(timestamp, rocPattern);
    }

    /**
     * AD String調整pattern
     *
     * @param adStr           adStr
     * @param adBeforePattern adBeforePattern
     * @param adAfterPattern  adAfterPattern
     * @return String
     */
    public static String convertAdPattern(String adStr, String adBeforePattern, String adAfterPattern) {
        if (checkPattern(adStr, adBeforePattern)) {
            return format(parseLocalDate(adStr, adBeforePattern), adAfterPattern);
        }
        if (checkPattern(adStr, adBeforePattern)) {
            return format(parseLocalDateTime(adStr, adBeforePattern), adAfterPattern);
        }
        return null;
    }

    /**
     * ROC String轉AD String
     *
     * @param rocStr     rocStr
     * @param rocPattern rocPattern
     * @param adPattern  adPattern
     * @return String
     */
    public static String convertRoc2Ad(String rocStr, String rocPattern, String adPattern) {
        return format(convertRoc2LocalDate(rocStr, rocPattern), adPattern);
    }

    /**
     * ROC String轉LocalDate
     *
     * @param rocStr     rocStr
     * @param rocPattern rocPattern
     * @return LocalDate
     */
    public static LocalDate convertRoc2LocalDate(String rocStr, String rocPattern) {
        if (StringUtil.isBlank(rocStr)) {
            return null;
        }
        Chronology chrono = MinguoChronology.INSTANCE;
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder().parseLenient()
                .appendPattern(rocPattern).toFormatter().withChronology(chrono)
                .withDecimalStyle(DecimalStyle.of(Locale.getDefault()));
        ChronoLocalDate chronoLocalDate = chrono.date(dateTimeFormatter.parse(rocStr));
        return LocalDate.from(chronoLocalDate);
    }

    /**
     * 民國西元年互轉
     *
     * @param dateStr      dateStr
     * @param beforeFormat beforeFormat
     * @param afterFormat  afterFormat
     * @return String
     * @throws ParseException exception
     * @deprecated
     */
    @Deprecated
    private static String convertRocAndAd(String dateStr, String beforeFormat, String afterFormat) throws ParseException {
        if (StringUtil.isBlank(dateStr)) {
            return null;
        }
        SimpleDateFormat df4 = new SimpleDateFormat(beforeFormat);
        SimpleDateFormat df2 = new SimpleDateFormat(afterFormat);
        Calendar cal = Calendar.getInstance();
        cal.setTime(df4.parse(dateStr));
        if (cal.get(Calendar.YEAR) > 1492)
            cal.add(Calendar.YEAR, -1911);
        else
            cal.add(Calendar.YEAR, +1911);
        return df2.format(cal.getTime());
    }

    /**
     * 取得兩日期中最小的timestamp
     *
     * @param timestamp1 timestamp1
     * @param timestamp2 timestamp2
     * @return Timestamp
     */
    public static Timestamp getMin(Timestamp timestamp1, Timestamp timestamp2) {
        if (nonNull(timestamp1) && nonNull(timestamp2)) {
            return timestamp1.before(timestamp2) ? timestamp1 : timestamp2;
        } else {
            return isNull(timestamp2) ? timestamp1 : timestamp2;
        }
    }

    /**
     * 取得兩日期中最大的timestamp
     *
     * @param timestamp1 timestamp1
     * @param timestamp2 timestamp2
     * @return Timestamp
     */
    public static Timestamp getMax(Timestamp timestamp1, Timestamp timestamp2) {
        if (nonNull(timestamp1) && nonNull(timestamp2)) {
            return timestamp1.after(timestamp2) ? timestamp1 : timestamp2;
        } else {
            return isNull(timestamp2) ? timestamp1 : timestamp2;
        }
    }

    /**
     * 取得Current Timestamp
     *
     * @return Timestamp
     */
    @Deprecated
    public static Timestamp getCurrentTimestamp() {
        return Timestamp.valueOf(now());
    }

    /**
     * 取得Current LocalDateTime
     *
     * @return LocalDateTime
     */
    public static LocalDateTime now() {
        ZoneId zoneIdPlus8 = ZoneId.of("UTC+8");
        return LocalDateTime.ofInstant(Instant.now(), zoneIdPlus8);
    }

    /**
     * 取得Current String
     *
     * @return String
     */
    public static String now(String pattern) {
        return now().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 取得Current ROC String
     *
     * @param pattern pattern
     * @return String
     */
    public static String nowAsRoc(String pattern) {
        LocalDateTime localDateTime = LocalDateTime.now();
        MinguoDate minguoDate = MinguoDate.from(localDateTime.toLocalDate());
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(pattern, LOCAL);
        ChronoLocalDateTime<MinguoDate> minguoDateTime = minguoDate.atTime(localDateTime.toLocalTime());
        return minguoDateTime.format(dtf);
    }

    /**
     * 取得日期區間秒數
     *
     * @param start start
     * @param end   end
     * @return long
     */
    public static long getSecondBetweenTime(LocalDateTime start, LocalDateTime end) {
        Duration duration = Duration.between(start, end);
        return duration.getSeconds();
    }

    /**
     * 取得當天開始日期時間
     *
     * @param localDate localDate
     * @return LocalDateTime
     */
    public static LocalDateTime toStartOfDay(LocalDate localDate) {
        return localDate == null ? null : localDate.atStartOfDay();
    }

    /**
     * 取得日期
     *
     * @param localDateTime localDateTime
     * @return LocalDate
     */
    public static LocalDate toLocalDate(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.toLocalDate();
    }

    /**
     * 取得兩個日期間的天數
     * @param date1 date1
     * @param date2 date2
     * @return long
     */
    public static long getBetweenDays(LocalDate date1, LocalDate date2) {
        return ChronoUnit.DAYS.between(date1, date2);
    }

    /**
     * 日期格式檢查
     *
     * @param time    time
     * @param pattern pattern
     * @return boolean
     */
    private static boolean checkPattern(String time, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern.replace("y", "u"), LOCAL).withResolverStyle(ResolverStyle.STRICT);
        try {
            return formatter.parseUnresolved(time, new ParsePosition(0)) != null;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}
