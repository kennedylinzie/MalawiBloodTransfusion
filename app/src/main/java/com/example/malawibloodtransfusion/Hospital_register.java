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
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
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

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


//////////////////////

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class Hospital_register extends AppCompatActivity implements LocationListener {

    private EditText et_name, et_email, et_phonenumber, et_district, et_licence, et_password, et_re_password;
    private TextView tv_latitude, tv_longitude;
    private String name, email, district, licence, password, repassword, latitude, longitude,phone_number;
    private Button reg_button,get_loc;

    private final connectionsManager conman = new connectionsManager();

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
        setContentView(R.layout.activity_hospital_register);
        EnableRuntimePermission();


        et_name = findViewById(R.id.hospital_et_reg_name);
        et_email = findViewById(R.id.et_hospital_reg_email);
        et_phonenumber = findViewById(R.id.et_hospital_reg_phone_number);
        et_licence = findViewById(R.id.hospital_et_reg_licence);
        et_district = findViewById(R.id.hospital_et_reg_district);
        et_password = findViewById(R.id.hospital_et_reg_password);
        et_re_password = findViewById(R.id.hospital_et_reg_repassword);
        tv_latitude = findViewById(R.id.hospital_latitude);
        tv_longitude = findViewById(R.id.hospital_longitude);
        reg_button = findViewById(R.id.hospital_btn_register);
        get_loc = findViewById(R.id.hospital_btn_getlocation);
        reg_button.setEnabled(false);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        criteria = new Criteria();

        Holder = locationManager.getBestProvider(criteria, false);

        context = getApplicationContext();

        CheckGpsStatus();




        get_loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckGpsStatus();

                if(GpsStatus) {
                    if (Holder != null) {
                        if (ActivityCompat.checkSelfPermission(
                                Hospital_register.this,
                                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                &&
                                ActivityCompat.checkSelfPermission(Hospital_register.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                                        != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        location = locationManager.getLastKnownLocation(Holder);
                        locationManager.requestLocationUpdates(Holder, 12000, 7, Hospital_register.this);
                    }
                }else {

                    Toast.makeText(Hospital_register.this, "Please Enable GPS First", Toast.LENGTH_LONG).show();

                }

            }
        });


    }

    public void hospital_Register(View view) {
        name = et_name.getText().toString().trim();
        email = et_email.getText().toString().trim();
       phone_number = et_phonenumber.getText().toString().trim();
        district = et_district.getText().toString().trim();
        licence = et_licence.getText().toString().trim();
        password = et_password.getText().toString().trim();
        repassword = et_re_password.getText().toString().trim();
        latitude = tv_latitude.getText().toString().trim();
        longitude = tv_longitude.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty()  || district.isEmpty() || licence.isEmpty()
                || password.isEmpty() || repassword.isEmpty() || latitude.isEmpty() || longitude.isEmpty() || phone_number.isEmpty()) {
            Toast.makeText(this, "The fields should be filled ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(repassword)) {
            Toast.makeText(this, "passwords don't match", Toast.LENGTH_SHORT).show();
            return;
        }

        //  tv_man.setText(name +".."+ email+".."+phone_number+".."+district+".."+licence+".."+password+".."+repassword+".."+latitude+".."+longitude+"..");

        Hospital hos = new Hospital();
        hos.Register(getApplicationContext(),name,email,district,licence,latitude,longitude,password,phone_number);




                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(hos.getSuccess().equals("1"))
                            {
                               Toast.makeText(getApplicationContext(),"its success",Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(getApplicationContext(), Hospital_login.class);
                                startActivity(intent);
                                finish();

                            }else if(hos.getSuccess().equals("2"))
                            {
                                Toast.makeText(getApplicationContext(),"it fails",Toast.LENGTH_LONG).show();
                            }else if(hos.getSuccess().equals("3")) {
                                Toast.makeText(getApplicationContext(),"there may be a network problem",Toast.LENGTH_LONG).show();
                            }


                        }
                    }, 1000);


    }









    public void goto_hospital_reg_login(View view) {
        Intent intent = new Intent(this,Hospital_login.class);
        startActivity(intent);
    }






    public void CheckGpsStatus(){

        locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);

        GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

    }

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(Hospital_register.this,
                Manifest.permission.ACCESS_FINE_LOCATION))
        {

            Toast.makeText(Hospital_register.this,"ACCESS_FINE_LOCATION permission allows us to Access GPS in app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(Hospital_register.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(Hospital_register.this,"Permission Granted, Now your application can access GPS.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(Hospital_register.this,"Permission Canceled, Now your application cannot access GPS.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {

         tv_longitude.setText(""+location.getLongitude());
         tv_latitude.setText("" + location.getLatitude());
        if(("" + location.getLatitude()).equals("") && ("" + location.getLongitude()).equals(""))
        {

        }else {
            reg_button.setEnabled(true);
        }


    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}