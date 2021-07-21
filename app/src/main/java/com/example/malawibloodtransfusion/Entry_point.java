 package com.example.malawibloodtransfusion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;



 public class Entry_point extends AppCompatActivity {

    private String activeAccount;


    private final String channel_id = "BIG TEXT NOTIFICATION";
    private final int notification_id = 1;


     private static final int JOB_ID = 1001 ;
     private JobInfo jobInfo;
     private static final String TAG = "Home";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstpage);

        //SharedPreferences sharedPreferences = getSharedPreferences("current_active_user", Context.MODE_PRIVATE);

        //activeAccount = sharedPreferences.getString("usertype","");
        shared_pref_manager pref_manager = new shared_pref_manager();
         activeAccount =  pref_manager.get_current_user(this);




        if(activeAccount.equals("1"))
        {
            //donor
            Intent intent = new Intent(getApplicationContext(), donorCenter.class);
            startActivity(intent);
            finish();
          //  Toast.makeText(this,"donr is active",Toast.LENGTH_SHORT).show();

        }
        else if(activeAccount.equals("2"))
        {


            Intent intent = new Intent(getApplicationContext(), hospital_waiting_room.class);
            startActivity(intent);
            finish();
        }else if(activeAccount.equals("3"))
        {
            Intent intent = new Intent(getApplicationContext(), hospital_waiting_room.class);
            startActivity(intent);
            finish();
        }





      /*
        // Create the Handler object (on the main thread by default)
        Handler handler = new Handler();
       // Define the code block to be executed
         Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                Log.d("Handlers", "Called on main thread");
                Toast.makeText(getApplicationContext(),"...........................",Toast.LENGTH_SHORT).show();
                // Repeat this the same runnable code block again another 2 seconds
                // 'this' is referencing the Runnable object
                handler.postDelayed(this, 10000);
            }
        };
       // Start the initial runnable task by posting through the handler
        handler.post(runnableCode);
        */

    }

    public void goto_donorlogin(View view) {
        Intent intent = new Intent(this, donor_login.class );
        startActivity(intent);
        //finish();
    }

    public void goto_hospital_login(View view) {
        Intent intent = new Intent(this,Hospital_login.class);
        startActivity(intent);
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

    private void add_notification() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),channel_id);
        builder.setSmallIcon(R.drawable.smallbell);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.bigbell));
        builder.setContentTitle("Hi");
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText("we are here to let you know that there is a donation even at blantyre hospital"));
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(notification_id,builder.build());


    }


}