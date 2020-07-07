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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentLoginActivity extends AppCompatActivity {

    EditText studentLoginMail;
    EditText studentLoginPassword;
    EditText studentLoginEnrollmentNo;
    Button studentLoginSubmit;
    FirebaseAuth firebaseAuth;
    ProgressDialog pd;
    FirebaseAuth auth=FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        studentLoginMail=findViewById(R.id.studentLoginMail);
        studentLoginPassword=findViewById(R.id.studentLoginPassword);
        studentLoginSubmit=findViewById(R.id.studentLoginSubmit);
        studentLoginEnrollmentNo=findViewById(R.id.studentLoginEnrollmentNo);
        firebaseAuth=FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),StudentHomeActivity.class));
            finish();
        }

        studentLoginSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=studentLoginMail.getText().toString();
                String password=studentLoginPassword.getText().toString();
                String enrollment=studentLoginEnrollmentNo.getText().toString();
                getSharedPreferences("Enrollment",MODE_PRIVATE).edit().putString("enrollmentno",enrollment).apply();
                getSharedPreferences("Student Email",MODE_PRIVATE).edit().putString("email",mail).apply();
                checkValidity(mail,enrollment,password);
            }
        });
    }

    private void checkValidity(final String mail, String enrollment, final String password) {
        pd.setMessage("Please wait");
        pd.show();

        FirebaseDatabase.getInstance().getReference().child("Student").child(enrollment).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StudentModel studentModel=snapshot.getValue(StudentModel.class);
                try {
                    if (studentModel.getMail().equals(mail)) {
                        loginStudent(mail, password);
                    } else {
                        pd.dismiss();
                        Toast.makeText(StudentLoginActivity.this, "Your enrollment no is invalid!!", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    pd.dismiss();
                    Toast.makeText(StudentLoginActivity.this, "Your enrollment no is invalid!!", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loginStudent(String mail, String password) {


        firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    pd.dismiss();
                    startActivity(new Intent(getApplicationContext(),StudentHomeActivity.class));
                    Toast.makeText(StudentLoginActivity.this, "Login successfull", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    pd.dismiss();
                    Toast.makeText(StudentLoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}