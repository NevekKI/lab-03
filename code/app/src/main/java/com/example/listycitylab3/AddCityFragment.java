package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    interface AddCityDialogListener {
        void addCity(City city);
        void updateCity(int position, String newName, String newProvince);
    }

    private AddCityDialogListener listener;
    private City editingCity = null;
    private int editingPosition = -1;

    public AddCityFragment() {

    }

    public AddCityFragment(City cityToEdit, int position){
        this.editingCity = cityToEdit;
        this.editingPosition = position;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof AddCityDialogListener){
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener()");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);

        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        boolean isEdit = (editingCity != null && editingPosition >= 0);
        if (isEdit){
            editCityName.setText(editingCity.getName());
            editProvinceName.setText(editingCity.getProvince());
        }

        String dialogTitle = isEdit ? "Edit City" : "Add a City";
        String positiveText = isEdit ? "Save" : "Add";

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle(dialogTitle)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(positiveText, (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();

                    if (isEdit){
                        listener.updateCity(editingPosition, cityName, provinceName);
                    } else {
                        listener.addCity(new City(cityName, provinceName));
                    }
                })
                .create();
    }





}
