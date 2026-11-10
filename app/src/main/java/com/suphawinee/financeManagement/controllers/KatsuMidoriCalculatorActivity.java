package com.suphawinee.financeManagement.controllers;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.suphawinee.financeManagement.R;
import com.suphawinee.financeManagement.adapters.PlateAdapter;
import com.suphawinee.financeManagement.models.Plate;
import com.suphawinee.financeManagement.models.Plates;
import com.suphawinee.financeManagement.services.Datasource;
import com.suphawinee.financeManagement.services.PlatesFileHardCodeDatasource;

import java.util.Locale;

public class KatsuMidoriCalculatorActivity extends AppCompatActivity {

    private RecyclerView recyclerPlates;

    private Plates plates;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.katsu_calculator);

        recyclerPlates = findViewById(R.id.recyclerPlates);

        setupPlates();

        Button calculate = findViewById(R.id.calculate);
        calculate.setOnClickListener(v -> calculateListener());
    }

    private void setupPlates() {
        Datasource<Plates> datasource = new PlatesFileHardCodeDatasource();
        plates = datasource.readData();

        recyclerPlates.setLayoutManager(new GridLayoutManager(this, 3));
        PlateAdapter adapter = new PlateAdapter(plates.findAllPlates());
        recyclerPlates.setAdapter(adapter);
    }

    private void calculateListener() {
        showPricePopup(plates.calculateTotal());
    }

    @SuppressLint("SetTextI18n")
    private void showPricePopup(double total) {
        if (isFinishing()) return;

        View customLayout = getLayoutInflater().inflate(R.layout.custom_popup_layout, null);
        TextView titleTextView = customLayout.findViewById(R.id.titleText);
        TextView messageTextView = customLayout.findViewById(R.id.messageText);

        StringBuilder text = new StringBuilder();
        for(Plate p : plates.findAllPlates()) {
            if (p.getQuantity() > 0) {
                text.append(String.format(
                    Locale.getDefault(),
                    "ราคา %3.0f บาท จำนวน %3d จาน (สี %s)\n",
                    p.getPrice(),
                    p.getQuantity(),
                    p.getName()
                ));
            }
        }

        titleTextView.setText("ค่าอาหารมื้อนี้");
        messageTextView.setText(
                text +
                String.format(Locale.getDefault(), "Service Charge (10%%) %.2f บาท\n", plates.calculateServiceCharge()) +
                String.format(Locale.getDefault(), "รวมทั้งหมด %,.2f บาท", total)
        );

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(customLayout)
                .setPositiveButton("คำนวณใหม่", (d, which) -> d.dismiss())
                .create();
        dialog.show();
    }
}
