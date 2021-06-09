package com.example.btlandroid;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.btlandroid.Activity.Item;
import com.example.btlandroid.Model.Notification;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.io.Serializable;

public class NotiAdapter extends FirebaseRecyclerAdapter<Notification, NotiAdapter.ViewHolder> {

    public NotiAdapter(@NonNull FirebaseRecyclerOptions<Notification> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull NotiAdapter.ViewHolder holder, int position, @NonNull Notification noti) {
        holder.title.setText(noti.getTitle());
        holder.category.setText(noti.getCategory());
        holder.time.setText(noti.getTime());
        holder.date.setText(noti.getDate());
        holder.description.setText(noti.getDescription());
        String key = getRef(position).getKey();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Item.class);
                intent.putExtra("item", noti);
                intent.putExtra("key", key);
                v.getContext().startActivity(intent);
            }
        });
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_row, parent,false);
        return new ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, category, date,time, description;
        public ViewHolder(@NonNull View v) {
            super(v);
            title=v.findViewById(R.id.tv1);
            category=v.findViewById(R.id.tv2);
            time=v.findViewById(R.id.tv3);
            date=v.findViewById(R.id.tv4);
            description=v.findViewById(R.id.tv5);
        }
    }
}
