package com.example.tuitionfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter_Review extends RecyclerView.Adapter<MyAdapter_Review.MyViewHolder> {

    Context context;
    ArrayList<Review> list;

    public MyAdapter_Review(Context context, ArrayList<Review> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_reviews_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Review reviewList = list.get(position);
        holder.rating.setText(String.valueOf(reviewList.getRating()));
        holder.reviews.setText(reviewList.getReviews());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView rating, reviews;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rating = itemView.findViewById(R.id.student_rating);
            reviews = itemView.findViewById(R.id.student_review);
        }
    }
}
