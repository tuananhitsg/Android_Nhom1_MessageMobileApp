package com.example.nhom1_messagemobileapp.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class CustomeDateTime {
    static DateFormat datetimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    static DateFormat hMFormat = new SimpleDateFormat("HH:mm");
    static DateFormat dayFormat = new SimpleDateFormat("EEEE");
    static DateFormat dateMonthFormat = new SimpleDateFormat("dd MMM");
    static DateFormat dateMonthYearFormat = new SimpleDateFormat("dd MMM yyyy");

    public static Date today(){
        return new Date();
    }

    public static String dateFormat(Date d){
        try {
            return dateFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String datetimeFormat(Date d){
        try {
            return datetimeFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String HMFormat(Date d){
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(d);
            Log.e("------date format", calendar.toString());
            Log.e("date format", d.toString());
            Date today = today();
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(today);

            if(d.getDate() == today.getDate()){
                Log.e("date format", hMFormat.format(d));
                return hMFormat.format(d);
            }else if(calendar.get(Calendar.WEEK_OF_YEAR) == calendar2.get(Calendar.WEEK_OF_YEAR)){
                Log.e("date format", dayFormat.format(d)+" lúc "+hMFormat.format(d));
                return dayFormat.format(d)+" lúc "+hMFormat.format(d);
            }else if(calendar.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)){
                Log.e("date format", dateMonthFormat.format(d)+" lúc "+hMFormat.format(d));
                return dateMonthFormat.format(d)+" lúc "+hMFormat.format(d);
            }else{
                Log.e("date format", dateMonthYearFormat.format(d)+" lúc "+hMFormat.format(d));
                return dateMonthYearFormat.format(d)+" lúc "+hMFormat.format(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
