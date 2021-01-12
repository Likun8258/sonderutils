package com.sonder.demo.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 *
 * @author zero
 */
public final class TimeUtils {

    /**
     * 默认使用系统当前时区
     */
    private static final ZoneId ZONE = ZoneId.systemDefault();

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    private static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    private static final String TIME_NOFUII_FORMAT = "yyyyMMddHHmmss";

    private static final String REGEX = "\\:|\\-|\\s";

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 默认时区
     */
    public static final ZoneOffset DEFAULT_ZONEOFFSET = ZoneOffset.of("+8");

    /**
     * 一天的毫秒时间
     */
    public static final long ONE_DAY_MILLI = 24 * 60 * 60 * 1000L;


    /**
     * 评讯系统中的时间变化
     */

    public static  String getTimeStringStyle(LocalDateTime from,LocalDateTime to){

        long num = 0L;
        //1分钟内
        if((num =chronoUnitBetweenByTime(ChronoUnit.SECONDS,from,to)) < 60){
            return  num + "秒前";
        }
        //1小时内
        if((num =chronoUnitBetweenByTime(ChronoUnit.MINUTES,from,to)) < 60){
            return  num + "分钟前";
        }

        if((num =chronoUnitBetweenByTime(ChronoUnit.WEEKS,from,to)) < 1){
            //2天内
            if((num =chronoUnitBetweenByTime(ChronoUnit.DAYS,from,to)) < 1){

                if(chronoUnitBetweenByTime(ChronoUnit.HALF_DAYS,from,to) == 0){
                    return num +"小时前";
                }else{
                    int minute = from.getMinute();
                    int hour = from.getHour();
                    return "昨天"+(hour<10?"0"+hour:hour)+":"+ (minute<10?"0"+minute:minute);
                }
            }
            return num + "天前";
        }else{
            return timeFormat(DATE_FORMAT_DEFAULT,from);
        }
    }


    public static void main(String[] args) {

        LocalDateTime from = LocalDateTime.of(2019,Month.FEBRUARY,26,22,50);
        LocalDateTime to = LocalDateTime.now();
        System.out.println(getTimeStringStyle(from,to));

    }








    /**
     * localDateTime 转时间戳
     * @param localDateTime
     * @return
     */
    public static  Long locateTimeToLong(LocalDateTime localDateTime){
        ZoneOffset zoneOffset = ZoneOffset.of("+8");
        return LocalDateTime.now().toInstant(zoneOffset).toEpochMilli();
    }


    /**
     * 转为默认的日期格式
     * @param time
     * @return
     */
    public static String timeFormat(LocalDateTime time) {
        return timeFormat(DATE_FORMAT_DEFAULT,time);
    }


