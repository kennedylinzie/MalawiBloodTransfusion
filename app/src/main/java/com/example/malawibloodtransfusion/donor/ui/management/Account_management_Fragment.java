package com.example.malawibloodtransfusion.donor.ui.management;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.malawibloodtransfusion.R;
import com.example.malawibloodtransfusion.models.Donor;
import com.example.malawibloodtransfusion.shared_pref_manager;

//import com.example.malawibloodtransfusion.donor.R;

public class Account_management_Fragment extends Fragment {



  @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch aSwitch;

    private final Donor don = new Donor();


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.donor_fragment_management, container, false);

        aSwitch = root.findViewById(R.id.switch1);
        shared_pref_manager shad = new shared_pref_manager();
        shad.donor_getlastuser(getContext());

        aSwitch.setChecked(shad.getD_receive_notes().equals("true"));



        aSwitch.setOnClickListener(v -> {


            if(aSwitch.isChecked())
            {
               // Toast.makeText(this.getContext(),"ON",Toast.LENGTH_SHORT).show();
                if(shad.getD_receive_notes().equals("true"))
                {
                    receive_notifications("false");
                }else {
                    receive_notifications("true");
                }


            }else {
                if(shad.getD_receive_notes().equals("true"))
                {
                    receive_notifications("false");
                }else {
                    receive_notifications("true");
                }
            }

        });


        return root;
    }

    public  void receive_notifications(String state){
       don.chenge_receieve_notes(getContext(),state);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(don.getSuccess().equals("1"))
                {
                    shared_pref_manager shad = new shared_pref_manager();
                    shad.donor_getlastuser(getContext());




                }
                else if(don.getSuccess().equals("2"))
                {////wrong username

                    // Toast.makeText(getApplicationContext(),"",Toast.LENGTH_SHORT).show();
                    //  return;
                }
                else if(don.getSuccess().equals("3"))
                {   ///something is wrong let the admin know


                }

            }
        },500);


    }


}