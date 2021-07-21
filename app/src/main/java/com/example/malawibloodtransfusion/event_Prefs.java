package com.example.malawibloodtransfusion;

import android.content.Context;
import android.content.SharedPreferences;

public class event_Prefs {


    private String received;
    private String message;


    public void donor_get_message(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("donor_message", Context.MODE_PRIVATE);
        received = sharedPreferences.getString("received","");
        message = sharedPreferences.getString("message","");


    }

    public void donor_save_message(Context context,String message,String received) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("donor_message", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("received",received);
        editor.putString("message",message);
        editor.apply();
    }

    public  void clear_current_messages(Context context)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("donor_message", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }




}
