package com.example.sc_147.healthmate;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import Interface.APIManager;
import Model.LoginSignup_ResModel;
import Utility.Const;
import Utility.MySharedPrefs;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Login extends BaseActivity {

    @BindView(R.id.editText_emailid)
    EditText editTextEmailid;
    @BindView(R.id.editText_password)
    EditText editTextPassword;

    @BindView(R.id.button_login)
    Button buttonLogin;
    @BindView(R.id.button_signup)
    Button buttonSignup;

    EditText edtitextUrl;
    Button buttonSave;

    ProgressDialog p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        editTextEmailid.setText("keyul31@gmail.com");
//        editTextPassword.setText("123456789");
    }

    void getRetrofitArray(String email, String password) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MySharedPrefs.loadUrl(this))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIManager service = retrofit.create(APIManager.class);
        Call<List<LoginSignup_ResModel>> call = service.getLoginResult(email, password);
        call.enqueue(new Callback<List<LoginSignup_ResModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<LoginSignup_ResModel>> call, @NonNull Response<List<LoginSignup_ResModel>> response) {
                if (response.isSuccessful()) {
                    p.dismiss();
                    if (response.body().get(0).getResult().equals(Const.SUCCESS)) {
                        if (!MySharedPrefs.loadLocation(Activity_Login.this).equals("")) {
                            MySharedPrefs.clearSharedpref(Activity_Login.this);
                        }
                        MySharedPrefs.saveEmaiID(Activity_Login.this, editTextEmailid.getText().toString());
                        startActivity(new Intent(Activity_Login.this, Activity_SetLocation.class));
                        finish();
                    } else if (response.body().get(0).getResult().equals(Const.FAILED)) {
                        Toast.makeText(Activity_Login.this, response.body().get(0).getResult(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Activity_Login.this, response.body().get(0).getResult(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    p.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<LoginSignup_ResModel>> call, @NonNull Throwable t) {
                p.dismiss();
                Toast.makeText(Activity_Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick({R.id.button_login, R.id.button_signup, R.id.image_Button_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                if (!TextUtils.isEmpty(editTextEmailid.getText().toString())) {
                    if (!TextUtils.isEmpty(editTextPassword.getText().toString())) {
                        p = new ProgressDialog(this);
                        p.setTitle("Logging in");
                        p.setMessage("Please wait...");
                        p.setCancelable(false);
                        p.show();
                        getRetrofitArray(editTextEmailid.getText().toString(), editTextPassword.getText().toString());
                    } else {
                        editTextPassword.requestFocus();
                        editTextPassword.setError("Please enter password");
                    }
                } else {
                    editTextEmailid.requestFocus();
                    editTextEmailid.setError("Please enter email id");
                }
                break;
            case R.id.button_signup:
                startActivity(new Intent(this, Activity_SignUp.class));
                break;
            case R.id.image_Button_setting:
                dialog();
                break;
        }
    }

    private void dialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_layout_url_setting);

        edtitextUrl = dialog.findViewById(R.id.editText_url);
        buttonSave = dialog.findViewById(R.id.button_save);

        edtitextUrl.setText(MySharedPrefs.loadUrl(this));

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(edtitextUrl.getText().toString())) {
                    MySharedPrefs.saveUrl(Activity_Login.this, edtitextUrl.getText().toString());
                    Toast.makeText(Activity_Login.this, "URL saved", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    edtitextUrl.requestFocus();
                    edtitextUrl.setError("Enter URL");
                }
            }
        });

        dialog.create();

        dialog.show();
    }
}