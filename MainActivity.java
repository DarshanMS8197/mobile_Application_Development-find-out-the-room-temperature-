package com.example.temperature;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    EditText bp, temp;
    Button btn;
    int bp_int,temp_int;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bp = findViewById(R.id.bp);
        temp = findViewById(R.id.temp);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                makeNotification();
            }
        });
    }

    private void makeNotification() {
        String chennel_id = "CHANNEL_ID";
        String msg;
        bp_int=Integer.parseInt(bp.getText().toString());
        temp_int=Integer.parseInt(temp.getText().toString());
        if((bp_int>=60&&bp_int<=100)||temp_int==98)
            msg="Your vitals are fine";
        else
            msg="Consult a docter";

        Intent intent=new Intent(getApplicationContext(),
                NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("data",msg);
        PendingIntent pendingIntent= PendingIntent.getActivity(
                getApplication(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );


        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);


        NotificationChannel notificationChannel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(
                    chennel_id, "My Channel", NotificationManager.IMPORTANCE_HIGH);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Notification.Builder builder =
                null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            builder = new Notification.Builder(getApplicationContext(),
                    chennel_id);
        }
        builder.setSmallIcon(R.drawable.ic_notiifoication)
                .setContentTitle("Health Condion")
                .setContentText("bp" + bp.getText().toString() + "temp" + temp.getText().toString())
                .setContentIntent(pendingIntent);

        notificationManager.notify(0, builder.build());

    }
}