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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AdminRegisterActivity extends AppCompatActivity {

    EditText adminName;
    EditText adminEmail;
    EditText adminPassword;
    EditText adminEnrollment;
    Button adminRegister;
    Button adminLogin;
    FirebaseAuth firebaseAuth;
    ProgressDialog pd;
    DatabaseReference myrootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_register);

        adminName=findViewById(R.id.adminName);
        adminEmail=findViewById(R.id.adminEmail);
        adminPassword=findViewById(R.id.adminPassword);
        adminEnrollment=findViewById(R.id.adminEnrollment);
        adminRegister=findViewById(R.id.adminRegister);
        adminLogin=findViewById(R.id.adminLogin);
        firebaseAuth=FirebaseAuth.getInstance();
        myrootRef=FirebaseDatabase.getInstance().getReference();
        pd=new ProgressDialog(this);
        if(firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),AdminHomeActivity.class));
            finish();
        }

        adminRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=adminEmail.getText().toString();
                String password=adminPassword.getText().toString();
                String name=adminName.getText().toString();
                String enrollment=adminEnrollment.getText().toString();
                registerAdmin(mail,password,name,enrollment);
            }
        });
        adminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminLoginActivity.class));
                finish();
            }
        });

    }

    private void registerAdmin(final String mail, String password, final String name, final String enrollment) {
        pd.setMessage("Please wait");
        pd.show();
        firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String,Object> map=new HashMap<>();
                map.put("AdminName",name);
                map.put("AdminMail",mail);
                map.put("AdminImage","default");
                map.put("ID",firebaseAuth.getCurrentUser().getUid());
                map.put("AdminEnrollment",enrollment);
                myrootRef.child("Admin").child(firebaseAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            pd.dismiss();
                            Toast.makeText(AdminRegisterActivity.this, "Update your profile for a better experience", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),AdminHomeActivity.class));
                            finish();

                        }
                        else{
                            Toast.makeText(AdminRegisterActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }
}