package top.jiangliuhong.olcp.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

    public static String formatDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static String formatDateTime(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }

   /* public static Long formatTimestamp(Date date) {
        Timestamp timestamp = new Timestamp(date.getTime());
        return timestamp.getTime();
    }*/


    public static Date parseDate(String source) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(source);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date parseDateTime(String source) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return dateFormat.parse(source);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
