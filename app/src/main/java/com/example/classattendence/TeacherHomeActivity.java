package com.example.classattendence;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class TeacherHomeActivity extends AppCompatActivity {

    Button takeAttendenceButton;
    Button teacherViewAttendenceButton;
    Button teacherProfileButton;
    Button teacherResetPasword;
    Button teacherLogout;
    TextView teacherHomeText;
    FirebaseAuth auth=FirebaseAuth.getInstance();


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.teacher_home,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.logoutTeacher:
                auth.signOut();
                startActivity(new Intent(getApplicationContext(),DesignationActivity.class));
                finish();
        }
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
        final String text=getSharedPreferences("Teacher Email",MODE_PRIVATE).getString("email","none");
        final String enrollment=getSharedPreferences("Teacher Name",MODE_PRIVATE).getString("enrollment","none");
        takeAttendenceButton=findViewById(R.id.takeAttendenceButton);

        teacherViewAttendenceButton=findViewById(R.id.teacherViewAttendenceButton);
        teacherProfileButton=findViewById(R.id.teacherProfileButton);
        teacherHomeText=findViewById(R.id.teacherHomeText);
        teacherHomeText.setText("WELCOME TO THE TEACHER SECTION");

        teacherResetPasword=findViewById(R.id.teacherResetPassword);
        teacherLogout=findViewById(R.id.teacherLogout);
        teacherLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(getApplicationContext(),DesignationActivity.class));
                finish();
            }
        });
        takeAttendenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),SubjectChooseActivity.class));
            }
        });
        teacherViewAttendenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ViewAttendenceCheckActivity.class));
            }
        });
        teacherProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),TeacherProfileActivity.class);
                intent.putExtra("Profile Enrollment",enrollment);
                startActivity(intent);
            }
        });
        teacherResetPasword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final TextView resetMail=new TextView(v.getContext());
                AlertDialog.Builder passwordReset=new AlertDialog.Builder(v.getContext());
                passwordReset.setTitle("Reset Password");
                passwordReset.setMessage("You will rececive your reset link in this email?Do you want to continue..");
                resetMail.setText(text);
                resetMail.setTextSize(18);
                passwordReset.setView(resetMail);

                passwordReset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String mail=resetMail.getText().toString();
                        auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(TeacherHomeActivity.this, "Reset password sent to your registered email", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(TeacherHomeActivity.this, "Error, link not sent"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                passwordReset.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordReset.create().show();
            }
        });



    }



}