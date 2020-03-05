package com.ru.progmaservice.progmalab;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class dateutil {

    public static Date getCurrentDate(){
        return Calendar.getInstance().getTime();
     }

    public static String getCurrentDateString(boolean isShowTime){
        Date currentTime = Calendar.getInstance().getTime();
        String format;
        if(isShowTime)
                format = "dd ММ yyyy HH:mm:ss";
            else
                format = "dd ММ yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(currentTime);

    }

    public static String getCurrentDateString(String format){
        Date currentTime = Calendar.getInstance().getTime();
        if(format.length() == 0) {
            format = "yyyyММdd_HHmmss";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(currentTime);

    }

    public static String getDateString(Date currentTime, boolean isShowTime){
        String format;
        if(isShowTime)
            format = "dd ММ yyyy HH:mm:ss";
        else
            format = "dd ММ yyyy";

        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(currentTime);

    }

    public static Date convertStringToData(String pattern, String data, String time){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date d = sdf.parse(String.format("%s%s", data, time));
            return d;
        } catch (ParseException ex) {
            Log.v("Exception", ex.getLocalizedMessage());
        }

        return null;
    }

    public static String convertDateToString(String pattern, Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern); // Set your date format
        return  sdf.format(date); // Get Date String according to date format
    }

    /*Сравниваем с системной датой*/
    public static boolean isDataAfterSystemData(String pattern, String data, String time){
        Date my_data = convertStringToData(pattern, data, time);
        Date currentTime = Calendar.getInstance().getTime();
        return !my_data.after(currentTime);
    }


    public static Date addTimeToDate(Date currentDate, int year, int month, int day, int hour, int minute, int second){
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        // manipulate date
        c.add(Calendar.YEAR, year);
        c.add(Calendar.MONTH, month);
        c.add(Calendar.DATE, day); //same with c.add(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.HOUR, hour);
        c.add(Calendar.MINUTE, minute);
        c.add(Calendar.SECOND, second);
        return c.getTime();
    }


}
