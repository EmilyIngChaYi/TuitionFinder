package com.example.tuitionfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;

    Context context;
    ArrayList<TuitionList> list;

    public MyAdapter(Context context, ArrayList<TuitionList> list, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.list = list;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_view,parent,false);
        return new MyViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TuitionList tuitionList = list.get(position);
        holder.tuitionName.setText(tuitionList.getTuitionName());
        holder.price.setText("RM "+tuitionList.getPrice());
        holder.tuitionAddress.setText(tuitionList.getTuitionAddress());
        holder.averageRating.setText(tuitionList.getAverageRating()+" /5.0");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView tuitionName, price, tuitionAddress, averageRating;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tuitionName = itemView.findViewById(R.id.TuitionName);
            price = itemView.findViewById(R.id.TuitionPrice);
            tuitionAddress = itemView.findViewById(R.id.Address);
            averageRating = itemView.findViewById(R.id.TuitionRating);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }
    }
}
