package com.example.malawibloodtransfusion;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.widget.NestedScrollView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.malawibloodtransfusion.models.Donor;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

public class donorCenter extends AppCompatActivity {

   //w private TextView tv_name,tv_sirname,tv_dob,tv_usertype,tv_receive_notes,tv_verified;
    private String name,sirname,dob,usertype,receive_notes,verified;
    FloatingActionButton floatingActionButton;

    Button expand,collapse;
    BottomSheetBehavior bottomSheetBehavior;
    View bottomsheet;
    private final String channel_id = "BIG TEXT NOTIFICATION";
    private final int notification_id = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_center);

        BottomNavigationView navView = findViewById(R.id.nav_view_donor);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.donor_navigation_home, R.id.donor_navigation_location, R.id.donor_navigation_management)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_donor);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);





        floatingActionButton = findViewById(R.id.floatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(),"the fab was clilcked",Toast.LENGTH_SHORT).show();

                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                bottomSheet.show(getSupportFragmentManager(),
                        "ModalBottomSheet");

                Donor don = new Donor();
                don.get_message(getApplicationContext());
                event_Prefs eve = new event_Prefs();
                eve.donor_get_message(getApplicationContext());
              //  Toast.makeText(getApplicationContext(),"....."+eve.getMessage(),Toast.LENGTH_LONG).show();

            }
        });



        // Create the Handler object (on the main thread by default)
        Handler handler = new Handler();
       // Define the code block to be executed
         Runnable runnableCode = new Runnable() {
            @Override
            public void run() {

            Donor  don = new Donor();
            don.get_message(getApplicationContext());

            event_Prefs eve = new event_Prefs();
            eve.donor_get_message(getApplicationContext());

            shared_pref_manager shad = new shared_pref_manager();
            shad.donor_getlastuser(getApplicationContext());


            if(eve.getReceived().equals("true"))
            {
               // Toast.makeText(getApplicationContext(),receieved+"....TRUE..."+message,Toast.LENGTH_LONG).show();
                eve.donor_get_message(getApplicationContext());
                don.set_msg_off(getApplicationContext());

                if(shad.getD_receive_notes().equals("true"))
                {
                    create_notification();
                    add_notification(eve.getMessage());
                }

            }else if(eve.getReceived().equals("false"))
            {
              //  Toast.makeText(getApplicationContext(),receieved+"....FALSE..."+message,Toast.LENGTH_LONG).show();
            }else{
               // Toast.makeText(getApplicationContext(),"....NUH...",Toast.LENGTH_LONG).show();
            }

                eve.donor_get_message(getApplicationContext());
                don.get_message(getApplicationContext());



                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                handler.postDelayed(this, 10000);
            }
        };
       // Start the initial runnable task by posting through the handler
        handler.post(runnableCode);

    }


    private void create_notification() {

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            CharSequence name = "BIG TEXT NOTIFICATION";
            String desc  = "This is all about Big text notification";

            NotificationChannel notificationChannel = new NotificationChannel(channel_id,name, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(desc);

            NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);

        }

    }

    private void add_notification(String message) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),channel_id);
        builder.setSmallIcon(R.drawable.smallbell);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.bigbell));
        builder.setContentTitle("Hi");
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(notification_id,builder.build());


    }






    public void donor_logout(View view) {
        Donor don = new Donor();
        don.Logout(getApplicationContext());
        Intent intent = new Intent(getApplicationContext(), Entry_point.class);
        startActivity(intent);
        finish();
    }

    public void home_donor_delete_acccount(View view) {

///..................................


        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("NOTE!");
        builder.setMessage("Do you want to delete your account as a donor ?? THIS CAN'T BE UNDONE");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"OK WAS CLICKED",Toast.LENGTH_SHORT).show();

                    Donor don = new Donor();
                    shared_pref_manager shad = new shared_pref_manager();
                    shad.donor_getlastuser(getApplicationContext());
                    don.Delete_account(shad.getD_id(),getApplicationContext());


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                       // Toast.makeText(getApplicationContext(),""+,Toast.LENGTH_SHORT).show();
                        if(don.getSuccess().equals("1"))
                        {
                            shad.clear_current_user(getApplicationContext());
                            shad.donor_clear_data_donor_(getApplicationContext());
                            Intent intent = new Intent(getApplicationContext(), Entry_point.class);
                            startActivity(intent);
                            finish();
                        }else if(don.getSuccess().equals("2"))
                        {
                            shad.clear_current_user(getApplicationContext());
                            shad.donor_clear_data_donor_(getApplicationContext());
                            Intent intent = new Intent(getApplicationContext(), Entry_point.class);
                            startActivity(intent);
                            finish();
                        }else if(don.getSuccess().equals("3"))
                        {
                            Toast.makeText(getApplicationContext(),"We cant reach our servers contact the admin",Toast.LENGTH_SHORT).show();
                        }

                    }
                },1000);



            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Toast.makeText(getApplicationContext(),"Cancel WAS CLICKED",Toast.LENGTH_SHORT).show();


            }
        });
        builder.create().show();

    }

    public void goto_donor_update(View view) {

        Intent intent = new Intent(getApplicationContext(),Donor_update_Activity.class);
        startActivity(intent);

    }

    public void donorlogout(View view) {

        Donor don = new Donor();
        don.Logout(getApplicationContext());
        Intent intent = new Intent(getApplicationContext(),Entry_point.class);
        startActivity(intent);

    }




    public void home_donor_delete_acccount_(View view) {


        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("NOTE!");
        builder.setMessage("Do you want to delete your account as a donor ?? THIS CAN'T BE UNDONE");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getApplicationContext(),"OK WAS CLICKED",Toast.LENGTH_SHORT).show();

                Donor don = new Donor();
                shared_pref_manager shad = new shared_pref_manager();
                shad.donor_getlastuser(getApplicationContext());
                don.Delete_account(shad.getD_id(),getApplicationContext());


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        // Toast.makeText(getApplicationContext(),""+,Toast.LENGTH_SHORT).show();
                        if(don.getSuccess().equals("1"))
                        {
                            shad.clear_current_user(getApplicationContext());
                            shad.donor_clear_data_donor_(getApplicationContext());
                            Intent intent = new Intent(getApplicationContext(), Entry_point.class);
                            startActivity(intent);
                            finish();
                        }else if(don.getSuccess().equals("2"))
                        {
                            shad.clear_current_user(getApplicationContext());
                            shad.donor_clear_data_donor_(getApplicationContext());
                            Intent intent = new Intent(getApplicationContext(), Entry_point.class);
                            startActivity(intent);
                            finish();
                        }else if(don.getSuccess().equals("3"))
                        {
                            Toast.makeText(getApplicationContext(),"We cant reach our servers contact the admin",Toast.LENGTH_SHORT).show();
                        }

                    }
                },1000);



            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(getApplicationContext(),"Cancel WAS CLICKED",Toast.LENGTH_SHORT).show();


            }
        });
        builder.create().show();



    }

    public void donor_help_page(View view) {

      //  Toast.makeText(getApplicationContext(),"help WAS CLICKED",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), donor_help_page.class);
        startActivity(intent);


    }
}