package com.example.malawibloodtransfusion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.malawibloodtransfusion.models.Donor;
import com.example.malawibloodtransfusion.models.Hospital;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Hospital_center extends AppCompatActivity {


    private FloatingActionButton fb;
    // Create the Handler object (on the main thread by default)
   private Handler handler = new Handler();
    private Runnable runnableCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_center);
        fb = findViewById(R.id.fab);

        BottomNavigationView navView = findViewById(R.id.nav_view_hospital);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.hospital_navigation_home, R.id.hospital_navigation_dashboard, R.id.hospital_navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_hospital);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);








       // Define the code block to be executed
        runnableCode = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
               // Log.d("Handlers", "Called on main thread");
                //Toast.makeText(getApplicationContext(),".......",Toast.LENGTH_SHORT).show();
                check_blocked();
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                handler.postDelayed(this, 10000);
            }
        };
       // Start the initial runnable task by posting through the handler
        handler.post(runnableCode);


        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),hospital_views.class);
                startActivity(intent);


            }
        });


    }

    public void hospital_logout(View view) {

        Hospital hos = new Hospital();
        hos.Logout(getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), Entry_point.class);
        startActivity(intent);
        finish();

    }

    public void check_blocked()
    { connectionsManager conman = new connectionsManager();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getLocation_data_pulls(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {
               // Toast.makeText(getApplicationContext(),response+"..............",Toast.LENGTH_LONG).show();
                //   Toast.makeText(getContext(),"we cant connect"+response,Toast.LENGTH_LONG).show();
                shared_pref_manager shad = new shared_pref_manager();
                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);
                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {
                        //getting product object from json array
                        JSONObject product = array.getJSONObject(i);
                        //adding the product to product list
                        String veri = product.getString("verified");
                        if(veri.equals("false"))
                        {
                            shad.set_activation(getApplicationContext(),"false");
                            //Hospital hos = new Hospital();
                          //  hos.Logout(getApplicationContext());
                            Intent intent = new Intent(getApplicationContext(), hospital_waiting_room.class);
                            startActivity(intent);
                            finish();
                            handler.removeCallbacks(runnableCode);

                        }
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


    public void hospital_delete_account(View view) {

        Toast.makeText(getApplicationContext(), "delete.........", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("NOTE!");
        builder.setMessage("Do you want to delete your account as a donor ?? THIS CAN'T BE UNDONE");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"OK WAS CLICKED",Toast.LENGTH_SHORT).show();

                Hospital hos = new Hospital();
                shared_pref_manager shad = new shared_pref_manager();
                shad.hospital_getlastuser(getApplicationContext());
                hos.Delete_account(shad.getH_id(),getApplicationContext());


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // Toast.makeText(getApplicationContext(),""+,Toast.LENGTH_SHORT).show();
                        if(hos.getSuccess().equals("1"))
                        {
                            shad.clear_current_user(getApplicationContext());
                            shad.hospital_clear_data_(getApplicationContext());
                            Intent intent = new Intent(getApplicationContext(), Entry_point.class);
                            startActivity(intent);
                            finish();
                        }else if(hos.getSuccess().equals("2"))
                        {
                            shad.clear_current_user(getApplicationContext());
                            shad.hospital_clear_data_(getApplicationContext());
                            Intent intent = new Intent(getApplicationContext(), Entry_point.class);
                            startActivity(intent);
                            finish();
                        }else if(hos.getSuccess().equals("3"))
                        {
                            Toast.makeText(getApplicationContext(),"We cant reach our servers contact the admin",Toast.LENGTH_SHORT).show();
                        }

                    }
                },1000);



            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(getApplicationContext(),"Cancel WAS CLICKED",Toast.LENGTH_SHORT).show();


            }
        });
        builder.create().show();

    }

    public void hospital_update_account(View view) {

        Intent intent = new Intent(getApplicationContext(), hospital_update_Activity.class);
        startActivity(intent);
      //  finish();

    }

    public void hospital_blood_edit(View view) {

        Intent intent = new Intent(getApplicationContext(), blood_update_Activity.class);
        startActivity(intent);
        //  finish();

    }

    public void hospital_help_page(View view) {

        Intent intent = new Intent(getApplicationContext(), hospital_help_page.class);
        startActivity(intent);

    }
}