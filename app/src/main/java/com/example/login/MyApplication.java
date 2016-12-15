package com.example.login;

import android.app.Application;

import com.example.login.dbhelper.DatabaseHelper;

/**
 * Created by LENOVO on 17-12-2015.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        DatabaseHelper.getInstance(MyApplication.this);
    }
}
