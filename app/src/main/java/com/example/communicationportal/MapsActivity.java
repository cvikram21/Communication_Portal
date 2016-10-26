package com.example.communicationportal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;

import HttpConnection.CustomAlertDialog;
import HttpConnection.ServiceMethodListener;
import HttpConnection.ServiceWithoutParameters;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ServiceMethodListener{

    private GoogleMap mMap;
    String types, resLat, resLon, placeId, placeName, website, rating;
    CustomAlertDialog alertDialog;
    Double lat3, lon3;
    int i;
    Marker aliasLocation = null;
    String placeNames[], websites[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        types = getIntent().getStringExtra("Types");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng currentLocation = new LatLng(30.096153, -95.988439);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom((currentLocation), 11.0f));
        Marker currentMarker = mMap.addMarker(new MarkerOptions().position((currentLocation)).title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        currentMarker.showInfoWindow();
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng point) {
                if (i == 0) {
                    // TODO Auto-generated method stub
                    lat3 = point.latitude;
                    lon3 = point.longitude;
                    aliasLocation = mMap.addMarker(new MarkerOptions().position(new LatLng(lat3, lon3)).title("Selected alias location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                    aliasLocation.showInfoWindow();
                    i++;

                    if (lat3 != 0 && lon3 != 0) {
                        String url2 = getResources().getString(R.string.base_url) + "multiplelocations.php?" + "types=" + types + "&lattitude=" + lat3 + "&longitude=" + lon3 + "";
                        Log.d("Vikki", url2);
                        ServiceWithoutParameters postmethod = new ServiceWithoutParameters(MapsActivity.this, url2, "Case3class", "Case3method");
                        postmethod.execute();
                    } else {
                        alertDialog.showOkDialog("Please select an alias location.");
                    }
                }
            }
        });
        //function added by Vikram
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                ListLink listLinks = new ListLink();
                GetSet getSet = new GetSet();
                String placeName = marker.getTitle();
                getSet.setPlaceName(placeName);
                for(int i = 0; i<placeNames.length; i++){
                    if(placeNames[i].equalsIgnoreCase(placeName)){
                        break;
                    }
                }
                listLinks.fetchUrls1(websites[i-1]);
                final ProgressDialog progress = ProgressDialog.show(MapsActivity.this, "Please wait while we fetch details", "Making contacts ready", true);
                progress.setCancelable(true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(15000);
                            progress.dismiss();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(MapsActivity.this, ListEmails.class));
                                }
                            });

                        }catch (Exception e){

                        }
                    }
                }).start();
            }
        });
        //till here
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
                    placeNames = new String[len];
                    websites = new String[len];
                    String status = job.getString("status");
                    if (status.equalsIgnoreCase("200")) {
                        for (int i = 0; i < len; i++) {
                            JSONObject jb1 = ja.getJSONObject(i);
                            resLat = jb1.getString("lat");
                            resLon = jb1.getString("lon");
                            placeId = jb1.getString("place_id");
                            placeName = jb1.getString("placeName");
                            placeNames[i] = placeName;
                            website = jb1.getString("website");
                            websites[i] = website;
                            rating = jb1.getString("rating");
                            Marker currentMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(resLat), Double.parseDouble(resLon))).title(placeName).snippet("Rating:" + rating).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                            currentMarker.showInfoWindow();
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
//code by teja till here



}

