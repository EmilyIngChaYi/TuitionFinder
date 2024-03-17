package com.example.tuitionfinder;

import static com.example.tuitionfinder.R.id.rejectBtn;

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

public class BookingDetail_tuition extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(), BookingList.class));
        finish();
    }

    FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userID = fAuth.getCurrentUser().getUid();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference, ref, studRef;
    TextView tuitionName, tuitionID, tuitionPhone, selectedSubjects, studentName, studentEmail, studentID, studentGrade, studentPhone;
    Button reject;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail_tuition);
        String student_ID = getIntent().getStringExtra("studentID");

        ref = database.getReference("Users").child(userID);
        databaseReference = database.getReference("Tuition Booking").child(userID).child(student_ID);
        tuitionName = findViewById(R.id.tc_name);
        tuitionID = findViewById(R.id.tc_ID);
        tuitionPhone = findViewById(R.id.tc_phone);
        selectedSubjects = findViewById(R.id.stud_selectedSubject);
        studentName = findViewById(R.id.stud_name);
        studentEmail = findViewById(R.id.stud_email);
        studentID = findViewById(R.id.stud_ID);
        studentGrade = findViewById(R.id.stud_grade);
        studentPhone = findViewById(R.id.stud_phone);
        reject = findViewById(rejectBtn);


        databaseReference.addValueEventListener(new ValueEventListener() {
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

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studRef = database.getReference("Booking Lists").child(studentID.getText().toString()).child(tuitionName.getText().toString());
                studRef.removeValue();
                databaseReference.removeValue();
                startActivity(new Intent(getApplicationContext(), BookingHistory.class));
            }
        });
    }
}