package com.example.sc_147.healthmate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Interface.APIManager;
import Interface.onDateSet;
import Model.BookAppointment_ResModel;
import Model.GetTimeSlot_ResModel;
import Utility.Const;
import Utility.Dialogs;
import Utility.MySharedPrefs;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static Utility.Const.INTENT_KEY_DOCTORNAME;
import static Utility.Const.INTENT_KEY_HOSPITAL;

public class Activity_BookAppointment extends BaseActivity implements onDateSet, AdapterView.OnItemSelectedListener {

    @BindView(R.id.button_OpenDatePicker)
    Button buttonOpenDatePicker;

    String doctorname = "";
    String hospital = "";
    String date = "";
    String month = "";
    String year = "";
    String time = "";

    boolean time_selected;
    boolean date_selected;

    ProgressDialog p;

    ArrayAdapter<String> timeSlotAdapter;
    @BindView(R.id.spinner_Timeslot)
    NiceSpinner spinnerTimeslot;
    @BindView(R.id.textView_doctorname)
    TextView textViewDoctorname;
    @BindView(R.id.textView_Hospitalname)
    TextView textViewHospitalname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_apointment);
        ButterKnife.bind(this);
        getIntentData();
        init();
    }

    private void init() {
        setTimeSlotSpinner(Arrays.asList(getResources().getStringArray(R.array.time_slot)));
        spinnerTimeslot.setOnItemSelectedListener(this);
    }

    private void getIntentData() {
        if (getIntent() != null) {
            doctorname = getIntent().getStringExtra(INTENT_KEY_DOCTORNAME);
            List<String> drNm = Arrays.asList(doctorname.split("\\s"));
            doctorname = drNm.get(0) + " " + drNm.get(1);
            hospital = getIntent().getStringExtra(INTENT_KEY_HOSPITAL);
            textViewDoctorname.setText("Doctor name : " + doctorname);
            textViewHospitalname.setText("Hospital name : " + hospital);
        }
    }

    private void OpenDatePicker() {
        Dialogs d = new Dialogs(this);
        d.OpenDatePicker(this);
    }

    private void setTimeSlotSpinner(List<String> time) {
        timeSlotAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, time);
        spinnerTimeslot.setAdapter(timeSlotAdapter);
        spinnerTimeslot.setSelectedIndex(0);
    }

    void getRetrofitArray(String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MySharedPrefs.loadUrl(this))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIManager service = retrofit.create(APIManager.class);
        Call<List<BookAppointment_ResModel>> call = service.getBookAppointmentResult(email, date, doctorname, time, hospital);
        call.enqueue(new Callback<List<BookAppointment_ResModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<BookAppointment_ResModel>> call, @NonNull Response<List<BookAppointment_ResModel>> response) {
                if (response.isSuccessful()) {
                    p.dismiss();
                    if (response.body().get(0).getResult().equals(Const.SUCCESS)) {
                        Toast.makeText(Activity_BookAppointment.this, response.body().get(0).getResult(), Toast.LENGTH_SHORT).show();
                        new Intent(Activity_BookAppointment.this,Activity_Home.class);
                        finish();
                    } else if (response.body().get(0).getResult().equals(Const.FAILED)) {
                        Toast.makeText(Activity_BookAppointment.this, response.body().get(0).getResult(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Activity_BookAppointment.this, response.body().get(0).getResult(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    p.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<BookAppointment_ResModel>> call, @NonNull Throwable t) {
                p.dismiss();
                Toast.makeText(Activity_BookAppointment.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getTimeslots(String date, String doctorname) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MySharedPrefs.loadUrl(this))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIManager service = retrofit.create(APIManager.class);

        Call<GetTimeSlot_ResModel> call = service.getAppointmentTimeResult(date, doctorname);

        call.enqueue(new Callback<GetTimeSlot_ResModel>() {
            @Override
            public void onResponse(@NonNull Call<GetTimeSlot_ResModel> call, @NonNull Response<GetTimeSlot_ResModel> response) {
                p.dismiss();
                if (response.isSuccessful()) {
                    List<String> times = new ArrayList<>();
                    times.add(0, "Select time*");
                    times.addAll(response.body().getTime());
                    setTimeSlotSpinner(times);
                }
            }

            @Override
            public void onFailure(@NonNull Call<GetTimeSlot_ResModel> call, @NonNull Throwable t) {
                p.dismiss();
            }
        });
    }

    @Override
    public void onDateSet(int dd, int mm, int yy) {
        year = String.valueOf(yy);
        if (dd < 10) {
            date = "0" + dd;
            if (mm < 10) {
                month = String.valueOf(mm + 1);
                month = "0" + month;
                date = date + "/" + month + "/" + year;
            } else {
                month = String.valueOf(mm + 1);
                date = date + "/" + month + "/" + year;
            }
        } else {
            date = String.valueOf(dd);
            if (mm < 10) {
                month = String.valueOf(mm + 1);
                month = "0" + month;
                date = date + "/" + month + "/" + year;
            } else {
                month = String.valueOf(mm + 1);
                date = date + "/" + month + "/" + year;
            }
        }
        date_selected = true;
        buttonOpenDatePicker.setText(date);
        p = new ProgressDialog(this);
        p.setTitle("Time slot");
        p.setMessage("Please wait...");
        p.setCancelable(false);
        p.show();
        getTimeslots(date, doctorname);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (position > 0) {
            time_selected = true;
            time = timeSlotAdapter.getItem(position);
        } else {
            time_selected = false;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        time_selected = false;
    }

    @OnClick({R.id.button_OpenDatePicker, R.id.button_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_OpenDatePicker:
                OpenDatePicker();
                break;
            case R.id.button_submit:
                if (date_selected) {
                    if (time_selected) {
                        p = new ProgressDialog(this);
                        p.setTitle("Booking appointment");
                        p.setMessage("Please wait...");
                        p.setCancelable(false);
                        p.show();
                        getRetrofitArray(MySharedPrefs.loadEmail(this));
                    } else {
                        Toast.makeText(this, "Select time", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Select date", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
