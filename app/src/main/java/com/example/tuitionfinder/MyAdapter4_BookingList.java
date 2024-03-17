package com.example.tuitionfinder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter4_BookingList extends RecyclerView.Adapter<MyAdapter4_BookingList.MyViewHolder> {
private final RecyclerViewInterface recyclerViewInterface;

        Context context;
        ArrayList<BookingInfo> list;

public MyAdapter4_BookingList(BookingList recyclerViewInterface, Context context, ArrayList<BookingInfo> list) {
        this.recyclerViewInterface = (RecyclerViewInterface) recyclerViewInterface;
        this.context = context;
        this.list = list;
        }

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_list_recyclerview,parent,false);
        return new MyViewHolder(view, recyclerViewInterface);
        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        BookingInfo bookingInfo = list.get(position);

        holder.studentName.setText(bookingInfo.getStudentName());

        }

@Override
public int getItemCount() {
        return list.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView studentName;

    public MyViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
        super(itemView);
        studentName = itemView.findViewById(R.id.student_id);

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
