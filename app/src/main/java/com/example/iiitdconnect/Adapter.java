package com.example.project;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class Adapter extends RecyclerView.Adapter<Adapter.NotificationViewHolder> {

    private String[] data;
    public Adapter(String[] data)
    {
        this.data=data;
    }
    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.view_list,viewGroup,false);

        return new NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder notificationViewHolder, int i) {
        String notification=data[i];
        notificationViewHolder.textView.setText(notification);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public NotificationViewHolder(View itemview)
        {
            super(itemview);
            textView=(TextView) itemview.findViewById(R.id.text_notification);
        }
    }
}
