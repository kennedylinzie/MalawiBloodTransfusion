package com.example.malawibloodtransfusion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.malawibloodtransfusion.models.Donor;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class donor_register extends AppCompatActivity {

   private EditText dob,etname,et_sirname,et_height,et_weight,et_dinumber,et_phonenumber,et_email, et_password,et_repassword;
   private String name,sirname,dateofbirth,gender,height,weight,bloodgroup,phone_number,email,password,repassword,temp_gender_holder,temp_blood_holder;

    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    private RadioGroup radioGroup;
    private RadioButton radioButton1,radioButton2;

   private Spinner bloodSpiner;
   private final String[] bloodGroup = {"A POSITIVE","A NEGATIVE","B POSITIVE","B NEGATIVE","O POSITIVE","O NEGATIVE","AB POSITIVE","AB NEGATIVE","DONT KNOW YET"};

    private final connectionsManager conman = new connectionsManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_register);

        dob = findViewById(R.id.donor_et_reg_dob);
        etname = findViewById(R.id.donor_et_reg_name);
        et_sirname = findViewById(R.id.et_donor_reg_sirname);
        et_height = findViewById(R.id.donor_et_reg_height);
        et_weight = findViewById(R.id.donor_et_reg_weight);
        et_phonenumber = findViewById(R.id.donor_et_reg_phonenumber);
        et_email = findViewById(R.id.donor_et_reg_email);
        et_password = findViewById(R.id.donor_et_reg_password);
        et_repassword = findViewById(R.id.donor_et_reg_repassword);




        radioGroup = findViewById(R.id.rgroup);
        radioButton1 = findViewById(R.id.rButton1);
        radioButton2 = findViewById(R.id.rButton2);

        bloodSpiner = findViewById(R.id.sp_reg_bg_spinner);


        try {
            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,bloodGroup);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bloodSpiner.setAdapter(arrayAdapter);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        bloodSpiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getApplicationContext(),bloodGroup[position],Toast.LENGTH_SHORT).show();
               // temp_blood_holder = bloodgroup[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //check box for selecting the gender of the user
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.rButton1:
                        //Toast.makeText(getApplicationContext(),"Male",Toast.LENGTH_SHORT).show();

                        temp_gender_holder = "male";

                        break;
                    case R.id.rButton2:
                       // Toast.makeText(getApplicationContext(),"female",Toast.LENGTH_SHORT).show();

                        temp_gender_holder ="female";

                        break;
                    default:
                       // Toast.makeText(getApplicationContext(),"None",Toast.LENGTH_SHORT).show();

                        break;

                }

            }
        });


       //date dialog onclick
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month+1;
                dob.setText(dayOfMonth+" / "+month+" / "+year);

            }
        };

    }
   //an onclick that sets sets the date of birth
    public void showdate(View view) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(1990,1,1);
        int year =calendar.get(Calendar.YEAR);
        int month =calendar.get(Calendar.MONTH);
        int day =calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(donor_register.this,onDateSetListener,year,month,day);
        try {
            datePickerDialog.show();
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    public void goto_reg_login(View view) {

        Intent intent = new Intent(this,donor_login.class);
        startActivity(intent);
        finish();

    }

    public void donor_Register(View view) {
                Donor don = new Donor();

                 name = etname.getText().toString().trim();
                 sirname = et_sirname.getText().toString().trim();
                 dateofbirth = dob.getText().toString().trim();
                gender = temp_gender_holder;
                height = et_height.getText().toString().trim();
                weight = et_weight.getText().toString().trim();
                bloodgroup = bloodSpiner.getSelectedItem().toString();
                phone_number = et_phonenumber.getText().toString().trim();
                email = et_email.getText().toString().trim();
                password = et_password.getText().toString().trim();
                repassword = et_repassword.getText().toString().trim();

                if(name.isEmpty()||sirname.isEmpty()||dateofbirth.isEmpty()||gender.isEmpty()||height.isEmpty()||weight.isEmpty()||bloodgroup.isEmpty()||phone_number.isEmpty()
                        ||email.isEmpty()||password.isEmpty()||repassword.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Something is empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(repassword))
                {
                    Toast.makeText(getApplicationContext(),"Passowrds don't Match",Toast.LENGTH_SHORT).show();
                    return;
                }
                don.setName(name);
                don.setSirname(sirname);
                don.setDateofbirth(dateofbirth);
                don.setGender(gender);
                don.setHeight(height);
                don.setWeight(weight);
                don.setBloodgroup(bloodgroup);
                don.setPhone_number(phone_number);
                don.setEmail(email);
                don.setPassword(password);
                don.donor_Register(getApplicationContext());
               /*  if(don.getSuccess().equals(true))
                 {
                     Toast.makeText(getApplicationContext(),"successsssssssssss",Toast.LENGTH_SHORT).show();
                 }
                 else if(don.getSuccess().equals(false))
                 {
                     Toast.makeText(getApplicationContext(),"something happened chief",Toast.LENGTH_SHORT).show();
                 }
*/    new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(don.getSuccess().equals("1"))
                {
                    Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), donor_login.class);
                    startActivity(intent);
                    finish();

                }
                else if(don.getSuccess().equals("2"))
                {
                    Toast.makeText(getApplicationContext(),"User already exists",Toast.LENGTH_SHORT).show();
                }
                else if(don.getSuccess().equals("3"))
                {
                    Toast.makeText(getApplicationContext(),"Something is wrong contact admin",Toast.LENGTH_SHORT).show();
                }

            }
        },1000);



    }
}