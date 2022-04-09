package com.cygen.cygendineinpos;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.cygen.cygendineinpos.Retrofit.RetrofitBuilderLogin;
import com.cygen.cygendineinpos.Retrofit.RetrofitInterface;
import com.cygen.cygendineinpos.RetrofitRequest.LoginRequest;
import com.cygen.cygendineinpos.RetrofitResponse.LoginResponse;
import com.cygen.cygendineinpos.RetrofitResponse.OutletResponse;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by madhu on 12/1/2017.
 */

public class SplashActivity extends BaseActivity {

    //    SessionManager session;
    Intent intent;

    String packageName, version, newVersion, version1 = null, version2 = null;
    ProgressBar splashProgressDialog;
    SessionManager sessionManager;
    RetrofitInterface apiService;

    AlertDialog outletAlertDialog;

    String username = null, password = null;
    HashMap<String, String> user1;
    String outlet_id;
    LoginResponse.UserListResponse loginList;
    String userId, name, roleId, roleName, loggedIn;
    String message;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

//        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title

        apiService = RetrofitBuilderLogin.getApiBuilder().create(RetrofitInterface.class);

        sessionManager = new SessionManager(getApplicationContext());
        user1 = sessionManager.getUserDetails();

        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        packageName = getApplication().getPackageName();

        sessionManager = new SessionManager(getApplicationContext());

        sessionMethod();

//        setStatusBarTopColor();
//        outletLoginPopUp();
//        if (CommonUtilities.checkConn(getApplicationContext()))
//            getOutletResponse();
//        else
//            showLongSnackBar("No Internet Connection. Please try again later");

    }

    public void outletLoginPopUp() {

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_outlet_login, null);
        dialogBuilder.setView(dialogView);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        outletAlertDialog = dialogBuilder.create();
        int displayWidth = displayMetrics.widthPixels;
        int displayHeight = displayMetrics.heightPixels;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(outletAlertDialog.getWindow().getAttributes());
        int dialogWindowWidth = (int) (displayWidth * 0.65f);
//        int dialogWindowHeight = (int) (displayHeight * 0.40f);
        int dialogWindowHeight = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        outletAlertDialog.getWindow().setAttributes(layoutParams);
        outletAlertDialog.setCancelable(false);
        outletAlertDialog.setCanceledOnTouchOutside(false);
        final EditText amountEt = (EditText) dialogView.findViewById(R.id.amountEt);
        final EditText disEt = (EditText) dialogView.findViewById(R.id.disEt);
        final AppCompatTextView CashInOutSaveTv = (AppCompatTextView) dialogView.findViewById(R.id.CashInOutSaveTv);
        final AppCompatTextView cashInOutCloseTv = (AppCompatTextView) dialogView.findViewById(R.id.cashInOutCloseTv);
        hideKeyBoard();
        CashInOutSaveTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = amountEt.getText().toString();
                password = disEt.getText().toString();
                if (amountEt.getText().toString().length() != 0) {
                    if (disEt.getText().toString().length() != 0) {
                        hideKeyBoard();
                        if (CommonUtilities.checkConn(getApplicationContext()))
                            getOutletResponse();
                        else {
                            showLongSnackBar("Please Check your internet connection, and reopen the app");
                        }

                    } else {
                        showLongSnackBar("Please Enter Password");
                    }
                } else {
                    showLongSnackBar("Please Enter Username");
                }
            }
        });

        ImageView closeIcon = (ImageView) dialogView.findViewById(R.id.closeIcon);
        outletAlertDialog.setCanceledOnTouchOutside(false);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outletAlertDialog.dismiss();
            }
        });

        cashInOutCloseTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                outletAlertDialog.dismiss();
            }
        });

        outletAlertDialog.show();
        hideKeyBoard();
//        Window window = alertDialog.getWindow();
//        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void getOutletResponse() {

        showMProgressDialog();
        LoginRequest request = new LoginRequest();
        request.setUsername("urbn");
        request.setPassword("1234");
        Log.d("loginRequest", new Gson().toJson(request));

        retrofit2.Call<OutletResponse> call = apiService.outletLoginData("1234", request);

        call.enqueue(new Callback<OutletResponse>() {
            @Override
            public void onResponse(retrofit2.Call<OutletResponse> call, Response<OutletResponse> response) {

                Log.d("responseee", new Gson().toJson(response.body()));

                if (response.isSuccessful()) {

                    hideMProgressDialog();

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

//                                sessionMethod();
                                getLoginResponse();

                            }
                        });

                    } else {
                        infoPopup(message);
//                        showShortSnackBar(message);
                    }

                } else {
                    hideMProgressDialog();
                    String message = "Oops! server error please try again";
                    infoPopup(message);
                }
            }

            @Override
            public void onFailure(Call<OutletResponse> call, Throwable t) {

                hideMProgressDialog();
                String message = "Oops! something went wrong please try again";
//                showShortSnackBar(message);
                infoPopup(message);

            }
        });

    }

    private void getLoginResponse() {

        outlet_id = user1.get(SessionManager.KEY_OUTLET_ID);

        LoginRequest request = new LoginRequest();
        request.setUsername(username);
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

    public void sessionMethod() {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {

                        intent = new Intent(SplashActivity.this, LoginActivity.class);
                        startActivity(intent);
//
//                        if (sessionManager.isLogged()) {
//                            intent = new Intent(SplashActivity.this, LoginActivity.class);
//                            startActivity(intent);
//                        } else {
//                            intent = new Intent(SplashActivity.this, LoginActivity.class);
//                            startActivity(intent);
//                        }
//                        outletAlertDialog.dismiss();
                        finish();
                    }
                }, 1000);
            }
        });

    }

    public void showLongSnackBar(String message) {
        @SuppressLint("WrongConstant") Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
        TextView textView = (TextView) snackBarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        textView.setTextSize(18);
        snackbar.show();
    }

    public void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void infoPopup(String message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SplashActivity.this);
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

}
