package com.example.classattendence.ui.dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.classattendence.AddSubjectActivity;
import com.example.classattendence.Admin;
import com.example.classattendence.R;
import com.example.classattendence.SearchTeacherActivity;
import com.example.classattendence.StudentRegisterActivity;
import com.example.classattendence.TeacherRegisterActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardFragment extends Fragment {

    private DashboardViewModel homeViewModel;
    TextView dashboardWelcome;
    Button dashboardAddTeacher;
    Button dashboardAddStudent;
    Button dashboardAddSubject;
    Button dashboardPersonalInfo;
    Button dashboardSearchTeacher;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        dashboardWelcome=view.findViewById(R.id.dashboardWelcome);
        dashboardAddTeacher=view.findViewById(R.id.addTeacherDashboard);
        dashboardAddStudent=view.findViewById(R.id.addStudentDashboard);
        dashboardAddSubject=view.findViewById(R.id.addSubjectDashboard);
        dashboardPersonalInfo=view.findViewById(R.id.personalInfoDashboard);
        dashboardSearchTeacher=view.findViewById(R.id.searchDashboard);
        String previlage=getContext().getSharedPreferences("Designation",Context.MODE_PRIVATE).getString("desig_name","none");
        dashboardWelcome.setText("Welcome "+previlage);
        dashboardAddTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), TeacherRegisterActivity.class));
            }
        });
        dashboardSearchTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), SearchTeacherActivity.class));
            }
        });
        dashboardAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), AddSubjectActivity.class));
            }
        });
        dashboardAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContext().startActivity(new Intent(getContext(), StudentRegisterActivity.class));
            }
        });




        return view;
    }
}