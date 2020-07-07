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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class TeacherLoginActivity extends AppCompatActivity {

    EditText teacherLoginMail;
    EditText teacherLoginPassword;
    Button teacherLoginButton;
    EditText teacherLoginEnrollment;
    FirebaseAuth firebaseAuth;
    ProgressDialog pd;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        teacherLoginMail=findViewById(R.id.teacherLoginMail);
        teacherLoginPassword=findViewById(R.id.teacherLoginPassword);
        teacherLoginEnrollment=findViewById(R.id.teacherLoginEnrollment);
        teacherLoginButton=findViewById(R.id.teacherLoginButton);
        pd=new ProgressDialog(this);
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),TeacherHomeActivity.class));
            finish();
        }
        firebaseAuth=FirebaseAuth.getInstance();
        teacherLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=teacherLoginMail.getText().toString();
                String password=teacherLoginPassword.getText().toString();
                String enrollment=teacherLoginEnrollment.getText().toString();

                loginTeacher(mail,password,enrollment);
            }
        });

    }

    private void loginTeacher(final String mail, String password, final String enrollment) {
        pd.setMessage("Please wait");
        pd.show();
        firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    pd.dismiss();
                    Intent intent=new Intent(getApplicationContext(),TeacherHomeActivity.class);
                    getSharedPreferences("Teacher Name",MODE_PRIVATE).edit().putString("enrollment",enrollment).apply();
                    getSharedPreferences("Teacher Email",MODE_PRIVATE).edit().putString("email",mail).apply();
                    startActivity(intent);

                    Toast.makeText(TeacherLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    pd.dismiss();
                    Toast.makeText(TeacherLoginActivity.this, "Something went wrrong", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}