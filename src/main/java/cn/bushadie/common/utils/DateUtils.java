package cn.bushadie.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 时间工具类
 *
 * @author ruoyi
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static String YYYY="yyyy" ;

    public static String YYYY_MM="yyyy-MM" ;

    public static String YYYY_MM_DD="yyyy-MM-dd" ;

    public static String YYYYMMDDHHMMSS="yyyyMMddHHmmss" ;

    public static String YYYY_MM_DD_HH_MM_SS="yyyy-MM-dd HH:mm:ss" ;

    private static String[] parsePatterns={
            "yyyy-MM-dd" ,"yyyy-MM-dd HH:mm:ss" ,"yyyy-MM-dd HH:mm" ,"yyyy-MM" ,
            "yyyy/MM/dd" ,"yyyy/MM/dd HH:mm:ss" ,"yyyy/MM/dd HH:mm" ,"yyyy/MM" ,
            "yyyy.MM.dd" ,"yyyy.MM.dd HH:mm:ss" ,"yyyy.MM.dd HH:mm" ,"yyyy.MM"};

    /**
     * 获取当前Date型日期
     *
     * @return Date() 当前日期
     */
    public static Date getNowDate() {
        return new Date();
    }

    /**
     * 获取当前日期, 默认格式为yyyy-MM-dd
     *
     * @return String
     */
    public static String getDate() {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime() {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow() {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format) {
        return parseDateToStr(format,new Date());
    }

    public static final String dateTime(final Date date) {
        return parseDateToStr(YYYY_MM_DD,date);
    }

    public static final String parseDateToStr(final String format,final Date date) {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format,final String ts) {
        try {
            return new SimpleDateFormat(format).parse(ts);
        }catch(ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日期路径 即年/月/日 如2018/08/08
     */
    public static final String datePath() {
        Date now=new Date();
        return DateFormatUtils.format(now,"yyyy/MM/dd");
    }

    /**
     * 日期路径 即年/月/日 如20180808
     */
    public static final String dateTime() {
        Date now=new Date();
        return DateFormatUtils.format(now,"yyyyMMdd");
    }

    /**
     * 日期型字符串转化为日期 格式
     */
    public static Date parseDate(Object str) {
        if(str==null) {
            return null;
        }
        try {
            return parseDate(str.toString(),parsePatterns);
        }catch(ParseException e) {
            return null;
        }
    }

    private static SimpleDateFormat formatter_yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String getDateTime(Date date){
        return formatter_yyyy_MM_dd.format(date);
    }

    /**
     * 是否在时间段内
     * @param before
     * @param after
     * @return  0  在事件段
     *          -1  早于时间段
     *          +1  晚于时间段
     */
    public static int isBetween(Date before,Date after){
        Date now = new Date();
        boolean f1=before.before(now);
        boolean f2=now.before(after);
        if( f1 && f2 ){
            return 0;
        }
        if( !f1 ){
            return -1;
        }
        return 1;
    }
}
