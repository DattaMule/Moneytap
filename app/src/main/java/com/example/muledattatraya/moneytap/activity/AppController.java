package com.example.muledattatraya.moneytap.activity;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.muledattatraya.moneytap.R;
import com.squareup.picasso.Picasso;

public class AppController extends Application {

    private RequestQueue mRequestQueue;
    private RequestQueue mNotificationRequestQueue;
    private static AppController mInstance;
    public static final String TAG = AppController.class.getSimpleName();
    public static final String NOTIFICATION_REQ = "notification_req";


    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    public static synchronized AppController getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // HttpsTrustManager.allowAllSSL();
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void cancelPendingRequests() {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
    }


    public RequestQueue getNotificationRequestQueue() {
        if (mNotificationRequestQueue == null) {
            mNotificationRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mNotificationRequestQueue;
    }

    public <T> void addToNotificationRequestQueue(Request<T> req) {
        req.setTag(NOTIFICATION_REQ);
        getNotificationRequestQueue().add(req);
    }

    public void cancelPendingNotificationRequests() {
        if (mNotificationRequestQueue != null) {
            mNotificationRequestQueue.cancelAll(NOTIFICATION_REQ);
        }
    }
}
