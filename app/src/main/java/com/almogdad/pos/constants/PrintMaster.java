package com.almogdad.pos.constants;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;

public class PrintMaster {

    private static final String TAG = "PrintMaster";

    // Printer Object
//    public static Printer print;

    public static Context PrintActivity;

    public PrintMaster(Context printActivity) {
        PrintActivity = printActivity;
    }

    public static void initializePrinters() {
        switch (AppConstant.POS_Type) {
            case "type1":
                // Ininlization the Type 1 printer
                break;
            case "type2":
                try {
                    // Ininlization the Type 2 printer

                }catch (Exception exception){
                    Log.e(TAG, exception.getMessage() + "");
                }

                break;


        }
    }

    public static void printText(String string, int size) {
        try {

            switch (AppConstant.POS_Type) {
                case "type1":
                    //print.printText(string, size);
                    break;
                case "type2":
                    // print.printText(string, size);
                    break;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex + "");
        }
    }

    public static void printBitmap(String string) {
        try {

            switch (AppConstant.POS_Type) {
                case "type1":
                    //print.printBitmap(string);
                    break;
                case "type2":
                    // print.printBitmap(string);
                    break;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex + "");
        }
    }
}
