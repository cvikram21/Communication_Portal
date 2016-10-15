package com.example.communicationportal;

import android.app.Activity;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import HttpConnection.ServiceMethodListener;
import HttpConnection.ServiceWithoutParameters;

/**
 * Created by darshita on 10/15/16.
 */
public class PlaceDetails extends Activity implements ServiceMethodListener{

    String placeName;

    public String getMarkerDetails(String placeid){
        String place_id = placeid;
        Log.d("Test1","After calling place details class");
        Log.d("abc","abc");
        String url3 = "abc";//getResources().getString(R.string.base_url) + "placedetails.php?" + "placeid=" + place_id +  "";
        Log.d("Vikki", url3);
        ServiceWithoutParameters postmethod1 = new ServiceWithoutParameters(PlaceDetails.this, url3, "Case2class", "Case2method");
        postmethod1.execute();
        Log.d("End","After calling post method");
        return placeName;
    }

    @Override
    public void getResponse(String data, String classname, String methodname) {
        Log.d("Vikki","Entered");
        if (classname.equalsIgnoreCase("Case2class")) {
            if (methodname.equalsIgnoreCase("Case2method")) {
                try {
                    JSONObject job = new JSONObject(data);
                    String notes = job.getString("record");
                    //jarray = new JSONArray(notes);
                    JSONArray ja = new JSONArray(notes);
                    int len = ja.length();
                    String status = job.getString("status");
                    if (status.equalsIgnoreCase("200")) {
                        for (int i = 0; i < len; i++) {
                            JSONObject jb1 = ja.getJSONObject(i);
                            placeName = jb1.getString("name");
                        }
                        Log.d("Vikki", "Entered getResponse");
                        Log.d("Vikki", data);

                    } else {
                        Log.d("abc","abc");
                        //alertDialog.showOkDialog("Something went wrong, please try again");
                    }
                } catch (Exception e) {
                    Log.d("Exception", "Exception");
                }

            }
        }

    }
}
