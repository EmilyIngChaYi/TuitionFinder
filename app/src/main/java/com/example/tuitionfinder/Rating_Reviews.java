package com.example.tuitionfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;

public class Rating_Reviews extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    FirebaseDatabase database;
    DatabaseReference reference;
    RatingBar ratingBar;
    EditText review;
    Button submitBtn;
    Review reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_reviews);
        String name = getIntent().getStringExtra("name");

        ratingBar = findViewById(R.id.ratingBar3);
        review = findViewById(R.id.review);
        submitBtn = findViewById(R.id.rating_reviewBtn);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Tuition Center Lists").child(name);
        reviews = new Review();

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float rating = ratingBar.getRating();
                String comment = review.getText().toString();
                String reviewID = reference.child("reviews").push().getKey();

                //make sure user have enter value in ratingBar
                if (rating == 0.0){
                    Toast.makeText(Rating_Reviews.this, "Please rate.", Toast.LENGTH_SHORT).show();
                }else{

                    //save review and rating
                    reviews.setReviews(comment);
                    reviews.setRating(rating);
                    reference.child("rate_reviews").child(reviewID).setValue(reviews);

                    // retrieve the rating and calculate the average rating; update the average rating
                    reference.child("rate_reviews").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            double total = 0.0;
                            double count = 0.0;
                            double average = 0.0;
                            NumberFormat format = new DecimalFormat("#0.0");

                            for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                                double rate = Double.parseDouble(dataSnapshot.child("rating").getValue().toString());
                                total += rate;
                                count +=1;
                                average = total / count;
                            }
                            HashMap<String, Object> rate = new HashMap<>();
                            rate.put("averageRating",format.format(average));
                            reference.updateChildren(rate).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Rating_Reviews.this, "Thank you for your rate.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        finish();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}