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

import Adapter.Adapter_ViewAppointments;
import Interface.APIManager;
import Model.GetViewAppointments_ResModel;
import Utility.MySharedPrefs;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_ViewAppointments extends AppCompatActivity {

    @BindView(R.id.recyclerView_Viewappointments)
    RecyclerView recyclerViewViewappointments;

    ProgressDialog p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointments);
        ButterKnife.bind(this);
        p = new ProgressDialog(this);
        p.setTitle("Getting View Appointment data");
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
        Call<List<GetViewAppointments_ResModel>> call = service.getViewAppointmentResult(email);
        call.enqueue(new Callback<List<GetViewAppointments_ResModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<GetViewAppointments_ResModel>> call, @NonNull Response<List<GetViewAppointments_ResModel>> response) {
                if (response.isSuccessful()) {
                    p.dismiss();
                    Log.v("Date retrieve", "Success");
                    recyclerViewViewappointments.setLayoutManager(new LinearLayoutManager(Activity_ViewAppointments.this, LinearLayoutManager.VERTICAL, false));
                    recyclerViewViewappointments.setAdapter(new Adapter_ViewAppointments(response.body()));
                } else {
                    p.dismiss();
                    Toast.makeText(Activity_ViewAppointments.this, "No data to show", Toast.LENGTH_SHORT).show();
                    Log.v("Date retrieve", "Failed");
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GetViewAppointments_ResModel>> call, @NonNull Throwable t) {
                p.dismiss();
                Toast.makeText(Activity_ViewAppointments.this, "No data to show", Toast.LENGTH_SHORT).show();
                Log.v("Date retrieve", "Failed");
            }
        });
    }
}
