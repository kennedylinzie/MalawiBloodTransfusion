package com.example.malawibloodtransfusion;

public class connectionsManager {


    private final String locallink = "http://10.0.2.2";
    private final String wifilink = "http://192.168.137.1";


    private final String donor_login =                     locallink +"/mbts/v1/login.php";
    private final String donor_register =                  locallink +"/mbts/v1/register.php";
    private final String hospital_login =                  locallink +"/mbts/v1/login.php";
    private final String hospital_register =               locallink +"/mbts/v1/register.php";
    private final String delete_account =                  locallink +"/mbts/v1/delete_account.php";
    private final String update_accounts =                 locallink +"/mbts/v1/update_accounts.php";
    private final String location_data_pulls =             locallink +"/mbts/v1/location_data_pulls.php";
    private final String donor_notif_link =                locallink +"/mbts/v1/get_messages.php";
    private final String hospital_check_if_verified_link = locallink +"/mbts/v1/check_hospital_verification.php";

    public String getHospital_check_if_verified_link() {
        return hospital_check_if_verified_link;
    }

    public String getUpdate_accounts() {
        return update_accounts;
    }

    public String getDonor_login() {
        return donor_login;
    }

    public String getDonor_register() {
        return donor_register;
    }

    public String getHospital_login() {
        return hospital_login;
    }

    public String getHospital_register() {
        return hospital_register;
    }
    public String getDelete_account() {
        return delete_account;
    }

    public String getLocation_data_pulls() {
        return location_data_pulls;
    }

    public String getDonor_notif_link() {
        return donor_notif_link;
    }
}
