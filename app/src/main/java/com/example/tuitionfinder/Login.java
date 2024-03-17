package com.example.tuitionfinder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    EditText email, password;
    Button login;
    TextView goRegister, forgetPwd;
    FirebaseAuth auth;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPwd);
        login = findViewById(R.id.loginBtn);
        goRegister = findViewById(R.id.goRegister);
        forgetPwd = findViewById(R.id.forgetPassword);
        progressBar = findViewById(R.id.progressBar3);
        auth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);

                //make sure user have enter value
                String Email = email.getText().toString();
                String Pwd = password.getText().toString();

                if(TextUtils.isEmpty(Email)){
                    email.setError("Please enter user's email address.");
                    return;
                }
                if(TextUtils.isEmpty(Pwd)){
                    password.setError("Please enter password.");
                    return;
                }

                //firebase auth.
                auth.signInWithEmailAndPassword(Email ,Pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            //check user email verification
                            FirebaseUser user = auth.getCurrentUser();
                            if (user.isEmailVerified()){
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(Login.this, "User login successful.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }else{
                                progressBar.setVisibility(View.GONE);
                                user.sendEmailVerification();
                                Toast.makeText(Login.this, "Please verify your email. Verify email has been send to your email.", Toast.LENGTH_SHORT).show();
                            }

                        }else{
                            Toast.makeText(Login.this, "User login fail.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        //direct user go to register page.
        goRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        });

        //forgot password.
        forgetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password");
                passwordResetDialog.setMessage("Please enter registered email to receive reset password link.");
                passwordResetDialog.setView(resetMail);
                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //if choose "yes".
                        //extract the email and send the link.
                        String mail = resetMail.getText().toString();
                        auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(Login.this, "Reset password link have sent to your email.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Email not sent"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //if choose "no", close the dialog
                    }
                });
                passwordResetDialog.create().show();
            }
        });
    }
}