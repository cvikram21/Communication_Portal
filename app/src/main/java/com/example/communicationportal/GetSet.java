package com.example.communicationportal;

import android.util.Log;

import java.util.HashSet;
import java.util.Set;


public class GetSet {
    String  firstName, lastName;
   static String email;
    static Set<String> Emailset1 = new HashSet<String>();

    public void setEmail(String email) {
        this.email = email;
        Log.d("Vi", this.email);
    }
    public String getEmail() {
        return email;
    }



    public void setFirstName(String firstName) {
        this.firstName = firstName;

        Log.d("Vi", this.firstName);
    }
    public String getFirstName() {
        return firstName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
        Log.d("Vi", lastName);
    }

    public String getLastName() {
        return lastName;
    }
    //code from here written by Vikram
    String urls[];
    String placeName;
    public String[] getUrls() {
        return urls;
    }
    public void setUrls(String[] urls) {
        this.urls = urls;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
        Log.d("PlaceName in getset",this.placeName);
    }

    public Set<String> getEmailset1() {
        return Emailset1;
    }

    public void setEmailset1(Set<String> emailset1) {
        Emailset1 = emailset1;
        Log.d("In get set emailset1 is",emailset1.toString());
    }
//till here
}
//code written by teja
