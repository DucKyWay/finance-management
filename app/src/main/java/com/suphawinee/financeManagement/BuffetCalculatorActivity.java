package com.suphawinee.financeManagement;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class BuffetCalculatorActivity extends AppCompatActivity {

    // UI
    private EditText pricePerUnit;
    private EditText drinkPricePerUnit;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch isTax;
    private EditText serChargeInput;
    private EditText tipsInput;
    private EditText headCountInput;

    private RadioGroup saleRadio;
    private EditText saleInput;

    // CONSTS
    private static final double DEFAULT_TAX_RATE = 0.07;
    private static final DecimalFormat MONEY_2DP = new DecimalFormat("#,##0.00");

    // STATE
    private double totalPrice = 0.0;
    private double totalPricePerUnit = 0.0;

    private final View.OnClickListener calculateListener = view -> {
        // read, default
        Double price = readDouble(pricePerUnit, null);
        Integer headCount = readInt(headCountInput, null);

        if (price == null || headCount == null || headCount <= 0) {
            showWarning("คำเตือน", "กรุณากรอกข้อมูล ค่าอาหาร และ จำนวนคน > 0");
            return;
        }

        double drinker = readDouble(drinkPricePerUnit, 0.0);
        int serviceChargePercent = readInt(serChargeInput, 0);
        int tipsPercent = readInt(tipsInput, 0);
        double saleValue = readDouble(saleInput, 0.0);

        boolean isTaxChecked = isTax.isChecked();
        double taxRate = isTaxChecked ? DEFAULT_TAX_RATE : 0.0;

        // base cal
        double basePerHead = (price + drinker);
        double rateSum = 1
                + taxRate
                + (serviceChargePercent / 100.0)
                + (tipsPercent / 100.0);

        double subtotalPerHead = basePerHead * rateSum;
        double subtotal = subtotalPerHead * headCount;

        // coupon discount
        int checkedId = saleRadio.getCheckedRadioButtonId();
        double discountedTotal = subtotal;

        if (checkedId == R.id.radioSalePrice) {
            // total on amount
            discountedTotal = Math.max(0.0, subtotal - saleValue);
        } else if (checkedId == R.id.radioSalePercent) {
            // total on percent
            double salePercent = Math.max(0.0, saleValue) / 100.0;
            salePercent = Math.min(salePercent, 1.0);
            discountedTotal = subtotal * (1 - salePercent);
        } else {
            // not discount
        }

        // update
        totalPrice = discountedTotal;
        totalPricePerUnit = headCount > 0 ? (discountedTotal / headCount) : 0.0;

        showPricePopup(
                headCount,
                totalPricePerUnit,
                totalPrice
        );
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buffet_calculator);

        // Bind UI
        pricePerUnit = findViewById(R.id.inputBcPricePerUnit);
        drinkPricePerUnit = findViewById(R.id.inputBcDrinkerPrice);
        isTax = findViewById(R.id.switchBcTax);
        serChargeInput = findViewById(R.id.inputBcServiceCharge);
        tipsInput = findViewById(R.id.inputBcTips);
        headCountInput = findViewById(R.id.inputBcHeadCount);
        saleInput = findViewById(R.id.inputBcSale);
        saleRadio = findViewById(R.id.radioSale);

        Button calculator = findViewById(R.id.buttonBcCalculator);
        calculator.setOnClickListener(calculateListener);
    }

    // Helper

    private Double readDouble(EditText et, Double fallbackIfEmpty) {
        String s = et == null ? null : et.getText().toString().trim();
        if (TextUtils.isEmpty(s)) {
            return fallbackIfEmpty;
        }
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return fallbackIfEmpty;
        }
    }

    private Integer readInt(EditText et, Integer fallbackIfEmpty) {
        String s = et == null ? null : et.getText().toString().trim();
        if (TextUtils.isEmpty(s)) {
            return fallbackIfEmpty;
        }
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return fallbackIfEmpty;
        }
    }

    @SuppressLint("SetTextI18n")
    private void showPricePopup(int headcount, double perHead, double total) {
        if (isFinishing()) return;

        View customLayout = getLayoutInflater().inflate(R.layout.custom_popup_layout, null);
        TextView titleTextView = customLayout.findViewById(R.id.titleText);
        TextView messageTextView = customLayout.findViewById(R.id.messageText);

        titleTextView.setText("ค่าอาหารมื้อนี้");
        messageTextView.setText(
                "ราคาต่อคน: " + MONEY_2DP.format(perHead) + " บาท\n" +
                        "จำนวนคนในกลุ่ม: " + headcount + " คน\n" +
                        "ราคารวมทั้งหมด: " + MONEY_2DP.format(total) + " บาท"
        );

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(customLayout)
                .setPositiveButton("คำนวณใหม่", (d, which) -> d.dismiss())
                .create();
        dialog.show();
    }

    private void showWarning(String title, String message) {
        if (isFinishing()) return;

        View customLayout = getLayoutInflater().inflate(R.layout.custom_popup_layout, null);
        TextView titleTextView = customLayout.findViewById(R.id.titleText);
        TextView messageTextView = customLayout.findViewById(R.id.messageText);

        titleTextView.setText(title);
        messageTextView.setText(message);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(customLayout)
                .setPositiveButton("OK", (d, w) -> d.dismiss())
                .create();
        dialog.show();
    }
}
