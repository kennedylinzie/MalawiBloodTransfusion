package com.example.malawibloodtransfusion.models;

import android.content.Context;

public class User {

    private String  id;
    private String  name;
    private String  email;
    private String  phone_number;
    private String  usertype;
    private String  verified;
    private String  password;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void Delete_account(String id, Context context)
    {

    }
    public void Login(Context context,String email,String password)
    {

    }
    public void Logout(Context context)
    {

    }




}
