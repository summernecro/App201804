package com.summer.record.tool;

//by summer on 2018-08-17.

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static int toYear(String time){
        long t = Long.parseLong(time)*1000;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(t));
        return calendar.get(Calendar.YEAR);
    }
}
