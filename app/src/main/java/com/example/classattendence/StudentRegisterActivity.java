package com.example.classattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class StudentRegisterActivity extends AppCompatActivity {

    EditText studentRegisterName;
    EditText studentRegisterMail;
    EditText studentRegisterPassword;
    EditText studentRegisterEnrollment;
    Button studentRegisterButton;
    FirebaseAuth firebaseAuth;
    ProgressDialog pd;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_register);

        studentRegisterName=findViewById(R.id.studentRegisterName);
        studentRegisterMail=findViewById(R.id.studentRegisterMail);
        studentRegisterPassword=findViewById(R.id.studentRegisterPassword);
        studentRegisterEnrollment=findViewById(R.id.studentRegisterEnrollment);
        studentRegisterButton=findViewById(R.id.studentRegisterButton);
        firebaseAuth=FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        studentRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=studentRegisterName.getText().toString();
                String mail=studentRegisterMail.getText().toString();
                String password=studentRegisterPassword.getText().toString();
                String enrollment=studentRegisterEnrollment.getText().toString();
                registerStudent(mail,password,name,enrollment);
            }
        });
    }

    private void registerStudent(final String mail, String password, final String name, final String enrollment) {
        pd.setMessage("Please wait");
        pd.show();
        firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    HashMap<String,Object> map=new HashMap<>();
                    map.put("Name",name);
                    map.put("Mail",mail);
                    map.put("StudentImage","default");
                    map.put("EnrollmentNo",enrollment);
                    FirebaseDatabase.getInstance().getReference().child("Student").child(enrollment).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                pd.dismiss();
                                Toast.makeText(StudentRegisterActivity.this, "Student added to the database", Toast.LENGTH_SHORT).show();
                            }else{
                                pd.dismiss();
                                Toast.makeText(StudentRegisterActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });

    }
}