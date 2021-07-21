package com.example.malawibloodtransfusion.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.malawibloodtransfusion.Hospital_center;
import com.example.malawibloodtransfusion.Hospital_login;
import com.example.malawibloodtransfusion.Hospital_register;
import com.example.malawibloodtransfusion.donor_login;
import com.example.malawibloodtransfusion.connectionsManager;
import com.example.malawibloodtransfusion.shared_pref_manager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Hospital extends User {
    private String mean;
    private  connectionsManager conman;
    private String district;
    private String licence;
    private String latitude;
    private String longitude;
    private String success;


    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
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
    public void Delete_account(String id, Context context) {
        conman = new connectionsManager();

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
                params.put("type","2");
                return params;
            }
        };
        requestQueue.add(stringRequest);

    }


    @Override
    public void Login( Context context, String email, String password) {

        conman = new connectionsManager();


        RequestQueue requestQueue = Volley.newRequestQueue(context);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getHospital_login(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    String status = jsonObject.getString("status");
                    if(status.equals("ok"))
                    {
                        setSuccess("1");
                        String id = jsonObject.getString("id");
                        String hospital_name = jsonObject.getString("hospital_name");
                        String email = jsonObject.getString("email");
                        String district = jsonObject.getString("district");
                        String licence = jsonObject.getString("licence");
                        String latitude = jsonObject.getString("latitude");
                        String longitude = jsonObject.getString("longitude");
                        String usertype = jsonObject.getString("usertype");
                        String verified = jsonObject.getString("verified");

                        shared_pref_manager pref_manager = new shared_pref_manager();
                        pref_manager.set_current_user(context,"3");
                        pref_manager.hospital_save_user(context,id,hospital_name,email,district,licence,latitude,longitude,usertype,verified);

/*
                        Intent intent = new Intent(context, Hospital_center.class);
                        intent.putExtra("id",id);
                        intent.putExtra("hospital_name",hospital_name);
                        intent.putExtra("email",email);
                        intent.putExtra("district",district);
                        intent.putExtra("licence",licence);
                        intent.putExtra("latitude",latitude);
                        intent.putExtra("longitude",longitude);
                        intent.putExtra("usertype",usertype);
                        intent.putExtra("verified",verified);

                        startActivity(intent);
                        finish();

 */

                    }else {
                        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                        setSuccess("3");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context,"" + error.getMessage(),Toast.LENGTH_SHORT).show();
                setSuccess("2");



            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("email",email);
                params.put("password",password);
                params.put("type","2");

                return params;
            }
        };
        requestQueue.add(stringRequest);




    }


    public void Register( Context context, String name, String email, String district, String licence, String latitude, String longitude, String password,String phone_number) {
             conman = new connectionsManager();


        RequestQueue requestQueue = Volley.newRequestQueue(context);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getHospital_register(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.equals("user already exists")) {

                    //Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show();
                    setSuccess("2");

                } else if (response.equals("successful")) {

                 Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();

                    setSuccess("1");
                    /*
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           // Intent intent = new Intent(context, donor_login.class);
                            //startActivity(intent);
                          //  finish();

                        }
                    }, 1000);
*/
                }else {
                    setSuccess("3");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Error :  " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("hospital_name", name);
                params.put("email", email);
                params.put("district", district);
                params.put("licence", licence);
                params.put("phone_number", phone_number);
                params.put("latitude", latitude);
                params.put("longitude", longitude);
                params.put("password", password);
                params.put("usertype", "2");
                params.put("verified", "false");
                params.put("type", "2");


                return params;
            }
        };
        requestQueue.add(stringRequest);




    }


    @Override
    public void Logout(Context context) {

        shared_pref_manager shad = new shared_pref_manager();
        shad.clear_current_user(context);
        shad.hospital_clear_data_(context);

    }


    public void update_blood( Context context, String A_POSITIVE, String A_NEGATIVE,String B_POSITIVE, String B_NEGATIVE, String O_POSITIVE, String O_NEGATIVE, String AB_POSITIVE, String AB_NEGATIVE) {
        conman = new connectionsManager();


        RequestQueue requestQueue = Volley.newRequestQueue(context);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getUpdate_accounts(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.equals("user already exists")) {

                    Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show();
                    setSuccess("2");

                } else if (response.equals("successful")) {

                      Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();

                    setSuccess("1");
                    /*
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           // Intent intent = new Intent(context, donor_login.class);
                            //startActivity(intent);
                          //  finish();

                        }
                    }, 1000);
*/
                }else {
                    setSuccess("3");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Error :  " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                shared_pref_manager shad = new shared_pref_manager();
                shad.hospital_getlastuser(context);

                Map<String, String> params = new HashMap<>();
                params.put("A_POSITIVE", A_POSITIVE);
                params.put("A_NEGATIVE", A_NEGATIVE);
                params.put("B_POSITIVE", B_POSITIVE);
                params.put("B_NEGATIVE", B_NEGATIVE);
                params.put("O_POSITIVE", O_POSITIVE);
                params.put("O_NEGATIVE", O_NEGATIVE);
                params.put("AB_POSITIVE", AB_POSITIVE);
                params.put("AB_NEGATIVE", AB_NEGATIVE);
                params.put("id", shad.getH_id());
                params.put("type", "5");


                return params;
            }
        };
        requestQueue.add(stringRequest);




    }

    public void update_details( Context context, String hospital_name, String email,String district, String licence,String phone_number) {
        conman = new connectionsManager();


        RequestQueue requestQueue = Volley.newRequestQueue(context);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getUpdate_accounts(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.equals("user already exists")) {

                    Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show();
                    setSuccess("2");

                } else if (response.equals("successful")) {

                    Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();

                    setSuccess("1");
                    /*
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           // Intent intent = new Intent(context, donor_login.class);
                            //startActivity(intent);
                          //  finish();

                        }
                    }, 1000);
*/
                }else {
                    setSuccess("3");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Error :  " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                shared_pref_manager shad = new shared_pref_manager();
                shad.hospital_getlastuser(context);

                Map<String, String> params = new HashMap<>();
                params.put("hospital_name", hospital_name);
                params.put("email", email);
                params.put("district", district);
                params.put("licence", licence);
                params.put("phone_number",phone_number);

                params.put("id", shad.getH_id());
                params.put("type", "6");


                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void update_location( Context context, String latitude, String longitude) {
        conman = new connectionsManager();


        RequestQueue requestQueue = Volley.newRequestQueue(context);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getUpdate_accounts(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.equals("user already exists")) {

                    Toast.makeText(context, "User already exists", Toast.LENGTH_SHORT).show();
                    setSuccess("2");

                } else if (response.equals("successful")) {

                    Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();

                    setSuccess("1");
                    /*
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           // Intent intent = new Intent(context, donor_login.class);
                            //startActivity(intent);
                          //  finish();

                        }
                    }, 1000);
*/
                }else {
                    setSuccess("3");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Error :  " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                shared_pref_manager shad = new shared_pref_manager();
                shad.hospital_getlastuser(context);

                Map<String, String> params = new HashMap<>();

                params.put("longitude", longitude);
                params.put("latitude", latitude);

                params.put("id", shad.getH_id());
                params.put("type", "7");


                return params;
            }
        };
        requestQueue.add(stringRequest);

    }

    public void update_change_password( Context context, String oldpass, String newpass) {
        conman = new connectionsManager();


        RequestQueue requestQueue = Volley.newRequestQueue(context);


        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getUpdate_accounts(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.equals("NOT successful")) {

                    Toast.makeText(context, "failed try again", Toast.LENGTH_SHORT).show();
                    setSuccess("2");

                } else if (response.equals("successful")) {

                    Toast.makeText(context, "Successful", Toast.LENGTH_SHORT).show();

                    setSuccess("1");
                    /*
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           // Intent intent = new Intent(context, donor_login.class);
                            //startActivity(intent);
                          //  finish();

                        }
                    }, 1000);
*/
                }else if(response.equals("Wrong password")){
                    Toast.makeText(context, "Wrong password", Toast.LENGTH_SHORT).show();
                    setSuccess("3");
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "Error :  " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }) {

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                shared_pref_manager shad = new shared_pref_manager();
                shad.hospital_getlastuser(context);

                Map<String, String> params = new HashMap<>();

                params.put("old_pass", oldpass);
                params.put("new_pass", newpass);

                params.put("id", shad.getH_id());
                params.put("type", "8");


                return params;
            }
        };
        requestQueue.add(stringRequest);

    }


}