    /**
     * 根据传入的时间格式返回系统当前的时间
     *
     * @param format string
     * @return
     */
    public static String timeFormat(String format,LocalDateTime time) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        return time.format(dateTimeFormatter);
    }

    /**
     * 将Date转换成LocalDateTime
     *
     * @param d date
     * @return
     */
    public static LocalDateTime dateToLocalDateTime(Date d) {
        Instant instant = d.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime;
    }

    /**
     * 将Date转换成LocalDate
     *
     * @param d date
     * @return
     */
    public static LocalDate dateToLocalDate(Date d) {
        Instant instant = d.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime.toLocalDate();
    }

    /**
     * 将Date转换成LocalTime
     *
     * @param d date
     * @return
     */
    public static LocalTime dateToLocalTime(Date d) {
        Instant instant = d.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime.toLocalTime();
    }

    /**
     * 将Date转换成LocalTime
     *
     * @param lTime
     * @return
     */
    public static LocalDateTime longToToLocalTime(Long lTime) {

        return dateToLocalDateTime(new Date(lTime * 1000));
    }


    /**
     * 将时间戳转换成LocalDate
     *
     * @param lTime
     * @return
     */
    public static LocalDate dateToLocalDate(long lTime) {
        Date date = new Date(lTime);
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        return localDateTime.toLocalDate();
    }


    /**
     * 将时间戳转换成LocalDate
     *
     * @param lTime
     * @return
     */
    public static Date longToDayMax(long lTime) {
        Date date = new Date(lTime);
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        //当天零点
        LocalDateTime start = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MAX);
        return localDateTimeToDate(start);
    }


    /**
     * 获取当天零点
     * @param localDateTime
     * @return
     */
    public static Date getDayStart(LocalDateTime localDateTime){
        LocalDateTime start = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
        return localDateTimeToDate(start);
    }

    /*public static void main(String[] args) {
        System.out.println(getDayStart(LocalDateTime.now().minusDays(1)));

    }*/

    /**
     * 将时间戳转换成LocalDate
     *
     * @param lTime
     * @return
     */
    public static Date longToDayMin(long lTime) {
        Date date = new Date(lTime);
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZONE);
        //当天零点
        LocalDateTime start = LocalDateTime.of(localDateTime.toLocalDate(), LocalTime.MIN);
        return localDateTimeToDate(start);
    }

    /**
     * 获取前N天零点时间
     * @param days
     * @return
     */
    public static Date getOneDayMin(int days,boolean dayFlag,int years,boolean yearFlag){
        //当天零点
        LocalDateTime todayStart = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LocalDateTime oneDayStart = null;
        if(dayFlag){
            oneDayStart = todayStart.minusDays(days);
        }else if(yearFlag){
            oneDayStart = todayStart.minusYears(years);
        }
        Instant instant = oneDayStart.atZone(ZONE).toInstant();
        return Date.from(instant);
    }



    /**
     * 将LocalDate转换成Date
     *
     * @param localDate
     * @return date
     */
    public static Date localDateToDate(LocalDate localDate) {
        Instant instant = localDate.atStartOfDay().atZone(ZONE).toInstant();
        return Date.from(instant);
    }

    /**
     * 将LocalDateTime转换成Date
     *
     * @param localDateTime
     * @return date
     */
    public static Date localDateTimeToDate(LocalDateTime localDateTime) {
        Instant instant = localDateTime.atZone(ZONE).toInstant();
        return Date.from(instant);
    }






    /**
     * 将相应格式yyyy-MM-dd yyyy-MM-dd HH:mm:ss 时间字符串转换成Date
     *
     * @param time string
     * @param format string
     * @return date
     */
    public static Date stringToDate(String time , String format) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern(format);
        if (DATE_FORMAT_DEFAULT.equals(format)) {
            return TimeUtils.localDateTimeToDate(LocalDateTime.parse(time, f));
        } else if (DATE_FORMAT.equals(format)){
            return TimeUtils.localDateToDate(LocalDate.parse(time, f));
        }
        return null;
    }


    /**
     * 根据ChronoUnit枚举计算两个时间差，日期类型对应枚举
     * 注:注意时间格式，避免cu选择不当的类型出现的异常
     *
     * @param cu chronoUnit.enum.key
     * @param d1 date
     * @param d2 date
     * @return
     */
    public static long chronoUnitBetweenByDate(ChronoUnit cu, Date d1, Date d2) {
        return cu.between(TimeUtils.dateToLocalDateTime(d1), TimeUtils.dateToLocalDateTime(d2));
    }


    /**
     * 根据ChronoUnit枚举计算两个时间差，日期类型对应枚举
     * 注:注意时间格式，避免cu选择不当的类型出现的异常
     * @param cu
     * @param from
     * @param to
     * @return
     */
    public static long chronoUnitBetweenByTime(ChronoUnit cu, LocalDateTime from, LocalDateTime to) {
        return cu.between(from,to);
    }







    /**
     * 根据ChronoUnit枚举计算两个时间差，日期类型对应枚举
     * 注:注意时间格式，避免cu选择不当的类型出现的异常
     *
     * @param cu chronoUnit.enum.key
     * @param s1 string
     * @param s2 string
     * @return
     */
    public static Long chronoUnitBetweenByString(ChronoUnit cu, String s1, String s2, String dateFormat) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern(dateFormat);
        if (DATE_FORMAT_DEFAULT.equals(dateFormat)) {
            LocalDateTime l1 = TimeUtils.dateToLocalDateTime(TimeUtils.stringToDate(s1,dateFormat));
            LocalDateTime l2 = TimeUtils.dateToLocalDateTime(TimeUtils.stringToDate(s2,dateFormat));
            return cu.between(l1,l2);
        }
        if (DATE_FORMAT.equals(dateFormat)) {
            LocalDate l1 = TimeUtils.dateToLocalDate(TimeUtils.stringToDate(s1,dateFormat));
            LocalDate l2 = TimeUtils.dateToLocalDate(TimeUtils.stringToDate(s2,dateFormat));
            return cu.between(l1,l2);
        }
        if (TIME_NOFUII_FORMAT.equals(dateFormat)) {
            LocalDateTime l1 = LocalDateTime.parse(s1.replaceAll(REGEX,""), f);
            LocalDateTime l2 = LocalDateTime.parse(s2.replaceAll(REGEX,""), f);
            return cu.between(l1,l2);
        }
        return null;
    }

    /**
     * 根据ChronoUnit枚举计算两个时间相加，日期类型对应枚举
     * 注:注意时间格式，避免cu选择不当的类型出现的异常
     *
     * @param cu chronoUnit.enum.key
     * @param d1 date
     * @param d2 long
     * @return
     */
    public static Date chronoUnitPlusByDate(ChronoUnit cu, Date d1, long d2) {
        return TimeUtils.localDateTimeToDate(cu.addTo(TimeUtils.dateToLocalDateTime(d1),d2));
    }

    /**
     * 将time时间转换成毫秒时间戳
     *
     * @param time string
     * @return
     */
    public static long stringDateToMilli (String time) {
        return TimeUtils.stringToDate(time,DATE_FORMAT_DEFAULT).toInstant().toEpochMilli();
    }

    /**
     * 将毫秒时间戳转换成Date
     *
     * @param time string
     * @return
     */
    public static Date timeMilliToDate (String time) {
        return Date.from(Instant.ofEpochMilli(Long.parseLong(time)));
    }
}