package com.example.myhelloworldapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RegisterFinishActivity extends AppCompatActivity {
    private static final String tag = "Register Finish Activity";

    private String mPhone;
    private String mBeach;
    private String mRegion;
    private String mRegion1;
    private String mTime;
    private String mServices;

    private Button btn_submit;
    private Button btn_rg_return;

    private TextView tv_id;
    private TextView tv_beach;
    private TextView tv_region;
    private TextView tv_region_1;
    private TextView tv_time;
    private TextView tv_services;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_finish);

        mPhone = getIntent().getStringExtra("phone");
        mBeach = getIntent().getStringExtra("beach");
        mRegion = getIntent().getStringExtra("region");
        mRegion1 = getIntent().getStringExtra("region");
        mTime = getIntent().getStringExtra("time");

        ArrayList<String> list = new ArrayList<>();
        list.add(getIntent().getStringExtra("chair"));
        list.add(getIntent().getStringExtra("shoes"));
        list.add(getIntent().getStringExtra("umbrella"));
        list.add(getIntent().getStringExtra("vehicle"));
        list.add(getIntent().getStringExtra("soccer"));
        list.add(getIntent().getStringExtra("bathroom"));

        for (int i = 0; i < list.size(); i++) {
            if (list.get(0) == "need") mServices += "beach chair ";
            else mServices += "";
            if (list.get(1) == "need") mServices += ";beach shoes";
            else mServices += "";
            if (list.get(2) == "need") mServices += ";beach umbrella";
            else mServices += "";
            if (list.get(3) == "need") mServices += ";beach vehicle";
            else mServices += "";
            if (list.get(4) == "need") mServices += ";beach soccer";
            else mServices += "";
            if (list.get(5) == "need") mServices += ";bathroom use";
            else mServices += "";
        }


        tv_id = findViewById(R.id.tv_id);
        tv_id.setText("ID:         " + mPhone);

        tv_beach = findViewById(R.id.tv_beach);
        tv_beach.setText("Beach:      " + mBeach);

        tv_region_1 = findViewById(R.id.tv_region_1);
        tv_region_1.setText("Region: " + mRegion1);

        tv_time = findViewById(R.id.tv_region);
        tv_time.setText("Time:       " + mTime);

        tv_services = findViewById(R.id.tv_services);
        tv_services.setText("Services needed:  " + mServices);

        btn_submit = findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(RegisterFinishActivity.this, FragmentActivity.class);
                startActivity(intent);
            }
        });

        // 通过按钮实现结束当前页面
        btn_rg_return = findViewById(R.id.btn_rg_return);
        btn_rg_return.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


    }
}