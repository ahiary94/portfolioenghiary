package com.example.abeer.mysecretportfolio.signup;

import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public interface SignupView {

    void showLoading();

    void dismissLoading();

    Context getSignupContext();

    void onResponse(JSONObject response);

    void onFailer(VolleyError error);

    String getUsername();

    String getUserPassword();

}
