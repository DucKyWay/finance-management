
package com.suphawinee.financeManagement.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suphawinee.financeManagement.R;
import com.suphawinee.financeManagement.models.Plate;

import java.util.List;
import java.util.Locale;

public class PlateAdapter extends RecyclerView.Adapter<PlateAdapter.PlateViewHolder> {

    private final List<Plate> plates;

    public PlateAdapter(List<Plate> plates) {
        this.plates = plates;
    }

    @NonNull
    @Override
    public PlateViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.katsu_item_layout, parent, false);

        return new PlateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull PlateViewHolder holder, int position) {

        holder.bind(plates.get(position));
    }

    @Override
    public int getItemCount() {
        return plates.size();
    }

    static class PlateViewHolder extends RecyclerView.ViewHolder {

        private final ImageButton imagePlateButton;
        private final TextView textPrice;
        private final EditText editTextQuantity;
        private final Button buttonPlus;
        private final Button buttonMinus;

        private TextWatcher watcher;

        PlateViewHolder(View itemView) {
            super(itemView);

            imagePlateButton = itemView.findViewById(R.id.imagePlateButton);
            textPrice = itemView.findViewById(R.id.textPrice);
            editTextQuantity = itemView.findViewById(R.id.editTextQuantity);
            buttonPlus = itemView.findViewById(R.id.buttonPlus);
            buttonMinus = itemView.findViewById(R.id.buttonMinus);
        }

        void bind(Plate plate) {

            // Show price
            textPrice.setText(String.format(Locale.getDefault(),
                    "%.0f บาท",
                    plate.getPrice()
            ));

            // Show plate image
            int imageId = itemView.getResources().getIdentifier(
                    plate.getFileName(),
                    "drawable",
                    itemView.getContext().getPackageName()
            );

            imagePlateButton.setImageResource(imageId);

            // Remove old watcher
            if (watcher != null) {
                editTextQuantity.removeTextChangedListener(watcher);
            }

            // Show current quantity
            editTextQuantity.setText(
                    String.valueOf(plate.getQuantity())
            );

            // Update Model immediately when typing
            watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(
                        CharSequence s,
                        int start,
                        int count,
                        int after) {}

                @Override
                public void onTextChanged(
                        CharSequence s,
                        int start,
                        int before,
                        int count) {

                    try {
                        int quantity;
                        if(s.length() == 0) {
                            quantity = 0;
                        } else {
                            quantity = Integer.parseInt(s.toString());
                        }

                        plate.setQuantity(quantity);

                    } catch (NumberFormatException e) {
                        plate.setQuantity(0);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            };

            editTextQuantity.addTextChangedListener(watcher);

            buttonPlus.setOnClickListener(v -> {
                plate.setQuantity(readQuantity());
                plate.addQuantity();
                showQuantity(plate);
            });

            buttonMinus.setOnClickListener(v -> {
                plate.setQuantity(readQuantity());
                plate.desQuantity();
                showQuantity(plate);
            });

            imagePlateButton.setOnClickListener(v -> {
                plate.setQuantity(readQuantity());
                plate.addQuantity();
                showQuantity(plate);
            });
        }

        int readQuantity() {
            try {
                return Integer.parseInt(editTextQuantity.getText().toString());
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        void showQuantity(Plate plate) {
            editTextQuantity.setText(
                    String.valueOf(plate.getQuantity())
            );
        }
    }
}
