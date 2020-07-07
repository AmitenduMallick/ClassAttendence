package com.example.classattendence.ui.teacherlist;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.classattendence.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeacherListFragment extends Fragment {

    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    List<String> list;
    ProgressDialog pd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_teacher_list, container, false);
        listView=view.findViewById(R.id.listViewSubjects);
        list=new ArrayList<>();
        pd=new ProgressDialog(getContext());
        arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);

        getTeacherList();



        return view;
    }

    private void getTeacherList() {
        pd.setMessage("Please wait");
        pd.show();
        FirebaseDatabase.getInstance().getReference().child("Teacher").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                list.clear();

                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Teacher teacher=snapshot.getValue(Teacher.class);

                    list.add(teacher.getName());
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