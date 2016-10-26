package com.example.communicationportal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import HttpConnection.CustomAlertDialog;

public class ChatScreen extends AppCompatActivity {
    CustomAlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        alertDialog = new CustomAlertDialog(ChatScreen.this);
        alertDialog.showOkDialog("No conversations found");
    }
}
