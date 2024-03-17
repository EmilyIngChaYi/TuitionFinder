package com.example.tuitionfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TuitionReview extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Review> list;
    MyAdapter_Review adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_review);
        String tuition_name = getIntent().getStringExtra("t_name");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Tuition Center Lists").child(tuition_name);
        recyclerView = findViewById(R.id.recyclerView5);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new MyAdapter_Review(this, list);
        recyclerView.setAdapter(adapter);

        reference.child("rate_reviews").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Review review = dataSnapshot.getValue(Review.class);
                    list.add(review);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}