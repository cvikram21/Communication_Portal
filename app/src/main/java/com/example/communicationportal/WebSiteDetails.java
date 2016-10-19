package com.example.communicationportal;

import android.util.Log;

/**
 * Created by darshita on 10/19/16.
 */
public class WebSiteDetails {
    String web[], name[];

    public String[] getWeb() {
        return web;
    }

    public void setWeb(String[] web) {
        this.web = web;
        //for(int i = 0; i<web.length; i++){
          //  Log.d("Website string array",web[i]);
        //}
    }

    public String[] getName() {
        return name;
    }

    public void setName(String[] name) {
        this.name = name;
    }

    public String getWebSite(String place){
        int i;
        for(i = 0; i < name.length; i++){
            if(name[i].equalsIgnoreCase(place)){
                break;
            }
        }
        return web[i];
    }
}
