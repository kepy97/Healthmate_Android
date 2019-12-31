package Utility;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.util.Calendar;

import Interface.onDateSet;

/**
 * Created by sc-147 on 2018-03-16.
 */

public class Dialogs implements DatePickerDialog.OnDateSetListener {
    private int startYear = Calendar.getInstance().get(Calendar.YEAR);
    private int starthMonth = Calendar.getInstance().get(Calendar.MONTH);
    private int startDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    private onDateSet onDateSet;

    public Dialogs(Interface.onDateSet onDateSet) {
        this.onDateSet = onDateSet;
    }

    public void OpenDatePicker(Context c) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(c, Dialogs.this, startYear, starthMonth, startDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        onDateSet.onDateSet(dayOfMonth, month, year);
    }
}