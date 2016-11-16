package com.example.communicationportal;

import android.app.Activity;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import HttpConnection.CustomAlertDialog;
import HttpConnection.ServiceMethodListener;
import HttpConnection.ServiceWithoutParameters;

import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import org.json.JSONArray;
import org.json.JSONObject;
import eu.inmite.android.lib.validations.form.FormValidator;
import eu.inmite.android.lib.validations.form.annotations.NotEmpty;
import eu.inmite.android.lib.validations.form.callback.SimpleErrorPopupCallback;




public class MainActivity extends Activity implements ServiceMethodListener{
    @NotEmpty(messageId = R.string.validation_not_empty, order = 1)
    EditText edUsername;
    @NotEmpty(messageId = R.string.validation_not_empty, order = 2)
    EditText edPassword;
    Button loginSubmit;
    Button register;
    String username, password;
    Boolean isValid;
    String[] list1;
    CustomAlertDialog alertDialog;
    GetSet getSet = new GetSet();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        edUsername = (EditText)findViewById(R.id.user);
        edPassword = (EditText)findViewById(R.id.password);
        loginSubmit = (Button)findViewById(R.id.loin);
        register = (Button)findViewById(R.id.register);
        alertDialog = new CustomAlertDialog(MainActivity.this);
        loginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    username = edUsername.getText().toString();
                    username = username.trim();
                    password = edPassword.getText().toString();
                    loginServiceCall();


            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Register.class));
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        moveTaskToBack(true);
        MainActivity.this.finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
    public void loginServiceCall(){
        String url1 = getResources().getString(R.string.base_url) +"login.php?"+"username="+username+"&password="+password+"";
        Log.d("Vikki", url1);
        ServiceWithoutParameters postmethods = new ServiceWithoutParameters(MainActivity.this, url1, "Loginclass", "Loginmethod");
        postmethods.execute();
    }

    public void getResponse(String data, String Loginclass, String Loginmethod){
        try{
            String email="", fname="", lname="";
            JSONObject job = new JSONObject(data);
            String status=job.getString("status");
            String message=job.getString("message");
            if(status.equals("200")) {
                getSet.setEmail(username);
                Intent intent = new Intent(MainActivity.this, FirstScreen.class);
                startActivity(intent);
            }
            else{
                alertDialog.showOkDialog("Username  and Passwords do not match. Please try again.");
            }
        }
        catch (Exception e){
            Log.d("JSON Exception", e.toString());

        }
    }

}
//code written by vikram