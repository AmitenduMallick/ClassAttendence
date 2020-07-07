package com.example.classattendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class DesignationActivity extends AppCompatActivity {
    Spinner spinner;
    List<String> desingnationList;
    Button apply;
    int position=0;
    String getDesignation;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designation);
        getDesignation=getSharedPreferences("Designation",MODE_PRIVATE).getString("desig_name","none");
        if(auth.getCurrentUser()!=null){

            if(getDesignation.equals("Admin")){
                startActivity(new Intent(getApplicationContext(),AdminHomeActivity.class));
                finish();
            }
            else if(getDesignation.equals("Teacher")){
                startActivity(new Intent(getApplicationContext(),TeacherHomeActivity.class));
                finish();
            }
            else if(getDesignation.equals("Student")){
                startActivity(new Intent(getApplicationContext(),StudentHomeActivity.class));
                finish();
            }

        }
        spinner=findViewById(R.id.spinner);
        apply=findViewById(R.id.apply);
        desingnationList=new ArrayList<>();
        desingnationList.add("Admin");
        desingnationList.add("Teacher");
        desingnationList.add("Student");
        ArrayAdapter<String> designationAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,desingnationList);
        designationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(designationAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
                spinner.setSelection(i);
                position=i;
                getSharedPreferences("Designation",MODE_PRIVATE).edit().putString("desig_name",desingnationList.get(i)).apply();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(desingnationList.get(position).equals("Admin")){
                    startActivity(new Intent(getApplicationContext(), AdminRegisterActivity.class));
                    finish();
                }
                else if(desingnationList.get(position).equals("Teacher")){

                    startActivity(new Intent(getApplicationContext(),TeacherLoginActivity.class));
                    finish();

                }
                else{

                    startActivity(new Intent(getApplicationContext(),StudentLoginActivity.class));
                    finish();

                }
            }
        });
    }
}