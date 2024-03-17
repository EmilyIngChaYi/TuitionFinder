package com.example.tuitionfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class confirmBooking extends AppCompatActivity {

    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    FirebaseDatabase database;
    DatabaseReference reference, ref, databaseReference, reff;
    TextView studentName, studentEmail, studentGrade, studentPhone;
    CheckBox p_bm, p_bi, p_bc, p_math, p_sc, s_bm, s_bi, s_bc, s_math, s_sc, s_geo, s_sej, s_addmath, s_eco, s_acc, s_phy, s_che, s_bio, s_pd;
    ArrayList<String> selectedSubject;
    Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);

        String tuitionCenterName = getIntent().getStringExtra("tuitionName");
        BookingInfo bookingInfo = new BookingInfo();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Tuition Center Lists").child(tuitionCenterName);
        ref = database.getReference("Users").child(userID);
        databaseReference = database.getReference("Booking Lists");
        reff = database.getReference("Tuition Booking");
        selectedSubject = new ArrayList<>();
        studentName = findViewById(R.id.StudentName);
        studentEmail = findViewById(R.id.StudentEmail);
        studentGrade = findViewById(R.id.StudentGrade);
        studentPhone = findViewById(R.id.StudentPhone);
        p_bm = findViewById(R.id.primary_bm);
        p_bi = findViewById(R.id.primary_bi);
        p_bc = findViewById(R.id.primary_bc);
        p_math = findViewById(R.id.primary_maths);
        p_sc = findViewById(R.id.primary_sc);
        s_bm = findViewById(R.id.secondary_bm);
        s_bi = findViewById(R.id.secondary_bi);
        s_bc = findViewById(R.id.secondary_bc);
        s_math = findViewById(R.id.secondary_maths);
        s_sc = findViewById(R.id.secondary_sc);
        s_geo = findViewById(R.id.secondary_geo);
        s_sej = findViewById(R.id.secondary_sej);
        s_addmath = findViewById(R.id.secondary_addmaths);
        s_eco = findViewById(R.id.secondary_ekonomi);
        s_acc = findViewById(R.id.secondary_acc);
        s_phy = findViewById(R.id.secondary_phy);
        s_che = findViewById(R.id.secondary_che);
        s_bio = findViewById(R.id.secondary_bio);
        s_pd = findViewById(R.id.secondary_pd);
        confirmBtn = findViewById(R.id.confirmBooking);

        //to string
        String PrimaryBM = p_bm.getText().toString();
        String PrimaryBI = p_bi.getText().toString();
        String PrimaryBC = p_bc.getText().toString();
        String PrimaryMaths = p_math.getText().toString();
        String PrimarySc = p_sc.getText().toString();
        String SecondaryBM = s_bm.getText().toString();
        String SecondaryBI = s_bi.getText().toString();
        String SecondaryBC = s_bc.getText().toString();
        String SecondaryMaths = s_math.getText().toString();
        String SecondarySc = s_sc.getText().toString();
        String SecondaryGeo = s_geo.getText().toString();
        String SecondarySej = s_sej.getText().toString();
        String SecondaryAddMath = s_addmath.getText().toString();
        String SecondaryEco = s_eco.getText().toString();
        String SecondaryAcc = s_acc.getText().toString();
        String SecondaryPhy = s_phy.getText().toString();
        String SecondaryChe = s_che.getText().toString();
        String SecondaryBio = s_bio.getText().toString();
        String SecondaryPD = s_pd.getText().toString();

        //show student information
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Name = snapshot.child("name").getValue().toString();
                String Email = snapshot.child("email").getValue().toString();
                String Grade = snapshot.child("grade").getValue().toString();
                String Phone = snapshot.child("phone").getValue().toString();

                studentName.setText(Name);
                bookingInfo.setStudentName(Name);

                studentEmail.setText(Email);
                bookingInfo.setStudentEmail(Email);

                studentGrade.setText(Grade);
                bookingInfo.setStudentGrade(Grade);

                studentPhone.setText(Phone);
                bookingInfo.setStudentPhone(Phone);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //list out subject available and let student to choose
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String TuitionName = snapshot.child("tuitionName").getValue().toString();
                bookingInfo.setTuitionName(TuitionName);
                String TuitionID = snapshot.child("uid").getValue().toString();
                bookingInfo.setTuitionID(TuitionID);
                String phone = snapshot.child("tuitionPhone").getValue().toString();
                bookingInfo.setTuitionPhone(phone);

                String subject = snapshot.child("subjects").getValue().toString();

                if (subject.contains(PrimaryBM)){
                    p_bm.setVisibility(View.VISIBLE);
                }

                if (subject.contains(PrimaryBI)){
                    p_bi.setVisibility(View.VISIBLE);
                }

                if (subject.contains(PrimaryBC)){
                    p_bc.setVisibility(View.VISIBLE);
                }

                if (subject.contains(PrimaryMaths)){
                    p_math.setVisibility(View.VISIBLE);
                }

                if (subject.contains(PrimarySc)){
                    p_sc.setVisibility(View.VISIBLE);
                }

                if (subject.contains(SecondaryBM)){
                    s_bm.setVisibility(View.VISIBLE);
                }

                if (subject.contains(SecondaryBI)){
                    s_bi.setVisibility(View.VISIBLE);
                }

                if (subject.contains(SecondaryBC)){
                    s_bc.setVisibility(View.VISIBLE);
                }

                if (subject.contains(SecondaryMaths)){
                    s_math.setVisibility(View.VISIBLE);
                }

                if (subject.contains(SecondarySc)){
                    s_sc.setVisibility(View.VISIBLE);
                }

                if (subject.contains(SecondarySej)){
                    s_sej.setVisibility(View.VISIBLE);
                }

                if (subject.contains(SecondaryGeo)){
                    s_geo.setVisibility(View.VISIBLE);
                }

                if (subject.contains(SecondaryAddMath)){
                    s_addmath.setVisibility(View.VISIBLE);
                }

                if (subject.contains(SecondaryEco)){
                    s_eco.setVisibility(View.VISIBLE);
                }

                if (subject.contains(SecondaryBio)){
                    s_bio.setVisibility(View.VISIBLE);
                }

                if (subject.contains(SecondaryPhy)){
                    s_phy.setVisibility(View.VISIBLE);
                }

                if (subject.contains(SecondaryChe)){
                    s_che.setVisibility(View.VISIBLE);
                }

                if (subject.contains(SecondaryPD)){
                    s_pd.setVisibility(View.VISIBLE);
                }

                if (subject.contains(SecondaryAcc)){
                    s_acc.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (p_bm.isChecked()){
                    selectedSubject.remove(PrimaryBM);
                    selectedSubject.add(PrimaryBM);
                }else{
                    selectedSubject.remove(PrimaryBM);
                }

                if (p_bi.isChecked()){
                    selectedSubject.remove(PrimaryBI);
                    selectedSubject.add(PrimaryBI);
                }else{
                    selectedSubject.remove(PrimaryBI);
                }

                if (p_bc.isChecked()){
                    selectedSubject.remove(PrimaryBC);
                    selectedSubject.add(PrimaryBC);
                }else{
                    selectedSubject.remove(PrimaryBC);
                }

                if (p_math.isChecked()){
                    selectedSubject.remove(PrimaryMaths);
                    selectedSubject.add(PrimaryMaths);
                }else{
                    selectedSubject.remove(PrimaryMaths);
                }

                if (p_sc.isChecked()){
                    selectedSubject.remove(PrimarySc);
                    selectedSubject.add(PrimarySc);
                }else{
                    selectedSubject.remove(PrimarySc);
                }

                if (s_bm.isChecked()){
                    selectedSubject.remove(SecondaryBM);
                    selectedSubject.add(SecondaryBM);
                }else{
                    selectedSubject.remove(SecondaryBM);
                }

                if (s_bi.isChecked()){
                    selectedSubject.remove(SecondaryBI);
                    selectedSubject.add(SecondaryBI);
                }else{
                    selectedSubject.remove(SecondaryBI);
                }

                if (s_bc.isChecked()){
                    selectedSubject.remove(SecondaryBC);
                    selectedSubject.add(SecondaryBC);
                }else{
                    selectedSubject.remove(SecondaryBC);
                }

                if (s_math.isChecked()){
                    selectedSubject.remove(SecondaryMaths);
                    selectedSubject.add(SecondaryMaths);
                }else{
                    selectedSubject.remove(SecondaryMaths);
                }

                if (s_sc.isChecked()){
                    selectedSubject.remove(SecondarySc);
                    selectedSubject.add(SecondarySc);
                }else{
                    selectedSubject.remove(SecondarySc);
                }

                if (s_geo.isChecked()){
                    selectedSubject.remove(SecondaryGeo);
                    selectedSubject.add(SecondaryGeo);
                }else{
                    selectedSubject.remove(SecondaryGeo);
                }

                if (s_sej.isChecked()){
                    selectedSubject.remove(SecondarySej);
                    selectedSubject.add(SecondarySej);
                }else{
                    selectedSubject.remove(SecondarySej);
                }

                if (s_addmath.isChecked()){
                    selectedSubject.remove(SecondaryAddMath);
                    selectedSubject.add(SecondaryAddMath);
                }else{
                    selectedSubject.remove(SecondaryAddMath);
                }

                if (s_eco.isChecked()){
                    selectedSubject.remove(SecondaryEco);
                    selectedSubject.add(SecondaryEco);
                }else{
                    selectedSubject.remove(SecondaryEco);
                }

                if (s_acc.isChecked()){
                    selectedSubject.remove(SecondaryAcc);
                    selectedSubject.add(SecondaryAcc);
                }else{
                    selectedSubject.remove(SecondaryAcc);
                }

                if (s_phy.isChecked()){
                    selectedSubject.remove(SecondaryPhy);
                    selectedSubject.add(SecondaryPhy);
                }else{
                    selectedSubject.remove(SecondaryPhy);
                }

                if (s_che.isChecked()){
                    selectedSubject.remove(SecondaryChe);
                    selectedSubject.add(SecondaryChe);
                }else{
                    selectedSubject.remove(SecondaryChe);
                }

                if (s_bio.isChecked()){
                    selectedSubject.remove(SecondaryBio);
                    selectedSubject.add(SecondaryBio);
                }else{
                    selectedSubject.remove(SecondaryBio);
                }

                if (s_pd.isChecked()){
                    selectedSubject.remove(SecondaryPD);
                    selectedSubject.add(SecondaryPD);
                }else{
                    selectedSubject.remove(SecondaryPD);
                }

                bookingInfo.setSelectSubjects(selectedSubject);
                bookingInfo.setStudentID(userID);

                databaseReference.child(userID).child(tuitionCenterName).setValue(bookingInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(confirmBooking.this, "Book Success", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                reff.child(bookingInfo.TuitionID).child(userID).setValue(bookingInfo);

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String tuitionName = snapshot.child("tuitionName").getValue().toString();

                        Intent intent = new Intent(confirmBooking.this, Rating_Reviews.class);
                        intent.putExtra("name", tuitionName);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}