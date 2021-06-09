package com.example.btlandroid.Fragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.btlandroid.R;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

public class Alarm extends Fragment {

    private TimePicker time;
    private TextView tv;
    private Button button;
    final String CHANNEL_ID = "101";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        time = view.findViewById(R.id.timeAlarm);
        time.setIs24HourView(true);
        tv = view.findViewById(R.id.tvalarm);
        button = view.findViewById(R.id.buttonAlarm);
        createNotificationChannel();

        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                tv.setText("Báo thức: "+time.getHour()+":"+time.getMinute());

                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        while (true) {
                            Calendar c = Calendar.getInstance();
                            int hour, minute, second;
                            hour = c.get(Calendar.HOUR_OF_DAY);
                            minute = c.get(Calendar.MINUTE);
                            second = c.get(Calendar.SECOND);
                            if (hour == time.getHour() && minute == time.getMinute() &&
                                    second >= 0) {
                                NotificationCompat.Builder builder =
                                        new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
                                                .setSmallIcon(R.drawable.ic_launcher_background)
                                                .setContentTitle("ALARM")
                                                .setContentText("TIME'S UP")
                                                .setColor(Color.RED)
                                                .setPriority(NotificationCompat.PRIORITY_HIGH)
                                                .setAutoCancel(true);
                                NotificationManagerCompat notificationManagerCompat =
                                        NotificationManagerCompat.from(getActivity());
                                notificationManagerCompat.notify(1, builder.build());
                                break;
                            }
                        }
                    }
                }).start();

            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("This is notification channel");
            NotificationManager manager = getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}