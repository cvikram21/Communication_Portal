package com.example.communicationportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import HttpConnection.CustomAlertDialog;
import HttpConnection.ServiceMethodListener;
import HttpConnection.ServiceWithoutParameters;

public class ChatScreen extends AppCompatActivity implements ServiceMethodListener{
    CustomAlertDialog alertDialog;
    ListView listView;
    GetSet getSet = new GetSet();
    String email, mailerid, mailername, mailsubject;
    String names[], subjects[], mailerids[];
    List<String> list = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        listView = (ListView)findViewById(R.id.listView3);
        //alertDialog = new CustomAlertDialog(ChatScreen.this);
        //alertDialog.showOkDialog("No conversations found");
        String url2 = getResources().getString(R.string.base_url) + "chats.php?" + "username=" + getSet.getEmail() + "";
        Log.d("Vikki", url2);
        ServiceWithoutParameters postmethod = new ServiceWithoutParameters(ChatScreen.this, url2, "Case3class", "Case3method");
        postmethod.execute();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);
                Log.d("itemValue",itemValue);
                Log.d("mailerid", mailerids[position]);
                // Show Alert
/*                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();
*/
                Intent intent = new Intent(ChatScreen.this, Messages.class);
                intent.putExtra("subject",itemValue);
                intent.putExtra("email",mailerids[position]);
                startActivity(intent);
            }

        });
    }

    @Override
    public void getResponse(String data, String classname, String methodname) {
        if (classname.equalsIgnoreCase("Case3class")) {
            if (methodname.equalsIgnoreCase("Case3method")) {
                try {
                    JSONObject job = new JSONObject(data);
                    String notes = job.getString("record");
                    JSONArray ja = new JSONArray(notes);
                    int len = ja.length();
                    names = new String[len];
                    mailerids = new String[len];
                    subjects = new String[len];
                    String status = job.getString("status");
                    if (status.equalsIgnoreCase("200")) {
                        for (int i = 0; i < len; i++) {
                            JSONObject jb1 = ja.getJSONObject(i);
                            email = jb1.getString("userName");
                            mailerid = jb1.getString("sentmailid");
                            mailername = jb1.getString("sentmailername");
                            mailsubject = jb1.getString("sentmailsubject");
                            names[i] = mailername;
                            subjects[i] = mailsubject;
                            mailerids[i] = mailerid;
                            list.add(names[i]+"         "+subjects[i]);
                        }

                    } else {
                        alertDialog.showOkDialog("Something went wrong, please try again");
                    }
                } catch (Exception e) {
                    Log.d("Exception", e.toString());
                }

            }
        }
    }
}
