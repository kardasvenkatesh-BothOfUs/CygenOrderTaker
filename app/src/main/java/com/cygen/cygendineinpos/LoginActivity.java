package com.cygen.cygendineinpos;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.cygen.cygendineinpos.Retrofit.RetrofitBuilderLogin;
import com.cygen.cygendineinpos.Retrofit.RetrofitInterface;
import com.cygen.cygendineinpos.RetrofitRequest.CashInRequest;
import com.cygen.cygendineinpos.RetrofitRequest.LoginRequest;
import com.cygen.cygendineinpos.RetrofitResponse.CashInResponse;
import com.cygen.cygendineinpos.RetrofitResponse.LoginResponse;
import com.cygen.cygendineinpos.RetrofitResponse.OutletResponse;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    Button loginBtn;
    String userName, password;
    String message;
    String userId, name, roleId, roleName, loggedIn;
    String mobile_token;
    TextView loginSignUpTv, forgotPasswordTv;
    EditText usernameEt, passwordEt;
    List<String> customerNameList;
    LoginResponse.UserListResponse loginList;
    SessionManager sessionManager;
    AlertDialog runningOrdersDialog;
    String amount;
    HashMap<String, String> user1;
    String outlet_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        retrofitService();

        apiService = RetrofitBuilderLogin.getApiBuilder().create(RetrofitInterface.class);
        setStatusBarTopColor();

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        sessionManager = new SessionManager(getApplicationContext());
        user1 = sessionManager.getUserDetails();
        outlet_id = user1.get(SessionManager.KEY_OUTLET_ID);

        usernameEt = findViewById(R.id.userNameEt);
        passwordEt = findViewById(R.id.passwordEt);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyBoard();

                userName = usernameEt.getText().toString();
                password = passwordEt.getText().toString();

                if (usernameEt.getText().toString().trim().length() != 0) {

                    if (passwordEt.getText().toString().length() != 0) {

                        getOutletResponse();

                    } else {
                        Toast.makeText(getApplicationContext(), "Please Enter Valid Password", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Please Enter a Valid UserName", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

//    private void getOutletResponse() {
//
//        LoginRequest request = new LoginRequest();
//        request.setUsername(userName);
//        request.setPassword(password);
//        showProgressDialog();
//        Log.d("loginRequest", new Gson().toJson(request));
//
//        retrofit2.Call<OutletResponse> call = apiService.outletLoginData("1234", request);
//
//        call.enqueue(new Callback<OutletResponse>() {
//            @Override
//            public void onResponse(retrofit2.Call<OutletResponse> call, Response<OutletResponse> response) {
//
//                Log.d("responseee", new Gson().toJson(response.body()));
//
//                if (response.isSuccessful()) {
//
//                    hideProgressDialog();
//                    final OutletResponse user = response.body();
//
//                    String message = user.getMessage();
//                    int success = user.getSuccess();
//
//                    if (success == 0) {
//
//                        Log.d("wq3rer", "yuiyu" + new Gson().toJson(response.body()));
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                sessionManager.storeOutletDetails(user.outlet_id, user.base_url, user.contact_number, user.contact_email);
//                                Constants.baseUrl = user.base_url;
////                                retrofitService();
//                                getLoginResponse();
//
//                            }
//                        });
//
//                    } else {
//
//                        showShortSnackBar(message);
//
//                    }
//
//                } else {
//
//                    hideProgressDialog();
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<OutletResponse> call, Throwable t) {
//                hideProgressDialog();
//
//                message = "Oops! something went wrong please try again";
//
//                Intent intent = new Intent(getApplicationContext(), NewPosMainActivity.class);
//                startActivity(intent);
//
//                finish();
//
//            }
//        });
//
//    }
//
//    private void getLoginResponse() {
//
//        outlet_id = user1.get(SessionManager.KEY_OUTLET_ID);
//
//        LoginRequest request = new LoginRequest();
//        request.setUsername(userName);
//        request.setPassword(password);
//        request.setOutlet_id(outlet_id);
//        showProgressDialog();
//        Log.d("loginRequest", new Gson().toJson(request));
//
//        retrofit2.Call<LoginResponse> call = apiService.userLoginData("1234", request);
//
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(retrofit2.Call<LoginResponse> call, Response<LoginResponse> response) {
//
//                Log.d("responseee", new Gson().toJson(response.body()));
//
//                if (response.isSuccessful()) {
//
//                    hideProgressDialog();
//                    LoginResponse user = response.body();
//
//                    final String message = user.getMessage();
//                    String success = user.getResponseCode();
//
//                    Log.d("ppppppp", success);
//
//                    if (success.equalsIgnoreCase("0")) {
//
//                        loginList = user.userResults;
//
//                        Log.d("wq3rer", "yuiyu" + new Gson().toJson(response.body()));
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                userId = loginList.getInv_userid();
//                                name = loginList.getInv_username();
//                                roleId = loginList.getRole_id();
//                                roleName = loginList.getRole_name();
//                                sessionManager.storeLoginDetails(name, userId);
//
////                                CashInPopup();
//                                Intent intent = new Intent(getApplicationContext(), NewPosMainActivity.class);
//                                startActivity(intent);
//
//                                finish();
//                            }
//                        });
//                    } else {
//                        infoPopup(message);
//                    }
//
//                } else {
//                    hideProgressDialog();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                hideProgressDialog();
////                sessionManager.storeLoginDetails("2", "naveen@cygen");
//                message = "Invalid Credentials";
//                infoPopup(message);
//
//            }
//        });
//
//    }

    private void getOutletResponse() {

        showProgressDialog();
        LoginRequest request = new LoginRequest();
        request.setUsername(userName);
        request.setPassword(password);
        Log.d("loginRequest", new Gson().toJson(request));

        retrofit2.Call<OutletResponse> call = apiService.outletLoginData("1234", request);

        call.enqueue(new Callback<OutletResponse>() {
            @Override
            public void onResponse(retrofit2.Call<OutletResponse> call, Response<OutletResponse> response) {

                Log.d("responseee", new Gson().toJson(response.body()));

                if (response.isSuccessful()) {

                    hideProgressDialog();

                    final OutletResponse user = response.body();

                    String message = user.getMessage();
                    String success = user.getResponseCode();

                    if (success.equalsIgnoreCase("0")) {

                        Log.d("wq3rer", "yuiyu" + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                sessionManager.storeOutletDetails(user.outlet_id, user.base_url, user.contact_number, user.contact_email);
                                Constants.baseUrl = user.base_url;

                                outlet_id = user.outlet_id;

//                                sessionMethod();
                                getLoginResponse();

                            }
                        });

                    } else {
                        infoPopup(message);
//                        showShortSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
                    String message = "Oops! server error please try again";
                    infoPopup(message);
                }
            }

            @Override
            public void onFailure(Call<OutletResponse> call, Throwable t) {

                hideProgressDialog();
                String message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);
                infoPopup(message);

            }
        });

    }

    private void getLoginResponse() {
        

        LoginRequest request = new LoginRequest();
        request.setUsername(userName);
        request.setPassword(password);
        request.setOutlet_id(outlet_id);

        Log.d("loginRequest", new Gson().toJson(request));

        retrofit2.Call<LoginResponse> call = apiService.userLoginData("1234", request);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(retrofit2.Call<LoginResponse> call, Response<LoginResponse> response) {

                Log.d("responseee", new Gson().toJson(response.body()));

                if (response.isSuccessful()) {

                    LoginResponse user = response.body();

                    final String message = user.getMessage();
                    String success = user.getResponseCode();

                    Log.d("ppppppp", success);

                    if (success.equalsIgnoreCase("0")) {

                        loginList = user.userResults;

                        Log.d("wq3rer", "yuiyu" + new Gson().toJson(response.body()));
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                userId = loginList.getInv_userid();
                                name = loginList.getInv_username();
                                roleId = loginList.getRole_id();
                                roleName = loginList.getRole_name();
                                sessionManager.storeLoginDetails(name, userId);

//                                CashInPopup();
                                Intent intent = new Intent(getApplicationContext(), NewPosMainActivity.class);
                                startActivity(intent);

                                finish();
                            }
                        });
                    } else {
                        infoPopup(message);
                    }

                } else {
//                    hideProgressDialog();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
//                hideProgressDialog();
//                sessionManager.storeLoginDetails("2", "naveen@cygen");
                infoPopup(message);

            }
        });

    }

    public void infoPopup(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.info_popup, null);
        builder.setView(dialogView);
        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        int dialogWindowWidth = WindowManager.LayoutParams.WRAP_CONTENT;
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;

        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        dialog.getWindow().setAttributes(layoutParams);

        TextView error_text_textview = dialogView.findViewById(R.id.infoTextView);
        error_text_textview.setText(message);

        dialog.findViewById(R.id.yesButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    private void geCashInResponse() {

        CashInRequest request = new CashInRequest();
        request.setUser_id(userId);
        request.setAmount(amount);
        request.setOutlet_id(outlet_id);

        showProgressDialog();
        Log.d("loginRequest", new Gson().toJson(request));

        retrofit2.Call<CashInResponse> call = apiService.cashInData("1234", request);

        call.enqueue(new Callback<CashInResponse>() {
            @Override
            public void onResponse(retrofit2.Call<CashInResponse> call, Response<CashInResponse> response) {

                Log.d("responseee", new Gson().toJson(response.body()));

                if (response.isSuccessful()) {

                    hideProgressDialog();
                    CashInResponse user = response.body();

                    String message = user.getMessage();
                    String success = user.getResponseCode();

                    Log.d("ppppppp", success);

                    if (success.equalsIgnoreCase("0")) {

                        Intent intent = new Intent(getApplicationContext(), NewPosMainActivity.class);
                        startActivity(intent);

                        finish();

                    } else {
                        showShortSnackBar(message);
                    }

                } else {
                    hideProgressDialog();
                    message = "Server Error. Please try again later";
                    showShortSnackBar(message);
                }
            }

            @Override
            public void onFailure(Call<CashInResponse> call, Throwable t) {
                hideProgressDialog();
//                sessionManager.storeLoginDetails("2", "naveen@cygen");
                message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);

                Intent intent = new Intent(getApplicationContext(), NewPosMainActivity.class);
                startActivity(intent);

                finish();
            }
        });

    }

    public void CashInPopup() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_cash_in_popup, null);
        builder.setView(dialogView);
        runningOrdersDialog = builder.create();
        runningOrdersDialog.show();
        runningOrdersDialog.setCanceledOnTouchOutside(false);
        runningOrdersDialog.setCancelable(false);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(runningOrdersDialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.50f);
//        int dialogWindowHeight = (int) (displayHeight * 0.40f);
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        runningOrdersDialog.getWindow().setAttributes(layoutParams);

        final AppCompatTextView saveBtn = runningOrdersDialog.findViewById(R.id.cashInSaveTv);
        final AppCompatEditText amountEt = runningOrdersDialog.findViewById(R.id.amountCashInEt);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyBoard();
                if (amountEt.getText().toString().isEmpty()) {
                    showLongSnackBar("Please enter amount");
                } else {
                    amount = amountEt.getText().toString();
                    try {
                        Double payableAmount = Double.parseDouble(amount);
                        geCashInResponse();
                    } catch (NumberFormatException e) {
                        showLongSnackBar("Invalid expression");
                    }

                }
            }
        });

        runningOrdersDialog.show();
    }

    @Override
    public void onBackPressed() {

    }

}
