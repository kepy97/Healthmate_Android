package com.example.sc_147.healthmate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import Adapter.Adapter_DoctorList;
import Interface.APIManager;
import Interface.onItemClick;
import Model.GetDoctorList_ResModel;
import Utility.MySharedPrefs;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static Utility.Const.INTENT_KEY_DOCTORNAME;
import static Utility.Const.INTENT_KEY_HOSPITAL;

public class Activity_DoctorList extends BaseActivity implements onItemClick {

    @BindView(R.id.recyclerView_DoctorsList)
    RecyclerView recyclerViewDoctorsList;

    String hospital;
    ProgressDialog p;
    List<GetDoctorList_ResModel> arrayDoctorList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__doctor_list);
        ButterKnife.bind(this);
        getIntentData();
    }

    private void getIntentData() {
        if (getIntent() != null) {
            hospital = getIntent().getStringExtra(INTENT_KEY_HOSPITAL);
            p = new ProgressDialog(this);
            p.setTitle("Getting doctors list");
            p.setMessage("Please wait...");
            p.setCancelable(false);
            p.show();
            getRetrofitDoctorNameArray(hospital);
        } else {
            //Do nothing...
        }
    }

    void getRetrofitDoctorNameArray(final String _hospital) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MySharedPrefs.loadUrl(this))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIManager service = retrofit.create(APIManager.class);
        Call<List<GetDoctorList_ResModel>> call = service.getDoctorListResult(_hospital);
        call.enqueue(new Callback<List<GetDoctorList_ResModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<GetDoctorList_ResModel>> call, @NonNull Response<List<GetDoctorList_ResModel>> response) {
                if (response.body() != null) {
                    if (response.body().size() == 0) {
                        p.dismiss();
                    } else {
                        p.dismiss();
                        arrayDoctorList = response.body();
                        recyclerViewDoctorsList.setLayoutManager(new LinearLayoutManager(Activity_DoctorList.this, LinearLayoutManager.VERTICAL, false));
                        recyclerViewDoctorsList.setAdapter(new Adapter_DoctorList(response.body(), Activity_DoctorList.this));
                    }
                } else {
                    p.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GetDoctorList_ResModel>> call, @NonNull Throwable t) {
                p.dismiss();
                Toast.makeText(Activity_DoctorList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void ItemClicked(int position) {
        String doctorName = arrayDoctorList.get(position).getFirstname() + " " + arrayDoctorList.get(position).getLastname();
        startActivity(new Intent(this, Activity_BookAppointment.class)
                .putExtra(INTENT_KEY_DOCTORNAME, doctorName)
                .putExtra(INTENT_KEY_HOSPITAL,hospital));
    }
}
