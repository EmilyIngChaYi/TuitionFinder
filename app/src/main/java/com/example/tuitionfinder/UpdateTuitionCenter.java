package com.example.tuitionfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
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
import java.util.HashMap;

public class UpdateTuitionCenter extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),UserProfile.class));
        finish();
    }

    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference, userReference;
    String userID;
    TextView tuitionName, tuitionID;
    EditText phone, address, postcode, state, startBusinessHour, endBusinessHour, personInChargeName, personInChargePhone, description;
    CheckBox tuitionPrimary, tuitionSecondary, p_bm, p_bi, p_bc, p_math, p_sc, s_bm, s_bi, s_bc, s_math, s_sc, s_geo, s_sej, s_addmath, s_eco, s_acc, s_phy, s_che, s_bio, s_pd;
    Button saveBtn, deleteBtn;
    ArrayList<String> student;
    ArrayList<String> subjects;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_tuition_center);

        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Tuition Center Lists");
        userReference = database.getReference("Users").child(userID);
        tuitionName = findViewById(R.id.tuitionName);
        tuitionID = findViewById(R.id.tuitionID);
        phone = findViewById(R.id.tuitionPhone);
        address = findViewById(R.id.tuitionAddress);
        postcode = findViewById(R.id.tuitionPostcode);
        state = findViewById(R.id.tuitionState);
        startBusinessHour = findViewById(R.id.tuitionStartBusinessHour);
        endBusinessHour = findViewById(R.id.tuitionEndBusinessHour);
        personInChargeName = findViewById(R.id.tuitionPICName);
        personInChargePhone = findViewById(R.id.tuitionPICPhone);
        description = findViewById(R.id.tuitionDescription);
        saveBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        student = new ArrayList<>();
        subjects = new ArrayList<>();
        tuitionPrimary = findViewById(R.id.PrimaryStudent);
        tuitionSecondary = findViewById(R.id.MiddleStudent);
        p_bm = findViewById(R.id.primary_BM);
        p_bi = findViewById(R.id.primary_BI);
        p_bc = findViewById(R.id.primary_BC);
        p_math = findViewById(R.id.primary_Maths);
        p_sc = findViewById(R.id.primary_SC);
        s_bm = findViewById(R.id.secondary_BM);
        s_bi = findViewById(R.id.secondary_BI);
        s_bc = findViewById(R.id.secondary_BC);
        s_math = findViewById(R.id.secondary_Maths);
        s_sc = findViewById(R.id.secondary_Sc);
        s_geo = findViewById(R.id.secondary_Geo);
        s_sej = findViewById(R.id.secondary_Sej);
        s_addmath = findViewById(R.id.secondary_AddMaths);
        s_eco = findViewById(R.id.secondary_Ekonomi);
        s_acc = findViewById(R.id.secondary_Acc);
        s_phy = findViewById(R.id.secondary_Phy);
        s_che = findViewById(R.id.secondary_Che);
        s_bio = findViewById(R.id.secondary_Bio);
        s_pd = findViewById(R.id.secondary_PD);

        //subject checkbox getText
        String PrimaryStudent = tuitionPrimary.getText().toString();
        String SecondaryStudent = tuitionSecondary.getText().toString();
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

        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String tuition_name = snapshot.child("tuitionName").getValue().toString();

                reference.child(tuition_name).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //retrieve data from database
                        String TuitionName = snapshot.child("tuitionName").getValue().toString();
                        String Phone = snapshot.child("tuitionPhone").getValue().toString();
                        String Address = snapshot.child("tuitionAddress").getValue().toString();
                        String Postcode = snapshot.child("tuitionPostcode").getValue().toString();
                        String State = snapshot.child("state").getValue().toString();
                        String StartBusinessHour = snapshot.child("startBusinessHour").getValue().toString();
                        String EndBusinessHour = snapshot.child("endBusinessHour").getValue().toString();
                        String PICName = snapshot.child("tuitionPersonInChargeName").getValue().toString();
                        String PICPhone = snapshot.child("tuitionPersonInChargePhone").getValue().toString();
                        String Description = snapshot.child("description").getValue().toString();
                        String StudentType = snapshot.child("student").getValue().toString();
                        String SubjectAvailable = snapshot.child("subjects").getValue().toString();

                        tuitionName.setText(TuitionName);
                        tuitionID.setText(userID);
                        phone.setText(Phone);
                        address.setText(Address);
                        postcode.setText(Postcode);
                        state.setText(State);
                        startBusinessHour.setText(StartBusinessHour);
                        endBusinessHour.setText(EndBusinessHour);
                        personInChargeName.setText(PICName);
                        personInChargePhone.setText(PICPhone);
                        description.setText(Description);
                        //student type
                        if (StudentType.contains("Tuition for primary student (Year1-Year6)")){
                            tuitionPrimary.setChecked(true);
                        }

                        if (StudentType.contains("Tuition for Secondary Student (Form1-Form5)")){
                            tuitionSecondary.setChecked(true);
                        }

                        //subject available
                        if (SubjectAvailable.contains(PrimaryBM)){
                            p_bm.setChecked(true);
                        }

                        if (SubjectAvailable.contains(PrimaryBI)){
                            p_bi.setChecked(true);
                        }

                        if (SubjectAvailable.contains(PrimaryBC)){
                            p_bc.setChecked(true);
                        }

                        if (SubjectAvailable.contains(PrimaryMaths)){
                            p_math.setChecked(true);
                        }

                        if (SubjectAvailable.contains(PrimarySc)){
                            p_sc.setChecked(true);
                        }

                        if (SubjectAvailable.contains(SecondaryBM)){
                            s_bm.setChecked(true);
                        }

                        if (SubjectAvailable.contains(SecondaryBI)){
                            s_bi.setChecked(true);
                        }

                        if (SubjectAvailable.contains(SecondaryBC)){
                            s_bc.setChecked(true);
                        }

                        if (SubjectAvailable.contains(SecondaryMaths)){
                            s_math.setChecked(true);
                        }

                        if (SubjectAvailable.contains(SecondarySc)){
                            s_sc.setChecked(true);
                        }

                        if (SubjectAvailable.contains(SecondarySej)){
                            s_sej.setChecked(true);
                        }

                        if (SubjectAvailable.contains(SecondaryGeo)){
                            s_geo.setChecked(true);
                        }

                        if (SubjectAvailable.contains(SecondaryAddMath)){
                            s_addmath.setChecked(true);
                        }

                        if (SubjectAvailable.contains(SecondaryEco)){
                            s_eco.setChecked(true);
                        }

                        if (SubjectAvailable.contains(SecondaryBio)){
                            s_bio.setChecked(true);
                        }

                        if (SubjectAvailable.contains(SecondaryPhy)){
                            s_phy.setChecked(true);
                        }

                        if (SubjectAvailable.contains(SecondaryChe)){
                            s_che.setChecked(true);
                        }

                        if (SubjectAvailable.contains(SecondaryPD)){
                            s_pd.setChecked(true);
                        }

                        if (SubjectAvailable.contains(SecondaryAcc)){
                            s_acc.setChecked(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //saveBtn, editBtn
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (i == 0){
                   //set editText and checkBox enabled
                   phone.setEnabled(true);
                   phone.requestFocus();

                   address.setEnabled(true);
                   address.requestFocus();

                   postcode.setEnabled(true);
                   postcode.requestFocus();

                   state.setEnabled(true);
                   state.requestFocus();

                   startBusinessHour.setEnabled(true);
                   startBusinessHour.requestFocus();

                   endBusinessHour.setEnabled(true);
                   endBusinessHour.requestFocus();

                   personInChargeName.setEnabled(true);
                   personInChargeName.requestFocus();

                   personInChargePhone.setEnabled(true);
                   personInChargePhone.requestFocus();

                   tuitionPrimary.setEnabled(true);
                   tuitionSecondary.setEnabled(true);

                   p_bm.setEnabled(true);
                   p_bi.setEnabled(true);
                   p_bc.setEnabled(true);
                   p_math.setEnabled(true);
                   p_sc.setEnabled(true);
                   s_bm.setEnabled(true);
                   s_bi.setEnabled(true);
                   s_bc.setEnabled(true);
                   s_math.setEnabled(true);
                   s_sc.setEnabled(true);
                   s_sej.setEnabled(true);
                   s_addmath.setEnabled(true);
                   s_phy.setEnabled(true);
                   s_che.setEnabled(true);
                   s_bio.setEnabled(true);
                   s_acc.setEnabled(true);
                   s_eco.setEnabled(true);
                   s_pd.setEnabled(true);
                   s_geo.setEnabled(true);

                   description.setEnabled(true);
                   description.requestFocus();

                   saveBtn.setText("Save");
                   i++;
               }else{
                   //get new value from edit text
                   String newPhone = phone.getText().toString();
                   String newAddress = address.getText().toString();
                   String newPostcode = postcode.getText().toString();
                   String newState = state.getText().toString();
                   String newStartBusinessHour = startBusinessHour.getText().toString();
                   String newEndBusinessHour = endBusinessHour.getText().toString();
                   String newPICName = personInChargeName.getText().toString();
                   String newPICPhone = personInChargePhone.getText().toString();
                   String newDescription = description.getText().toString();
                   //get Text from checkbox and add into the arraylist
                   if (tuitionPrimary.isChecked()){
                       student.remove(PrimaryStudent);
                       student.add(PrimaryStudent);
                   }else{
                       student.remove(PrimaryStudent);
                   }
                   if (tuitionSecondary.isChecked()){
                       student.remove(SecondaryStudent);
                       student.add(SecondaryStudent);
                   }else{
                       student.remove(SecondaryStudent);
                   }
                   if (p_bm.isChecked()){
                       subjects.remove(PrimaryBM);
                       subjects.add(PrimaryBM);
                   }else{
                       subjects.remove(PrimaryBM);
                   }

                   if (p_bi.isChecked()){
                       subjects.remove(PrimaryBI);
                       subjects.add(PrimaryBI);
                   }else{
                       subjects.remove(PrimaryBI);
                   }

                   if (p_bc.isChecked()){
                       subjects.remove(PrimaryBC);
                       subjects.add(PrimaryBC);
                   }else{
                       subjects.remove(PrimaryBC);
                   }

                   if (p_math.isChecked()){
                       subjects.remove(PrimaryMaths);
                       subjects.add(PrimaryMaths);
                   }else{
                       subjects.remove(PrimaryMaths);
                   }

                   if (p_sc.isChecked()){
                       subjects.remove(PrimarySc);
                       subjects.add(PrimarySc);
                   }else{
                       subjects.remove(PrimarySc);
                   }

                   if (s_bm.isChecked()){
                       subjects.remove(SecondaryBM);
                       subjects.add(SecondaryBM);
                   }else{
                       subjects.remove(SecondaryBM);
                   }

                   if (s_bi.isChecked()){
                       subjects.remove(SecondaryBI);
                       subjects.add(SecondaryBI);
                   }else{
                       subjects.remove(SecondaryBI);
                   }

                   if (s_bc.isChecked()){
                       subjects.remove(SecondaryBC);
                       subjects.add(SecondaryBC);
                   }else{
                       subjects.remove(SecondaryBC);
                   }

                   if (s_math.isChecked()){
                       subjects.remove(SecondaryMaths);
                       subjects.add(SecondaryMaths);
                   }else{
                       subjects.remove(SecondaryMaths);
                   }

                   if (s_sc.isChecked()){
                       subjects.remove(SecondarySc);
                       subjects.add(SecondarySc);
                   }else{
                       subjects.remove(SecondarySc);
                   }

                   if (s_geo.isChecked()){
                       subjects.remove(SecondaryGeo);
                       subjects.add(SecondaryGeo);
                   }else{
                       subjects.remove(SecondaryGeo);
                   }

                   if (s_sej.isChecked()){
                       subjects.remove(SecondarySej);
                       subjects.add(SecondarySej);
                   }else{
                       subjects.remove(SecondarySej);
                   }

                   if (s_addmath.isChecked()){
                       subjects.remove(SecondaryAddMath);
                       subjects.add(SecondaryAddMath);
                   }else{
                       subjects.remove(SecondaryAddMath);
                   }

                   if (s_eco.isChecked()){
                       subjects.remove(SecondaryEco);
                       subjects.add(SecondaryEco);
                   }else{
                       subjects.remove(SecondaryEco);
                   }

                   if (s_acc.isChecked()){
                       subjects.remove(SecondaryAcc);
                       subjects.add(SecondaryAcc);
                   }else{
                       subjects.remove(SecondaryAcc);
                   }

                   if (s_phy.isChecked()){
                       subjects.remove(SecondaryPhy);
                       subjects.add(SecondaryPhy);
                   }else{
                       subjects.remove(SecondaryPhy);
                   }

                   if (s_che.isChecked()){
                       subjects.remove(SecondaryChe);
                       subjects.add(SecondaryChe);
                   }else{
                       subjects.remove(SecondaryChe);
                   }

                   if (s_bio.isChecked()){
                       subjects.remove(SecondaryBio);
                       subjects.add(SecondaryBio);
                   }else{
                       subjects.remove(SecondaryBio);
                   }

                   if (s_pd.isChecked()){
                       subjects.remove(SecondaryPD);
                       subjects.add(SecondaryPD);
                   }else{
                       subjects.remove(SecondaryPD);
                   }

                   HashMap<String,Object> update = new HashMap<>();
                   update.put("tuitionPhone", newPhone);
                   update.put("tuitionAddress", newAddress);
                   update.put("tuitionPostcode", newPostcode);
                   update.put("state", newState);
                   update.put("startBusinessHour", newStartBusinessHour);
                   update.put("endBusinessHour", newEndBusinessHour);
                   update.put("tuitionPersonInChargeName", newPICName);
                   update.put("tuitionPersonInChargePhone", newPICPhone);
                   update.put("student", student);
                   update.put("subjects", subjects);
                   update.put("description", newDescription);

                   //update in firebase
                   reference.child(tuitionName.getText().toString()).updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                       @Override
                       public void onComplete(@NonNull Task<Void> task) {
                           if (task.isSuccessful()){
                               Toast.makeText(UpdateTuitionCenter.this, "Tuition center information have been updates successfully.", Toast.LENGTH_SHORT).show();
                           }
                       }
                   });

                   phone.setEnabled(false);

                   address.setEnabled(false);

                   postcode.setEnabled(false);

                   state.setEnabled(false);

                   startBusinessHour.setEnabled(false);

                   endBusinessHour.setEnabled(false);

                   personInChargeName.setEnabled(false);

                   personInChargePhone.setEnabled(false);

                   tuitionPrimary.setEnabled(false);
                   tuitionSecondary.setEnabled(false);

                   p_bm.setEnabled(false);
                   p_bi.setEnabled(false);
                   p_bc.setEnabled(false);
                   p_math.setEnabled(false);
                   p_sc.setEnabled(false);
                   s_bm.setEnabled(false);
                   s_bi.setEnabled(false);
                   s_bc.setEnabled(false);
                   s_math.setEnabled(false);
                   s_sc.setEnabled(false);
                   s_sej.setEnabled(false);
                   s_addmath.setEnabled(false);
                   s_phy.setEnabled(false);
                   s_che.setEnabled(false);
                   s_bio.setEnabled(false);
                   s_acc.setEnabled(false);
                   s_eco.setEnabled(false);
                   s_pd.setEnabled(false);
                   s_geo.setEnabled(false);

                   description.setEnabled(false);

                   saveBtn.setText("Edit");
                   i--;
               }
            }
        });

        //deleteBtn
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder deleteTuitionCenter = new AlertDialog.Builder(view.getContext());
                deleteTuitionCenter.setTitle("Delete Tuition Center");
                deleteTuitionCenter.setMessage("Do you really want to delete this tuition center? Once it was deleted, it would not recovery back. The booking list also will be delete.");
                deleteTuitionCenter.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        userReference.child("tuitionName").removeValue();
                        DatabaseReference tuitionBooking = database.getReference("Tuition Booking").child(userID);
                        tuitionBooking.removeValue();
                        reference.child(tuitionName.getText().toString()).removeValue();
                        Toast.makeText(UpdateTuitionCenter.this, "You have successfully delete this tuition center. You may add new tuition center.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });
                deleteTuitionCenter.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //close dialog
                    }
                });
                deleteTuitionCenter.create().show();
            }
        });
    }
}