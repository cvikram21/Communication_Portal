package com.example.communicationportal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import HttpConnection.CustomAlertDialog;

public class ListEmails extends AppCompatActivity {
    Set<String> Emailset1 = new HashSet<String>();
    GetSet getSet = new GetSet();
    Spinner spinner;
    ListView listView;
    String placeName;
    CustomAlertDialog alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_emails);
        Emailset1 = getSet.getEmailset1();
        alertDialog = new CustomAlertDialog(ListEmails.this);
        listView = (ListView) findViewById(R.id.listView2);
        placeName = getIntent().getExtras().getString("placeName");
        List<String> list = new ArrayList<String>(Emailset1);
        //code from here written by Teja
        if(list.isEmpty()){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            alertDialogBuilder
                    .setMessage("No email contacts found for this place. Would you like to contact via phone? Press Yes to call or press No to go back and search again.")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            moveTaskToBack(true);
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            intent.setData(Uri.parse("tel:0123456789"));
                            startActivity(intent);

                        }


                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                            Intent intent = new Intent(ListEmails.this, Dashboard.class);
                            startActivity(intent);

                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();

        }
        //till here
        else {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, android.R.id.text1, list);
            listView.setAdapter(adapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);
                Log.d("itemValue",itemValue);
                // Show Alert
/*                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                        .show();
*/
                Intent intent = new Intent(ListEmails.this, EmailSubjectBody.class);
                intent.putExtra("placeName",placeName);
                intent.putExtra("email",itemValue);
                startActivity(intent);
            }

        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ListEmails.this, Dashboard.class);
        startActivity(intent);
    }
}
//Code written by Ayesha
