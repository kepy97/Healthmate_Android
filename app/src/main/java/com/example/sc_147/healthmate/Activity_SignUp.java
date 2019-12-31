package com.example.sc_147.healthmate;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import Interface.APIManager;
import Model.LoginSignup_ResModel;
import Utility.Const;
import Utility.MySharedPrefs;
import Utility.Validation;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static Utility.Const.SUCCESS;

public class Activity_SignUp extends BaseActivity {

    @BindView(R.id.editText_email)
    EditText editTextEmail;
    @BindView(R.id.editText_password)
    EditText editTextPassword;
    @BindView(R.id.editText_fname)
    EditText editTextFname;
    @BindView(R.id.editText_lname)
    EditText editTextLname;
    @BindView(R.id.editText_mobile)
    EditText editTextMobile;
    @BindView(R.id.button_submit)
    Button buttonSubmit;

    ProgressDialog p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__sign_up);
        ButterKnife.bind(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    void getRetrofitArray(String email, String password, String fname, String lname, String mobile) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MySharedPrefs.loadUrl(this))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIManager service = retrofit.create(APIManager.class);
        Call<List<LoginSignup_ResModel>> call = service.getSignupResult(email, password, fname, lname, mobile);
        call.enqueue(new Callback<List<LoginSignup_ResModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<LoginSignup_ResModel>> call, @NonNull Response<List<LoginSignup_ResModel>> response) {
                if (response.body() != null) {
                    p.dismiss();
                    if (response.body().get(0).getResult().equals(SUCCESS)) {
                        finish();
                    } else if (response.body().get(0).getResult().equals(Const.FAILED)) {
                        Toast.makeText(Activity_SignUp.this, response.body().get(0).getResult(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Activity_SignUp.this, response.body().get(0).getResult(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    p.dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<LoginSignup_ResModel>> call, @NonNull Throwable t) {
                p.dismiss();
                Toast.makeText(Activity_SignUp.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.button_submit)
    public void onViewClicked() {

        Validation.validateEmail(editTextEmail.getText().toString());
        if (Validation.errorMessage.equals(SUCCESS)) {
            if (!TextUtils.isEmpty(editTextPassword.getText().toString())) {
                Validation.validateUserName(editTextFname.getText().toString(), "first name");
                if (Validation.errorMessage.equals(SUCCESS)) {
                    Validation.validateUserName(editTextFname.getText().toString(), "last name");
                    if (Validation.errorMessage.equals(SUCCESS)) {
                        if (!TextUtils.isEmpty(editTextMobile.getText().toString())) {
                            if (editTextMobile.getText().toString().length() == 10) {
                                p = new ProgressDialog(this);
                                p.setTitle("Signing in");
                                p.setMessage("Please wait...");
                                p.setCancelable(false);
                                p.show();
                                getRetrofitArray(editTextEmail.getText().toString()
                                        , editTextPassword.getText().toString()
                                        , editTextFname.getText().toString()
                                        , editTextLname.getText().toString()
                                        , editTextMobile.getText().toString());
                            } else {
                                editTextMobile.requestFocus();
                                editTextMobile.setError("Mobile number should be 10 digit long");
                            }
                        } else {
                            editTextMobile.requestFocus();
                            editTextMobile.setError("Please enter your mobile number");
                        }
                    } else {
                        editTextLname.requestFocus();
                        editTextLname.setError(Validation.errorMessage);
                    }
                } else {
                    editTextFname.requestFocus();
                    editTextFname.setError(Validation.errorMessage);
                }
            } else {
                editTextPassword.requestFocus();
                editTextPassword.setError("Please enter password");
            }
        } else {
            editTextEmail.requestFocus();
            editTextEmail.setError(Validation.errorMessage);
        }
    }
}