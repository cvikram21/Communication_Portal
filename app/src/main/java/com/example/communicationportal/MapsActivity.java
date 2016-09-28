package com.example.communicationportal;

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

import HttpConnection.CustomAlertDialog;
import HttpConnection.ServiceMethodListener;
import HttpConnection.ServiceWithoutParameters;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, ServiceMethodListener{

    private GoogleMap mMap;
    String types, resLat, resLon;
    CustomAlertDialog alertDialog;
    Double lat3, lon3;
    int i;
    Marker aliasLocation = null;

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


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng currentLocation = new LatLng(30.096153, -95.988439);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom((currentLocation), 11.0f));
        Marker currentMarker = mMap.addMarker(new MarkerOptions().position((currentLocation)).title("Current Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        currentMarker.showInfoWindow();
        //alertDialog.showOkDialog("Please select an alias location and radius to be sent to the google server");
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
                }
                if (lat3 != 0 && lon3 != 0) {
                    String url2 = getResources().getString(R.string.base_url) + "multiplelocations.php?" + "types=" + types + "&lattitude=" + lat3 + "&longitude=" + lon3 +  "";
                    Log.d("Vikki", url2);
                    ServiceWithoutParameters postmethod = new ServiceWithoutParameters(MapsActivity.this, url2, "Case3class", "Case3method");
                    postmethod.execute();
                    } else {
                    alertDialog.showOkDialog("Please select an alias location.");
                }
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
                    //jarray = new JSONArray(notes);
                    JSONArray ja = new JSONArray(notes);
                    int len = ja.length();
                    String status = job.getString("status");
                    if (status.equalsIgnoreCase("200")) {
                        for (int i = 0; i < len; i++) {
                            JSONObject jb1 = ja.getJSONObject(i);
                            resLat = jb1.getString("lat");
                            resLon = jb1.getString("lon");
                            Log.d("Result Lattitude", resLat);
                            Log.d("Result Longitude", resLon);
                            Marker currentMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(resLat), Double.parseDouble(resLon))).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
                            currentMarker.showInfoWindow();
                        }
                        Log.d("Vikki", "Entered getResponse");
                        Log.d("Vikki", data);

                    } else {
                        alertDialog.showOkDialog("Something went wrong, please try again");
                    }
                } catch (Exception e) {

                }

            }
        }

    }
}
