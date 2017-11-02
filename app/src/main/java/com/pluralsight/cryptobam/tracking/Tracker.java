package com.pluralsight.cryptobam.tracking;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by omrierez on 02.11.17.
 */

public class Tracker {
    private static final String TAG = Tracker.class.getSimpleName();
    private final String TRACKING_URL = "https://www.google.com";
    private final RequestQueue mQueue;
    private final String mOsVersion;

    public Tracker(Context con) {
        mOsVersion = Build.VERSION.RELEASE;
        mQueue = Volley.newRequestQueue(con);
    }

    private StringRequest generateTrackingStringRequest(final String eventName) {
        return new StringRequest(Request.Method.POST, TRACKING_URL,
                response -> {
                    Log.d(TAG, "onResponse() called with: response = [" + response + "]");

                },
                error -> Log.d(TAG, "onErrorResponse() called with: error = [" + error + "]")) {
            @Override
            public byte[] getBody() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("eventName", eventName);
                params.put("osVersion", mOsVersion);
                return new JSONObject(params).toString().getBytes();
            }
        };
    }

    public void trackOnCreate() {

        mQueue.add(generateTrackingStringRequest("create"));
    }

    public void trackOnDestroy() {

        mQueue.add(generateTrackingStringRequest("destroy"));

    }

    public void trackOnStart() {

        mQueue.add(generateTrackingStringRequest("start"));

    }

    public void trackOnResume() {

        mQueue.add(generateTrackingStringRequest("resume"));

    }

    public void trackOnPause() {

        mQueue.add(generateTrackingStringRequest("pause"));

    }

    public void trackOnStop() {

        mQueue.add(generateTrackingStringRequest("stop"));

    }

    public void trackLocation(int lat, int lng) {
        mQueue.add(generateTrackingStringRequest("location\t" + lat + "-" + lng));

    }
}
