package com.example.tuitionfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookingHistory extends AppCompatActivity implements RecyclerViewInterface {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),UserProfile.class));
        finish();
    }

    RecyclerView recyclerView;
    ArrayList<BookingInfo> list;
    MyAdapter3_BookingHistory adapter;
    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    String userID = fAuth.getCurrentUser().getUid();
    DatabaseReference reference = database.getReference("Booking Lists").child(userID);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);

        list = new ArrayList<>();
        adapter = new MyAdapter3_BookingHistory(this, this, list);
        recyclerView = findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    BookingInfo bookingInfo = dataSnapshot.getValue(BookingInfo.class);
                    list.add(bookingInfo);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(BookingHistory.this, BookingDetail.class);
        intent.putExtra("tuition",list.get(position).getTuitionName());

        startActivity(intent);
    }
}