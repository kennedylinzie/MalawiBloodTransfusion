package com.example.malawibloodtransfusion;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class userAdapter extends RecyclerView.Adapter<userAdapter.userViewHolder> {

    public Context context;

    public static class userViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout containerView;
        private final TextView name;
        private final TextView district;
        private final TextView email;


        public userViewHolder(@NonNull View view) {
             super(view);
            containerView = view.findViewById(R.id.user_card_row);
            name = view.findViewById(R.id.rec_name);
            district = view.findViewById(R.id.rec_district);
            email = view.findViewById(R.id.rec_email);



                    containerView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //CAST THE THE TAG AS THE USER
                            user_ current = (user_) containerView.getTag();
                            Intent intent = new Intent(v.getContext(),viewSpecificuser.class);
                            intent.putExtra("name",current.getName());
                            intent.putExtra("email",current.getEmail());
                            intent.putExtra("district",current.getDistrict());

                            intent.putExtra("aplus",current.getA_plus());
                            intent.putExtra("aminus",current.getA_minus());
                            intent.putExtra("bplus",current.getB_plus());
                            intent.putExtra("bminus",current.getB_minus());
                            intent.putExtra("oplus",current.getO_plus());
                            intent.putExtra("ominus",current.getO_minus());
                            intent.putExtra("abplus",current.getAb_plus());
                            intent.putExtra("abminus",current.getAb_minus());


                            v.getContext().startActivity(intent);

                        }
                    });


        }

    }
/*
    public List<user_> users = Arrays.asList(


            new user_("james","james@gmail.com","balaka"),
            new user_("ivy","ivy@gmail.com","zomba"),
            new user_("jones","jones@gmail.com","lilongwe")

    );
    */



    public List<user_> users = new ArrayList<>();
    private RequestQueue requestQueue;

    userAdapter(Context context){

    requestQueue = Volley.newRequestQueue(context);
        load_data(context);
    }


    public void load_data(Context context){
        //users.add(new user_("name","email","district"));

        connectionsManager conman  = new connectionsManager();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getLocation_data_pulls(), new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

               // Toast.makeText(context.getApplicationContext(),response+"..............",Toast.LENGTH_LONG).show();
                //   Toast.makeText(getContext(),"we cant connect"+response,Toast.LENGTH_LONG).show();
                shared_pref_manager shad = new shared_pref_manager();
                shad.hospital_getlastuser(context);



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

                        //distance between
                        float[] results = new float[1];
                        Location.distanceBetween(my_latitude,my_longitude,latitude,lognitude,results);
                        float  distance = results[0];

                        float dist=0;

                       // float kilometre = (float) (distance/1000);
                        float metre = (float) (distance/5000);



                       // Toast.makeText(context.getApplicationContext(),name+" "+email+" "+district,Toast.LENGTH_LONG).show();
                        users.add(i,new user_(name.substring(0,1).toUpperCase()+name.substring(1),
                                email.substring(0,1).toUpperCase()+email.substring(1),
                                district.substring(0,1).toUpperCase()+district.substring(1),
                                A_POSITIVE,
                                A_NEGATIVE,
                                B_POSITIVE,
                                B_NEGATIVE,
                                O_POSITIVE,
                                O_NEGATIVE,
                                AB_POSITIVE,
                                AB_NEGATIVE,
                                metre


                        ));




                        //    Toast.makeText(context.getApplicationContext(),"..............",Toast.LENGTH_LONG).show();
                    }

                    notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"" + error.getMessage(),Toast.LENGTH_SHORT).show();



            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                shared_pref_manager shad = new shared_pref_manager();
                shad.donor_getlastuser(context);

                params.put("id",shad.getD_id());
                params.put("type","1");

                return params;
            }
        };
        requestQueue.add(stringRequest);



    }



    @NonNull
    @Override
    public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View  view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_rows,parent,false);


        return new userViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull userViewHolder holder, int position) {
          user_ current = users.get(position);
          holder.name.setText(current.getName());
          holder.district.setText(current.getDistrict());
          holder.email.setText(current.getEmail());
         //get access to the current user
          holder.containerView.setTag(current);
          //Collections.sort(users.sort());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
