package com.example.classattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewAttendenceCheckActivity extends AppCompatActivity {

    EditText attendenceCheckEnrollment;
    Spinner attendenceCheckSubjectCode;
    Button checkAttendenceSubmit;
    List<String> subjects;
    ArrayAdapter<String> arrayAdapter;
    String subjectcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendence_check);
        attendenceCheckEnrollment=findViewById(R.id.attendenceCheckEnrollment);
        attendenceCheckSubjectCode=findViewById(R.id.attendenceCheckSubjectCode);
        checkAttendenceSubmit=findViewById(R.id.checkAttendenceSubmit);
        subjects=new ArrayList<>();
        arrayAdapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_item,subjects);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        attendenceCheckSubjectCode.setAdapter(arrayAdapter);
        getSubjectList();
        attendenceCheckSubjectCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                attendenceCheckSubjectCode.setSelection(position);
                subjectcode=subjects.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        checkAttendenceSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ViewAttendenceActivity.class);
                intent.putExtra("Enrollment No",attendenceCheckEnrollment.getText().toString());
                intent.putExtra("Subject Code",subjectcode);
                startActivity(intent);
            }
        });
    }

    private void getSubjectList() {

        FirebaseDatabase.getInstance().getReference().child("Subjects").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    subjects.add(snapshot.getKey());
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}