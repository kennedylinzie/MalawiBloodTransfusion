package com.example.malawibloodtransfusion;

import android.content.Context;
import android.content.SharedPreferences;

public class  shared_pref_manager {

    private String d_current_latitude;
    private String d_current_longitude;

    private String d_id;
    private String d_name;
    private String d_sirname;
    private String d_dob;
    private String d_usertype;
    private String d_receive_notes;
    private String d_verified;
    private String d_bloodgroup;
    private String d_phone;
    private String d_email;
    private String d_height;
    private String d_weight;
    private String d_gender;


    private String h_id;
    private String h_hospital_name;
    private String h_email;
    private String h_district;
    private String h_licence;
    private String h_latitude;
    private String h_longitude;
    private String h_usertype;
    private String h_verified;


    public String getD_current_latitude() {
        return d_current_latitude;
    }

    public void setD_current_latitude(String d_current_latitude) {
        this.d_current_latitude = d_current_latitude;
    }

    public String getD_current_longitude() {
        return d_current_longitude;
    }

    public void setD_current_longitude(String d_current_longitude) {
        this.d_current_longitude = d_current_longitude;
    }

    public String getD_email() {
        return d_email;
    }

    public void setD_email(String d_email) {
        this.d_email = d_email;
    }

    public String getD_height() {
        return d_height;
    }

    public void setD_height(String d_height) {
        this.d_height = d_height;
    }

    public String getD_weight() {
        return d_weight;
    }

    public void setD_weight(String d_weight) {
        this.d_weight = d_weight;
    }

    public String getD_gender() {
        return d_gender;
    }

    public void setD_gender(String d_gender) {
        this.d_gender = d_gender;
    }



    ///////////////.....donor current coordinates
    public void donor_get_current_cord_(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("donor_logged_cord", Context.MODE_PRIVATE);
        d_current_latitude = sharedPreferences.getString("c_longitude","");
        d_current_longitude = sharedPreferences.getString("c_latiitude","");
    }

