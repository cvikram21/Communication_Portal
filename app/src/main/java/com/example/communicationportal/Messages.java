package com.example.communicationportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.vision.text.Text;
import com.google.api.services.gmail.model.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import HttpConnection.ServiceMethodListener;
import HttpConnection.ServiceWithoutParameters;

public class Messages extends AppCompatActivity {
    String temp,subject, mailid, replyMessage;
    GetSet getSet = new GetSet();
    EditText editText;
    ImageView imageButton;
    TextView textView;
    List<String> messages = new ArrayList<String>();
    ListMessages listMessages = new ListMessages();
    public com.google.api.services.gmail.Gmail mService = null;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        listView = (ListView)findViewById(R.id.listView4);
        textView = (TextView)findViewById(R.id.textView5);
        editText = (EditText)findViewById(R.id.replytext);
        imageButton = (ImageView)findViewById(R.id.image);
        temp = getIntent().getExtras().getString("subject");
        mailid = getIntent().getExtras().getString("email");
        System.out.println(temp.matches("\\w.*"));
        String[] splitString = (temp.split("\\s+"));
        subject = splitString[splitString.length-1];
        textView.setText(subject);
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
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //alertDialog = new CustomAlertDialog(ChatScreen.this);
                //alertDialog.showOkDialog("No conversations found");
                final ProgressDialog progress = ProgressDialog.show(Messages.this, "Please wait while we send the message", "Sending Message", true);
                progress.setCancelable(true);

                replyMessage = editText.getText().toString();
                try {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            SendEmail sendEmail = new SendEmail();
                            try {
                                //sendEmail.sendMessage(mService, user , createEmail(senderEmail , "me", subject, message));
                                sendEmail.sendMessage(mService, "me" , createEmail("cvikram785@gmail.com" , "me", subject, replyMessage));
                                progress.dismiss();
                                Intent intent= new Intent(Messages.this, ChatScreen.class);
                                startActivity(intent);
                            } catch (MessagingException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    } catch (Exception e) {
                    e.printStackTrace();
                }


            }


        });



    }

    public MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);

        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO,
                new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }


    private void printToScreen(List<String> messages) {
        Log.d("messages","in print to screen method");
        //List<String> messages1 = new ArrayList<>();
        Collections.reverse(messages);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, messages);
        listView.setAdapter(adapter);

    }


}
