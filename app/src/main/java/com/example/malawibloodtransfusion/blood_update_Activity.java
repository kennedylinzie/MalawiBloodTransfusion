package com.example.malawibloodtransfusion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class blood_update_Activity extends AppCompatActivity {

    private EditText a_POSITIVE;
    private EditText a_NEGATIVE;
    private EditText b_POSITIVE;
    private EditText b_NEGATIVE;
    private EditText o_POSITIVE;
    private EditText o_NEGATIVE;
    private EditText ab_POSITIVE;
    private EditText ab_NEGATIVE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_update);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        a_POSITIVE = findViewById(R.id.hospital_et_update_aplus);
        a_NEGATIVE = findViewById(R.id.et_hospital_update_aminus);
        b_POSITIVE = findViewById(R.id.hospital_et_update_phone_bplus);
        b_NEGATIVE = findViewById(R.id.hospital_et_update_bminus);
        o_POSITIVE = findViewById(R.id.hospital_et_update_oplus);
        o_NEGATIVE = findViewById(R.id.et_hospital_update_ominus);
        ab_POSITIVE = findViewById(R.id.hospital_et_update_abplus);
        ab_NEGATIVE = findViewById(R.id.hospital_et_update_abminus);




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
                        String A_POSITIVE_ = product.getString("A_POSITIVE");
                        String A_NEGATIVE_ = product.getString("A_NEGATIVE");
                        String B_POSITIVE_ = product.getString("B_POSITIVE");
                        String B_NEGATIVE_ = product.getString("B_NEGATIVE");
                        String O_POSITIVE_ = product.getString("O_POSITIVE");
                        String O_NEGATIVE_ = product.getString("O_NEGATIVE");
                        String AB_POSITIVE_ = product.getString("AB_POSITIVE");
                        String AB_NEGATIVE_ = product.getString("AB_NEGATIVE");





                        a_POSITIVE.setText(A_POSITIVE_);
                        a_NEGATIVE.setText(A_NEGATIVE_);
                        b_POSITIVE.setText(B_POSITIVE_);
                        b_NEGATIVE.setText(B_NEGATIVE_);
                        o_POSITIVE.setText(O_POSITIVE_);
                        o_NEGATIVE.setText(O_NEGATIVE_);
                        ab_POSITIVE.setText(AB_POSITIVE_);
                        ab_NEGATIVE.setText(AB_NEGATIVE_);






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

    public void update_blood_stock(View view) {

        Hospital hos = new Hospital();
        hos.update_blood(getApplicationContext(),
                a_POSITIVE.getText().toString().trim(),
                a_NEGATIVE.getText().toString().trim(),
                b_POSITIVE.getText().toString().trim(),
                b_NEGATIVE.getText().toString().trim(),
                o_POSITIVE.getText().toString().trim(),
                o_NEGATIVE.getText().toString().trim(),
                ab_POSITIVE.getText().toString().trim(),
                ab_NEGATIVE.getText().toString().trim()
        );




    }
}