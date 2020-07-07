package com.example.classattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GiveAttendenceActivity extends AppCompatActivity {

    TextView giveAttendenceName;
    TextView giveAttendenceEnrollment;
    RadioGroup radioGroup;
    Button attendenceSubmit;
    String enrollment;
    HashMap<String,Object> map;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_attendence);
        map=new HashMap<>();
        list=new ArrayList<>();

        radioGroup=findViewById(R.id.radioGroup);
        attendenceSubmit=findViewById(R.id.attendenceSubmit);



        giveAttendenceEnrollment=findViewById(R.id.giveAttendenceEnrollment);
        giveAttendenceName=findViewById(R.id.giveAttendenceName);
        enrollment=getIntent().getStringExtra("Enrollment Number");
        giveAttendenceEnrollment.setText("Enrollment Number: "+enrollment);
        getStudentName(enrollment);
        attendenceSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedId=radioGroup.getCheckedRadioButtonId();
                if(checkedId==-1){
                    Toast.makeText(GiveAttendenceActivity.this, "Please select either Present or absent!!", Toast.LENGTH_SHORT).show();

                }
                else{
                    findRadioButton(checkedId);
                }
            }
        });

    }

    private void findRadioButton(int checkedId) {

        switch (checkedId){
            case R.id.present:
                addToMap();

                break;
            case R.id.absent:
                Toast.makeText(this, "Attendence absent selected", Toast.LENGTH_SHORT).show();
        }
    }



    private void addToMap() {
        map.put("EnrollmentNo",enrollment);
        addtoDatabase();



    }

    private void addtoDatabase() {


        String subject=getSharedPreferences("Subject",MODE_PRIVATE).getString("chosenSubject","none");
        String date=getSharedPreferences("Date",MODE_PRIVATE).getString("dateChosen","none");


        FirebaseDatabase.getInstance().getReference().child("AttendenceList").child(subject).child(date).child(enrollment).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(GiveAttendenceActivity.this, "Attendence marked present", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getStudentName(final String enrollment){

        FirebaseDatabase.getInstance().getReference().child("Student").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    StudentModel studentModel=snapshot.getValue(StudentModel.class);

                    if(studentModel.getEnrollmentNo().equals(enrollment)){
                        giveAttendenceName.setText("Student Name: "+studentModel.getName());
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}