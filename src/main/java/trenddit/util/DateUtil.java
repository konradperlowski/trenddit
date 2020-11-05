package trenddit.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    public static String daysAgo(Integer days) {
        return dateToString(ago(days));
    }

    public static Date ago(Integer days) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -days);
        return cal.getTime();
    }

    public static String dateToString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd").format(date);
    }

    public static List<Date> periodOfTime(Integer from, Integer to) {
        List<Date> dateList = new ArrayList<>();
        for (int i = from - 1; i >= to; i--) {
            dateList.add(ago(i));
        }
        return dateList;
    }
}
