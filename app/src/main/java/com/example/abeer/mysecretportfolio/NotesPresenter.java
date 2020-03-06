package com.example.abeer.mysecretportfolio;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.abeer.mysecretportfolio.BuildConfig;
import com.example.abeer.mysecretportfolio.SignUpActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class NotesPresenter implements Response.Listener<JSONObject>, Response.ErrorListener {

    private String signupUrl = String.format("%s%s", BuildConfig.URL, "SignUp.php");//192.168.1.12
    private RequestQueue rq;
    private JsonObjectRequest jsonObjectRequest;
    private SignUpActivity signUpActivity;

    public NotesPresenter(SignUpActivity signUpActivity) {
        this.signUpActivity = signUpActivity;
        this.rq = Volley.newRequestQueue(signUpActivity.getSignupContext());
    }

    public void onConnect() {
        signUpActivity.showLoading();
        JSONObject object = new JSONObject();
        try {
            object.put("username", signUpActivity.getUsername());
            object.put("password", signUpActivity.getUserPassword());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, signupUrl,object , this, this);
        rq.add(jsonObjectRequest);
    }

    @Override
    public void onResponse(JSONObject response) {
        signUpActivity.showLoading();
        signUpActivity.onResponse(response);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        signUpActivity.dismissLoading();
        signUpActivity.onFailer(error);
    }
}