    public void donor_save_current_cord_(Context context,String lat,String lon) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("donor_logged_cord", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("c_latiitude",lat);
        editor.putString("c_longitude",lon);
        editor.apply();
    }





///////////////.....donor
    public void donor_getlastuser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("donor_logged_in_user", Context.MODE_PRIVATE);
        d_id = sharedPreferences.getString("id","");
        d_name = sharedPreferences.getString("name","");
        d_sirname = sharedPreferences.getString("sirname","");
        d_dob = sharedPreferences.getString("dob","");
        d_usertype = sharedPreferences.getString("usertype","");
        d_receive_notes = sharedPreferences.getString("receive_notes","");
        d_verified = sharedPreferences.getString("verified","");
        d_bloodgroup = sharedPreferences.getString("bloodgroup","");
        d_phone = sharedPreferences.getString("phonenumber","");
        d_gender = sharedPreferences.getString("gender","");
        d_height = sharedPreferences.getString("height","");
        d_weight = sharedPreferences.getString("weight","");
        d_email = sharedPreferences.getString("email","");

    }

    public void donor_save_user(Context context,String id,String name,String sirname,String bloodgroup,String phone,String dob
            ,String usertype,String Receive_notes, String verified,String gender,String height,String weight,String email) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("donor_logged_in_user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id",id);
        editor.putString("name",name);
        editor.putString("sirname",sirname);
        editor.putString("dob",dob);
        editor.putString("usertype",usertype);
        editor.putString("receive_notes",Receive_notes);
        editor.putString("verified",verified);
        editor.putString("bloodgroup",bloodgroup);
        editor.putString("phonenumber",phone);
        editor.putString("gender",gender);
        editor.putString("height",height);
        editor.putString("weight",weight);
        editor.putString("email",email);
        editor.apply();
    }
    public void donor_change_receive_notes(Context context,String Receive_notes) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("donor_logged_in_user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("receive_notes",Receive_notes);
        editor.apply();
    }



    public void donor_save_user_updated(Context context,String name,String sirname,String bloodgroup,String phone,String dob
            ,String gender,String height,String weight,String email) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("donor_logged_in_user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name",name);
        editor.putString("sirname",sirname);
        editor.putString("dob",dob);
        editor.putString("bloodgroup",bloodgroup);
        editor.putString("phonenumber",phone);
        editor.putString("gender",gender);
        editor.putString("height",height);
        editor.putString("weight",weight);
        editor.putString("email",email);
        editor.apply();
    }




    public void donor_clear_data_donor_(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("donor_logged_in_user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("id");
        editor.remove("name");
        editor.remove("sirname");
        editor.remove("dob");
        editor.remove("usertype");
        editor.remove("receive_notes");
        editor.remove("verified");
        editor.remove("bloddgroup");
        editor.remove("phonenumber");
        editor.remove("gender");
        editor.remove("height");
        editor.remove("weight");
        editor.remove("email");
        editor.apply();
        //clear_current_user();
    }


///////////////////....hospital

    public void hospital_getlastuser(Context context) {


        SharedPreferences sharedPreferences = context.getSharedPreferences("hospital_logged_in_user", Context.MODE_PRIVATE);
        h_id = sharedPreferences.getString("id","");
        h_hospital_name = sharedPreferences.getString("name","");
        h_email = sharedPreferences.getString("email","");
        h_district = sharedPreferences.getString("district","");
        h_licence = sharedPreferences.getString("licence","");
        h_latitude = sharedPreferences.getString("latitude","");
        h_longitude = sharedPreferences.getString("longitude","");
        h_usertype = sharedPreferences.getString("usertype","");
        h_verified = sharedPreferences.getString("verified","");
    }

    public void hospital_save_user(Context context,String id,String name,String email,String district,String licence, String latitude, String longitude, String usertype, String verified) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("hospital_logged_in_user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id",id);
        editor.putString("name",name);
        editor.putString("email",email);
        editor.putString("district",district);
        editor.putString("licence",licence);
        editor.putString("latitude",latitude);
        editor.putString("longitude",longitude);
        editor.putString("usertype",usertype);
        editor.putString("verified",verified);
        editor.apply();
    }

    public void hospital_clear_data_(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("hospital_logged_in_user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove("id");
        editor.remove("name");
        editor.remove("email");
        editor.remove("district");
        editor.remove("licence");
        editor.remove("latitude");
        editor.remove("longitude");
        editor.remove("usertype");
        editor.remove("verified");
        editor.apply();
        //clear_current_user();
    }



    public  void clear_current_user(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("current_active_user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public  void  set_current_user(Context context,String usertype )
    {

        SharedPreferences sharedPreferences = context.getSharedPreferences("current_active_user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("usertype",usertype);
        editor.apply();

    }

    public String get_current_user(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("current_active_user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("usertype","");
    }

    public String get_activation(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("hospital_logged_in_user", Context.MODE_PRIVATE);
        return sharedPreferences.getString("verified","");
    }

    public void set_activation(Context context,String verified) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("hospital_logged_in_user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("verified",verified);
        editor.apply();
    }

    //.........................donor
    public String getD_id() {
        return d_id;
    }

    public void setD_id(String d_id) {
        this.d_id = d_id;
    }
    public String getD_name() {
        return d_name;
    }

    public String getD_sirname() {
        return d_sirname;
    }

    public String getD_dob() {
        return d_dob;
    }

    public String getD_Usertype() {
        return d_usertype;
    }

    public String getD_receive_notes() {
        return d_receive_notes;
    }

    public String getD_verified() {
        return d_verified;
    }

    public String getD_bloodgroup() {
        return d_bloodgroup;
    }

    public void setD_bloodgroup(String d_bloodgroup) {
        this.d_bloodgroup = d_bloodgroup;
    }


    public String getD_phone() {
        return d_phone;
    }

    public void setD_phone(String d_phone) {
        this.d_phone = d_phone;
    }


    //.........................hospital
    public String getH_id() {
        return h_id;
    }

    public String getH_hospital_name() {
        return h_hospital_name;
    }

    public String getH_email() {
        return h_email;
    }

    public String getH_district() {
        return h_district;
    }

    public String getH_licence() {
        return h_licence;
    }

    public String getH_latitude() {
        return h_latitude;
    }

    public String getH_longitude() {
        return h_longitude;
    }

    public String getH_usertype() {
        return h_usertype;
    }

    public String getH_verified() {
        return h_verified;
    }

}