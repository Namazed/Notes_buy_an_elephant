package com.namazed.notesbuyanelephant;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;

public class Utils {

    public static String getDate(long date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        return dateFormat.format(date);
    }

    public static String getTime(long time) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH.mm");//todo 24hour or 12hour (if it, then need 'pm/am')
        return timeFormat.format(time);
    }

    public static String getFullDate(long date) {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd.MM.yy  HH.mm");
        return fullDateFormat.format(date);
    }
}
