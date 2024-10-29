package com.example.frame;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;



public class SeeAlarmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seealarm);

        ImageButton nav_home = findViewById(R.id.nav_home);
        Button PlusAlarm = findViewById(R.id.PlusAlarm);


        nav_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeAlarmActivity.this,HomeActivity.class);
                startActivity(intent);
            }
        });



        PlusAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SeeAlarmActivity.this,AlarmSetActivity.class);
                startActivity(intent);
            }
        });

    }
}