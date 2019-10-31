package com.example.iiitdconnect;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Post> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private TextView createdBy;
        private TextView timestamp;
        private TextView venue;
        private TextView interestedCount;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.createdBy = (TextView) itemView.findViewById(R.id.created_by);
            this.timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            this.venue = (TextView) itemView.findViewById(R.id.venue);
            this.interestedCount = (TextView) itemView.findViewById(R.id.interested_people);
        }
    }

    public CustomAdapter(ArrayList<Post> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards_layout, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView title = holder.title;
        TextView venue = holder.venue;
        TextView interestedCount = holder.interestedCount;
        TextView createdBy = holder.createdBy;
        TextView timestamp = holder.timestamp;


        title.setText(dataSet.get(listPosition).getTitle());
        venue.setText(dataSet.get(listPosition).getVenue());
        int count = 0;
        if (dataSet.get(listPosition).getInterestedpeople().getInterested_ids() != null)
            count = dataSet.get(listPosition).getInterestedpeople().getInterested_ids().size();
        interestedCount.setText(Integer.toString(count - 1) + " people are interested");
        createdBy.setText(dataSet.get(listPosition).getCreatedBy());
        timestamp.setText(dataSet.get(listPosition).getDate() + dataSet.get(listPosition).getTime());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
