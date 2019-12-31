package com.example.sc_147.healthmate;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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
import java.util.HashSet;
import java.util.List;

import Interface.APIManager;
import Model.GetCityArea_ResModel;
import Utility.MySharedPrefs;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_SetLocation extends BaseActivity implements AdapterView.OnItemSelectedListener {


    @BindView(R.id.textView_lable_state)
    TextView textViewLableState;
    @BindView(R.id.textView_lable_citylist)
    TextView textViewLableCitylist;
    @BindView(R.id.textView_lable_arealist)
    TextView textViewLableArealist;

    @BindView(R.id.spinner_state)
    NiceSpinner spinnerState;
    @BindView(R.id.spinner_city)
    NiceSpinner spinnerCity;
    @BindView(R.id.spinner_area)
    NiceSpinner spinnerArea;

    @BindView(R.id.button_submit)
    Button buttonSubmit;

    ProgressDialog p;

    ArrayAdapter<String> state_adapter;
    ArrayAdapter<String> city_adapter;
    ArrayAdapter<String> area_adapter;

    ArrayList<String> cityList;
    ArrayList<String> areaList;

    boolean state_selected, city_selected, area_selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__setlocation);
        ButterKnife.bind(this);
        setStatelistSpinner();
        initView();
    }

    private void initView() {
        p = new ProgressDialog(this);
        spinnerState.setOnItemSelectedListener(this);
        spinnerCity.setOnItemSelectedListener(this);
        spinnerArea.setOnItemSelectedListener(this);
    }

    private void setStatelistSpinner() {
        state_adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.state_list));
        spinnerState.setAdapter(state_adapter);
        spinnerState.setSelectedIndex(0);
    }

    private void setCitylistSpinner(List<GetCityArea_ResModel> body) {
        cityList = new ArrayList<>();
        for (int i = 0; i < body.size(); i++) {
            cityList.add(body.get(i).getCity());
        }
        HashSet<String> hashSet = new HashSet<>();
        hashSet.addAll(cityList);
        cityList.clear();
        cityList.add(0, getResources().getString(R.string.select_city));
        cityList.addAll(hashSet);
        city_adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, cityList);
        spinnerCity.setAdapter(city_adapter);
        spinnerCity.setSelectedIndex(0);
    }

    private void setArealistSpinner(List<GetCityArea_ResModel> body) {
        areaList = new ArrayList<>();
        areaList.add(0, getResources().getString(R.string.select_area));
        for (int i = 1; i < body.size(); i++) {
            areaList.add(i, body.get(i).getArea());
        }
        area_adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, areaList);
        spinnerArea.setAdapter(area_adapter);
        spinnerArea.setSelectedIndex(0);
    }

    @OnClick(R.id.button_submit)
    public void onViewClicked() {
        if (state_selected) {
            if (city_selected) {
                if (area_selected) {
                    textViewLableArealist.setTextColor(Color.BLACK);
                    textViewLableCitylist.setTextColor(Color.BLACK);
                    textViewLableState.setTextColor(Color.BLACK);
                    MySharedPrefs.saveLocation(this, spinnerArea.getText().toString());
                    Intent intent = new Intent(this, Activity_Home.class);
                    startActivity(intent);
                    finish();
                } else {
                    textViewLableArealist.setText(getResources().getString(R.string.please_select_area));
                    textViewLableArealist.setTextColor(getResources().getColor(R.color.colorRed));
                }
            } else {
                textViewLableCitylist.setText(getResources().getString(R.string.please_select_city));
                textViewLableCitylist.setTextColor(getResources().getColor(R.color.colorRed));
            }
        } else {
            textViewLableState.setText(getResources().getString(R.string.please_select_state));
            textViewLableState.setTextColor(getResources().getColor(R.color.colorRed));
        }
    }

    void getRetrofitCityArray(String state) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MySharedPrefs.loadUrl(this))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIManager service = retrofit.create(APIManager.class);
        Call<List<GetCityArea_ResModel>> call = service.getCityListResult(state);
        call.enqueue(new Callback<List<GetCityArea_ResModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<GetCityArea_ResModel>> call, @NonNull Response<List<GetCityArea_ResModel>> response) {
                if (response.body() != null) {
                    p.dismiss();
                    if (response.body().size() == 0) {
                        Toast.makeText(Activity_SetLocation.this, "No city found for selected state", Toast.LENGTH_LONG).show();

                        city_selected = false;
                        area_selected = false;

                        if (cityList != null) {
                            cityList.clear();
                            cityList.add(0, getResources().getString(R.string.select_city));
                            city_adapter = new ArrayAdapter<>(Activity_SetLocation.this, R.layout.support_simple_spinner_dropdown_item, cityList);
                            spinnerCity.setAdapter(city_adapter);
                            spinnerCity.setSelectedIndex(0);
                        } else {
                            //Do nothing...
                        }

                        if (areaList != null) {
                            areaList.clear();
                            areaList.add(0, getResources().getString(R.string.select_area));
                            area_adapter = new ArrayAdapter<>(Activity_SetLocation.this, R.layout.support_simple_spinner_dropdown_item, areaList);
                            spinnerArea.setAdapter(area_adapter);
                            spinnerArea.setSelectedIndex(0);
                        } else {
                            //Do nothing...
                        }
                    } else {
                        setCitylistSpinner(response.body());
                    }
                } else {
                    //Do nothing...
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GetCityArea_ResModel>> call, @NonNull Throwable t) {
                p.dismiss();
                Toast.makeText(Activity_SetLocation.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void getRetrofitAreaArray(String city) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MySharedPrefs.loadUrl(this))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIManager service = retrofit.create(APIManager.class);
        Call<List<GetCityArea_ResModel>> call = service.getAreaListResult(city);
        call.enqueue(new Callback<List<GetCityArea_ResModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<GetCityArea_ResModel>> call, @NonNull Response<List<GetCityArea_ResModel>> response) {
                if (response.body() != null) {
                    p.dismiss();
                    if (response.body().size() == 0) {
                        Toast.makeText(Activity_SetLocation.this, "No area found for selected city", Toast.LENGTH_LONG).show();

                        area_selected = false;

                        if (areaList != null) {
                            areaList.clear();
                            areaList.add(0, getResources().getString(R.string.select_area));
                            area_adapter = new ArrayAdapter<>(Activity_SetLocation.this, R.layout.support_simple_spinner_dropdown_item, areaList);
                            spinnerArea.setAdapter(area_adapter);
                            spinnerArea.setSelectedIndex(0);
                        } else {
                            //Do nothing...
                        }
                    } else {
                        setArealistSpinner(response.body());
                    }
                } else {
                    p.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<GetCityArea_ResModel>> call, @NonNull Throwable t) {
                p.dismiss();
                Toast.makeText(Activity_SetLocation.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int spinner_id = parent.getId();
        if (spinner_id == R.id.spinner_state) {
            if (position > 0) {
                textViewLableState.setTextColor(Color.BLACK);
                textViewLableCitylist.setTextColor(Color.BLACK);
                textViewLableArealist.setTextColor(Color.BLACK);

                textViewLableArealist.setText(getResources().getString(R.string.select_area));
                textViewLableCitylist.setText(getResources().getString(R.string.select_city));
                textViewLableState.setText(getResources().getString(R.string.select_state));

                state_selected = true;
                p.setTitle("Getting city list");
                p.setMessage("Please wait...");
                p.setCancelable(false);
                p.show();
                getRetrofitCityArray(state_adapter.getItem(position));
            } else {
                state_selected = false;
                textViewLableState.setTextColor(Color.BLACK);
                textViewLableCitylist.setTextColor(Color.BLACK);
                textViewLableArealist.setTextColor(Color.BLACK);

                textViewLableArealist.setText(getResources().getString(R.string.select_area));
                textViewLableCitylist.setText(getResources().getString(R.string.select_city));
                textViewLableState.setText(getResources().getString(R.string.select_state));

                cityList.clear();
                cityList.add(0, getResources().getString(R.string.select_city));
                city_adapter = new ArrayAdapter<>(Activity_SetLocation.this, R.layout.support_simple_spinner_dropdown_item, cityList);
                spinnerCity.setAdapter(city_adapter);
                spinnerCity.setSelectedIndex(0);

                if (areaList != null) {
                    areaList.clear();
                    areaList.add(0, getResources().getString(R.string.select_area));
                    area_adapter = new ArrayAdapter<>(Activity_SetLocation.this, R.layout.support_simple_spinner_dropdown_item, areaList);
                    spinnerArea.setAdapter(area_adapter);
                    spinnerArea.setSelectedIndex(0);
                } else {
                    //Do nothing...
                }
            }
        } else if (spinner_id == R.id.spinner_city) {
            if (position > 0) {
                textViewLableCitylist.setTextColor(Color.BLACK);
                textViewLableArealist.setTextColor(Color.BLACK);

                textViewLableArealist.setText(getResources().getString(R.string.select_area));
                textViewLableCitylist.setText(getResources().getString(R.string.select_city));

                city_selected = true;
                p.setTitle("Getting area list");
                p.setMessage("Please wait...");
                p.setCancelable(false);
                p.show();
                getRetrofitAreaArray(city_adapter.getItem(position));
            } else {
                city_selected = false;
            }
        } else if (spinner_id == R.id.spinner_area) {
            if (position > 0) {
                textViewLableArealist.setTextColor(Color.BLACK);

                textViewLableArealist.setText(getResources().getString(R.string.select_area));

                area_selected = true;
            } else {
                area_selected = false;
            }
        } else {
            //Do nothing...
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (!MySharedPrefs.loadLocation(this).equals("")) {
            Intent intent = new Intent(this, Activity_Home.class);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }
}