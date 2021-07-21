package com.example.malawibloodtransfusion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

public class viewSpecificuser extends AppCompatActivity {

    private TextView tv_name;
    private TextView tv_email;
    private TextView tv_district;

    private TextView tv_aplus,tv_aminus,tv_bplus,tv_bminus,tv_oplus,tv_ominus,tv_abplus,tv_abminus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_specificuser);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        String name = getIntent().getStringExtra("name");
        String email = getIntent().getStringExtra("email");
        String district = getIntent().getStringExtra("district");

        String aplus = getIntent().getStringExtra("aplus");
        String aminus = getIntent().getStringExtra("aminus");
        String bplus = getIntent().getStringExtra("bplus");
        String bminus = getIntent().getStringExtra("bminus");
        String oplus = getIntent().getStringExtra("oplus");
        String ominus = getIntent().getStringExtra("ominus");
        String abplus = getIntent().getStringExtra("abplus");
        String abminus = getIntent().getStringExtra("abminus");





        tv_name = findViewById(R.id.hos_rec_name);
        tv_email = findViewById(R.id.hos_rec_email);
        tv_district = findViewById(R.id.hos_rec_district);

                 tv_aplus = findViewById(R.id.home_hospital_aplus);
                tv_aminus= findViewById(R.id.home_hospital_aminus);
                tv_bplus= findViewById(R.id.home_hospital_bplus);
                tv_bminus= findViewById(R.id.home_hospital_bminus);
                tv_oplus= findViewById(R.id.home_hospital_oplus);
                tv_ominus= findViewById(R.id.home_hospital_ominus);
                tv_abplus= findViewById(R.id.home_hospital_abplus);
                tv_abminus= findViewById(R.id.home_hospital_abminus);

        tv_name.setText(name);
        tv_email.setText(email);
        tv_district.setText(district);

        tv_aplus.setText(aplus);
        tv_aminus.setText(aminus);
        tv_bplus.setText(bplus);
        tv_bminus.setText(bminus);
        tv_oplus.setText(oplus);
        tv_ominus.setText(ominus);
        tv_abplus.setText(abplus);
        tv_abminus.setText(abminus);


    }
}