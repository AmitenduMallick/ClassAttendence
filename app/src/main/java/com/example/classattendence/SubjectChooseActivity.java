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

public class SubjectChooseActivity extends AppCompatActivity {
    ListView listView;
    List<String> list;
    ArrayAdapter<String> arrayAdapter;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_choose);
        pd=new ProgressDialog(this);
        listView=findViewById(R.id.subjectChoseListView);
        list=new ArrayList<>();
        arrayAdapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
        getSubjectList();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                getSharedPreferences("Subject",MODE_PRIVATE).edit().putString("chosenSubject",list.get(position)).apply();
                startActivity(new Intent(getApplicationContext(),ChooseDateActivity.class));
            }
        });
    }

    private void getSubjectList() {
        pd.setMessage("Please wait");
        pd.show();
        FirebaseDatabase.getInstance().getReference().child("Subjects").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
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