package com.example.tuitionfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class addTuitionCenter extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    EditText tuitionName, tuitionPhone, tuitionAddress, tuitionPostcode, PIC_name, PIC_phone, price, description;
    CheckBox tuitionPrimary, tuitionSecondary, p_bm, p_bi, p_bc, p_math, p_sc, s_bm, s_bi, s_bc, s_math, s_sc, s_geo, s_sej, s_addmath, s_eco, s_acc, s_phy, s_che, s_bio, s_pd;
    Button saveBtn, chooseImage;
    TextView alertText;
    Spinner spinner;
    EditText businessHour1, businessHour2;
    int hour1, minute1, hour2, minute2;
    FirebaseAuth fAuth;
    String uid;
    FirebaseDatabase database;
    DatabaseReference reference, userRef;
    ArrayList<String> student = new ArrayList<>();
    ArrayList<String> subjects = new ArrayList<>();
    ArrayList<Uri> imageList = new ArrayList<Uri>();  // for image use
    Uri ImageUri;
    ProgressDialog progressDialog;
    StorageReference imageRef;
    int upload_count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tuition_center);

        //image view
        alertText = findViewById(R.id.alertText);
        chooseImage = findViewById(R.id.chooseImage);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Image uploading...");

        //initialize variable
        tuitionName = findViewById(R.id.TuitionCenterName);
        tuitionPhone = findViewById(R.id.TuitionPhone);
        tuitionAddress = findViewById(R.id.TuitionAddress);
        tuitionPostcode = findViewById(R.id.TuitionPostcode);
        PIC_name = findViewById(R.id.TuitionPersonInChargeName);
        PIC_phone = findViewById(R.id.TuitionPersonInChargePhone);
        price = findViewById(R.id.price);
        description = findViewById(R.id.description);
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
        spinner = findViewById(R.id.spinner2);
        businessHour1 = findViewById(R.id.businessHour_start);
        businessHour2 = findViewById(R.id.businessHour_end);
        saveBtn = findViewById(R.id.savaBtn);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Tuition Center Lists");
        fAuth = FirebaseAuth.getInstance();
        uid = fAuth.getCurrentUser().getUid();
        userRef = database.getReference("Users").child(uid);
        TuitionCenterInfo tuitionCenterInfo = new TuitionCenterInfo();

        //chooseBtn
        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        //spinner for the state option
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(addTuitionCenter.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.state));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        //time picker dialog for business hour start; spinner mode
        businessHour1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hour1 = selectedHour;
                        minute1 = selectedMinute;
                        businessHour1.setText(String.format(Locale.getDefault(),"%02d:%02d",hour1,minute1));
                    }
                };
                int style = AlertDialog.THEME_HOLO_DARK;
                TimePickerDialog timePickerDialog = new TimePickerDialog(addTuitionCenter.this, style, onTimeSetListener, hour1, minute1, false);
                timePickerDialog.show();
            }
        });

        //time picker dialog for business hour end; spinner mode
        businessHour2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        hour2 = selectedHour;
                        minute2 = selectedMinute;
                        businessHour2.setText(String.format(Locale.getDefault(),"%02d:%02d",hour2,minute2));
                    }
                };
                int style = AlertDialog.THEME_HOLO_DARK;
                TimePickerDialog timePickerDialog = new TimePickerDialog(addTuitionCenter.this, style, onTimeSetListener, hour2, minute2, false);
                timePickerDialog.show();
            }
        });

        //save button
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String TuitionName = tuitionName.getText().toString();
                String TuitionPhone = tuitionPhone.getText().toString();
                String TuitionAddress = tuitionAddress.getText().toString();
                String TuitionPostcode = tuitionPostcode.getText().toString();
                String TuitionPersonInChargeName = PIC_name.getText().toString();
                String TuitionPersonInChargePhone = PIC_phone.getText().toString();
                String Price = price.getText().toString();
                String Description = description.getText().toString();
                String Primary = tuitionPrimary.getText().toString();
                String Secondary = tuitionSecondary.getText().toString();
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
                String state = spinner.getSelectedItem().toString();
                String startBusinessHour = businessHour1.getText().toString();
                String endBusinessHour = businessHour2.getText().toString();

                //the box have been fill in or not
                if (TextUtils.isEmpty(TuitionName)){
                    tuitionName.setError("Please fill in tuition center's name.");
                    return;
                }else {
                    tuitionCenterInfo.setTuitionName(TuitionName);
                }

                if (TextUtils.isEmpty(TuitionPhone)){
                    tuitionPhone.setError("Please fill in tuition center's contact number.");
                    return;
                }

                if (TuitionPhone.length()<9 || TuitionPhone.length()>11){
                    tuitionPhone.setError("Please fill in correct tuition center contact number without '-'.");
                    return;
                }else {
                    tuitionCenterInfo.setTuitionPhone(TuitionPhone);
                }

                if (TextUtils.isEmpty(TuitionAddress)){
                    tuitionAddress.setError("Please fill in tuition center's address.");
                    return;
                }else{
                    tuitionCenterInfo.setTuitionAddress(TuitionAddress);
                }

                if (TextUtils.isEmpty(TuitionPostcode)){
                    tuitionPostcode.setError("Please fill in tuition center's postcode.");
                    return;
                }

                if (TuitionPostcode.length() !=5){
                    tuitionPostcode.setError("Please fill in correct tuition center's postcode.");
                    return;
                }else{
                    tuitionCenterInfo.setTuitionPostcode(TuitionPostcode);
                }

                if (TextUtils.isEmpty(TuitionPersonInChargeName)){
                    PIC_name.setError("Please fill in name of person in charge of the tuition center.");
                    return;
                }else{
                    tuitionCenterInfo.setTuitionPersonInChargeName(TuitionPersonInChargeName);
                }

                if (TextUtils.isEmpty(TuitionPersonInChargePhone)){
                    PIC_phone.setError("Please fill in contact number of person in charge of the tuition center.");
                    return;
                }

                if (TuitionPersonInChargePhone.length() <10 || TuitionPersonInChargePhone.length()>11){
                    PIC_phone.setError("Please fill in contact number of person in charge of the tuition center without '-'.");
                    return;
                }else{
                    tuitionCenterInfo.setTuitionPersonInChargePhone(TuitionPersonInChargePhone);
                }

                if (TextUtils.isEmpty(Price)){
                    price.setError("Please fill the price / price range per subject for a month in RM.");
                    return;
                }else{
                    tuitionCenterInfo.setPrice(Price);
                }

                if (TextUtils.isEmpty(Description)){
                    description.setError("Please add up others additional information about the tuition center / tuition class to attract more students.");
                    return;
                }else{
                    tuitionCenterInfo.setDescription(Description);
                }

                if (TextUtils.isEmpty(startBusinessHour)){
                    businessHour1.setError("Please fill in valid business start time.");
                    return;
                }else{
                    tuitionCenterInfo.setStartBusinessHour(startBusinessHour);
                }

                if (TextUtils.isEmpty(endBusinessHour)){
                    businessHour2.setError("Please fill in valid business end time.");
                    return;
                }else{
                    tuitionCenterInfo.setEndBusinessHour(endBusinessHour);
                }

                tuitionCenterInfo.setState(state);

                if (tuitionPrimary.isChecked()){
                    student.remove(Primary);
                    student.add(Primary);
                }else{
                    student.remove(Primary);
                }

                if (tuitionSecondary.isChecked()){
                    student.remove(Secondary);
                    student.add(Secondary);
                }else{
                    student.remove(Secondary);
                }

                tuitionCenterInfo.setStudent(student);

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

                tuitionCenterInfo.setSubjects(subjects);
                tuitionCenterInfo.setUid(uid);

                //make sure each id only handle one tuition center
                reference.orderByChild("uid").equalTo(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Toast.makeText(addTuitionCenter.this, "You have already handle for a tuition center.", Toast.LENGTH_SHORT).show();
                        }else{

                            //make sure no redundant tuition name
                            reference.child(TuitionName).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()){
                                        Toast.makeText(addTuitionCenter.this,"This tuition name was existed.",Toast.LENGTH_SHORT).show();
                                    }else{

                                        //add tuition name to user database
                                        HashMap<String, Object> update = new HashMap<>();
                                        update.put("tuitionName", TuitionName);
                                        userRef.updateChildren(update);

                                        //upload image
                                        progressDialog.show();

                                        imageRef = FirebaseStorage.getInstance().getReference().child(TuitionName);
                                        for (upload_count = 0; upload_count < imageList.size(); upload_count++){
                                            Uri IndividualImage = imageList.get(upload_count);
                                            StorageReference imageName = imageRef.child("Image"+IndividualImage.getLastPathSegment());
                                            imageName.putFile(IndividualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    imageName.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            String url = String.valueOf(uri);
                                                            StoreLink(url);
                                                        }
                                                    });
                                                }
                                            });
                                        }

                                        //save tuition center information
                                        reference.child(TuitionName).setValue(tuitionCenterInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()){
                                                    Toast.makeText(addTuitionCenter.this,"Tuition center added successfully.",Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                }else{
                                                    Toast.makeText(addTuitionCenter.this,"Tuition center added fail. Please try again.",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void StoreLink(String url) {

        String name = tuitionName.getText().toString();
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("ImageLink",url);
        reference.child(name).child("images").push().updateChildren(hashMap);
        progressDialog.dismiss();
        alertText.setText("Image uploaded successfully");
        imageList.clear();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE){
            if (resultCode == RESULT_OK){
                if (data.getClipData() != null){
                    int countClipData = data.getClipData().getItemCount();
                    int currentImageSelected = 0;
                    while (currentImageSelected < countClipData){
                        ImageUri = data.getClipData().getItemAt(currentImageSelected).getUri();
                        imageList.add(ImageUri);
                        currentImageSelected = currentImageSelected + 1;

                    }
                    chooseImage.setVisibility(View.GONE);
                    alertText.setVisibility(View.VISIBLE);
                    alertText.setText(imageList.size()+" images have selected.");
                }else{
                    Toast.makeText(this, "Please select more than one image.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
