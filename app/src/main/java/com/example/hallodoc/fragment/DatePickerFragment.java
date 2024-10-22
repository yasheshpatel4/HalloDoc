package com.example.hallodoc.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import androidx.fragment.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    EditText tt;
    public DatePickerFragment(EditText t){
        this.tt=t;
    }
    @Override

    public Dialog onCreateDialog (Bundle savedInstanceState) {

        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(requireContext(), this, year,month,day);
    }

    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        tt.setText(i2+"-"+i1+"-"+i);
    }
}
