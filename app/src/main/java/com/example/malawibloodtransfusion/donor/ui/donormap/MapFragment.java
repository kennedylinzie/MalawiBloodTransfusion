package com.example.malawibloodtransfusion.donor.ui.donormap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.malawibloodtransfusion.R;
import com.example.malawibloodtransfusion.models.Donor;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.malawibloodtransfusion.shared_pref_manager;
import com.example.malawibloodtransfusion.connectionsManager;
import com.example.malawibloodtransfusion.DataBaseHelper;

import java.util.HashMap;
import java.util.Map;

public class MapFragment extends Fragment {

    Donor don = new Donor();
    connectionsManager conman = new connectionsManager();
    GoogleMap googleMap;
    ImageView mylocation,outview;
    LatLng location;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.donor_fragment_map, container, false);
        mylocation = view.findViewById(R.id.mylocation);
        outview = view.findViewById(R.id.donor_outerview);
        //initialise map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);



        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
                //when map is loaded
                loadMap(googleMap);
               // get_my_location(googleMap)



            }
        });




        return view;
    }



    public void loadMap(GoogleMap googleMap) {

        shared_pref_manager shad = new shared_pref_manager();
        shad.donor_get_current_cord_(getContext());
        MyLocationListener myLocationListener = new MyLocationListener(getContext());
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);


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
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                30,
                5,
                myLocationListener);

        googleMap.setBuildingsEnabled(true);
        googleMap.setTrafficEnabled(true);
       // googleMap.setMyLocationEnabled(true);

        mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getContext(),"musings",Toast.LENGTH_LONG).show();
                try {
                    if(!shad.getD_current_latitude().isEmpty() && !shad.getD_current_longitude().isEmpty())
                    {
                        double latitide_ = Double.parseDouble(shad.getD_current_latitude());
                        double longitude_ = Double.parseDouble(shad.getD_current_longitude());

                        location = new LatLng(latitide_,longitude_);
                        googleMap.addMarker(new MarkerOptions().position(location).title("Your location")
                                // .snippet(""+email+" : "+""+district).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)))
                                .snippet("Your current location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 10));

                    }else {
                        Toast.makeText(getContext(),"Getting locations please wait",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        outview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if(!shad.getD_current_latitude().isEmpty() && !shad.getD_current_longitude().isEmpty())
                    {
                       double latitide_ = Double.parseDouble(shad.getD_current_latitude());
                        double longitude_ = Double.parseDouble(shad.getD_current_longitude());
                        location = new LatLng(latitide_,longitude_);
                        googleMap.addMarker(new MarkerOptions().position(location).title("Your location")
                                // .snippet(""+email+" : "+""+district).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)))
                                .snippet("Your current location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 7));

                    }else {
                        Toast.makeText(getContext(),"Getting locations please wait",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });



        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getLocation_data_pulls(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                // Toast.makeText(getContext(),response+"..............",Toast.LENGTH_LONG).show();
                //   Toast.makeText(getContext(),"we cant connect"+response,Toast.LENGTH_LONG).show();


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
                        String phone = product.getString("phone_number");
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
                        DataBaseHelper data = new DataBaseHelper(getContext());




                        double latitude = product.getDouble("latitude");
                        double lognitude = product.getDouble("longitude");

                         location = new LatLng(latitude, lognitude);
                        googleMap.addMarker(new MarkerOptions().position(location).title(name + "")
                                // .snippet(""+email+" : "+""+district).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)))
                                .snippet("" + email + " : " + "" + phone).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));

                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 7));




                        if(!shad.getD_current_latitude().isEmpty() && !shad.getD_current_longitude().isEmpty())
                        {
                            double latitide_ = Double.parseDouble(shad.getD_current_latitude());
                            double longitude_ = Double.parseDouble(shad.getD_current_longitude());

                             location = new LatLng(latitide_,longitude_);
                            googleMap.addMarker(new MarkerOptions().position(location).title("Your location")
                                    // .snippet(""+email+" : "+""+district).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)))
                                    .snippet("Your current location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 7));

                        }else {
                            Toast.makeText(getContext(),"Getting locations please wait",Toast.LENGTH_LONG).show();
                        }


                        /// Toast.makeText(context,id+""+name+""+email+""+district+""+licence+""+lat+""+lon+"",Toast.LENGTH_LONG).show();

                        //  hospitals[i] = id+""+name+""+email+""+district+""+licence+""+lat+""+lon+"";


                       // lilongwe   -13.50773189902398, 33.82875581440084

                       // blantyre   -15.730261445140131, 35.01614844202773

                        try {
                            location = new LatLng(-13.50773189902398, 33.82875581440084);
                            googleMap.addMarker(new MarkerOptions().position(location).title("Malawi Blood Transfusion")
                                     .snippet(""+email+" : "+""+district).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                                    .snippet("Lilongwe"));

                            googleMap.moveCamera(CameraUpdateFactory.newLatLng(location));
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 7));


                            location = new LatLng(-15.730261445140131, 35.01614844202773);
                            googleMap.addMarker(new MarkerOptions().position(location).title("Malawi Blood Transfusion")
                                     .snippet(""+email+" : "+""+district).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                                    .snippet("Blantyre"));

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


        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(@NonNull @NotNull Marker marker) {
                Toast.makeText(getContext(),".infr window clicked.............",Toast.LENGTH_LONG).show();
                marker.showInfoWindow();
            }
        });



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





               /*
                LatLng chilomoni = new LatLng(-15.7793, 34.9824);
                googleMap.addMarker(new MarkerOptions().position(chilomoni).title("CHILOMONI"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(chilomoni));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(chilomoni, 5));

                */


