package com.example.malawibloodtransfusion;

import android.content.Context;

public class user_ {




    private  String name;
    private  String email;
    private  String district;

    private  String a_plus;
    private  String a_minus;
    private  String b_plus;
    private  String b_minus;
    private  String o_plus;
    private  String o_minus;
    private  String ab_plus;
    private  String ab_minus;
    private  float distance;


    user_(String name , String email ,String district) {
      this.name = name;
      this.district = district;
      this.email = email;


    }

    user_(String name , String email ,String district,float dist) {
        this.name = name;
        this.district = district;
        this.email = email;
        this.distance = dist;

    }

    public user_(String name, String email, String district, String a_plus, String a_minus, String b_plus, String b_minus, String o_plus, String o_minus, String ab_plus, String ab_minus,float dist) {
        this.name = name;
        this.email = email;
        this.district = district;
        this.a_plus = a_plus;
        this.a_minus = a_minus;
        this.b_plus = b_plus;
        this.b_minus = b_minus;
        this.o_plus = o_plus;
        this.o_minus = o_minus;
        this.ab_plus = ab_plus;
        this.ab_minus = ab_minus;
        this.distance = dist;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getA_plus() {
        return a_plus;
    }

    public void setA_plus(String a_plus) {
        this.a_plus = a_plus;
    }

    public String getA_minus() {
        return a_minus;
    }

    public void setA_minus(String a_minus) {
        this.a_minus = a_minus;
    }

    public String getB_plus() {
        return b_plus;
    }

    public void setB_plus(String b_plus) {
        this.b_plus = b_plus;
    }

    public String getB_minus() {
        return b_minus;
    }

    public void setB_minus(String b_minus) {
        this.b_minus = b_minus;
    }

    public String getO_plus() {
        return o_plus;
    }

    public void setO_plus(String o_plus) {
        this.o_plus = o_plus;
    }

    public String getO_minus() {
        return o_minus;
    }

    public void setO_minus(String o_minus) {
        this.o_minus = o_minus;
    }

    public String getAb_plus() {
        return ab_plus;
    }

    public void setAb_plus(String ab_plus) {
        this.ab_plus = ab_plus;
    }

    public String getAb_minus() {
        return ab_minus;
    }

    public void setAb_minus(String ab_minus) {
        this.ab_minus = ab_minus;
    }
}
