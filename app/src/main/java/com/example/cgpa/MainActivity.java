package com.example.cgpa;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button calculate_sgpa,calculate_cgpa;
    @SuppressLint("MissingInflatedId")//supress android:id attribute
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calculate_sgpa=findViewById(R.id.sgpa);
        calculate_cgpa=findViewById(R.id.cgpa);

        calculate_sgpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,sgpaCalculation.class);
                startActivityForResult(i,1);
            }
        });

        calculate_cgpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,cgpaCalculation.class);
                startActivityForResult(i,2);
            }
        });
    }
}
