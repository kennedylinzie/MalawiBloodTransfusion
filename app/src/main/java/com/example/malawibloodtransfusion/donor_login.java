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
import android.view.WindowManager;
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
import com.example.malawibloodtransfusion.models.Donor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class donor_login extends AppCompatActivity {

    private EditText etEmail,etPassword;
    private String email,password;
    private TextView temp;
    private final connectionsManager conman = new connectionsManager();
    private final Donor don = new Donor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_login);

        etEmail = findViewById(R.id.donot_et_login_email);
        etPassword = findViewById(R.id.donot_et_login_password);
      //  temp = findViewById(R.id.tempview);


    }

    public void goto_register(View view) {
        Intent intent = new Intent(this, donor_register.class );
        startActivity(intent);
        finish();
    }

    public void donor_lgin(View view) {

        email = etEmail.getText().toString().trim();
        password = etPassword.getText().toString().trim();
        if (email.isEmpty() || password.isEmpty())
        {
            Toast.makeText(this,"Please make sure all fields are filled",Toast.LENGTH_SHORT).show();
            return;
        }


                  don.setEmail(email);
                  don.setPassword(password);
                  don.donor_login(getApplicationContext());


                  new Handler().postDelayed(new Runnable() {
                      @Override
                      public void run() {

                          if(don.getSuccess().equals("1"))
                          {

                              Intent intent = new Intent(getApplicationContext(), donorCenter.class);
                              intent.putExtra("id",don.getId());
                              intent.putExtra("name",don.getName());
                              intent.putExtra("sirname",don.getSirname());
                              intent.putExtra("dob",don.getDateofbirth());
                              intent.putExtra("gender",don.getGender());
                              intent.putExtra("height",don.getHeight());
                              intent.putExtra("weight",don.getWeight());
                              intent.putExtra("bloodgroup",don.getBloodgroup());
                              intent.putExtra("phone_number",don.getPhone_number());
                              intent.putExtra("email",don.getEmail());
                              intent.putExtra("usertype",don.getUsertype());
                              intent.putExtra("receive_notes",don.getReceive_notes());
                              intent.putExtra("verified",don.getVerified());
                              startActivity(intent);
                              finish();

                          }
                          else if(don.getSuccess().equals("2"))
                          {////wrong username

                              // Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
                              //  return;
                          }
                          else if(don.getSuccess().equals("3"))
                          {   ///something is wrong let the admin know


                          }else if(don.getSuccess().equals("0"))
                          {
                              Toast.makeText(getApplicationContext(),"We cant communicate with home,might be our servers or your network",Toast.LENGTH_SHORT).show();
                              return;
                          }

                      }
                  },500);







    }
}