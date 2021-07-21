package com.example.malawibloodtransfusion.hospital.ui.dashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.malawibloodtransfusion.DataBaseHelper;
import com.example.malawibloodtransfusion.R;
import com.example.malawibloodtransfusion.connectionsManager;
import com.example.malawibloodtransfusion.shared_pref_manager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
//import com.example.malawibloodtransfusion.hospital.R;

public class location_Fragment extends Fragment {

    connectionsManager conman = new connectionsManager();
    GoogleMap googleMap;
    ImageView green_locator,out_view;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.hospital_fragment_location, container, false);

        green_locator = root.findViewById(R.id.green_locator);
        out_view = root.findViewById(R.id.all_out_view);

        //initialise map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map_hospital);




        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
                //when map is loaded
                loadMap(googleMap);


            }
        });


        return root;
    }

    public void locator(GoogleMap googleMap)
    {

      //  Toast.makeText(getContext(),"we cant connect",Toast.LENGTH_LONG).show();

        shared_pref_manager shad = new shared_pref_manager();
        shad.hospital_getlastuser(getContext());

        double latitude =  Double.parseDouble(shad.getH_latitude());
        double lognitude =  Double.parseDouble(shad.getH_longitude());

        LatLng location = new LatLng(latitude,lognitude);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 16));


    }


    public void out_(GoogleMap googleMap)
    {

        shared_pref_manager shad = new shared_pref_manager();
        shad.hospital_getlastuser(getContext());

        double latitude =  Double.parseDouble(shad.getH_latitude());
        double lognitude =  Double.parseDouble(shad.getH_longitude());

        LatLng location = new LatLng(latitude,lognitude);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 7));


    }


    public void loadMap(GoogleMap googleMap) {



        green_locator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                locator(googleMap);
            }
        });

        out_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                out_(googleMap);
            }
        });

        googleMap.setBuildingsEnabled(true);
        googleMap.setTrafficEnabled(true);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setMapToolbarEnabled(true);


        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getLocation_data_pulls(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                // Toast.makeText(getContext(),response+"..............",Toast.LENGTH_LONG).show();
                //   Toast.makeText(getContext(),"we cant connect"+response,Toast.LENGTH_LONG).show();
                  shared_pref_manager shad = new shared_pref_manager();
                shad.hospital_getlastuser(getContext());

                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);


                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject product = array.getJSONObject(i);

                        //adding the product to product list

                        String id = product.getString("id");
                        String name = product.getString("hospital_name");
                        String email = product.getString("email");
                        String district = product.getString("district");
                        String licence = product.getString("licence");
                        String lat = product.getString("latitude");
                        String lon = product.getString("longitude");
                        String A_POSITIVE = product.getString("A_POSITIVE");
                        String A_NEGATIVE = product.getString("A_NEGATIVE");
                        String B_POSITIVE = product.getString("B_POSITIVE");
                        String B_NEGATIVE = product.getString("B_NEGATIVE");
                        String O_POSITIVE = product.getString("O_POSITIVE");
                        String O_NEGATIVE = product.getString("O_NEGATIVE");
                        String AB_POSITIVE = product.getString("AB_POSITIVE");
                        String AB_NEGATIVE = product.getString("AB_NEGATIVE");



                        double latitude = product.getDouble("latitude");
                        double lognitude = product.getDouble("longitude");
                        double my_latitude = Double.parseDouble( shad.getH_latitude());
                        double my_longitude = Double.parseDouble( shad.getH_longitude());




                        LatLng location = new LatLng(latitude, lognitude);

                        if(shad.getH_hospital_name().equals(name))
                        {



                            googleMap.addMarker(new MarkerOptions().position(location).title(name + "")
                                    .flat(true)
                                    // .snippet(""+email+" : "+""+district).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)))
                                    .snippet("" + email + " : " + "" + district).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                            CircleOptions circly = new CircleOptions()
                                    .center(location)
                                    .radius(20);
                            Circle circle = googleMap.addCircle(circly);
                            circle.setStrokeColor(Color.GREEN);

                           // googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                           // googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 5));
                        }else {

                            //distance between
                            float[] results = new float[1];
                            Location.distanceBetween(my_latitude,my_longitude,latitude,lognitude,results);
                            float  distance = results[0];

                            float dist=0;

                            float kilometre = (float) (distance/1000);
                            float metre = (float) (distance/500);

                            if(kilometre < .9)
                            {
                              dist =   metre;
                            }else if(kilometre >= 1)
                            {
                                dist = kilometre;
                            }

                           // Toast.makeText(getContext(),my_latitude,my_longitude,latitude,lognitude ,Toast.LENGTH_SHORT).show();

                            // LatLng location = new LatLng(latitude, lognitude);
                            googleMap.addMarker(new MarkerOptions().position(location).title(name + "")
                                    .flat(true)
                                    // .snippet(""+email+" : "+""+district).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)))
                                    .snippet("" + email + " : " + "" + "distance(km) "+ dist).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
                          //  googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                          //  googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 5));

                        }

                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10));


                        // lilongwe   -13.50773189902398, 33.82875581440084

                        // blantyre   -15.730261445140131, 35.01614844202773


                        try {
                            //distance between
                            float[] results1 = new float[1];
                            Location.distanceBetween(-13.50773189902398,33.82875581440084,latitude,lognitude,results1);
                            float  distance1 = results1[0];

                            float dist1=0;

                            float kilometre1 = (float) (distance1/1000);
                            float metre1 = (float) (distance1/500);

                            if(kilometre1 < .9)
                            {
                                dist1 =   metre1;
                            }else if(kilometre1 >= 1)
                            {
                                dist1 = kilometre1;
                            }


                            location = new LatLng(-13.50773189902398, 33.82875581440084);
                            googleMap.addMarker(new MarkerOptions().position(location).title("Malawi Blood Transfusion")
                                    .snippet(""+email+" : "+""+district).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                                    .snippet("Lilongwe  " + " : " + "" + "distance(km) "+ dist1));

                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 7));


                            //distance between
                            float[] results2 = new float[1];
                            Location.distanceBetween(-15.730261445140131,35.01614844202773,latitude,lognitude,results2);
                            float  distance2 = results2[0];

                            float dist2=0;

                            float kilometre2 = (float) (distance2/1000);
                            float metre2 = (float) (distance2/500);

                            if(kilometre2 < .9)
                            {
                                dist2 =   metre2;
                            }else if(kilometre2 >= 1)
                            {
                                dist2 = kilometre2;
                            }


                            location = new LatLng(-15.730261445140131, 35.01614844202773);
                            googleMap.addMarker(new MarkerOptions().position(location).title("Malawi Blood Transfusion")
                                    .snippet(""+email+" : "+""+district).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                                    .snippet("Blantyre  "+ " : " + "" + "distance(km) "+ dist2));

                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 7));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }






                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(),"" + error.getMessage(),Toast.LENGTH_SHORT).show();



            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                shared_pref_manager shad = new shared_pref_manager();
                shad.donor_getlastuser(getContext());

                params.put("id",shad.getD_id());
                params.put("type","1");

                return params;
            }
        };
        requestQueue.add(stringRequest);



        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext() , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                // when permission granted
                // call method
                //  get_current_location();

            } else {
                //when permission is denied
                //request permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            }
        }

    }


}