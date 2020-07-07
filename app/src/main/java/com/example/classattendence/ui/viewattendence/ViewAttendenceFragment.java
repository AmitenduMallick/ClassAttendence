package com.example.classattendence.ui.viewattendence;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.classattendence.R;
import com.example.classattendence.ViewAttendenceActivity;
import com.example.classattendence.ViewAttendenceCheckActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ViewAttendenceFragment extends Fragment {

    EditText viewAttendenceEnrollmentNo;
    Spinner subjectList;
    Button submit;
    List<String> subjects;
    ArrayAdapter<String> arrayAdapter;
    String subjectCode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_view_attendence, container, false);
        viewAttendenceEnrollmentNo=view.findViewById(R.id.viewAttendenceAdminEnrollmentNo);
        subjectList=view.findViewById(R.id.subjectCodeAttendence);
        submit=view.findViewById(R.id.adminViewAttendenceSubmit);
        subjects=new ArrayList<>();
        arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item,subjects);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        subjectList.setAdapter(arrayAdapter);
        getSubjectList();
        subjectList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                subjectList.setSelection(position);
                subjectCode=subjects.get(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ViewAttendenceActivity.class);
                intent.putExtra("Enrollment No",viewAttendenceEnrollmentNo.getText().toString());
                intent.putExtra("Subject Code",subjectCode);
                startActivity(intent);
            }
        });
        return view;
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