package com.example.sc_147.healthmate;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import Adapter.Adapter_MyAppointments;
import Interface.APIManager;
import Model.GetMyAppointments_ResModel;
import Utility.MySharedPrefs;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_MyAppointments extends AppCompatActivity {

    @BindView(R.id.recyclerView_Myappointments)
    RecyclerView recyclerViewMyappointments;

    ProgressDialog p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);
        ButterKnife.bind(this);
        p = new ProgressDialog(this);
        p.setTitle("Getting My Appointment data");
        p.setMessage("Please wait...");
        p.setCancelable(false);
        p.show();
        getRetrofitArray(MySharedPrefs.loadEmail(this));
    }

    void getRetrofitArray(String email) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MySharedPrefs.loadUrl(this))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIManager service = retrofit.create(APIManager.class);
        Call<List<GetMyAppointments_ResModel>> call = service.getMyAppointmentResult(email);
        call.enqueue(new Callback<List<GetMyAppointments_ResModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<GetMyAppointments_ResModel>> call, @NonNull Response<List<GetMyAppointments_ResModel>> response) {
                if (response.isSuccessful()) {
                    p.dismiss();
                    Log.v("Date retrieve", "Success");
                    recyclerViewMyappointments.setLayoutManager(new LinearLayoutManager(Activity_MyAppointments.this, LinearLayoutManager.VERTICAL, false));
                    recyclerViewMyappointments.setAdapter(new Adapter_MyAppointments(response.body()));
                } else {
                    p.dismiss();
                    Toast.makeText(Activity_MyAppointments.this, "No data to show", Toast.LENGTH_SHORT).show();
                    Log.v("Date retrieve", "Failed");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GetMyAppointments_ResModel>> call, @NonNull Throwable t) {
                Toast.makeText(Activity_MyAppointments.this, "No data to show", Toast.LENGTH_SHORT).show();
                Log.v("Date retrieve", "Failed");
            }
        });
    }
}
