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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.malawibloodtransfusion.models.Donor;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Donor_update_Activity extends AppCompatActivity {

    private EditText et_name,et_sirname,et_dob,et_height,et_weight,et_bloodgroup,et_phonenumber,et_email;
    private TextView tv_gender,tv_bloodgroup;
    private EditText et_oldpassword,et_newpassword,et_repeat_newpassword;
    private final shared_pref_manager shad = new shared_pref_manager();

    private DatePickerDialog datePickerDialog;
    private DatePickerDialog.OnDateSetListener onDateSetListener;

    private RadioGroup radioGroup_u;
    private RadioButton radioButton1_u,radioButton2_u;
    private String temp_blood_holder;

    private Spinner bloodSpiner_u;
    private final String[] bloodGroup_u = {"NONE","A POSITIVE","A NEGATIVE","B POSITIVE","B NEGATIVE","O POSITIVE","O NEGATIVE","AB POSITIVE","AB NEGATIVE","DONT KNOW YET"};
    private String name_u,sirname_u,dateofbirth_u,gender_u,height_u,weight_u,bloodgroup_u,phone_number_u,email_u,password_u,repassword_u,old_password_u;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_update);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
       getSupportActionBar().hide();



        et_name = findViewById(R.id.donor_et_u_name);
        et_sirname = findViewById(R.id.donor_et_u_sirname);
        et_dob = findViewById(R.id.donor_et_u_dob);
        et_height = findViewById(R.id.donor_et_u_height);
        et_weight = findViewById(R.id.donor_et_u_weight);
       // et_bloodgroup = findViewById(R.id.donor_et_u_bloodgroup);
        et_phonenumber = findViewById(R.id.donor_et_u_phonenumber);
        et_email = findViewById(R.id.donor_et_u_email);

        et_oldpassword = findViewById(R.id.donor_et_u_old_password);
        et_newpassword = findViewById(R.id.donor_et_u_newpassword);
        et_repeat_newpassword = findViewById(R.id.donor_et_u_repeat_newpassword);

        tv_gender = findViewById(R.id.donor_u_show_gender);
        tv_bloodgroup = findViewById(R.id.donor_u_show_current_bloodgroup);

        radioGroup_u = findViewById(R.id.rgroup_u);
        radioButton1_u = findViewById(R.id.rButton1_u);
        radioButton2_u = findViewById(R.id.rButton2_u);
        bloodSpiner_u = findViewById(R.id.sp_reg_bg_spinner_u);

        et_oldpassword = findViewById(R.id.donor_et_u_old_password);
        et_newpassword = findViewById(R.id.donor_et_u_newpassword);
        et_repeat_newpassword = findViewById(R.id.donor_et_u_repeat_newpassword);


        try {
            ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,bloodGroup_u);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bloodSpiner_u.setAdapter(arrayAdapter);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        bloodSpiner_u.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 //Toast.makeText(getApplicationContext(),bloodGroup_u[position],Toast.LENGTH_SHORT).show();
                temp_blood_holder = bloodGroup_u[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        //date dialog onclick
        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month+1;
                et_dob.setText(dayOfMonth+" / "+month+" / "+year);

            }
        };

        radioGroup_u.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            //check box for selecting the gender of the user
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId){
                    case R.id.rButton1_u:
                        //Toast.makeText(getApplicationContext(),"Male",Toast.LENGTH_SHORT).show();

                        tv_gender.setText("male");

                        break;
                    case R.id.rButton2_u:
                         //Toast.makeText(getApplicationContext(),"female",Toast.LENGTH_SHORT).show();

                        tv_gender.setText("female");

                        break;
                    default:
                        // Toast.makeText(getApplicationContext(),"None",Toast.LENGTH_SHORT).show();

                        break;

                }

            }
        });




             get_information();
    }

    public void get_information()
    {
        shad.donor_getlastuser(getApplicationContext());
        connectionsManager conman = new connectionsManager();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, conman.getUpdate_accounts(), new Response.Listener<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String message = jsonObject.getString("message");
                    String status = jsonObject.getString("status");
                    if(status.equals("ok"))
                    {

                        //setId(jsonObject.getString("id"));
                        et_name.setText(jsonObject.getString("name"));
                        et_sirname.setText(jsonObject.getString("sirname"));
                        et_dob.setText(jsonObject.getString("dob"));
                        tv_gender.setText(jsonObject.getString("gender"));
                        et_height.setText(jsonObject.getString("height"));
                        et_weight.setText(jsonObject.getString("weight"));
                        tv_bloodgroup.setText(jsonObject.getString("bloodgroup"));
                        et_phonenumber.setText(jsonObject.getString("phone_number"));
                        et_email.setText(jsonObject.getString("email"));





                    }else {
                        Toast.makeText(getApplicationContext(),message+"",Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(context,"" + error.getMessage(),Toast.LENGTH_SHORT).show();



            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("id",shad.getD_id());
                params.put("type","1");

                return params;
            }
        };
        requestQueue.add(stringRequest);


    }




    public void change_details(View view) {

        Donor don = new Donor();


            if(temp_blood_holder.equals("NONE"))
            {
                bloodgroup_u = tv_bloodgroup.getText().toString().trim();
               // Toast.makeText(getApplicationContext(),""+bloodgroup_u,Toast.LENGTH_SHORT).show();

            }else {
                bloodgroup_u = temp_blood_holder;
                //Toast.makeText(getApplicationContext(),""+bloodgroup_u,Toast.LENGTH_SHORT).show();
            }

          gender_u = tv_gender.getText().toString().trim();
          name_u = et_name.getText().toString().trim();
          sirname_u = et_sirname.getText().toString().trim();
          dateofbirth_u = et_dob.getText().toString().trim();
          height_u = et_height.getText().toString().trim();
          weight_u = et_weight.getText().toString().trim();
          phone_number_u = et_phonenumber.getText().toString().trim();
          email_u = et_email.getText().toString().trim();

       // Toast.makeText(getApplicationContext(),""+name_u+""+sirname_u+""+dateofbirth_u+""+gender_u+""+height_u+""+weight_u+""+bloodgroup_u +""+phone_number_u+""+email_u,Toast.LENGTH_SHORT).show();

        don.setName(name_u);
        don.setSirname(sirname_u);
        don.setDateofbirth(dateofbirth_u);
        don.setGender(gender_u);
        don.setHeight(height_u);
        don.setWeight(weight_u);
        don.setBloodgroup(bloodgroup_u);
        don.setPhone_number(phone_number_u);
        don.setEmail(email_u);
        don.update_donor(getApplicationContext());



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // Toast.makeText(getApplicationContext(),""+,Toast.LENGTH_SHORT).show();
                if(don.getSuccess().equals("1"))
                {
                    gender_u = tv_gender.getText().toString().trim();
                    name_u = et_name.getText().toString().trim();
                    sirname_u = et_sirname.getText().toString().trim();
                    dateofbirth_u = et_dob.getText().toString().trim();
                    height_u = et_height.getText().toString().trim();
                    weight_u = et_weight.getText().toString().trim();
                    phone_number_u = et_phonenumber.getText().toString().trim();
                    email_u = et_email.getText().toString().trim();

                    Toast.makeText(getApplicationContext(),"successful",Toast.LENGTH_SHORT).show();
                    shared_pref_manager shad = new shared_pref_manager();
                    shad.donor_save_user_updated(getApplicationContext(),name_u,sirname_u,bloodgroup_u,phone_number_u,dateofbirth_u,gender_u,height_u,weight_u,email_u);

                    Intent intent = new Intent(getApplicationContext(), donorCenter.class);
                    startActivity(intent);
                    finish();

                }else if(don.getSuccess().equals("2"))
                {
                    Toast.makeText(getApplicationContext(),"The process failed please try again",Toast.LENGTH_SHORT).show();
                }else if(don.getSuccess().equals("3"))
                {
                    Toast.makeText(getApplicationContext(),"We cant reach our servers contact the admin",Toast.LENGTH_SHORT).show();
                }

            }
        },1000);

    }

    public void change_donor_password(View view) {

        password_u = et_newpassword.getText().toString().trim();
        repassword_u = et_repeat_newpassword.getText().toString().trim();
        old_password_u = et_oldpassword.getText().toString().trim();

        if(password_u.isEmpty() || repassword_u.isEmpty() || old_password_u.isEmpty())
        {
            Toast.makeText(getApplicationContext(),"No filed should be empty",Toast.LENGTH_SHORT).show();
            return;
        }

        if(!password_u.equals(repassword_u))
        {
            Toast.makeText(getApplicationContext(),"Password don't match",Toast.LENGTH_SHORT).show();
            return;
        }
        Donor don = new Donor();
        don.change_password(getApplicationContext(),old_password_u,password_u);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // Toast.makeText(getApplicationContext(),""+,Toast.LENGTH_SHORT).show();
                if(don.getSuccess().equals("1"))
                {

                    Toast.makeText(getApplicationContext(),"successful",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), donorCenter.class);
                    startActivity(intent);
                    finish();



                }else if(don.getSuccess().equals("2"))
                {
                    Toast.makeText(getApplicationContext(),"wrong password",Toast.LENGTH_SHORT).show();
                }else if(don.getSuccess().equals("3"))
                {
                    Toast.makeText(getApplicationContext(),"We cant reach our servers contact the admin",Toast.LENGTH_SHORT).show();
                }

            }
        },1000);







    }

    public void showdate_for_update(View view) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(1990,1,1);
        int year =calendar.get(Calendar.YEAR);
        int month =calendar.get(Calendar.MONTH);
        int day =calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(Donor_update_Activity.this,onDateSetListener,year,month,day);
        try {
            datePickerDialog.show();
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }



}