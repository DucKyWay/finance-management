package com.suphawinee.financeManagement.controllers;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.suphawinee.financeManagement.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button BuffetCalculatorButton = findViewById(R.id.buttonBuffetCalculator);
        BuffetCalculatorButton.setOnClickListener(view -> {
            Intent BuffetCalculatorPage = new Intent(MainActivity.this, BuffetCalculatorActivity.class);
            startActivity(BuffetCalculatorPage);
        });

        Button KatsuMidoriCalculatorButton = findViewById(R.id.buttonKatsuMidoriCalculator);
        KatsuMidoriCalculatorButton.setOnClickListener(view -> {
            Intent KatsuMidoriCalculatorPage = new Intent(MainActivity.this, KatsuMidoriCalculatorActivity.class);
            startActivity(KatsuMidoriCalculatorPage);
        });
    }
}