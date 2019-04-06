package com.sbay.mrz.sbay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.cloudinary.android.MediaManager;
import com.cloudinary.utils.ObjectUtils;

import java.io.IOException;
import java.util.Map;

public class delAsyncTask extends AsyncTask<String,Void,String> {
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private extraFunctions extraFunctions;

    private static final String TAG = "Media Manager Exception";

    delAsyncTask(Context context) {
        this.context = context;
        extraFunctions = new extraFunctions();
        extraFunctions.customToast((Activity) context, context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //extraFunctions.showProgressDialog(context,"Deleting Product");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
//        if (s.equals("ok")){
//            extraFunctions.hideProgressDialog();
//            extraFunctions.text.setText(context.getResources().getString(R.string.productdeleted));
//            extraFunctions.toast.show();
//        }
//        else {
//            extraFunctions.hideProgressDialog();
//            extraFunctions.text.setText(context.getResources().getString(R.string.sww));
//            extraFunctions.toast.show();
//        }
    }

    @Override
    protected String doInBackground(String... strings) {
        Map deleteParams = ObjectUtils.asMap("invalidate", true );
        String result = "";
        try {
             Map del = MediaManager.get().getCloudinary().uploader().destroy(strings[0], deleteParams);
             result = del.get("result").toString();
        } catch (IOException e) {
            Log.d(TAG, "onResponse: " + e.getMessage());;
        }
        return result;
    }
}
