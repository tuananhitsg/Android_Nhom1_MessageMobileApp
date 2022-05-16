package com.example.nhom1_messagemobileapp.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CustomeDateTime {
    static DateFormat datetimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    static DateFormat hMFormat = new SimpleDateFormat("HH:mm");
    static DateFormat dayFormat = new SimpleDateFormat("EEEE");

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
            if(d.getDate() == today().getDate()){
                return hMFormat.format(d);
            }else{
                return dayFormat.format(d)+" lúc "+hMFormat.format(d);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
