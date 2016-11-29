package com.example.communicationportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.api.services.gmail.model.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import HttpConnection.ServiceMethodListener;

public class Messages extends AppCompatActivity {
    String temp,subject, mailid;
    GetSet getSet = new GetSet();
    List<String> messages = new ArrayList<String>();
    ListMessages listMessages = new ListMessages();
    public com.google.api.services.gmail.Gmail mService = null;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        listView = (ListView)findViewById(R.id.listView4);
        temp = getIntent().getExtras().getString("subject");
        mailid = getIntent().getExtras().getString("email");
        System.out.println(temp.matches("\\w.*"));
        String[] splitString = (temp.split("\\s+"));
        subject = splitString[splitString.length-1];
        mService = getSet.getmService();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        messages = listMessages.listMessagesMatchingQuery(mService, "me", "from: cvikram785@gmail.com"+" subject: "+subject);
                        Log.d("messages",messages.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                printToScreen(messages);
                            }
                        });

                    }catch (IOException e){
                        e.printStackTrace();
                        Log.d("Exception in Messages", e.getMessage().toString());

                    }
                }


            }).start();


    }
    private void printToScreen(List<String> messages) {
        Log.d("messages","in print to screen method");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, messages);
        listView.setAdapter(adapter);

    }


}
