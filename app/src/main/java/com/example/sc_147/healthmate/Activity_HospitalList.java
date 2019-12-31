package com.example.sc_147.healthmate;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Adapter.Adapter_HospitalList;
import Interface.APIManager;
import Interface.onItemClick;
import Model.GetHospitalList_ResModel;
import Utility.MySharedPrefs;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static Utility.Const.INTENT_KEY_HOSPITAL;

public class Activity_HospitalList extends BaseActivity implements onItemClick {
    String location = "";
    @BindView(R.id.searchView)
    SearchView searchView;
    @BindView(R.id.recyclerView_Hospitals)
    RecyclerView recyclerViewHospitals;
    @BindView(R.id.textView_noHosptal)
    TextView textViewNoHosptal;
    @BindView(R.id.cardView_nodata)
    CardView cardViewNodata;

    Context c;
    List<GetHospitalList_ResModel> hospitals;
    ProgressDialog p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__hospital_list);
        init();
        ButterKnife.bind(this);
        getLocation();
    }

    private void init() {
        c = this;
    }

    private void getLocation() {
        location = MySharedPrefs.loadLocation(this);
        if (!location.equals("") && location != null) {
            p = new ProgressDialog(this);
            p.setTitle("Getting hospitals list");
            p.setMessage("Please wait...");
            p.setCancelable(false);
            p.show();
            getRetrofitHospitalNameArray(location);
        } else {
            //Do nothing...
        }
    }

    void getRetrofitHospitalNameArray(final String _location) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MySharedPrefs.loadUrl(this))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIManager service = retrofit.create(APIManager.class);
        Call<List<GetHospitalList_ResModel>> call = service.getHospitalListResult(_location);
        call.enqueue(new Callback<List<GetHospitalList_ResModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<GetHospitalList_ResModel>> call, @NonNull Response<List<GetHospitalList_ResModel>> response) {
                if (response.body() != null) {
                    if (response.body().size() == 0) {
                        p.dismiss();
                        hospitals = response.body();
                        String msg = "No hospital found in " + _location + " area";
                        textViewNoHosptal.setText(msg);
                        cardViewNodata.setVisibility(View.VISIBLE);
                    } else {
                        p.dismiss();
                        cardViewNodata.setVisibility(View.GONE);
                        recyclerViewHospitals.setVisibility(View.VISIBLE);
                        hospitals = response.body();
                        Adapter_HospitalList adapter_hospitalList = new Adapter_HospitalList(response.body(), Activity_HospitalList.this);
                        recyclerViewHospitals.setLayoutManager(new LinearLayoutManager(c, LinearLayoutManager.VERTICAL, false));
                        recyclerViewHospitals.setAdapter(adapter_hospitalList);
                    }
                } else {
                    p.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GetHospitalList_ResModel>> call, @NonNull Throwable t) {
                p.dismiss();
                Toast.makeText(Activity_HospitalList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void ItemClicked(int position) {
        if (hospitals != null) {
            startActivity(new Intent(this, Activity_DoctorList.class).putExtra(INTENT_KEY_HOSPITAL, hospitals.get(position).getHospitalname()));
        } else {
            //Do nothing...
        }
    }
}
