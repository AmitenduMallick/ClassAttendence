package com.example.classattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.classattendence.ui.teacherlist.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TeacherProfileActivity extends AppCompatActivity {

    TextView teacherProfileName;
    TextView teacherProfileEmail;
    TextView teacherProfileEnrollment;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        pd=new ProgressDialog(this);

        teacherProfileEmail=findViewById(R.id.teacherProfileEmail);
        teacherProfileName=findViewById(R.id.teacherProfileName);
        teacherProfileEnrollment=findViewById(R.id.teacherProfileEnrollment);
        String enrollment=getIntent().getStringExtra("Profile Enrollment");
        getDetails(enrollment);
    }

    private void getDetails(String enrollment){
        pd.setMessage("Loading details");
        pd.show();
        FirebaseDatabase.getInstance().getReference().child("Teacher").child(enrollment).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Teacher teacher=snapshot.getValue(Teacher.class);
                teacherProfileEmail.setText("EMAIL: "+teacher.getMail());
                teacherProfileName.setText("NAME: "+teacher.getName());
                teacherProfileEnrollment.setText("ENROLLMENT NUMBER: "+teacher.getEnrollmentNo());
                pd.dismiss();
                Log.i("ID",snapshot.getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}