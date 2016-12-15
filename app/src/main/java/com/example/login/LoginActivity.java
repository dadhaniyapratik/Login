package com.example.login;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.dbhelper.DBKeys;
import com.example.login.dbhelper.DatabaseHelper;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
   EditText email,password;
    Button login,signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final SessionManager session= new SessionManager(getApplicationContext());
      /*  dbHelper=DatabaseHelper.getInstance(LoginActivity.this);
        ArrayList<Item> allItemsList= dbHelper.getAllItems();
        for (Item item:allItemsList)
        {
            Log.e("log_tag","Item: "+item.getTitle());
        }*/

        email =(EditText)findViewById(R.id.etrEmail);
        password = (EditText)findViewById(R.id.etrPassword);
        login = (Button)findViewById(R.id.button);
        signup = (Button)findViewById(R.id.button2);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String mail =email.getText().toString();
                String Password = password.getText().toString();

                String data=DatabaseHelper.getInstance(LoginActivity.this).getdata(mail);
                if(data!=null && Password.equals(data)) {

                    session.createLoginSession(mail, Password);

                    Intent i = new Intent(LoginActivity.this, Home.class);
                    i.putExtra("value",mail);

                    startActivity(i);
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Invalid Email or password",Toast.LENGTH_LONG).show();
                }

            }

        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(i);
            }
        });

    }
}
