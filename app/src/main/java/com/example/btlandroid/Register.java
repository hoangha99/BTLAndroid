package com.example.btlandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.btlandroid.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText fullName, email, pass;
    private RadioButton gender;
    private DatePicker dateOfBirth;
    private Button register;

    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        firebaseAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sex = "";
                if (gender.isChecked()) {
                    sex = "Male";
                } else sex = "Female";
                String date = dateOfBirth.getDayOfMonth() + "/" + dateOfBirth.getMonth() + "/" + dateOfBirth.getYear();

                if (!isValid(email.getText().toString())) {
                    Toast.makeText(Register.this, "A valid email is required !", Toast.LENGTH_LONG).show();
                    return;
                }
                if (fullName.getText().toString().isEmpty()) {
                    Toast.makeText(Register.this, "Full name is not blank !", Toast.LENGTH_LONG).show();
                    return;
                }
                if (pass.getText().toString().length() < 6) {
                    Toast.makeText(Register.this, "Password is more than 6 !", Toast.LENGTH_LONG).show();
                    return;
                }
                if (date.isEmpty()) {
                    Toast.makeText(Register.this, "Full name is not blank !", Toast.LENGTH_LONG).show();
                    return;
                }

                User nUser = new User(fullName.getText().toString(), date, email.getText().toString(), sex);

                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                        .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    FirebaseDatabase.getInstance().getReference("User")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(nUser)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(Register.this, "Success", Toast.LENGTH_LONG).show();
                                                    finish();
                                                }
                                            });
                                } else {
                                    Toast.makeText(Register.this, email.getText().toString()+" already is exist", Toast.LENGTH_LONG).show();
                                }
                            }
                        });

            }
        });


    }

    private void init() {
        fullName = findViewById(R.id.txtFullname);
        dateOfBirth = findViewById(R.id.datePicker);
        email = findViewById(R.id.txtEmail);
        gender = findViewById(R.id.gender);
        pass = findViewById(R.id.txtPass);
        register = findViewById(R.id.doRegister);

        dateOfBirth.init(1999, 1, 21, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            }
        });
    }

    public boolean isValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}