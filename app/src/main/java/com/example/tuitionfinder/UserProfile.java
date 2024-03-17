package com.example.tuitionfinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class UserProfile extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    FirebaseAuth fAuth;
    String userId;
    FirebaseDatabase database;
    DatabaseReference reference, getImage;
    StorageReference imageRef;
    ImageView imageView;
    Uri imageUri;
    TextView id, email, grade, user, school;
    EditText name, phone, ic, age;
    Button booking, tuitionBooking, updateProfilePicture, logout, editProfile, tuitioninformation;
    ProgressDialog progressDialog;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        fAuth = FirebaseAuth.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users").child(userId);
        getImage = database.getReference("Users").child(userId).child("Profile Image");
        imageRef = FirebaseStorage.getInstance().getReference();
        imageView = findViewById(R.id.imageView);
        id = findViewById(R.id.userId);
        name = findViewById(R.id.username);
        email = findViewById(R.id.userEmail);
        phone = findViewById(R.id.contactNumber);
        ic = findViewById(R.id.ic_num);
        age = findViewById(R.id.Age);
        grade = findViewById(R.id.grade);
        user = findViewById(R.id.user);
        school = findViewById(R.id.school);
        booking = findViewById(R.id.checkBooking);
        tuitionBooking = findViewById(R.id.booking);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Image uploading...");
        updateProfilePicture = findViewById(R.id.updateProfilePicture);
        logout = findViewById(R.id.logout);
        editProfile = findViewById(R.id.editProfile);
        tuitioninformation = findViewById(R.id.tuitionInfomation);

        //imageView
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfilePicture.setVisibility(View.VISIBLE);
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });

        id.setText(userId);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String Name = snapshot.child("name").getValue().toString();
                String Email = snapshot.child("email").getValue().toString();
                String Phone = snapshot.child("phone").getValue().toString();
                String Ic = snapshot.child("icNum").getValue().toString();
                String Age = snapshot.child("age").getValue().toString();
                String Grade = snapshot.child("grade").getValue().toString();
                String User = snapshot.child("userType").getValue().toString();
                String School = snapshot.child("school").getValue().toString();

                if (User.matches("Tuition Center")){
                    tuitionBooking.setVisibility(View.VISIBLE);
                    tuitioninformation.setVisibility(View.VISIBLE);
                }else{
                    booking.setVisibility(View.VISIBLE);
                }

                name.setText(Name);
                email.setText(Email);
                phone.setText(Phone);
                ic.setText(Ic);
                age.setText(Age);
                grade.setText(Grade);
                user.setText(User);
                school.setText(School);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfile.this, "Server connect error.", Toast.LENGTH_SHORT).show();
            }
        });

        //enable user to edit the information, second clcik on button will save the information
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (i == 0){
                    name.setEnabled(true);
                    name.requestFocus();

                    phone.setEnabled(true);
                    phone.requestFocus();

                    ic.setEnabled(true);
                    ic.requestFocus();

                    age.setEnabled(true);
                    age.requestFocus();

                    editProfile.setText("Save");
                    i++;
                }else{

                    String newName = name.getText().toString();
                    String newPhone = phone.getText().toString();
                    String newIc = ic.getText().toString();
                    String newAge = age.getText().toString();

                    HashMap<String, Object> update = new HashMap<>();
                    update.put("name", newName);
                    update.put("phone", newPhone);
                    update.put("ic", newIc);
                    update.put("age", newAge);

                    reference.updateChildren(update).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(UserProfile.this, "Profile update successfully.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    name.setEnabled(false);
                    name.clearFocus();

                    phone.setEnabled(false);
                    phone.clearFocus();

                    ic.setEnabled(false);
                    ic.clearFocus();

                    age.setEnabled(false);
                    age.clearFocus();

                    i--;
                    editProfile.setText("Edit Profile");
                }
            }
        });

        //direct tuition center user to edit the tuition center information or delete it.
        //if tuition center haven't add tuition will direct to add tuition center page.
        //if student user were not able to see this button.
        tuitioninformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reference.child("tuitionName").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            startActivity(new Intent(getApplicationContext(), UpdateTuitionCenter.class));
                            finish();
                        }else{
                            Toast.makeText(UserProfile.this, "You haven't add a tuition center. Please add a tuition center.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), addTuitionCenter.class));
                        }
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        //student booking history
        booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BookingHistory.class));
                finish();
            }
        });

        //tuition center booking list
        tuitionBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), BookingList.class));
                finish();
            }
        });

        updateProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //upload image
                if (imageUri != null){
                    uploadToFirebase(imageUri);
                }else{
                    Toast.makeText(UserProfile.this, "Please select image for your profile.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //retrieve profile image
        getImage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String link = snapshot.getValue(String.class);
                Picasso.get().load(link).into(imageView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fAuth.signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }

    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = imageRef.child(System.currentTimeMillis() + "." +getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        //profileImage profileImage = new profileImage(uri.toString());
                        Map<String,Object> update = new HashMap<>();
                        update.put("Profile Image",uri.toString());
                        reference.updateChildren(update);
                        progressDialog.dismiss();
                        updateProfilePicture.setVisibility(View.GONE);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressDialog.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserProfile.this, "Uploading Failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }
    }
}