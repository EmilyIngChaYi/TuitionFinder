package com.example.tuitionfinder;

import static android.R.layout.simple_spinner_dropdown_item;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

    //initialize variable.
    EditText name, email, phone, icNum, age, pwd, confirmPwd;
    RadioButton student, tuitionCenter, primarySchool, middleSchool;
    Button registerBtn;
    TextView goLogin, alertText;
    Spinner spinner;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth fAuth;
    String userID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //declare variable
        name = findViewById(R.id.fullName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        icNum = findViewById(R.id.icNum);
        age = findViewById(R.id.age);
        pwd = findViewById(R.id.password);
        confirmPwd = findViewById(R.id.confirmPassword);
        student = findViewById(R.id.student);
        tuitionCenter = findViewById(R.id.tuition);
        primarySchool = findViewById(R.id.primarySchool);
        middleSchool = findViewById(R.id.middleSchool);
        registerBtn = findViewById(R.id.registerBtn);
        goLogin = findViewById(R.id.goLogin);
        alertText = findViewById(R.id.alertText);
        spinner = findViewById(R.id.spinner);
        progressBar = findViewById(R.id.progressBar2);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Users");
        fAuth = FirebaseAuth.getInstance();
        UserInfo userInfo = new UserInfo();

        //grade spinner (dropdown list).
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(Register.this, android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.grade));
        myAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
        spinner.setAdapter(myAdapter);

        //if register button clicked.
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get text
                String Name = name.getText().toString();
                String Email = email.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String Phone = phone.getText().toString();
                String IcNum = icNum.getText().toString();
                String Age = age.getText().toString();
                String Password = pwd.getText().toString();
                String ConfirmPassword = confirmPwd.getText().toString();
                String Student = student.getText().toString();
                String Tuition = tuitionCenter.getText().toString();
                String Primary = primarySchool.getText().toString();
                String Middle = middleSchool.getText().toString();
                String Grade = spinner.getSelectedItem().toString();

                //if else statement check every boxes have been fill in.
                if(TextUtils.isEmpty(Name)){
                    name.setError("Please enter user's full name.");
                    name.findFocus();
                    return;
                }
                if(TextUtils.isEmpty(Email)){
                    email.setError("Please enter user's email address.");
                    email.findFocus();
                    return;
                }
                if(!Email.matches(emailPattern)){
                    email.setError("Please enter valid email address.");
                    email.findFocus();
                    return;
                }
                if(TextUtils.isEmpty(Phone)){
                    phone.setError("Please enter user's contact number.");
                    email.findFocus();
                    return;
                }
                if(Phone.length()<10 || Phone.length()>11){
                    phone.setError("Please enter contact number without '-'.");
                    phone.findFocus();
                    return;
                }
                if(TextUtils.isEmpty(IcNum)){
                    icNum.setError("Please enter user's ic number.");
                    icNum.findFocus();
                    return;
                }
                if(IcNum.length() != 12){
                    icNum.setError("Please enter ic number without '-'.");
                    icNum.findFocus();
                    return;
                }
                if(TextUtils.isEmpty(Age)){
                    age.setError("Please enter user's age.");
                    age.findFocus();
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    pwd.setError("Please enter password.");
                    pwd.findFocus();
                    return;
                }
                //let user prompt strong password. at least 10 character; at least 1 number; at least 1 uppercase; at least 1 lowercase; at least 1 special character.
                if(Password.length() <10){
                    pwd.setError("Password should not less than 10 character. Password should be include at least 1 number. Password should be include at least 1 uppercase. Password should include at least 1 lowercase. Password should include at least 1 symbol.");
                    pwd.findFocus();
                    return;
                }
                if(!Password.matches(".*[0-9].*") ){
                    pwd.setError("Password should be include at least 1 number.");
                    pwd.findFocus();
                    return;
                }
                if(!Password.matches(".*[A-Z].*") ){
                    pwd.setError("Password should be include at least 1 uppercase.");
                    pwd.findFocus();
                    return;
                }
                if(!Password.matches(".*[a-z].*")){
                    pwd.setError("Password should include at least 1 lowercase.");
                    pwd.findFocus();
                    return;
                }
                if(!Password.matches("^(?=.*[_.()$@]).*$")){
                    pwd.setError("Password should include at least 1 symbol.");
                    pwd.findFocus();
                    return;
                }
                if(TextUtils.isEmpty(ConfirmPassword)){
                    confirmPwd.setError("Please enter correct confirm password.");
                    confirmPwd.findFocus();
                    return;
                }
                if(!ConfirmPassword.matches(Password)){
                    confirmPwd.setError("Please enter correct confirm password.");
                    confirmPwd.findFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                userInfo.setName(Name);
                userInfo.setEmail(Email);
                userInfo.setPhone(Phone);
                userInfo.setIcNum(IcNum);
                userInfo.setAge(Age);
                if(student.isChecked()){
                    userInfo.setUserType(Student);
                }else{
                    userInfo.setUserType(Tuition);
                }
                if(primarySchool.isChecked()){
                    userInfo.setSchool(Primary);
                }else{
                    userInfo.setSchool(Middle);
                }
                userInfo.setGrade(Grade);

                //firebase authentication.
                fAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(Register.this, "User register successful.", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();

                            //sava data to database. set child as UID.
                            reference.child(userID).setValue(userInfo);

                            //send verification email
                            FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
                            fUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(Register.this, "Verification email has been sent to your email.", Toast.LENGTH_SHORT).show();
                                        alertText.setVisibility(View.VISIBLE);
                                    }
                                }
                            });

                            progressBar.setVisibility(View.GONE);
                        }else{
                            Toast.makeText(Register.this, "User register fail.", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });

        //text view button. click and direct to login page.
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
    }
}