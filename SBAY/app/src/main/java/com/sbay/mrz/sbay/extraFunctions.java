package com.sbay.mrz.sbay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

public class extraFunctions {

    public View view;
    public LayoutInflater inflater;
    public View layout;
    public TextView text;
    public Toast toast;
    public AlertDialog mProgressDialog;
    private boolean confirmation;

    public void closeKeyboard(Activity activity, Context context) {
        view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void openKeyboard(Context context, EditText editText, Button button) {
        editText.setEnabled(true);
        editText.requestFocus();
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        button.setVisibility(View.VISIBLE);
    }

    public void customToast(Activity activity, Context context) {
        inflater = activity.getLayoutInflater();
        layout = inflater.inflate(R.layout.customtoast, (ViewGroup) activity.findViewById(R.id.custom_toast_container));
        text = (TextView) layout.findViewById(R.id.textToast);
        toast = new Toast(context);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 150);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
    }

    @SuppressLint("InflateParams")
    public void showProgressDialog(Context context, String message) {
        mProgressDialog = new AlertDialog.Builder(context).create();
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        mProgressDialog.setView(inflater.inflate(R.layout.custom_progress_dialog,null));
        mProgressDialog.show();
        Objects.requireNonNull(mProgressDialog.getWindow()).setBackgroundDrawable(null);
    }

    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }





}
