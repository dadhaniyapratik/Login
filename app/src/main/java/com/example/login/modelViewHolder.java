package com.example.login;

import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by LENOVO on 17-12-2015.
 */
public class modelViewHolder {
    private CheckBox checkBox;
    private TextView textView;

    public modelViewHolder() {
    }

    public modelViewHolder(TextView textView, CheckBox checkBox) {
        this.checkBox = checkBox;
        this.textView = textView;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public TextView getTextView() {
        return textView;
    }

    public void setTextView(TextView textView) {
        this.textView = textView;
    }
}
