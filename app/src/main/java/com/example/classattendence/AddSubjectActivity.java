package com.example.classattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddSubjectActivity extends AppCompatActivity {

    EditText subjectCode;
    EditText subjectName;
    Button addSubjectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);

        subjectCode=findViewById(R.id.subjectCode);
        subjectName=findViewById(R.id.subjectName);
        addSubjectButton=findViewById(R.id.addSubjectButton);
        addSubjectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> map=new HashMap<>();
                map.put("SubjectCode",subjectCode.getText().toString());
                map.put("SubjectName",subjectName.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("Subjects").child(subjectCode.getText().toString()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(AddSubjectActivity.this, "Subject added successfully!!", Toast.LENGTH_SHORT).show();
                            subjectCode.setText("");
                            subjectName.setText("");
                        }else{
                            Toast.makeText(AddSubjectActivity.this, "Something went wrong!!", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });
    }
}