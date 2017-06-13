package com.example.ariel.arielapp.member;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ariel.arielapp.LoginActivity;
import com.example.ariel.arielapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.ariel.arielapp.common.Constants.HOST_URL;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        SharedPreferences member = getSharedPreferences("member",MODE_PRIVATE);
        String id = member.getString("id","-");

        TextView tv_id = (TextView) findViewById(R.id.tv_id);
        tv_id.setText(id);
        //Toast.makeText(ListActivity.this,"여기시작",Toast.LENGTH_SHORT).show();
        initView();

    }

    public void initView(){

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, HOST_URL+"welcome/fetch_members",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Context context = ListActivity.this;

                        if(response.equals("null")){
                            Toast.makeText(context,"정보가없음",Toast.LENGTH_SHORT).show();
                        }else{
                            Log.d("신희.Response", response);

                            GsonBuilder builder = new GsonBuilder();
                            Gson gson = builder.create();

                            List<Member> list = Arrays.asList(gson.fromJson(response,Member[].class));

                            ListView listView= (ListView) findViewById(R.id.listView);
                            listView.setAdapter(new MemberAdapter(context,list));

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
        );
        requestQueue.add(postRequest);
    }

    private class MemberAdapter extends BaseAdapter {
        List<Member> list;
        LayoutInflater inflater;
        public MemberAdapter(Context context, List<Member> list) {
            this.list=list;
            this.inflater=LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View v, ViewGroup g) {
            ViewHolder holder;
            if(v==null){
                v=inflater.inflate(R.layout.member_item,null);
                holder=new ViewHolder();
                holder.tv_id= (TextView) v.findViewById(R.id.tv_id);
                holder.tv_id= (TextView) v.findViewById(R.id.tv_id);
                v.setTag(holder);
            }else{
                holder= (ViewHolder) v.getTag();
            }
            holder.tv_id.setText(list.get(i).getId());
            holder.tv_seq.setText(list.get(i).getSeq());
            return v;
        }
    }

    static class ViewHolder{
        TextView tv_seq;
        TextView tv_id;
    }
}
