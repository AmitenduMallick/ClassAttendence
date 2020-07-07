package com.example.classattendence.ui.subjectlist;

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

public class SubjectListFragment extends Fragment {

    ListView listView;
    List<String> list;
    ArrayAdapter<String> arrayAdapter;
    ProgressDialog pd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_subject_list, container, false);

        listView=view.findViewById(R.id.listViewSubjects);
        pd=new ProgressDialog(getContext());
        list=new ArrayList<>();
        arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,list);
        listView.setAdapter(arrayAdapter);
        getSubjectList();
        return view;
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