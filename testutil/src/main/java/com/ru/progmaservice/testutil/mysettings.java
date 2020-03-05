package com.ru.progmaservice.testutil;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;
public class mysettings {
    // именя файла настроек
    public static final String APP_PREFERENCES = "mysettings";

    public static final String APP_PREFERENCES_ACTIVITY = "activity";


    private static SharedPreferences instance;
    public static SharedPreferences Init(AppCompatActivity activity, String activity4start) {
        if (instance == null)
            instance = activity.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

        SetActivities(activity4start);

        return instance;
    }

    public static String GetActivities()
    {
        if(instance != null
                && instance.contains(APP_PREFERENCES_ACTIVITY))
            return instance.getString(APP_PREFERENCES_ACTIVITY, "");
        return "";
    }

    public static void SetActivities(String activity) {
        SharedPreferences.Editor editor = instance.edit();
        editor.putString(mysettings.APP_PREFERENCES_ACTIVITY, activity);
        editor.apply();
    }
}
