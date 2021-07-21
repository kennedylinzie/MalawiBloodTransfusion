package com.example.malawibloodtransfusion.hospital.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.Pie;
import com.anychart.anychart.ValueDataEntry;
import com.example.malawibloodtransfusion.DataBaseHelper;
import com.example.malawibloodtransfusion.Hospital_center;
import com.example.malawibloodtransfusion.shared_pref_manager;

import com.example.malawibloodtransfusion.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.example.malawibloodtransfusion.connectionsManager;
//import com.example.malawibloodtransfusion.hospital.R;

public class hospital_HomeFragment extends Fragment {

    AnyChartView anyChartView;
   private TextView name,district,email;
   private TextView aplus,aminus,bplus,bminus,oplus,ominus,abplus,abminus;
    Pie pie;
    String[] bloodgroup = {"A+","A-","B+","B-","O+","O-","AB+","AB-"};
    int [] units = {0,0,0,0,0,0,0,0};


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.hospital_fragment_home, container, false);

        name = root.findViewById(R.id.home_hospital_name);
        district = root.findViewById(R.id.home_hospital_district);
        email = root.findViewById(R.id.home_hospital_email);
        pie = AnyChart.pie();

        aplus = root.findViewById(R.id.home_hospital_aplus);
        aminus = root.findViewById(R.id.home_hospital_aminus);
        bplus = root.findViewById(R.id.home_hospital_bplus);
        bminus = root.findViewById(R.id.home_hospital_bminus);
        oplus = root.findViewById(R.id.home_hospital_oplus);
        ominus = root.findViewById(R.id.home_hospital_ominus);
        abplus = root.findViewById(R.id.home_hospital_abplus);
        abminus = root.findViewById(R.id.home_hospital_abminus);



        anyChartView = root.findViewById(R.id.any_chart_view);

           setupChart();





        connectionsManager conman = new connectionsManager();

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());



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
                        String veri = product.getString("verified");
                        int A_POSITIVE_ = product.getInt("A_POSITIVE");
                        int A_NEGATIVE_ = product.getInt("A_NEGATIVE");
                        int B_POSITIVE_ = product.getInt("B_POSITIVE");
                        int B_NEGATIVE_ = product.getInt("B_NEGATIVE");
                        int O_POSITIVE_ = product.getInt("O_POSITIVE");
                        int O_NEGATIVE_ = product.getInt("O_NEGATIVE");
                        int AB_POSITIVE_ = product.getInt("AB_POSITIVE");
                        int AB_NEGATIVE_ = product.getInt("AB_NEGATIVE");



                        name.setText(name_.substring(0,1).toUpperCase()+name_.substring(1));
                        district.setText(district_.substring(0,1).toUpperCase()+district_.substring(1));
                        email.setText(email_.substring(0,1).toUpperCase()+email_.substring(1));

                        aplus.setText(A_POSITIVE_+"   Units");
                        aminus.setText(A_NEGATIVE_+"   Units");
                        bplus.setText(B_POSITIVE_+"   Units");
                        bminus.setText(B_NEGATIVE_+"   Units");
                        oplus.setText(O_POSITIVE_+"   Units");
                        ominus.setText(O_NEGATIVE_+"   Units");
                        abplus.setText(AB_POSITIVE_+"   Units");
                        abminus.setText(AB_NEGATIVE_+"   Units");




                        units[0] =A_POSITIVE_;
                        units[1] =A_NEGATIVE_;
                        units[2] =B_POSITIVE_;
                        units[3] =B_NEGATIVE_;
                        units[4] =O_POSITIVE_;
                        units[5] =O_NEGATIVE_;
                        units[6] =AB_POSITIVE_;
                        units[7] =AB_NEGATIVE_;
                        setupChart();


                    }


                } catch (JSONException e) {
                    throw new NullPointerException();
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
                shad.hospital_getlastuser(getContext());

                params.put("id",shad.getH_id());
                params.put("type","2");

                return params;
            }
        };
        requestQueue.add(stringRequest);



        return root;
    }

    private void setupChart() {


        List<DataEntry> dataEntries = new ArrayList<>();

        for (int i1 = 0; i1 < bloodgroup.length ; i1++) {
            dataEntries.add(new ValueDataEntry(bloodgroup[i1],units[i1]));
        }

        pie.data(dataEntries);
        pie.setTitle("Blood available");

        anyChartView.setChart(pie);


    }
}






