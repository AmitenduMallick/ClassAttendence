package com.example.classattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.classattendence.ui.teacherlist.Teacher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchTeacherActivity extends AppCompatActivity {

    EditText searchTeacher;
    TextView detailsTeacher;
    TextView searchResultName;
    TextView searchResultMail;
    Button searchTeacherButton;
    String searchElement;
    boolean flag=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_teacher);


        searchTeacher=findViewById(R.id.searchTeacher);
        detailsTeacher=findViewById(R.id.detailsTeacher);
        searchResultName=findViewById(R.id.searchResultName);
        searchResultMail=findViewById(R.id.searchResultMail);
        searchElement=searchTeacher.getText().toString();
        searchTeacherButton=findViewById(R.id.searchTeacherButton);
        searchTeacherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FirebaseDatabase.getInstance().getReference().child("Teacher").addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       searchResultName.setVisibility(View.INVISIBLE);
                       searchResultMail.setVisibility(View.INVISIBLE);
                       detailsTeacher.setVisibility(View.INVISIBLE);
                       flag=true;

                       for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                           Teacher teacher=snapshot.getValue(Teacher.class);
                           if(teacher.getEnrollmentNo().equals(searchTeacher.getText().toString())){
                               detailsTeacher.setVisibility(View.VISIBLE);
                               detailsTeacher.setText("Teacher Found");
                               searchResultName.setVisibility(View.VISIBLE);
                               searchResultName.setText(teacher.getName());
                               searchResultMail.setVisibility(View.VISIBLE);
                               searchResultMail.setText(teacher.getMail());
                               flag=false;
                               break;
                           }
                       }
                       if(flag){
                           detailsTeacher.setVisibility(View.VISIBLE);
                           detailsTeacher.setText("Teacher not found!!");
                       }

                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {

                   }
               });
            }
        });
    }
}