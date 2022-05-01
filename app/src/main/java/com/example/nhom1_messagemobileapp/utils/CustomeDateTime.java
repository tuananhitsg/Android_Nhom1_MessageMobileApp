package com.example.nhom1_messagemobileapp.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomeDateTime {
    static DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    static DateTimeFormatter hMFormat = DateTimeFormatter.ofPattern("HH:mm");

    public static String dateFormat(LocalDateTime dt){
        return dateFormat.format(dt);
    }

    public static String datetimeFormat(LocalDateTime dt){
        return datetimeFormat.format(dt);
    }

    public static String HMFormat(LocalDateTime dt){
        return hMFormat.format(dt);
    }


}
