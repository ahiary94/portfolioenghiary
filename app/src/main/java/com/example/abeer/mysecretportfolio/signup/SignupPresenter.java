package com.example.abeer.mysecretportfolio.signup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abeer.mysecretportfolio.BuildConfig;

import org.json.JSONException;
import org.json.JSONObject;

public class SignupPresenter implements Response.Listener<JSONObject>, Response.ErrorListener {

    private String signupUrl = String.format("%s%s", BuildConfig.URL, "SignUp.php");//192.168.1.12
    private RequestQueue rq;
    private JsonObjectRequest jsonObjectRequest;
    private SignupView signupView;

    public SignupPresenter(SignupView signupView) {
        this.signupView = signupView;
        this.rq = Volley.newRequestQueue(signupView.getSignupContext());
    }

    public void onConnect() {
        signupView.showLoading();
        JSONObject object = new JSONObject();
        try {
            object.put("username", signupView.getUsername());
            object.put("password", signupView.getUserPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, signupUrl,object , this, this);
        rq.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        signupView.showLoading();
        signupView.onResponse(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        signupView.dismissLoading();
        signupView.onFailer(error);
    }
}
