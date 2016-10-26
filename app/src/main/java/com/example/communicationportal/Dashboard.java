package com.example.communicationportal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import HttpConnection.CustomAlertDialog;

public class Dashboard extends AppCompatActivity {
    Button button;
    RadioButton c1, c2,c3, c4, c5,c6, c7, c8, c9, c10;
    String check="";
    CustomAlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        c1 = (RadioButton)findViewById(R.id.checkBox1);
        c2 = (RadioButton)findViewById(R.id.checkBox2);
        c3 = (RadioButton)findViewById(R.id.checkBox3);
        c4 = (RadioButton)findViewById(R.id.checkBox4);
        c5 = (RadioButton)findViewById(R.id.checkBox5);
        c6 = (RadioButton)findViewById(R.id.checkBox6);
        c7 = (RadioButton)findViewById(R.id.checkBox7);
        c8 = (RadioButton)findViewById(R.id.checkBox8);
        c9 = (RadioButton)findViewById(R.id.checkBox9);
        c10 = (RadioButton)findViewById(R.id.checkBox10);
        button = (Button)findViewById(R.id.button);
        try {
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (c1.isChecked())
                        check = c1.getText().toString();
                    if (c2.isChecked())
                        check = c2.getText().toString();
                    if (c3.isChecked())
                        check = c3.getText().toString();
                    if (c4.isChecked())
                        check = c4.getText().toString();
                    if (c5.isChecked())
                        check = c5.getText().toString();
                    if (c6.isChecked())
                        check = c6.getText().toString();
                    if (c7.isChecked())
                        check = c7.getText().toString();
                    if (c8.isChecked())
                        check = c8.getText().toString();
                    if (c9.isChecked())
                        check = c9.getText().toString();
                    if (c10.isChecked())
                        check = c10.getText().toString();
                    Log.d("V", check);
                    if(check != "") {
                        Intent intent = new Intent(Dashboard.this, MapsActivity.class);
                        intent.putExtra("Types", check);
                        check = "";
                        startActivity(intent);
                    }else {

                        alertDialog = new CustomAlertDialog(Dashboard.this);
                        alertDialog.showOkDialog("Please select atleast one type to continue");
                    }
                         }
            });
        }catch (Exception e) {
            Log.d("Exception", e.toString());
        }
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Dashboard.this, About.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Dashboard.this, FirstScreen.class);
        startActivity(intent);
    }
}
//Code written by Vikram
