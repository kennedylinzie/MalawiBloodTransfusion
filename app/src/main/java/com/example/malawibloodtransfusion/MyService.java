package com.example.malawibloodtransfusion;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyService  extends Service {

    private MediaPlayer mediaPlayer;
    private  Handler handler = new Handler();
    private Runnable runnableCode;


    public MyService() {
    }

    @Override
    public void onCreate() {
       // mediaPlayer= MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
       // mediaPlayer.setLooping(true);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        try {
            // Create the Handler object (on the main thread by default)

            // Define the code block to be executed
            runnableCode = new Runnable() {
                @Override
                public void run() {

                    check_if_verified();

                    // Repeat this the same runnable code block again another 2 seconds
                    // 'this' is referencing the Runnable object
                    handler.postDelayed(this, 5000);
                }
            };
            // Start the initial runnable task by posting through the handler
            handler.post(runnableCode);

        }catch (Exception e){

        }



        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnableCode);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    public void check_if_verified() {
        try {

            connectionsManager conman;
            conman = new connectionsManager();


            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


            StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getHospital_check_if_verified_link(), new Response.Listener<String>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(String response) {


                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String stat = jsonObject.getString("verified");
                        String status = jsonObject.getString("status");
                        if(stat.equals("true"))
                        {
                            Toast.makeText(getApplicationContext(),jsonObject.getString("verified"),Toast.LENGTH_LONG).show();

                            shared_pref_manager pref_manager = new shared_pref_manager();
                            pref_manager.set_current_user(getApplicationContext(),"2");

                            Intent intent = new Intent(getApplicationContext(), Hospital_center.class );
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            handler.removeCallbacks(runnableCode);




                        }else if(stat.equals("false")) {
                            Toast.makeText(getApplicationContext(),jsonObject.getString("waiting for verification"),Toast.LENGTH_LONG).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
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
                    params.put("type","1");

                    return params;
                }
            };
            requestQueue.add(stringRequest);




        }catch (Exception e){

        }



    }

}
