package com.example.malawibloodtransfusion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.malawibloodtransfusion.models.Donor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


//import com.example.malawibloodtransfusion.donor.MainActivity;

public class hospital_waiting_room extends AppCompatActivity {

    private TextView tv_name,tv_sirname,tv_dob,tv_usertype,tv_receive_notes,tv_verified;
    private String name,sirname,dob,usertype,receive_notes,verified;
    private String activeAccount;
    private String status = "0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_waiting_room);


        shared_pref_manager pref_manager = new shared_pref_manager();
        activeAccount =  pref_manager.get_activation(this);
       // pref_manager.set_current_user(getApplicationContext(),"3");
      // Toast.makeText(getApplicationContext(),""+activeAccount,Toast.LENGTH_LONG).show();


        try {
            if(activeAccount.equals("true"))
            {
                //the user has already registered in the system
                end_thing();
                Intent intent = new Intent(getApplicationContext(), Hospital_center.class);
                startActivity(intent);
                finish();

            }else if(activeAccount.equals("false")) {
                start_thing();
            }
        }catch (Exception e){

        }

    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void start_thing() {
        //start the service
        Intent intent = new Intent(getApplicationContext(),MyService.class);
        startService(intent);

    }

    public void end_thing() {
         //kill the service
        Intent intent = new Intent(getApplicationContext(),MyService.class);
        stopService(intent);

    }
}