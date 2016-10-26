package com.example.communicationportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import HttpConnection.CustomAlertDialog;
import HttpConnection.ServiceMethodListener;
import HttpConnection.ServiceWithoutParameters;
import eu.inmite.android.lib.validations.form.FormValidator;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;


public class Register extends AppCompatActivity implements ServiceMethodListener {
    @NotEmpty(messageId = R.string.validation_not_empty, order = 1)
    EditText user;
    @NotEmpty(messageId = R.string.validation_not_empty, order = 2)
    EditText fname;
    @NotEmpty(messageId = R.string.validation_not_empty, order = 3)
    EditText lname;
    @NotEmpty(messageId = R.string.validation_not_empty, order = 4)
    EditText password;
    @NotEmpty(messageId = R.string.validation_not_empty, order = 5)
    EditText mobile;
    @NotEmpty(messageId = R.string.validation_not_empty, order = 6)
    EditText addr1;
    @NotEmpty(messageId = R.string.validation_not_empty, order = 7)
    EditText addr2;
    @NotEmpty(messageId = R.string.validation_not_empty, order = 8)
    EditText city;
    @NotEmpty(messageId = R.string.validation_not_empty, order = 9)
    EditText zipcode;
    Button submit, cancel;
    String username, firstName, lastName, pass, Addr1, Addr2, City, Zip,phNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        user = (EditText)findViewById(R.id.email);
        fname= (EditText)findViewById(R.id.fname);
        lname = (EditText)findViewById(R.id.lname);
        password = (EditText)findViewById(R.id.password);
        mobile = (EditText)findViewById(R.id.mobile);
        addr1 = (EditText)findViewById(R.id.add1);
        addr2 = (EditText)findViewById(R.id.add2);
        city = (EditText)findViewById(R.id.city);
        zipcode = (EditText)findViewById(R.id.zip);
        submit = (Button)findViewById(R.id.submit);
        cancel = (Button)findViewById(R.id.cancel);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //replace methods added by teja to remove bug while registration
                    username = user.getText().toString();
                    username = username.replace(" ","");
                    firstName = fname.getText().toString();
                    firstName = firstName.replace(" ","");
                    lastName = lname.getText().toString();
                    lastName = lastName.replace(" ","");
                    pass = password.getText().toString();
                    pass = pass.replace(" ","");
                Addr1 = addr1.getText().toString();
                Addr1 = Addr1.replace(" ","");
                Addr2 = addr2.getText().toString();
                Addr2 = Addr2.replace(" ","");
                City =  city.getText().toString();
                City = City.replace(" ","");
                Zip = zipcode.getText().toString();
                Zip = Zip.replace(" ","");
                phNumber = (mobile.getText().toString());
                phNumber = phNumber.replace(" ","");
                String url1 = getResources().getString(R.string.base_url)+"register.php?"+"firstName="+firstName+"&lastName="+lastName+"&email="+username+"&password="+pass+"&mobile="+phNumber+"&add1="+Addr1+"&add2="+Addr2+"&city="+City+"&state=TX&zip="+Zip+"";
                Log.d("Vikki", url1);
                ServiceWithoutParameters postmethods = new ServiceWithoutParameters(Register.this, url1 , "RegClass","RegMethod");
                postmethods.execute();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(Register.this ,MainActivity.class));
            }
        });
    }
    public void getResponse(String data, String classname, String methodname) {
        if (classname.equalsIgnoreCase("Regclass")) {
            if (methodname.equalsIgnoreCase("Regmethod")) {
                try {
                    Log.d("Vikki","Entered getResponse");
                    Log.d("Vikki",data);
                    JSONObject job = new JSONObject(data);
                    String status=job.getString("status");
                    String message=job.getString("message");

                    if(status.equalsIgnoreCase("200")){
                        //CustomAlertDialog.showOkDialog(message);
                        Intent intent = new Intent(Register.this,MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        //CustomAlertDialog.showOkDialog(message);
                    }
                }catch (Exception e){

                }

            }
        }
    }
}
//code written by ayesha
