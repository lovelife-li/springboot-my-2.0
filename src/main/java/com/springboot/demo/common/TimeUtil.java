/*
 * *********************************************************************************************
 *    Copyright (c) 2019. YNT CO., LTD. All Rights Reserved.
 *    FileName    : TimeUtil.java
 *    Date        : 2019-4-20
 *    Author      : 梁宇 （Eid Leung）
 *    Module      : TimeUtil
 *    Function    :
 *  --------------------------------------------------------------------------------------------
 *    Modify History
 *    NO        Date        Modifier        Modified Content
 * *********************************************************************************************
 */

package com.springboot.demo.common;

import lombok.extern.log4j.Log4j2;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

/**
 * 时间的工具类
 *
 * @author ioiogoo
 * @date 2019-03-15 10:36
 */
@Log4j2
public class TimeUtil {

    private TimeUtil() {
    }

    private static final String COMMON_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String COMMON_DATE_FORMAT = "yyyy-MM-dd";
    private static final String COMMON_TIME_FORMAT = "HH:mm:ss";

    /**
     * 获得格式化好的时间
     *
     * @return
     */
    public static String getFormatTime() {
        return getFormatTime(COMMON_DATE_TIME_FORMAT);
    }

    /**
     * 获得格式化好的时间
     *
     * @return
     */
    public static String getFormatTime(String patten) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(patten);
        return LocalDateTime.now().format(formatter);
    }

    /**
     * 获取时间间隔，单位：s
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getIntervalSecond(long startTime, long endTime) {
        return (int) ((endTime - startTime) / 1000);
    }

    /**
     * 得到当前时间
     * <p>
     * string -> Time
     *
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_TIME_FORMAT);
        return sdf.format(new Date());
    }

    /**
     * 得到当前时间
     * <p>
     * string -> Time
     *
     * @return
     */
    public static String getCurrentNanoTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        return sdf.format(new Date());
    }

    public static Time string2Time(String timeStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(COMMON_TIME_FORMAT);
        try {
            Date date = sdf.parse(timeStr);
            return new Time(date.getTime());
        } catch (ParseException e) {
            log.warn(e.getMessage());
        }
        return null;
    }

    public static java.sql.Date string2Date(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_FORMAT);
        try {
            Date date = sdf.parse(dateStr);
            return new java.sql.Date(date.getTime());
        } catch (ParseException e) {
            log.warn(e.getMessage());
        }
        return null;
    }

    public static String time2String(Time time) {
        SimpleDateFormat sdf = new SimpleDateFormat(COMMON_TIME_FORMAT);
        return sdf.format(time);
    }

    public static String date2String(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(COMMON_DATE_FORMAT);
        return sdf.format(date);
    }

    /**
         * 通过64位元时间戳获取LocalDateTime对象
     * desc: 
     * @param timestamp
     * @return
     * LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeOf64BitTimestamp(Long timestamp) {
        if (Objects.nonNull(timestamp)) {
            Instant instant = Instant.ofEpochMilli(timestamp);
            ZoneId zone = ZoneId.systemDefault();
            return LocalDateTime.ofInstant(instant, zone);
        }
        return null;
    }

    /**
         * 通过32位元时间戳获取 LocalDateTime 对象
     * desc: 
     * @param timestamp
     * @return
     * LocalDateTime
     */
    public static LocalDateTime getLocalDateTimeOf32BitTimestamp(Long timestamp) {
        if (Objects.nonNull(timestamp)) {
            Instant instant = Instant.ofEpochSecond(timestamp);
            ZoneId zone = ZoneId.systemDefault();
            return LocalDateTime.ofInstant(instant, zone);
        }
        return null;
    }

    /**
         * 获取64位元时间戳
     * desc: 
     * @param localDateTime
     * @return
     * Long
     */
    public static Long get64BitTimestampOfLocalDateTime(LocalDateTime localDateTime) {
        if (Objects.nonNull(localDateTime)) {
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = localDateTime.atZone(zone).toInstant();
            return instant.toEpochMilli();
        }
        return null;
    }

    /**
         *  获取32位元时间戳
     * desc: 
     * @param localDateTime
     * @return
     * Long
     */
    public static Long get32BitTimestampOfLocalDateTime(LocalDateTime localDateTime) {
        if (Objects.nonNull(localDateTime)) {
            ZoneId zone = ZoneId.systemDefault();
            Instant instant = localDateTime.atZone(zone).toInstant();
            return instant.getEpochSecond();
        }
        return null;
    }

    /**
         *  把字符串解析为LocalDateTime对象
     * desc: 
     * @param time
     * @param format
     * @return
     * LocalDateTime
     */
    public static LocalDateTime parseStringToLocalDateTime(String time, String format) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        return LocalDateTime.parse(time, df);
    }

    /**
         * 把字符串解析为LocalDateTime对象
     * desc: 
     * @param time
     * @return
     * LocalDateTime
     */
    public static LocalDateTime parseStringToLocalDateTime(String time) {
        return parseStringToLocalDateTime(time, COMMON_DATE_TIME_FORMAT);
    }

    /**
         *  把LocalDateTime对象解析为字符串
     * desc: 
     * @param localDateTime
     * @param format
     * @return
     * String
     */
    public static String getFormatLocalDateTime(LocalDateTime localDateTime, String format) {
        if (Objects.nonNull(localDateTime)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            return localDateTime.format(formatter);
        }
        return null;
    }

    /**
         * 把LocalDateTime对象解析为字符串
     * desc: 
     * @param localDateTime
     * @return
     * String
     */
    public static String getFormatLocalDateTime(LocalDateTime localDateTime) {
        return getFormatLocalDateTime(localDateTime, COMMON_DATE_TIME_FORMAT);
    }
}
