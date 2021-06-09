package com.example.btlandroid.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.btlandroid.DataFirebase.DAODatabase;
import com.example.btlandroid.MainActivity;
import com.example.btlandroid.Model.Notification;
import com.example.btlandroid.NotiAdapter;
import com.example.btlandroid.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Item extends AppCompatActivity {

    private EditText title, description, date;
    private Spinner category;
    private TimePicker time;
    private Button save, delte;
    DAODatabase db;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        init();
        Intent intent = getIntent();
        Notification noti = (Notification) intent.getSerializableExtra("item");
        String key = intent.getStringExtra("key");
        title.setText(noti.getTitle());
        description.setText(noti.getDescription());
        date.setText(noti.getDate());
        category.setSelection(getIndex(category, noti.getCategory()));
        String tmtime = noti.getTime();
        String[] tmp = tmtime.split(":");
        time.setHour(Integer.parseInt(tmp[0]));
        time.setMinute(Integer.parseInt(tmp[1]));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = time.getHour() + ":" + time.getMinute();
                Notification updateNoti = new Notification(title.getText().toString(), category.getSelectedItem().toString(), t, date.getText().toString(), description.getText().toString());
                db.update(key, updateNoti)
                        .addOnSuccessListener(suc -> {
                            Toast.makeText(Item.this, "Save Success!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Item.this, MainActivity.class);
                            startActivity(intent);
                        })
                        .addOnFailureListener(er -> {
                            Toast.makeText(Item.this, "Save Fail!", Toast.LENGTH_SHORT).show();
                        });
            }
        });

        delte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete(key).addOnSuccessListener(suc ->{
                    Toast.makeText(Item.this, "Delete Success!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Item.this, MainActivity.class);
                    startActivity(intent);
                });
            }
        });

    }

    public void init() {
        title = findViewById(R.id.edTitle);
        description = findViewById(R.id.edDes);
        category = findViewById(R.id.edCategory);
        time = findViewById(R.id.edTime);
        date = findViewById(R.id.edDate);
        save = findViewById(R.id.save);
        delte = findViewById(R.id.delte);
        time.setIs24HourView(true);
        db = new DAODatabase();
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(Item.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        date.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, mYear, mMonth, mDay);
                dialog.show();
            }
        });
    }

    private int getIndex(Spinner spinner, String s) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equals(s)) {
                return i;
            }
        }
        return 0;
    }
}