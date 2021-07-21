package com.example.malawibloodtransfusion;

public class List_item {

    private int id;
    private String hos_id;
    private String hospital_name;
    private String email;
    private String district;
    private String licence;
    private String latitude;
    private String longitude;
    private String A_POSITIVE ;
    private String A_NEGATIVE ;
    private String B_POSITIVE ;
    private String B_NEGATIVE ;
    private String O_POSITIVE ;
    private String O_NEGATIVE ;
    private String AB_POSITIVE ;
    private String AB_NEGATIVE ;


    public List_item(String hospital_name, String email, String district, String licence, String latitude, String longitude, int id, String hos_id) {
        this.hospital_name = hospital_name;
        this.email = email;
        this.district = district;
        this.licence = licence;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.hos_id = hos_id;
    }


    public List_item(String hospital_name, String email, String district, String licence, String latitude, String longitude, String a_POSITIVE, String a_NEGATIVE, String b_POSITIVE, String b_NEGATIVE, String o_POSITIVE, String o_NEGATIVE, String AB_POSITIVE, String AB_NEGATIVE, int id, String hos_id) {
        this.hospital_name = hospital_name;
        this.email = email;
        this.district = district;
        this.licence = licence;
        this.latitude = latitude;
        this.longitude = longitude;
        A_POSITIVE = a_POSITIVE;
        A_NEGATIVE = a_NEGATIVE;
        B_POSITIVE = b_POSITIVE;
        B_NEGATIVE = b_NEGATIVE;
        O_POSITIVE = o_POSITIVE;
        O_NEGATIVE = o_NEGATIVE;
        this.AB_POSITIVE = AB_POSITIVE;
        this.AB_NEGATIVE = AB_NEGATIVE;
        this.id = id;
        this.hos_id = hos_id;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHos_id() {
        return hos_id;
    }

    public void setHos_id(String hos_id) {
        this.hos_id = hos_id;
    }

    public String getHospital_name() {
        return hospital_name;
    }

    public void setHospital_name(String hospital_name) {
        this.hospital_name = hospital_name;
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

    public String getA_POSITIVE() {
        return A_POSITIVE;
    }

    public void setA_POSITIVE(String a_POSITIVE) {
        A_POSITIVE = a_POSITIVE;
    }

    public String getA_NEGATIVE() {
        return A_NEGATIVE;
    }

    public void setA_NEGATIVE(String a_NEGATIVE) {
        A_NEGATIVE = a_NEGATIVE;
    }

    public String getB_POSITIVE() {
        return B_POSITIVE;
    }

    public void setB_POSITIVE(String b_POSITIVE) {
        B_POSITIVE = b_POSITIVE;
    }

    public String getB_NEGATIVE() {
        return B_NEGATIVE;
    }

    public void setB_NEGATIVE(String b_NEGATIVE) {
        B_NEGATIVE = b_NEGATIVE;
    }

    public String getO_POSITIVE() {
        return O_POSITIVE;
    }

    public void setO_POSITIVE(String o_POSITIVE) {
        O_POSITIVE = o_POSITIVE;
    }

    public String getO_NEGATIVE() {
        return O_NEGATIVE;
    }

    public void setO_NEGATIVE(String o_NEGATIVE) {
        O_NEGATIVE = o_NEGATIVE;
    }

    public String getAB_POSITIVE() {
        return AB_POSITIVE;
    }

    public void setAB_POSITIVE(String AB_POSITIVE) {
        this.AB_POSITIVE = AB_POSITIVE;
    }

    public String getAB_NEGATIVE() {
        return AB_NEGATIVE;
    }

    public void setAB_NEGATIVE(String AB_NEGATIVE) {
        this.AB_NEGATIVE = AB_NEGATIVE;
    }





}
