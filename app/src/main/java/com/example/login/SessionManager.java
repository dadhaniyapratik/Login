package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by LENOVO on 23-12-2015.
 */
public class SessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context c){
        this.context =c;
        pref=context.getSharedPreferences("MyPref",Context.MODE_PRIVATE);
        editor =pref.edit();
    }

    public void createLoginSession(String email, String password){
        editor.putBoolean("isLoggedIn",true);
        editor.putString("mail", email);
        editor.putString("pass", password);
        editor.apply();
    }


    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(i);
    }


}
