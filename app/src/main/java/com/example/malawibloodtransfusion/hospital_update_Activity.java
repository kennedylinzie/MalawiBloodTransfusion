package com.example.malawibloodtransfusion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.malawibloodtransfusion.models.Hospital;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class hospital_update_Activity extends AppCompatActivity implements LocationListener {

    private EditText name,email,district,licence,oldpassword,newpassword,re_newpassword,phone_number;
    private TextView latitude,longitude;
    private Button re_loc;
    private FusedLocationProviderClient fusedLocationProviderClient;

    public  static final int RequestPermissionCode  = 1 ;
    Context context;
    Intent intent1 ;
    Location location;
    LocationManager locationManager ;
    boolean GpsStatus = false ;
    Criteria criteria ;
    String Holder;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_update);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        EnableRuntimePermission();

        name = findViewById(R.id.hospital_et_update_name);
        email = findViewById(R.id.et_hospital_update_email);
        district = findViewById(R.id.hospital_et_update_district);
        licence = findViewById(R.id.hospital_et_update_licence);
        phone_number = findViewById(R.id.hospital_et_update_phone_number);

        latitude = findViewById(R.id.hospital_update_latitude);
        longitude = findViewById(R.id.hospital_update_longitude);

        oldpassword = findViewById(R.id.old_update_password);
        newpassword = findViewById(R.id.hospital_et_update_password);
        re_newpassword = findViewById(R.id.hospital_et_update_repassword);

        re_loc = findViewById(R.id.update_reposition);
        re_loc.setEnabled(false);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();

        Holder = locationManager.getBestProvider(criteria, false);

        context = getApplicationContext();

        CheckGpsStatus();


        connectionsManager conman = new connectionsManager();

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());



        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getLocation_data_pulls(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                //Toast.makeText(getContext(),response+"..............",Toast.LENGTH_LONG).show();
                //   Toast.makeText(getContext(),"we cant connect"+response,Toast.LENGTH_LONG).show();



                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);


                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject product = array.getJSONObject(i);

                        //adding the product to product list

                        String id_ = product.getString("id");
                        String name_ = product.getString("hospital_name");
                        String email_ = product.getString("email");
                        String district_ = product.getString("district");
                        String licence_ = product.getString("licence");
                        String lat_ = product.getString("latitude");
                        String lon_ = product.getString("longitude");
                        String phone_ = product.getString("phone_number");
                        String A_POSITIVE_ = product.getString("A_POSITIVE");
                        String A_NEGATIVE_ = product.getString("A_NEGATIVE");
                        String B_POSITIVE_ = product.getString("B_POSITIVE");
                        String B_NEGATIVE_ = product.getString("B_NEGATIVE");
                        String O_POSITIVE_ = product.getString("O_POSITIVE");
                        String O_NEGATIVE_ = product.getString("O_NEGATIVE");
                        String AB_POSITIVE_ = product.getString("AB_POSITIVE");
                        String AB_NEGATIVE_ = product.getString("AB_NEGATIVE");

                        licence_ = licence_.toUpperCase();



                        name.setText(name_);
                        email.setText(email_);
                        district.setText(district_);
                        licence.setText(licence_);
                        latitude.setText(lat_);
                        longitude.setText(lon_);
                        phone_number.setText(phone_);






                    }


                } catch (JSONException e) {
                    throw new NullPointerException();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"" + error.getMessage(),Toast.LENGTH_SHORT).show();



            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                shared_pref_manager shad = new shared_pref_manager();
                shad.hospital_getlastuser(getApplicationContext());

                params.put("id",shad.getH_id());
                params.put("type","2");

                return params;
            }
        };
        requestQueue.add(stringRequest);



    }



    public void hospital_udpate_details(View view) {
        String lic = licence.getText().toString().trim();
        lic = lic.toUpperCase();

        Hospital hos = new Hospital();
        hos.update_details(getApplicationContext()
                ,name.getText().toString().trim()
                ,email.getText().toString().trim()
                ,district.getText().toString().trim()
                ,lic,
                phone_number.getText().toString().trim()
        );



    }

    public void update_locations(View view) {

       Hospital hos = new Hospital();
       hos.update_location(getApplicationContext()
               ,latitude.getText().toString().trim()
               ,longitude.getText().toString().trim());



    }

    ///////////////////////////////////location






    public void hospital_change_update_password(View view) {

        String oldpass = oldpassword.getText().toString().trim();
        String newpass = newpassword.getText().toString().trim();
        String renewpass = re_newpassword.getText().toString().trim();

        if(!oldpass.isEmpty() && !newpass.isEmpty() && !renewpass.isEmpty()){
           // Toast.makeText(getApplicationContext(),"good to go",Toast.LENGTH_SHORT).show();
            if(newpass.equals(renewpass)){
                Hospital hos = new Hospital();
                hos.update_change_password(getApplicationContext(),oldpass,newpass);

            }
        }else {
            Toast.makeText(getApplicationContext(),"Fill all the fields please",Toast.LENGTH_SHORT).show();
        }



    }

    public void get_hospital_up_location_up(View view) {


        CheckGpsStatus();

        if(GpsStatus) {
            if (Holder != null) {
                if (ActivityCompat.checkSelfPermission(
                        hospital_update_Activity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        &&
                        ActivityCompat.checkSelfPermission(hospital_update_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                location = locationManager.getLastKnownLocation(Holder);
                locationManager.requestLocationUpdates(Holder, 12000, 7, hospital_update_Activity.this);
            }
        }else {

            Toast.makeText(hospital_update_Activity.this, "Please Enable GPS First", Toast.LENGTH_LONG).show();

        }


    }

    public void CheckGpsStatus(){

        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(hospital_update_Activity.this,
                Manifest.permission.ACCESS_FINE_LOCATION))
        {

            Toast.makeText(hospital_update_Activity.this,"ACCESS_FINE_LOCATION permission allows us to Access GPS in app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(hospital_update_Activity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(hospital_update_Activity.this,"Permission Granted, Now your application can access GPS.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(hospital_update_Activity.this,"Permission Canceled, Now your application cannot access GPS.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

        longitude.setText(""+location.getLongitude());
        latitude.setText("" + location.getLatitude());
        if(("" + location.getLatitude()).equals("") && ("" + location.getLongitude()).equals(""))
        {

        }else {
            re_loc.setEnabled(true);
        }


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}