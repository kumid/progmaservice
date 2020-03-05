package com.ru.progmaservice.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ru.progmaservice.progmalab.fcm.mysettings;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mysettings.Init(this, "com.ru.progmaservice.test.MainActivity");

    }
}
