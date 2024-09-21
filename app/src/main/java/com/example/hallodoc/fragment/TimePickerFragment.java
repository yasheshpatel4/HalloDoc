package com.example.hallodoc.fragment;

import static android.app.PendingIntent.getActivity;
import static java.util.Calendar.getInstance;

import android.annotation.SuppressLint;
import android.app.Dialog;
import androidx.fragment.app.DialogFragment;

import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.fragment.app.FragmentManager;

import java.util.Calendar;

@SuppressLint("ValidFragment")
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    private EditText t;
    public TimePickerFragment(EditText tt){
        this.t=tt;
    }
    @Override
    public Dialog onCreateDialog (Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);

        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
    @Override
    public void onTimeSet(android.widget.TimePicker timePicker, int i, int i1) {
        t.setText(i+":"+i1);
    }
}
