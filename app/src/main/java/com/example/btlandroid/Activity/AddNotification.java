package com.example.btlandroid.Activity;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.btlandroid.DataFirebase.DAODatabase;
import com.example.btlandroid.MainActivity;
import com.example.btlandroid.Model.Notification;
import com.example.btlandroid.R;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddNotification extends AppCompatActivity {

    private EditText title, description, date;
    private Spinner category;
    private TimePicker time;
    private Button add;
    DAODatabase db;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notification);
        init();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = title.getText().toString();
                String b = category.getSelectedItem().toString();
                String c = time.getHour() + ":" + time.getMinute();
                String d = date.getText().toString();
                String e = description.getText().toString();
                Notification noti = new Notification(a, b, c, d, e);
                db = new DAODatabase();
                db.add(noti);
                Toast.makeText(getApplicationContext(), "Success!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddNotification.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@Nullable @org.jetbrains.annotations.Nullable View parent, @NonNull @NotNull String name, @NonNull @NotNull Context context, @NonNull @NotNull AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    public void init() {
        title = findViewById(R.id.addTitle);
        description = findViewById(R.id.addDes);
        category = findViewById(R.id.addCategory);
        time = findViewById(R.id.addTime);
        date = findViewById(R.id.addDate);
        add = findViewById(R.id.add);
        time.setIs24HourView(true);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(AddNotification.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(year,month,dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        date.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, mYear, mMonth, mDay);
                dialog.show();
            }
        });
    }
}