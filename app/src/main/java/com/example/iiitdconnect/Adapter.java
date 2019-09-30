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
<<<<<<< Updated upstream
=======
        notificationViewHolder.textView_title.setText("This is title");
>>>>>>> Stashed changes
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
<<<<<<< Updated upstream
=======
        TextView textView_title;
>>>>>>> Stashed changes
        public NotificationViewHolder(View itemview)
        {
            super(itemview);
            textView=(TextView) itemview.findViewById(R.id.text_notification);
<<<<<<< Updated upstream
=======
            textView_title=(TextView) itemview.findViewById(R.id.notification_title);
>>>>>>> Stashed changes
        }
    }
}
