package com.example.login;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.login.dbhelper.DatabaseHelper;

public class model  {
    private long rowId;
    private String name = "";
    private boolean checked = false;

    public model() {
    }

    public model(long id,String name) {
        this.name = name;
        this.rowId = id;
    }

    public model(String name, boolean checked) {
        this.name = name;
        this.checked = checked;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String toString() {
        return name;
    }


    public long getRowId() {

        return rowId;
    }
}