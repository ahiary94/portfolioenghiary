package com.example.abeer.mysecretportfolio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.abeer.mysecretportfolio.signup.signUpAct;

import java.util.HashMap;
import java.util.Map;

public class passwordAct extends AppCompatActivity {
    String val;
    EditText user;
    EditText pass;
    String url="https://mynovelsbox.000webhostapp.com/connect/myPortfolioLogin.php";
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        user= (EditText) findViewById(R.id.pass_user);
        pass= (EditText) findViewById(R.id.pass_pass);

        pd=new ProgressDialog(this);
        pd.setMessage("Please Wait...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    public void login(View view) {

        pd.show();
        RequestQueue rq= Volley.newRequestQueue(this);
        StringRequest sr=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pd.hide();
                if (response.equals("0"))
                {
                    Toast.makeText(passwordAct.this, "Login Fail ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    users_account.usersAccount=user.getText().toString();
                    Intent i=new Intent(passwordAct.this,MainActivity.class);
                    i.putExtra("password",pass.getText().toString());
                    startActivity(i);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.hide();
                Toast.makeText(passwordAct.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        })
        { @Override
            protected Map<String ,String> getParams() throws AuthFailureError {
            Map<String, String> map = new HashMap<>();
            map.put("u", user.getText().toString());
            map.put("p", pass.getText().toString());
            return map;
        }};

        rq.add(sr);
    }

    public void register(View view) {

       Intent intent=new Intent(this,signUpAct.class);
        startActivity(intent);

    }
}