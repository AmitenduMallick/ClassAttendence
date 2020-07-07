package com.example.classattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class TeacherRegisterActivity extends AppCompatActivity {

    EditText teacherRegisterName;
    EditText teacherRegisterMail;
    EditText teacherRegisterPassword;
    EditText teacherRegisterEnrollment;
    Button teacherRegisterButton;
    ProgressDialog pd;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_register);

        teacherRegisterName = findViewById(R.id.teacherRegisterName);
        teacherRegisterMail = findViewById(R.id.teacherRegisterMail);
        teacherRegisterPassword = findViewById(R.id.teacherRegisterPassword);
        teacherRegisterEnrollment = findViewById(R.id.teacherRegisterEnrollment);
        teacherRegisterButton = findViewById(R.id.teacherRegisterButton);
        pd = new ProgressDialog(this);
        auth = FirebaseAuth.getInstance();

        teacherRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = teacherRegisterName.getText().toString();
                String mail = teacherRegisterMail.getText().toString();
                String password = teacherRegisterPassword.getText().toString();
                String enroll = teacherRegisterEnrollment.getText().toString();
                registerTeacher(name, mail, password, enroll);
            }
        });
    }

    private void registerTeacher(final String name, final String mail, String password, final String enroll) {
        pd.setMessage("Please wait");
        pd.show();
        auth.createUserWithEmailAndPassword(mail,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                pd.dismiss();
                Toast.makeText(TeacherRegisterActivity.this, "Registered!!", Toast.LENGTH_SHORT).show();
                HashMap<String,Object> map=new HashMap<>();
                map.put("Name",name);
                map.put("Mail",mail);
                map.put("EnrollmentNo",enroll);
                map.put("TeacherImage","default");

                FirebaseDatabase.getInstance().getReference().child("Teacher").child(enroll).setValue(map);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                pd.dismiss();
                Toast.makeText(TeacherRegisterActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
