package com.example.login;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.login.dbhelper.DBKeys;
import com.example.login.dbhelper.DatabaseHelper;

public class SignUpActivity extends AppCompatActivity {
    EditText edtName, email, password, cpassword, number, address;
    Button Signup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        edtName = (EditText) findViewById(R.id.edtName);
        email = (EditText) findViewById(R.id.edtEmail);
        password = (EditText) findViewById(R.id.edtPassword);
        cpassword = (EditText) findViewById(R.id.edtConfirmPassword);
        number = (EditText) findViewById(R.id.edtNumber);
        address = (EditText) findViewById(R.id.edtAddress);
        Signup = (Button) findViewById(R.id.signup);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAndRegister();
            }
        });
    }

    private void checkAndRegister() {
        String name = edtName.getText().toString();
        if (name == null || name.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter name", Toast.LENGTH_SHORT).show();
            edtName.requestFocus();
            return;
        }

        String email = this.email.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(DatabaseHelper.getInstance(SignUpActivity.this).checkEmail(email)) {
            if (email.isEmpty() || !email.matches(emailPattern)) {
                Toast.makeText(SignUpActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
                this.email.requestFocus();
                return;
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"Email already Entered",Toast.LENGTH_LONG).show();
            this.email.requestFocus();
            return;
        }
        String number = this.number.getText().toString();
        if (number.length() != 10 || number.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Please enter number of 10 digit", Toast.LENGTH_SHORT).show();
            this.number.requestFocus();
            return;
        }
        String address = this.address.getText().toString();
        if (address.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Please enter address", Toast.LENGTH_SHORT).show();
            this.address.requestFocus();
            return;
        }
        String password = this.password.getText().toString();
        if (password.isEmpty() || password.length() != 6) {
            Toast.makeText(SignUpActivity.this, "Please enter password of 6 character", Toast.LENGTH_SHORT).show();
            this.password.requestFocus();
            return;
        }
        String cpassword = this.cpassword.getText().toString();

        if (cpassword.isEmpty() || !cpassword.equals(password)) {
            Toast.makeText(SignUpActivity.this, "Password not match", Toast.LENGTH_SHORT).show();
            this.cpassword.requestFocus();
            return;
        }

            ContentValues values = new ContentValues();
            values.put(DBKeys.EMAIL, email);
            values.put(DBKeys.PASSWORD, password);
           // values.put(DBKeys.ADDRESS, address);
           // values.put(DBKeys.NAME, name);
            // values.put(DBKeys.NUMBER, number);
             values.put(DBKeys.SELECTEDID,"a");

            DatabaseHelper.getInstance(SignUpActivity.this).insertLoginDetails(values);

            finish();
            Toast.makeText(SignUpActivity.this, "Added Successfully", Toast.LENGTH_LONG).show();
            Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(i);


    }
}
