package com.example.btlandroid.DataFirebase;

import com.example.btlandroid.Model.Notification;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class DAODatabase {

    public DatabaseReference databaseReference;

    public DAODatabase() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference().child("Notification");
    }

    public Task<Void> add(Notification notification){
        return databaseReference.push().setValue(notification);
    }

    public Task<Void> update(String key, Notification noti){
        return databaseReference.child(key).setValue(noti);
    }

    public Task<Void> delete(String key){
        return databaseReference.child(key).removeValue();
    }
}
