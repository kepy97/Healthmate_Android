package com.example.sc_147.healthmate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import Utility.MySharedPrefs;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Activity_Home extends BaseActivity {
    @BindView(R.id.textView_location)
    TextView textViewLocation;

    String location;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__home);
        ButterKnife.bind(this);
        setLocationText();
    }

    private void setLocationText() {
        location = MySharedPrefs.loadLocation(this);
        textViewLocation.setText(location);
    }

    @OnClick({R.id.imageView_location, R.id.button_book_apointment, R.id.button_my_appointments, R.id.button_view_appointments})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView_location:
                intent = new Intent(this, Activity_SetLocation.class);
                startActivity(intent);
                finish();
                break;
            case R.id.button_book_apointment:
                intent = new Intent(this, Activity_HospitalList.class);
                startActivity(intent);
                break;
            case R.id.button_my_appointments:
                intent = new Intent(this, Activity_MyAppointments.class);
                startActivity(intent);
                break;
            case R.id.button_view_appointments:
                intent = new Intent(this, Activity_ViewAppointments.class);
                startActivity(intent);
                break;
        }
    }
}
