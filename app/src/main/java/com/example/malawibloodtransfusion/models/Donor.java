package com.example.malawibloodtransfusion.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.malawibloodtransfusion.connectionsManager;
import com.example.malawibloodtransfusion.donorCenter;
import com.example.malawibloodtransfusion.donor_login;
import com.example.malawibloodtransfusion.donor_register;
import com.example.malawibloodtransfusion.shared_pref_manager;
import com.example.malawibloodtransfusion.event_Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Donor extends  User {
    private String dateofbirth;
    private String gender;
    private String height;
    private String weight;
    private String bloodgroup;
    private String sirname;
    private String success="0";// (1)is successfull (2)already exist (3) some other error
    private String receive_notes;
    public  String location_response;
    private final connectionsManager conman = new connectionsManager();

    private String msg;
    private String msg_received;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg_received() {
        return msg_received;
    }

    public void setMsg_received(String msg_received) {
        this.msg_received = msg_received;
    }

    public String getReceive_notes() {
        return receive_notes;
    }

    public void setReceive_notes(String receive_notes) {
        this.receive_notes = receive_notes;
    }

    public String getLocation_response() {
        return location_response;
    }

    public void setLocation_response(String location_response) {
        this.location_response = location_response;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public connectionsManager getConman() {
        return conman;
    }


    public void donor_Register(Context context) {



        RequestQueue requestQueue = Volley.newRequestQueue(context);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getDonor_register(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if(response.equals("user already exists"))
                {
                    setSuccess("2");//2 already exists
                    Toast.makeText(context,"User already exists",Toast.LENGTH_SHORT).show();
                }else if(response.equals("successful"))
                {
                    setSuccess("1");//1 for successs
                    Toast.makeText(context,"Successful",Toast.LENGTH_SHORT).show();


                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setSuccess("3");//for some error
                Toast.makeText(context,"Error.,,, :  " + error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name",getName());
                params.put("sirname",getSirname());
                params.put("dob",getDateofbirth());
                params.put("gender",getGender());
                params.put("height",getHeight());
                params.put("weight",getWeight());
                params.put("bloodgroup",getBloodgroup());
                params.put("phone_number",getPhone_number());
                params.put("email",getEmail());
                params.put("password",getPassword());
                params.put("type","1");
                params.put("usertype","1");
                params.put("receive_notes","true");
                params.put("verified","false");


                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
    public void donor_login(Context context) {


        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getDonor_login(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("message");
                    String status = jsonObject.getString("status");
                    if(status.equals("ok"))
                    {

                        setId(jsonObject.getString("id"));
                        setName(jsonObject.getString("name"));
                        setSirname(jsonObject.getString("sirname"));
                        setDateofbirth(jsonObject.getString("dob"));
                        setGender(jsonObject.getString("gender"));
                        setHeight(jsonObject.getString("height"));
                        setWeight(jsonObject.getString("weight"));
                        setBloodgroup(jsonObject.getString("bloodgroup"));
                        setPhone_number(jsonObject.getString("phone_number"));
                        setEmail(jsonObject.getString("email"));
                        setUsertype(jsonObject.getString("usertype"));
                        setReceive_notes(jsonObject.getString("receive_notes"));
                        setVerified(jsonObject.getString("verified"));

                        shared_pref_manager pref_manager = new shared_pref_manager();
                        pref_manager.set_current_user(context,getUsertype());
                        pref_manager.donor_save_user(context,getId(),getName(),getSirname(),getBloodgroup(),getPhone_number(),getDateofbirth(),getUsertype(),getReceive_notes(),getVerified(),getGender(),getHeight(),getWeight(),getEmail());

                        setSuccess("1");

                    }else {
                        Toast.makeText(context,message+"",Toast.LENGTH_LONG).show();
                        setSuccess("2");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setSuccess("3");
                Toast.makeText(context,"" + error.getMessage(),Toast.LENGTH_SHORT).show();



            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email",getEmail());
                params.put("password",getPassword());
                params.put("type","1");

                return params;
            }
        };
        requestQueue.add(stringRequest);



    }

    public String getSirname() {
        return sirname;
    }

    public void setSirname(String sirname) {
        this.sirname = sirname;
    }

    public String getDateofbirth() {
        return dateofbirth;
    }

    public void setDateofbirth(String dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }


    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public void setId(String id) {
        super.setId(id);
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Override
    public String getPhone_number() {
        return super.getPhone_number();
    }

    @Override
    public void setPhone_number(String phone_number) {
        super.setPhone_number(phone_number);
    }

    @Override
    public String getUsertype() {
        return super.getUsertype();
    }

    @Override
    public void setUsertype(String usertype) {
        super.setUsertype(usertype);
    }

    @Override
    public String getVerified() {
        return super.getVerified();
    }

    @Override
    public void setVerified(String verified) {
        super.setVerified(verified);
    }

    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public void Delete_account(String id,Context context) {

        RequestQueue requestQueue = Volley.newRequestQueue(context);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getDelete_account(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if(response.equals("successful"))
                {
                   setSuccess("1");  //1 for successs
                    Toast.makeText(context,"User deleted",Toast.LENGTH_SHORT).show();

                }else if(response.equals("User doesn't exist"))
                {
                    setSuccess("2");   //2 doesnt exist
                    Toast.makeText(context,"user doesn't exist",Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setSuccess("3");//for some error
                Toast.makeText(context,"Error.,,, :  " + error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id",id);
                params.put("type","1");
                return params;
            }
        };
        requestQueue.add(stringRequest);




    }

    @Override
    public void Login(Context context,String email, String password) {

    }

    @Override
    public void Logout(Context context) {
        shared_pref_manager shad = new shared_pref_manager();
        shad.clear_current_user(context);
        shad.donor_clear_data_donor_(context);


    }

    public void update_donor(Context context)
    {

        shared_pref_manager shad = new shared_pref_manager();
        shad.donor_getlastuser(context);



        RequestQueue requestQueue = Volley.newRequestQueue(context);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getUpdate_accounts(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if(response.equals("successful"))
                {
                    setSuccess("1");//2 already exists
                   // Toast.makeText(context,"successful",Toast.LENGTH_SHORT).show();
                }else if(response.equals("NOT successful"))
                {
                    setSuccess("2");//1 for successs
                    Toast.makeText(context,"failed",Toast.LENGTH_SHORT).show();


                }else
                {
                    setSuccess("3");//1 for successs
                   // Toast.makeText(context,"some error",Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setSuccess("3");//for some error
                Toast.makeText(context,"Error.,,, :  " + error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name",getName());
                params.put("sirname",getSirname());
                params.put("dob",getDateofbirth());
                params.put("gender",getGender());
                params.put("height",getHeight());
                params.put("weight",getWeight());
                params.put("bloodgroup",getBloodgroup());
                params.put("phone_number",getPhone_number());
                params.put("email",getEmail());
                params.put("type","2");
                params.put("id",shad.getD_id());



                return params;
            }
        };
        requestQueue.add(stringRequest);



    }


    public void change_password(Context context,String old_password,String new_password)
    {

        shared_pref_manager shad = new shared_pref_manager();
        shad.donor_getlastuser(context);



        RequestQueue requestQueue = Volley.newRequestQueue(context);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getUpdate_accounts(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if(response.equals("successful"))
                {
                    setSuccess("1");//2 already exists
                    // Toast.makeText(context,"successful",Toast.LENGTH_SHORT).show();
                }else if(response.equals("Wrong password"))
                {
                    setSuccess("2");//1 for successs
                    //Toast.makeText(context,"wrong",Toast.LENGTH_SHORT).show();


                }else
                {
                    setSuccess("3");//1 for successs
                    // Toast.makeText(context,"some error",Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setSuccess("3");//for some error
                Toast.makeText(context,"Error.,,, :  " + error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("old_pass",old_password);
                params.put("new_pass",new_password);
                params.put("type","3");
                params.put("id",shad.getD_id());



                return params;
            }
        };
        requestQueue.add(stringRequest);


    }

    public void chenge_receieve_notes(Context context,String state){

        shared_pref_manager shad = new shared_pref_manager();
        shad.donor_getlastuser(context);



        RequestQueue requestQueue = Volley.newRequestQueue(context);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getUpdate_accounts(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if(response.equals("successful"))
                {
                     setSuccess("1");//2 already exists
                     Toast.makeText(context,"successful",Toast.LENGTH_SHORT).show();
                     shad.donor_change_receive_notes(context,state);
                }else if(response.equals("NOT successful"))
                {
                    setSuccess("2");//1 for successs
                    Toast.makeText(context,"failed",Toast.LENGTH_SHORT).show();


                }else
                {
                    setSuccess("3");//1 for successs
                     Toast.makeText(context,"some error",Toast.LENGTH_SHORT).show();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setSuccess("3");//for some error
                Toast.makeText(context,"Error.,,, :  " + error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("receive_notes",state);
                params.put("type","4");
                params.put("id",shad.getD_id());



                return params;
            }
        };
        requestQueue.add(stringRequest);



    }

    public void get_hospital_location_data(Context context)
    {


        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getLocation_data_pulls(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

               // Toast.makeText(context,response,Toast.LENGTH_LONG).show();
                setLocation_response(response);

/*
                try {
                    //converting the string to json array object
                    JSONArray array = new JSONArray(response);

                    //traversing through all the object
                    for (int i = 0; i < array.length(); i++) {

                        //getting product object from json array
                        JSONObject product = array.getJSONObject(i);

                        //adding the product to product list

                       int id =  product.getInt("id");
                        String name =        product.getString("hospital_name");
                        String email =        product.getString("email");
                        String district =       product.getString("district");
                        String licence =       product.getString("licence");
                        double lat =       product.getDouble("latitude");
                        double lon =      product.getDouble("latitude");

                        Toast.makeText(context,id+""+name+""+email+""+district+""+licence+""+lat+""+lon+"",Toast.LENGTH_LONG).show();

                      //  hospitals[i] = id+""+name+""+email+""+district+""+licence+""+lat+""+lon+"";

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


*/


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                setSuccess("3");
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

    public void  get_message(Context context)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getDonor_notif_link(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                //Toast.makeText(context,response,Toast.LENGTH_LONG).show();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    String message = jsonObject.getString("message_");
                    String status = jsonObject.getString("status");
                    if(status.equals("ok"))
                    {

                        String received = jsonObject.getString("msg_received");
                        String eve_message = jsonObject.getString("message");

                        if(received.equals("true"))
                        {
                           // Toast.makeText(context,"its trueeeee",Toast.LENGTH_LONG).show();
                            event_Prefs vent = new event_Prefs();
                            vent.donor_save_message(context,eve_message,received);
                            vent.donor_get_message(context);

                           // setMsg(eve_message);
                           // setMsg_received(received);


                        }else if(received.equals("false")) {
                           // Toast.makeText(context,"its falseeeee",Toast.LENGTH_LONG).show();
                            event_Prefs vent = new event_Prefs();
                            vent.donor_save_message(context,eve_message,received);
                            vent.donor_get_message(context);
                        }


                    }else {
                      //  Toast.makeText(context,message+"...",Toast.LENGTH_LONG).show();
                       // setSuccess("2");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
             //   setSuccess("3");
             //   Toast.makeText(context,"" + error.getMessage(),Toast.LENGTH_SHORT).show();



            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                shared_pref_manager pre = new shared_pref_manager();
                pre.donor_getlastuser(context);

                params.put("id",pre.getD_id());
                params.put("type","1");

                return params;
            }
        };
        requestQueue.add(stringRequest);


    }

    public void set_msg_off(Context context)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getDonor_notif_link(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                shared_pref_manager pre = new shared_pref_manager();
                pre.donor_getlastuser(context);

                params.put("id",pre.getD_id());
                params.put("type","2");

                return params;
            }
        };
        requestQueue.add(stringRequest);



    }


}
