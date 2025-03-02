package com.suphawinee.financeManagement;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BuffetCalculatorActivity extends AppCompatActivity {

    private EditText PricePerUnit;
    private EditText DrinkPricePerUnit;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch isTax;
    private EditText SerCharge;
    private EditText Tips;
    private EditText HeadCount;

    private RadioGroup SaleRadio;
    private EditText Sale;

    private Double TotalPrice;
    private Double TotalPricePerUnit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buffet_calculator);

        // Input Variable
        PricePerUnit = findViewById(R.id.inputBcPricePerUnit);
        DrinkPricePerUnit = findViewById(R.id.inputBcDrinkerPrice);
        isTax = findViewById(R.id.switchBcTax);
        SerCharge = findViewById(R.id.inputBcServiceCharge);
        Tips = findViewById(R.id.inputBcTips);
        HeadCount = findViewById(R.id.inputBcHeadCount);
        Sale = findViewById(R.id.inputBcSale);

        Button Calculator = findViewById(R.id.buttonBcCalculator);
        Calculator.setOnClickListener(view -> {

            // Get Variable String
            String priceText = PricePerUnit.getText().toString();
            String drinkerText = DrinkPricePerUnit.getText().toString();
            String serChargeText = SerCharge.getText().toString();
            String tipsText = Tips.getText().toString();
            String headCountText = HeadCount.getText().toString();
            String saleText = Sale.getText().toString();

            if (priceText.isEmpty() || headCountText.isEmpty()) {
                RequiredFieldsErrorPopup();
                return;
            }

            // Convert Variable
            double price = Double.parseDouble(priceText);
            double drinker = drinkerText.isEmpty() ? 0.00 : Double.parseDouble(drinkerText); // if empty = 0
            boolean isTaxChecked = isTax.isChecked();
            int serviceCharge = serChargeText.isEmpty() ? 0 : Integer.parseInt(serChargeText); // if empty = 0
            int tips = tipsText.isEmpty() ? 0 : Integer.parseInt(tipsText); // if empty = 0
            int headCount = Integer.parseInt(headCountText);
            double sale = saleText.isEmpty() ? 0.00 : Double.parseDouble(saleText); // if empty = 0

            if (saleText.isEmpty()) {
                sale = 0.00;
            }
            double Tax = isTaxChecked ? 0.07 : 0.00;

            if (headCount != 0) {

                TotalPricePerUnit = (price + drinker) * (1 + Tax + (serviceCharge * 0.01) + (tips * 0.01));
                TotalPrice = TotalPricePerUnit * headCount;

                SaleRadio = findViewById(R.id.radioSale);
                int checkedRadioButtonId = SaleRadio.getCheckedRadioButtonId();
                if (checkedRadioButtonId == R.id.radioSalePrice) {

                    TotalPrice -= sale;
                    TotalPricePerUnit = TotalPrice / headCount;
                } else if (checkedRadioButtonId == R.id.radioSalePercent) {

                    double salePercent = sale * 0.01;
                    TotalPricePerUnit = TotalPrice / headCount;
                    TotalPrice *= 1 - salePercent;
                } else {
                    RequiredFieldsErrorPopup();
                }

                showPricePopup(headCount, TotalPricePerUnit, TotalPrice);
            } else {
                RequiredFieldsErrorPopup();
            }
        });
    }

    @SuppressLint("SetTextI18n")

    private void showPricePopup(int headcount, double totalPricePerUnit, double totalPrice) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        @SuppressLint("InflateParams") View customLayout = getLayoutInflater().inflate(R.layout.custom_popup_layout, null);
        builder.setCustomTitle(customLayout);

        TextView titleTextView = customLayout.findViewById(R.id.titleText);
        titleTextView.setText("ค่าอาหารมื้อนี้");
        TextView messageTextView = customLayout.findViewById(R.id.messageText);
        messageTextView.setText("ราคาต่อคน: " + totalPricePerUnit + "\nจำนวนคนในกลุ่ม: " + headcount + "\nราคารวมทั้งหมด: " + totalPrice);

        builder.setPositiveButton("คำนวนใหม่", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void RequiredFieldsErrorPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        @SuppressLint("InflateParams") View customLayout = getLayoutInflater().inflate(R.layout.custom_popup_layout, null);
        builder.setCustomTitle(customLayout);

        TextView titleTextView = customLayout.findViewById(R.id.titleText);
        titleTextView.setText("คำเตือน");
        TextView messageTextView = customLayout.findViewById(R.id.messageText);
        messageTextView.setText("กรุณากรอกข้อมูล ราคาอาหาร หรือ จำนวนคนมากกว่า 0");

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}