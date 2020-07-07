package com.example.classattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfileActivity extends AppCompatActivity {

    TextView studentProfileName;
    TextView studentProfileEmail;
    TextView studentProfileEnrollment;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);
        pd=new ProgressDialog(this);
        String enrollment=getSharedPreferences("Enrollment",MODE_PRIVATE).getString("enrollmentno","none");
        studentProfileName=findViewById(R.id.studentProfileName);
        studentProfileEmail=findViewById(R.id.studentProfileEmail);
        studentProfileEnrollment=findViewById(R.id.studentProfileEnrollment);
        getDetails(enrollment);
    }
    private void getDetails(String enrollment){
        pd.setMessage("Loading Details");
        pd.show();
        FirebaseDatabase.getInstance().getReference().child("Student").child(enrollment).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                StudentModel studentModel=snapshot.getValue(StudentModel.class);
                studentProfileName.setText("NAME: "+studentModel.getName());
                studentProfileEmail.setText("EMAIL: "+studentModel.getMail());
                studentProfileEnrollment.setText("ENROLLMENT NO: "+studentModel.getEnrollmentNo());
                pd.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}