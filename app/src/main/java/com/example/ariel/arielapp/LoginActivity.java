package com.example.ariel.arielapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ariel.arielapp.member.JoinActivity;
import com.example.ariel.arielapp.member.ListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.CollationElementIterator;
import java.util.HashMap;
import java.util.Map;

import static com.example.ariel.arielapp.common.Constants.HOST_URL;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView(){

        final Context context = LoginActivity.this;

        findViewById(R.id.btn_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_id = (EditText) findViewById(R.id.et_id);
                EditText et_pw = (EditText) findViewById(R.id.et_pw);
                String id = et_id.getText().toString();
                String pw = et_pw.getText().toString();

                if(id.toString().equals("")||pw.toString().equals("")){
                    Toast.makeText(context,"정확히 입력해주세요",Toast.LENGTH_SHORT).show();
                }else{
                    doLogin();
                }

            }
        });

        findViewById(R.id.btn_signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void doLogin(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, HOST_URL+"welcome/ajax_login",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                            Context context = LoginActivity.this;

                            if(response.equals("null")){
                                Toast.makeText(context,"정보가없음",Toast.LENGTH_SHORT).show();
                            }else{
                                //Log.d("신희.Response", response);
                                try {
                                    JSONObject obj = new JSONObject(response);

                                    SharedPreferences member = getSharedPreferences("member",MODE_PRIVATE);
                                    SharedPreferences.Editor editor = member.edit();

                                     editor.putString("seq",obj.getString("seq"));
                                     editor.putString("id",obj.getString("id"));
                                     editor.putString("pw",obj.getString("pw"));

                                    editor.commit();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                               startActivity(new Intent(context, ListActivity.class));
                            }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("신희Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                EditText et_id = (EditText) findViewById(R.id.et_id);
                EditText et_pw = (EditText) findViewById(R.id.et_pw);
                final String id = et_id.getText().toString();
                final String pw = et_pw.getText().toString();

                Map<String, String>  params = new HashMap<String, String>();
                params.put("id", id);
                params.put("pw", pw);

                return params;
            }
        };
        requestQueue.add(postRequest);
    }





}
