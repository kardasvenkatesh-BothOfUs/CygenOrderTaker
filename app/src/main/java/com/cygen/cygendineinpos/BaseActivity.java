package com.cygen.cygendineinpos;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.cygen.cygendineinpos.Retrofit.RetrofitBuilder;
import com.cygen.cygendineinpos.Retrofit.RetrofitInterface;
import com.google.android.material.snackbar.Snackbar;


import java.util.Timer;

/**
 * Created by lancius on 4/3/2018.
 */

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getName();
    Dialog dialog;
    public ProgressDialog mProgressDialog, dDialog, pDialog;
    Timer timer;
    public Intent intent = null;
    //    public RetrofitInterface apiService;
    public String message;
    public TextView header;
    public RetrofitInterface apiService;

    public void showShortSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
        TextView textView = (TextView) snackBarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.redColor));
        textView.setTextSize(18);
        snackbar.show();
//        snackbar.show();
    }

    public void showLongSnackBar(String message) {
        Snackbar snackbar = Snackbar
                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
        TextView textView = (TextView) snackBarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.redColor));
        textView.setTextSize(18);
        snackbar.show();
    }

    public void showShortToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showLongToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

//    public void retrofitService() {
//        apiService = RetrofitBuilder.getApiBuilder().create(RetrofitInterface.class);
//    }

//    public void showHeaderTextView(){
//        header = findViewById(R.id.header);
//    }

    public void retrofitService() {
        try {
            apiService = RetrofitBuilder.getApiBuilder().create(RetrofitInterface.class);
        } catch (NullPointerException e) {

        }
    }

    public ProgressDialog getProgressDialog(String message) {
        ProgressDialog p = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

        } else {
            p = new ProgressDialog(this);
        }
        p.setCancelable(false);
        p.setMessage(message);
        return p;
    }

//    public void showToolbar() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
//    }
//
//    public void showToolbarWithoutArrow() {
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

