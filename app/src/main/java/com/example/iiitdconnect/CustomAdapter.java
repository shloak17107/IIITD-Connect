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
        private TextView body;
        private TextView createdBy;
        private TextView timestamp;
        private TextView tags;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.body = (TextView) itemView.findViewById(R.id.body);
            this.createdBy = (TextView) itemView.findViewById(R.id.created_by);
            this.timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            this.tags = (TextView) itemView.findViewById(R.id.tags);
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
        TextView body = holder.body;
        TextView tags = holder.tags;
        TextView createdBy = holder.createdBy;
        TextView timestamp = holder.timestamp;


        title.setText(dataSet.get(listPosition).getTitle());
        body.setText(dataSet.get(listPosition).getBody());
//        tags.setText(dataSet.get(listPosition).getTags());
        tags.setText("TAGS");
        createdBy.setText(dataSet.get(listPosition).getCreatedBy());
        timestamp.setText(dataSet.get(listPosition).getTimestamp());

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}
