package com.example.communicationportal;

import android.util.Log;

/**
 * Created by darshita on 9/20/16.
 */
public class GetSet {
    String email, firstName, lastName;

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


}