//    public void showShortSnackBar(String message) {
//        Snackbar snackbar = Snackbar
//                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
//        View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
//        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
//        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
//        textView.setTextSize(18);
//        snackbar.show();
////        snackbar.show();
//    }
//
//    public void showLongSnackBar(String message) {
//        Snackbar snackbar = Snackbar
//                .make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
//        View snackBarView = snackbar.getView();
//        snackBarView.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
//        TextView textView = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_text);
//        textView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));
//        textView.setTextSize(18);
//        snackbar.show();
//    }

    public void navigateToActivity(Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent,
                    ActivityOptions.makeSceneTransitionAnimation(this).toBundle());

        } else {
            startActivity(intent);
        }
    }

    public boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    public void showError(EditText editText, String message) {
        editText.setError(message);
        editText.requestFocus();

    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(BaseActivity.this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
        }

        mProgressDialog.show();

    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    public void showMProgressDialog() {
        mProgressDialog = new ProgressDialog(BaseActivity.this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();

//        if (dDialog == null) {
//            dDialog = new ProgressDialog(BaseActivity.this);
//            dDialog.setMessage("Loading");
//            dDialog.setIndeterminate(false);
//            dDialog.setCanceledOnTouchOutside(false);
//            dDialog.setCancelable(false);
//        }
//
//        dDialog.show();
    }

    public void hideMProgressDialog() {
//        if (dDialog != null && dDialog.isShowing()) {
//            dDialog.dismiss();
//        }

        if(mProgressDialog != null)
            mProgressDialog.dismiss();
    }

    public void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void setStatusBarTopColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public void showDialog(String displayMessage) {
//
//        final Dialog dialog = new Dialog(this);
//
//        dialog.setContentView(R.layout.response_popup);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
////        dialog.setTitle("Custom Alert Dialog");
//
//        Button poupCloseButton = (Button) dialog.findViewById(R.id.poupCloseButton);
//        TextView popupContentTextView = (TextView) dialog.findViewById(R.id.popupContentTextView);
//        popupContentTextView.setText(displayMessage);
//        poupCloseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                finish();
//            }
//        });
//        dialog.show();
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public void showErrorDialog(String displayMessage) {
//
//        final Dialog dialog = new Dialog(this);
//
//        dialog.setContentView(R.layout.error_popup);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
////        dialog.setTitle("Custom Alert Dialog");
//
//        Button poupCloseButton = (Button) dialog.findViewById(R.id.poupCloseButton);
//        TextView popupContentTextView = (TextView) dialog.findViewById(R.id.popupContentTextView);
//        popupContentTextView.setText(displayMessage);
//        poupCloseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                finish();
//            }
//        });
//        dialog.show();
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public void showErrorWithoutFinishDialog(String displayMessage) {
//
//        final Dialog dialog = new Dialog(this);
//
//        dialog.setContentView(R.layout.error_popup);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
////        dialog.setTitle("Custom Alert Dialog");
//
//        Button poupCloseButton = (Button) dialog.findViewById(R.id.poupCloseButton);
//        TextView popupContentTextView = (TextView) dialog.findViewById(R.id.popupContentTextView);
//        popupContentTextView.setText(displayMessage);
//        poupCloseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
////                finish();
//            }
//        });
//        dialog.show();
//
//    }
//
//    public static double round(double value, int places) {
//        if (places < 0) throw new IllegalArgumentException();
//
//        long factor = (long) Math.pow(10, places);
//        value = value * factor;
//        long tmp = Math.round(value);
//        return (double) tmp / factor;
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public void showWarningDialog(String displayMessage) {
//
//        final Dialog dialog = new Dialog(this);
//
//        dialog.setContentView(R.layout.warning_popup);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
////        dialog.setTitle("Custom Alert Dialog");
//
//        Button poupCloseButton = (Button) dialog.findViewById(R.id.poupCloseButton);
//
//        TextView popupContentTextView = (TextView) dialog.findViewById(R.id.popupContentTextView);
//        popupContentTextView.setText(displayMessage);
//        poupCloseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//
//                intent = new Intent(getApplicationContext(), LoginType.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//
//                finish();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public void showWarningDialogWithFinish(String displayMessage) {
//
//        final Dialog dialog = new Dialog(this);
//
//        dialog.setContentView(R.layout.warning_popup);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
////        dialog.setTitle("Custom Alert Dialog");
//
//        Button poupCloseButton = (Button) dialog.findViewById(R.id.poupCloseButton);
//
//        TextView popupContentTextView = (TextView) dialog.findViewById(R.id.popupContentTextView);
//        popupContentTextView.setText(displayMessage);
//        poupCloseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//
//                finish();
//            }
//        });
//
//        dialog.show();
//
//    }
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public void showWarningWithoutFinishDialog(String displayMessage) {
//
//        final Dialog dialog = new Dialog(this);
//
//        dialog.setContentView(R.layout.warning_popup);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
////        dialog.setTitle("Custom Alert Dialog");
//
//        Button poupCloseButton = (Button) dialog.findViewById(R.id.poupCloseButton);
//
//        TextView popupContentTextView = (TextView) dialog.findViewById(R.id.popupContentTextView);
//        popupContentTextView.setText(displayMessage);
//        poupCloseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//
//            }
//        });
//
//        dialog.show();
//
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public void showDialogWithouFinish(String displayMessage) {
//
//        final Dialog dialog = new Dialog(this);
//
//        dialog.setContentView(R.layout.response_popup);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
////        dialog.setTitle("Custom Alert Dialog");
//
//        Button poupCloseButton = (Button) dialog.findViewById(R.id.poupCloseButton);
//        TextView popupContentTextView = (TextView) dialog.findViewById(R.id.popupContentTextView);
//        popupContentTextView.setText(displayMessage);
//        poupCloseButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
////                finish();
//            }
//        });
//        dialog.show();
//
//    }
//
////    @Override
////    protected void onPause() {
////        super.onPause();
////        timer = new Timer();
////        Log.i("gfgfgf", "Invoking logout timer");
////        LogOutTimerTask logoutTimeTask = new LogOutTimerTask();
////        timer.schedule(logoutTimeTask, 3000); //auto logout in 5 minutes
////    }
////
////    @Override
////    protected void onResume() {
////        super.onResume();
////        if (timer != null) {
////            timer.cancel();
////            Log.i("fggfgh", "cancel timer");
////            timer = null;
////        }
////    }
//
//    private class LogOutTimerTask extends TimerTask {
//        @Override
//        public void run() {
//
//            Intent i = new Intent(BaseActivity.this, LoginActivity.class);
//            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(i);
//            finish();
//        }
//    }


}
