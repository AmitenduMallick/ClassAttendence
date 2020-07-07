package com.example.classattendence;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChooseDateActivity extends AppCompatActivity {

    TextView attendenceDate;
    Button dateSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_date);

        attendenceDate=findViewById(R.id.attendenceDate);
        dateSubmitButton=findViewById(R.id.dateSubmitButton);
        dateSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSharedPreferences("Date",MODE_PRIVATE).edit().putString("dateChosen",attendenceDate.getText().toString()).apply();
                startActivity(new Intent(getApplicationContext(),StudentAttendenceListActivity.class));
                finish();
            }
        });

    }
}