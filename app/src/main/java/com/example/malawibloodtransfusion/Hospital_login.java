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
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.malawibloodtransfusion.models.Hospital;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Hospital_login extends AppCompatActivity {

    private EditText et_email,et_password;
    private String email,password;
    private final connectionsManager conman = new connectionsManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_login);

        et_email = findViewById(R.id.hospital_et_login_email);
        et_password = findViewById(R.id.hospital_et_login_password);
    }

    public void hospital_login(View view) {
        email = et_email.getText().toString().trim();
        password = et_password.getText().toString().trim();


        if(email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this,"Fields should not be empty",Toast.LENGTH_SHORT).show();

        }else
            {
                ///login code
                Hospital hos = new Hospital();
                hos.Login(getApplicationContext(),email,password);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(hos.getSuccess().equals("1"))
                        {
                            Toast.makeText(getApplicationContext(),"its success",Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(getApplicationContext(), hospital_waiting_room.class);
                            startActivity(intent);
                            finish();

                        }else if(hos.getSuccess().equals("2"))
                        {
                            Toast.makeText(getApplicationContext(),"it failed",Toast.LENGTH_LONG).show();
                        }else if(hos.getSuccess().equals("3")) {
                            Toast.makeText(getApplicationContext(),"there may be a network problem",Toast.LENGTH_LONG).show();
                        }


                    }
                }, 1000);







            }





    }

    public void goto_register(View view) {

        Intent intent = new Intent(this,Hospital_register.class);
        startActivity(intent);


    }
}