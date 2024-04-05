package com.vnpay.utils;

//import com.vnpay.constants.VNConstants;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class TimeUtil {
//    public static String timestampToDate(long epochMilli, String dateFormat) {
//        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli),
//            TimeZone.getTimeZone(VNConstants.TIME_ZONE_GMT_0).toZoneId()).format(DateTimeFormatter.ofPattern(dateFormat));
//    }
//
//    public static int getDateHour(long epochMilli) {
//        return LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), TimeZone.getTimeZone(VNConstants.TIME_ZONE_GMT_0).toZoneId()).getHour();
//    }

    public static int getDayOfWeekFromDateString(String date, String dateFormat) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(dateFormat));
            return localDate.getDayOfWeek().getValue();
        } catch (DateTimeParseException ignored) {
        }
        return -1;
    }

    public static int getDayOfMonthFromDateString(String date, String dateFormat) {
        try {
            LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(dateFormat));
            return localDate.getDayOfMonth();
        } catch (DateTimeParseException ignored) {
        }
        return -1;
    }

    public static String getTodayDate(String dateFormat) {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public static String getTodayDateTime(String dateFormat) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateFormat));
    }

    public static long dateToTimestamp(String date, String dateFormat) {
        return LocalDateTime.parse(normalizeDateString(date), DateTimeFormatter.ofPattern(dateFormat)).toEpochSecond(ZoneOffset.ofHours(7));
    }

    public static long dateToTimestampMillisecond(String date, String dateFormat) {
        return LocalDateTime
            .parse(normalizeDateString(date), DateTimeFormatter.ofPattern(dateFormat))
            .toEpochSecond(ZoneOffset.ofHours(7)) * 1000;
    }

    public static String normalizeDateString(String date) {
        String result = date;

        if (date.contains("T")) result = result.replaceFirst("T", " ");
        if (date.contains("Z")) result = result.replaceFirst("Z", " ");
        if (date.contains(".")) result = result.substring(0, date.indexOf("."));

        return result.trim();
    }

    public static List<String> getLatestDays(String finishDate, String dateFormat, int dateNumb) {
        List<String> dateList = new ArrayList<>();
        LocalDate localDate = LocalDate.parse(finishDate, DateTimeFormatter.ofPattern(dateFormat));

        for (int i = 1; i <= dateNumb; i++) {
            dateList.add(localDate.minusDays(i).toString());
        }

        return dateList;
    }

    public static String minusDate(String date, String dateFormat, int dateNumb) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(dateFormat));
        return localDate.minusDays(dateNumb).toString();
    }

    public static String addDate(String date, String dateFormat, int dateNumb) {
        LocalDate localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern(dateFormat));
        return localDate.plusDays(dateNumb).toString();
    }
}
