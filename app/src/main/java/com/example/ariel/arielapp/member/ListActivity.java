package com.example.ariel.arielapp.member;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.ariel.arielapp.R;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        SharedPreferences member = getSharedPreferences("member",MODE_PRIVATE);
        String id = member.getString("id","-");

        TextView tv_id = (TextView) findViewById(R.id.tv_id);
        tv_id.setText(id);


    }
}
