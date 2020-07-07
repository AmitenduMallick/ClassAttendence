package com.example.classattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.classattendence.ui.subjectlist.SubjectModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentHomeSubjectList extends AppCompatActivity {

    ListView listView;
    List<String> list;
    ArrayAdapter<String> arrayAdapter;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home_subject_list);
        pd=new ProgressDialog(this);
        listView=findViewById(R.id.studentSubjectList);
        list=new ArrayList<>();
        arrayAdapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
        getSubjectList();
        final String enrollment=getSharedPreferences("Enrollment",MODE_PRIVATE).getString("enrollmentno","none");
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),ViewAttendenceActivity.class);
                intent.putExtra("Enrollment No",enrollment);
                intent.putExtra("Subject Code",list.get(position));
                startActivity(intent);

            }
        });
    }

    private void getSubjectList() {

        pd.setMessage("Loading Subject List");
        pd.show();
        FirebaseDatabase.getInstance().getReference().child("Subjects").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    SubjectModel subjectModel=snapshot.getValue(SubjectModel.class);
                    list.add(subjectModel.getSubjectCode());
                }
                arrayAdapter.notifyDataSetChanged();
                pd.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}