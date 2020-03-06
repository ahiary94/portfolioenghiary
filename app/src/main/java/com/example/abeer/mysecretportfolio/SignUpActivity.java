package com.example.abeer.mysecretportfolio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.abeer.mysecretportfolio.models.SignupModel;

import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText suser;
    private EditText spass;
    private EditText scon;
    private Button signup;
    private ProgressDialog pd;
    private SignupModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        suser = findViewById(R.id.signup_user);
        spass = findViewById(R.id.signup_pass);
        scon = findViewById(R.id.signup_confirm);
        signup = findViewById(R.id.signup_button);
        signup.setOnClickListener(this);

        pd = new ProgressDialog(this);
        pd.setMessage("Please wait...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    public void showLoading() {
        pd.show();
    }

    public void dismissLoading() {
        pd.dismiss();
    }

    public Context getSignupContext() {
        return getBaseContext();
    }

    public String getUsername() {
        return model.getUsername();
    }

    public String getUserPassword() {
        return model.getPassword();
    }

    public void onResponse(JSONObject response) {
        if (response.equals("0"))
            // "0" mean that the uaser acyually exist and there is a data
            Toast.makeText(SignUpActivity.this, "User alraedy exist", Toast.LENGTH_SHORT).show();
        else {
            String password = spass.getText().toString();
            //condition to prevent the user from enter password less than 6 characters will appear in the form of error
            if (password.isEmpty() || password.length() < 6) {
                spass.setError("Password cannot be less than 6 characters!");
            } else {
                // save the username to use it in other activities
                users_account.usersAccount = suser.getText().toString();
                spass.setError(null);
                // users_account.uId= response.getBytes;
                Intent i = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(i);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.signup_button){
            pd.show();
            if (!TextUtils.isEmpty(suser.getText()) && !TextUtils.isEmpty(spass.getText()) && !TextUtils.isEmpty(scon.getText())) {
                model = new SignupModel(suser.getText().toString(), spass.getText().toString());
                if (model.getPassword().equals(scon.getText().toString())) {
                    new NotesPresenter(this).onConnect();
                } else
                    Toast.makeText(this, "Password not match", Toast.LENGTH_SHORT).show();
            } else {
                dismissLoading();
                Toast.makeText(this, "Please fill empty fields", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void onFailer(VolleyError error) {
        Toast.makeText(this, "There is error ...", Toast.LENGTH_SHORT).show();
        Log.e("SIGNUP error is", "" + error);
    }
}
