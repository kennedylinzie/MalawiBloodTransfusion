package com.example.malawibloodtransfusion.donor.ui.donormap;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.malawibloodtransfusion.MyService;
import com.example.malawibloodtransfusion.shared_pref_manager;


public class MyLocationListener  implements LocationListener {

    private Double latitide_;
    private Double longitude_;
    private Location location;


    Context context;

    public MyLocationListener(Context context)
    {


        this.context = context;
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        shared_pref_manager shad = new shared_pref_manager();
        try {
            String Loca = "LONG: " + String.valueOf(location.getLongitude()) +"LATI: " + String.valueOf(location.getLatitude());
            Toast.makeText(context, Loca,Toast.LENGTH_SHORT).show();

            if(!String.valueOf(location.getLongitude()).isEmpty() && !String.valueOf(location.getLatitude()).isEmpty())
            {
                shad.donor_save_current_cord_(context,String.valueOf(location.getLongitude()),String.valueOf(location.getLatitude()));
                shad.donor_get_current_cord_(context);
                this.latitide_ = Double.parseDouble(shad.getD_current_latitude());
                this.longitude_ = Double.parseDouble(shad.getD_current_longitude());

            }else {

                shad.donor_get_current_cord_(context);
                this.latitide_ = Double.parseDouble(shad.getD_current_latitude());
                this.longitude_ = Double.parseDouble(shad.getD_current_longitude());

            }

           // this.latitide_ = location.getLatitude();
          //  this.longitude_ = location.getLongitude();
           // this.location = location;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Toast.makeText(context, "gps is on",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(context, "gps is off",Toast.LENGTH_SHORT).show();
    }

    public Double getLatitide_() {
        return latitide_;
    }

    public Double getLongitude_() {
        return longitude_;
    }
}
