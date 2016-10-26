package com.example.communicationportal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class Emails extends AppCompatActivity {
    ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emails);
        list = getIntent().getStringArrayListExtra("Emails");
        System.out.print(list);
        Log.d("Emails",list.toString());

    }
}
//Code written by Vikram
