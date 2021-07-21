package com.example.malawibloodtransfusion.donor.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.malawibloodtransfusion.R;
import com.example.malawibloodtransfusion.shared_pref_manager;
//import com.example.malawibloodtransfusion.donor.R;

public class donor_HomeFragment extends Fragment {

   private  shared_pref_manager  pref;
   private Context context;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.donor_fragment_home, container, false);
       // final TextView id = root.findViewById(R.id.test_id);
        final TextView name = root.findViewById(R.id.home_donor_name);
        final TextView bloodgroup = root.findViewById(R.id.home_donor_bloodgroup);
        final TextView phone = root.findViewById(R.id.home_donor_phone_number);
        final TextView email = root.findViewById(R.id.home_donor_email);
        final TextView gender = root.findViewById(R.id.home_donor_gender);
        final TextView dob = root.findViewById(R.id.home_donor_dob);
        final TextView weight = root.findViewById(R.id.home_donor_weight);
        final TextView height = root.findViewById(R.id.home_donor_height);

        pref = new shared_pref_manager();

        pref.donor_getlastuser(this.getContext());

        name.setText(pref.getD_name().substring(0,1).toUpperCase()+pref.getD_name().substring(1)+" "+pref.getD_sirname().substring(0,1).toUpperCase()+pref.getD_sirname().substring(1));
        bloodgroup.setText(pref.getD_bloodgroup().substring(0,1).toUpperCase()+pref.getD_bloodgroup().substring(1));
        phone.setText(pref.getD_phone().substring(0,1).toUpperCase()+pref.getD_phone().substring(1));
        email.setText(pref.getD_email().substring(0,1).toUpperCase()+pref.getD_email().substring(1));
        dob.setText(""+pref.getD_dob().substring(0,1).toUpperCase()+pref.getD_dob().substring(1));
        gender.setText(""+pref.getD_gender().substring(0,1).toUpperCase()+pref.getD_gender().substring(1));
        weight.setText(""+pref.getD_weight().substring(0,1).toUpperCase()+pref.getD_weight().substring(1));
        height.setText(""+pref.getD_height().substring(0,1).toUpperCase()+pref.getD_height().substring(1));


        return root;




    }


   
}