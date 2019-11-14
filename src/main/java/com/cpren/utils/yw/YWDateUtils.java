package com.cpren.utils.yw;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 *
 * @author cdxu@iyunwen.com on 2018/3/13
 */
public class YWDateUtils {
    public static final String YEAR_FORMAT = "yyyy";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_FORMAT_PURE = "yyyyMMdd";
    public static final String DATE_HOUR_FORMAT = "yyyy-MM-dd HH";
    public static final String DATE_MINUTE_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String HOUR_FORMAT = "HH";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_PURE_SECOND = "yyyyMMddHHmmss";

    /**
     * 禁止实例化
     */
    private YWDateUtils() {

    }

    /**
     * 获取当前时间戳
     *
     * @return 当前时间戳
     */
    public static Timestamp currentTime() {
        return Timestamp.valueOf(LocalDateTime.now());
    }

    /**
     * 获取前天零点时间
     *
     * @return 前天零点时间
     */
    public static Timestamp beforeYesterdayZero() {
        return Timestamp.valueOf(LocalDateTime.of(LocalDate.now().plusDays(-2), LocalTime.MIN));
    }

    /**
     * 获取昨日零点时间
     *
     * @return 昨日零点时间
     */
    public static Timestamp yesterdayZero() {
        return Timestamp.valueOf(LocalDateTime.of(LocalDate.now().plusDays(-1), LocalTime.MIN));
    }

    /**
     * 获取今日零点时间
     *
     * @return 今日零点时间
     */
    public static Timestamp todayZero() {
        return Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.MIN));
    }

    /**
     * 获取明日零点时间
     *
     * @return 明日零点时间
     */
    public static Timestamp tomorrowZero() {
        return Timestamp.valueOf(LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIN));
    }

    /**
     * 获取七天前零点时间
     *
     * @return 明日零点时间
     */
    public static Timestamp beforeServenDayZero() {
        return Timestamp.valueOf(LocalDateTime.of(LocalDate.now().plusDays(-7), LocalTime.MIN));
    }

    /**
     * 获取指定时间跨度的零点时间
     *
     * @return
     */
    public static Timestamp beforeZero(Timestamp time, Integer days) {
        return Timestamp.valueOf(LocalDateTime.of(time.toLocalDateTime().toLocalDate().plusDays(days), LocalTime.MIN));
    }

    /**
     * date字符串转date对象
     *
     * @param dateStr date字符串
     * @param format  格式化模式
     * @return date对象
     */
    public static Date formatDateStr(String dateStr, String format) {
        if (YWStringUtils.isEmpty(dateStr) || YWStringUtils.isEmpty(format)) {
            return null;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * date 对象转字符串
     *
     * @param date   date对象
     * @param format 格式化模式
     * @return date字符串
     */
    public static String dateFormat(Date date, String format) {
        if (date == null || YWStringUtils.isEmpty(format)) {
            return null;
        }

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.format(date);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 系统时间的获取
     *
     * @return 系统时间
     */
    public static Date getSystemDate() {
        Calendar systemDate = Calendar.getInstance();
        return systemDate.getTime();
    }

    /**
     * @param dateFormatStr 指定日期格式
     * @return 系统时间
     * @describe 根据格式取得系统时间
     * @author y.you
     * @date 2018年10月15日 21:17
     * @version V5.0
     */
    public static String getSystemDate(String dateFormatStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatStr);
        Date systemDate = getSystemDate();
        return dateFormat.format(systemDate);
    }

    /**
     * 设置最大时间戳
     */
    public static long getMaxTime(){
        String dateString = "5000-12-30 00:00:00";
        SimpleDateFormat format = new SimpleDateFormat(YWDateUtils.DATE_TIME_FORMAT);
        try {
            Date date = format.parse(dateString);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 30000 * 30000;
        }
    }
}
