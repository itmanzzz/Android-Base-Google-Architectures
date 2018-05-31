package com.ilives.baseprj.common.util;

import android.util.Log;

import com.ilives.baseprj.BuildConfig;


public class LogUtils {

    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG)
            Log.d(tag, message);
    }

    public static void i(String tag, String message) {
        Log.d(tag, message);
    }

    public static void d(String tag, byte[] data, int size) {
        if (data != null) {
            StringBuilder builder = new StringBuilder();
            builder.append("Buffer size: " + size + ", data ----> ");
            for (int i = 0; i < data.length; i++) {
                builder.append("0x");
                builder.append(HEX[data[i] >> 4]);
                builder.append(HEX[data[i] & 0x0f]);
                builder.append(",");
            }
            Log.d(tag, builder.toString());
        }
    }

    private static String[] HEX = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
}
