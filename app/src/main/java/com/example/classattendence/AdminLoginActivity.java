package com.example.classattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLoginActivity extends AppCompatActivity {

    EditText adminLoginEmail;
    EditText adminLoginPassword;
    Button adminLoginButton;
    FirebaseAuth auth;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        adminLoginEmail=findViewById(R.id.adminLoginEmail);
        adminLoginPassword=findViewById(R.id.adminLoginPassword);
        adminLoginButton=findViewById(R.id.adminLoginButton);
        auth=FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        adminLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=adminLoginEmail.getText().toString();
                String password=adminLoginPassword.getText().toString();
                loginUser(mail,password);
            }
        });
    }

    private void loginUser(String mail, String password) {
        pd.setMessage("Please wait");
        pd.show();
        auth.signInWithEmailAndPassword(mail, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                pd.dismiss();
                Toast.makeText(AdminLoginActivity.this, "Login Complete", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),AdminHomeActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();;
                Toast.makeText(AdminLoginActivity.this, "Something went wrong!!Try again", Toast.LENGTH_SHORT).show();

            }
        });

    }
}