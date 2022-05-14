package com.example.nhom1_messagemobileapp.utils;

public class Random {
    public static int generateTicketNumber(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
