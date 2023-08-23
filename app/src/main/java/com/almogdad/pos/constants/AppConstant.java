package com.almogdad.pos.constants;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

public class AppConstant {

    public static double taxRate = 5;
    public static Integer DecimalPoints = 2;
    public static boolean isExclusive = false;
    public static String POS_Type = "";
    @SuppressLint("SimpleDateFormat")
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


    public static double getTotal (double price, int qty){
        return round(price * qty, DecimalPoints);
    }

    public static double taxCalculator (double amount, boolean isExclusive){
        if(isExclusive) {
            return (amount * (1 + taxRate / 100)) - amount;
        }else{
            return amount - (amount / (1 + taxRate / 100));
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static double calculateTotalWithTax(double total) {
        double tax = taxCalculator(total, true);
        if (isExclusive) {
            return round(total + tax, DecimalPoints);
        } else {
            return round(total, DecimalPoints);
        }
    }

}
