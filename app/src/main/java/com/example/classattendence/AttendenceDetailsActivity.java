package com.example.classattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AttendenceDetailsActivity extends AppCompatActivity {

    ListView attendenceDetailsListView;
    List<String> dates;
    List<String> details;
    ArrayAdapter<String> arrayAdapter;
    int flag=1;
    String subjectCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_details);

        final String enrollment=getIntent().getStringExtra("Enrollment No");
        subjectCode=getIntent().getStringExtra("Subject Code");


        attendenceDetailsListView=findViewById(R.id.attendenceDetailsListView);
        dates=new ArrayList<>();
        details=new ArrayList<>();
        arrayAdapter=new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,details);
        attendenceDetailsListView.setAdapter(arrayAdapter);
        getDates();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms

                for(final String date:dates){


                    FirebaseDatabase.getInstance().getReference().child("AttendenceList").child(subjectCode).child(date).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            flag=1;
                            for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                                if(snapshot.getKey().equals(enrollment)){
                                    details.add(date+"               "+"P");
                                    flag=0;
                                    break;
                                }
                            }
                            if(flag==1){
                                details.add(date+"               "+"A");
                            }

                            arrayAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }


                Log.i("The new Dates are","Dates"+dates);
            }
        }, 100);
    }

    private void getDates() {

        FirebaseDatabase.getInstance().getReference().child("AttendenceList").child(subjectCode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){

                    dates.add(snapshot.getKey());

                }
                Log.i("The dates are","Dates"+dates);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}