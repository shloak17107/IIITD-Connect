package com.example.iiitdconnect;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private ArrayList<Post> dataSet;
    public String decription;

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView title;
        private TextView createdBy;
        private TextView timestamp;
        private TextView venue;
        private TextView interestedCount;
        private TextView description;
        public Post post;
        public Context context;
        public LinearLayout main;

        public MyViewHolder(final View itemView) {
            super(itemView);
            this.context = itemView.getContext();
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.createdBy = (TextView) itemView.findViewById(R.id.created_by);
            this.timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            this.venue = (TextView) itemView.findViewById(R.id.venue);
            this.interestedCount = (TextView) itemView.findViewById(R.id.interested_people);
            main = (LinearLayout) itemView.findViewById(R.id.main);

            Log.d("check", "abc");

        }
    }

    public CustomAdapter(ArrayList<Post> data) {
        this.dataSet = data;
        Log.d("check", "check");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView title = holder.title;
        TextView venue = holder.venue;
        TextView interestedCount = holder.interestedCount;
        TextView createdBy = holder.createdBy;
        TextView timestamp = holder.timestamp;
        holder.post = dataSet.get(listPosition);
        title.setText(dataSet.get(listPosition).getTitle());
        venue.setText(dataSet.get(listPosition).getVenue());
        int count = 0;
        if (dataSet.get(listPosition).getInterestedpeople().getInterested_ids() != null)
            count = dataSet.get(listPosition).getInterestedpeople().getInterested_ids().size();
        interestedCount.setText(Integer.toString(count - 1) + " people are interested");
        createdBy.setText(dataSet.get(listPosition).getCreatedBy());
        timestamp.setText(dataSet.get(listPosition).getDate() + dataSet.get(listPosition).getTime());
        final Post pp = holder.post;

        holder.main.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.d("LOL", pp.getTitle());
                Intent intent = new Intent(holder.context, showPostActivity.class);
                intent.putExtra("createdBy", pp.getCreatedBy());
                intent.putExtra("title", pp.getTitle());
                intent.putExtra("body", pp.getBody());
                intent.putExtra("timestamp", pp.getTimestamp());
                intent.putExtra("time", pp.getTime());
                intent.putExtra("date", pp.getDate());
                intent.putExtra("venue", pp.getVenue());
                intent.putExtra("interested", (Serializable) pp.getInterestedpeople().getInterested_ids());
                intent.putExtra("cat", (Serializable) pp.getCategory().getCategories());

                holder.context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}