package com.example.tuitionfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BookingDetail extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), BookingHistory.class));
        finish();
    }

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userID = fAuth.getCurrentUser().getUid();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference, ref, tuitionRef;
    TextView tuitionName, tuitionID, tuitionPhone, selectedSubjects, studentName, studentEmail, studentID, studentGrade, studentPhone;
    Button delete;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail);
        String tuitionCenterName = getIntent().getStringExtra("tuition");

        ref = database.getReference("Users").child(userID);
        reference = database.getReference("Booking Lists").child(userID).child(tuitionCenterName);
        tuitionName = findViewById(R.id.tuition_name);
        tuitionID = findViewById(R.id.tuitionID);
        tuitionPhone = findViewById(R.id.tuition_phone);
        selectedSubjects = findViewById(R.id.selectedSubject);
        studentName = findViewById(R.id.student_name);
        studentEmail = findViewById(R.id.student_email);
        studentID = findViewById(R.id.studentID);
        studentGrade = findViewById(R.id.student_grade);
        studentPhone = findViewById(R.id.student_phone);
        delete = findViewById(R.id.deleteBtn);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tuition_name = snapshot.child("tuitionName").getValue().toString();
                String tuition_ID = snapshot.child("tuitionID").getValue().toString();
                String tuition_phone = snapshot.child("tuitionPhone").getValue().toString();
                String subjects = snapshot.child("selectSubjects").getValue().toString();
                String student_name = snapshot.child("studentName").getValue().toString();
                String student_ID = snapshot.child("studentID").getValue().toString();
                String student_email = snapshot.child("studentEmail").getValue().toString();
                String student_grade = snapshot.child("studentGrade").getValue().toString();
                String student_phone = snapshot.child("studentPhone").getValue().toString();

                tuitionName.setText(tuition_name);
                tuitionID.setText(tuition_ID);
                tuitionPhone.setText(tuition_phone);
                selectedSubjects.setText(subjects);
                studentName.setText(student_name);
                studentEmail.setText(student_email);
                studentID.setText(student_ID);
                studentGrade.setText(student_grade);
                studentPhone.setText(student_phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tuitionRef = database.getReference("Tuition Booking").child(tuitionID.getText().toString()).child(userID);
                tuitionRef.removeValue();
                reference.removeValue();
                startActivity(new Intent(getApplicationContext(), BookingHistory.class));
            }
        });
    }
}