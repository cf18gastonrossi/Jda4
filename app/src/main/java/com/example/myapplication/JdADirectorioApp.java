package com.example.myapplication;

import android.app.Application;

import com.example.myapplication.ui.Repository.Repository;

public class JdADirectorioApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Repository.get(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
