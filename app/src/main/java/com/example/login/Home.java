package com.example.login;

import android.content.ContentValues;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.login.dbhelper.DBKeys;
import com.example.login.dbhelper.DatabaseHelper;

public class Home extends AppCompatActivity {
    private ListView mainListView = null;
    private ArrayAdapter<model> listAdapter = null;
    SessionManager session;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        session = new SessionManager(getApplicationContext());

        mainListView = (ListView) findViewById(R.id.listView);


        ArrayList<model> modelList = new ArrayList<model>();
        for(int i=0;i<20;i++){
            modelList.add(new model(i+1,"item_"+(i+1)));
        }


        listAdapter = new CustomAdapter(this, modelList);
        mainListView.setAdapter(listAdapter);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String mail = bundle.getString("value");

        String items = DatabaseHelper.getInstance(Home.this).getSelectedItems(mail);
        if (!items.equals("")) {
            String[] itemId = items.split(",");
            List<String> list = Arrays.asList(itemId);
            int i;
            for (i = 0; i < modelList.size(); i++) {
                model model=modelList.get(i);
                if (list.contains(String.valueOf(model.getRowId()))) {

                    model.setChecked(true);
                }

            }
        }
        listAdapter.notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuitem, menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        String array = "";
        for (int i = 0; i < listAdapter.getCount(); i++) {
            model mod = listAdapter.getItem(i);
            if (mod.isChecked()) {
                array = array + mod.getRowId() + ",";
            }
        }

        ContentValues cv = new ContentValues();
        cv.put(DBKeys.SELECTEDID, array);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String mail = bundle.getString("value");

        DatabaseHelper.getInstance(Home.this).update_id(mail, cv);

        Toast.makeText(getApplicationContext(), "Your data stored", Toast.LENGTH_LONG).show();
        session.logoutUser();
        Intent i = new Intent(Home.this, LoginActivity.class);
        startActivity(i);
        finish();

        return true;
    }

}