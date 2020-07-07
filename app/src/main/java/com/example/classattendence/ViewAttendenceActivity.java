package com.example.classattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ViewAttendenceActivity extends AppCompatActivity {


    long days;
    List<String> list;
    int no=0;
    long percentage;
    TextView attendenceViewEnrollment;
    TextView attendenceViewPresent;
    TextView attendenceViewTotal;
    TextView studentAttendencePercentage;
    Button attendenceDetails;
    ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendence);
        pd=new ProgressDialog(this);

        attendenceViewEnrollment=findViewById(R.id.attendenceViewEnrollment);
        attendenceViewPresent=findViewById(R.id.attendenceViewPresent);
        attendenceViewTotal=findViewById(R.id.attendenceViewTotal);
        studentAttendencePercentage=findViewById(R.id.studentAttendencePercentage);

        attendenceDetails=findViewById(R.id.attendenceDetails);

        final String enrollment=getIntent().getStringExtra("Enrollment No");
        final String subjectCode=getIntent().getStringExtra("Subject Code");

        list=new ArrayList<>();
        pd.setMessage("Loading data");
        pd.show();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                no=list.size();


                for(String date:list){
                    FirebaseDatabase.getInstance().getReference().child("AttendenceList").child(subjectCode).child(date).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                                if(dataSnapshot.getKey().equals(enrollment)){
                                    days++;
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                //percentage=(days*100)/no;
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            percentage = (days * 100) / no;
                        }catch (Exception e){
                            Toast.makeText(ViewAttendenceActivity.this, "No classes are held yet", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),StudentHomeSubjectList.class));
                            finish();
                        }
                        //Do something after 100ms
                        attendenceDetails.setVisibility(View.VISIBLE);
                        attendenceDetails.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent=new Intent(getApplicationContext(),AttendenceDetailsActivity.class);
                                intent.putExtra("Enrollment No",enrollment);
                                intent.putExtra("Subject Code",subjectCode);
                                startActivity(intent);
                            }
                        });
                        pd.dismiss();
                        attendenceViewEnrollment.setText("Enrollment no: "+enrollment);
                        attendenceViewPresent.setText("Total no of days present: "+days);
                        attendenceViewTotal.setText("Total no of classes: "+no);


                        studentAttendencePercentage.setText("Overall Percentage: "+percentage);
                        Log.i("Days Present",String.valueOf(days));
                    }
                }, 100);



            }
        }, 5000);



        FirebaseDatabase.getInstance().getReference().child("AttendenceList").child(subjectCode).addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        list.add(dataSnapshot.getKey());
                    }
                    Log.i("Dates", "Dates" + list);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


    }

}