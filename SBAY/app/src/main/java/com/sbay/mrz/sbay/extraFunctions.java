package com.sbay.mrz.sbay;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

public class extraFunctions {

    public View view;
    public LayoutInflater inflater;
    public View layout;
    public TextView text;
    public Toast toast;
    public ProgressDialog mProgressDialog;

    public void closeKeyboard(Activity activity,Context context){
        view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void customToast(Activity activity,Context context){
        inflater = activity.getLayoutInflater();
        layout = inflater.inflate(R.layout.customtoast, (ViewGroup) activity.findViewById(R.id.custom_toast_container));
        text = (TextView) layout.findViewById(R.id.textToast);
        toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 250);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
    }

    public void showProgressDialog(Context context, String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(message);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

}
