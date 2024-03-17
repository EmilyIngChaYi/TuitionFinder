package com.example.tuitionfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TuitionCenterDetails extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    FirebaseAuth fAuth;
    String userID;
    FirebaseDatabase database;
    DatabaseReference reference, ref, imageRef;
    TextView tuitionName, studentType, subjects, phone, address, businessHour, description, price;
    RatingBar ratingBar;
    Button chatBtn, bookBtn, reviewBtn;
    RecyclerView recyclerView;
    ArrayList<TuitionImage> list;
    MyAdapter2 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuition_center_details);

        //past name of tuition center clicked in recyclerView.
        String tuitionCenterName = getIntent().getStringExtra("tuitionName");

        if (tuitionCenterName == null){
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Tuition Center Lists").child(tuitionCenterName);
        imageRef = database.getReference("Tuition Center Lists").child(tuitionCenterName).child("images");
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        ref = database.getReference("Users").child(userID);
        tuitionName = findViewById(R.id.TuitionCenterName);
        studentType = findViewById(R.id.StudentType);
        subjects = findViewById(R.id.Subject);
        phone = findViewById(R.id.Phone);
        address = findViewById(R.id.tuitionAddress);
        businessHour = findViewById(R.id.BusinessHour);
        description = findViewById(R.id.description);
        price = findViewById(R.id.Price);
        ratingBar = findViewById(R.id.ratingBar);
        chatBtn = findViewById(R.id.Chat);
        bookBtn = findViewById(R.id.book);
        reviewBtn = findViewById(R.id.review);
        //image recyclerView
        recyclerView = findViewById(R.id.recycleView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, true));
        list = new ArrayList<>();
        adapter = new MyAdapter2(this, list);
        recyclerView.setAdapter(adapter);

        //retrieve image
        imageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    TuitionImage tuitionImage = dataSnapshot.getValue(TuitionImage.class);
                    list.add(tuitionImage);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //retrieve data from firebase database and show it.
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String TuitionCenterName = snapshot.child("tuitionName").getValue().toString();
                String TuitionStudentType = snapshot.child("student").getValue().toString();
                String subject = snapshot.child("subjects").getValue().toString();
                String Phone = snapshot.child("tuitionPhone").getValue().toString();
                String Address = snapshot.child("tuitionAddress").getValue().toString();
                String businessStart = snapshot.child("startBusinessHour").getValue().toString();
                String businessEnd = snapshot.child("endBusinessHour").getValue().toString();
                String Description = snapshot.child("description").getValue().toString();
                String Price = snapshot.child("price").getValue().toString();
                if (snapshot.child("averageRating").exists()){
                    String rating = snapshot.child("averageRating").getValue().toString();
                    ratingBar.setRating(Float.parseFloat(rating));
                }else{
                    ratingBar.setRating(0);
                }

                tuitionName.setText(TuitionCenterName);

                studentType.setText(TuitionStudentType);
                subjects.setText(subject);
                phone.setText(Phone);
                address.setText(Address);
                businessHour.setText(businessStart+" - "+businessEnd);
                description.setText(Description);
                price.setText("RM "+Price+" /month");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //if user was not a student are not able to see chat button and book button
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String user = snapshot.child("userType").getValue().toString();

                if (user.matches("Student")){
                    chatBtn.setVisibility(View.VISIBLE);
                    bookBtn.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // if this tuition center already have reviews and rating will direct user to the page.
        // if no, then will Toast the message.
        reference.child("rate_reviews").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    reviewBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            reference.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    String t_name = snapshot.child("tuitionName").getValue().toString();
                                    Intent i = new Intent(TuitionCenterDetails.this, TuitionReview.class);
                                    i.putExtra("t_name", t_name);
                                    startActivity(i);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    });
                }else{
                    reviewBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(TuitionCenterDetails.this, "There is no rating and review yet.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String userid = snapshot.child("uid").getValue().toString();
                        String username = snapshot.child("tuitionName").getValue().toString();

                        Intent intent = new Intent(TuitionCenterDetails.this, MessageActivity.class);
                        intent.putExtra("userid", userid);
                        intent.putExtra("username",username);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        //direct student to the booking page tp proceed to next step
        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String tuitionName = snapshot.child("tuitionName").getValue().toString();

                        Intent i = new Intent(TuitionCenterDetails.this, confirmBooking.class);
                        i.putExtra("tuitionName",tuitionName);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }
}