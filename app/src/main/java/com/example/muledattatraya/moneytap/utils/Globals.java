package com.example.muledattatraya.moneytap.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.muledattatraya.moneytap.R;

public class Globals {

    public static String KEY_INTENT_TITLE = "title";

    public static ProgressDialog showProgressBar(Activity activity, boolean show, ProgressDialog progress, String message) {
        if (progress == null) {
            progress = new ProgressDialog(activity);
            if (message.equalsIgnoreCase("")) {
                progress.setMessage("Please wait");
            } else {
                progress.setMessage(message);
            }
            progress.setIndeterminateDrawable(activity.getResources().getDrawable(R.drawable.progress_drawable));
            progress.setCancelable(false);
        }
        if (show) {
            progress.show();
        } else {
            progress.dismiss();
        }
        return progress;
    }

    public static boolean isConnectedToInternet(Activity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
