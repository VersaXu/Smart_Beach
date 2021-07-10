package com.example.myhelloworldapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public class RegisterBeachActivity extends Activity implements View.OnClickListener{

    private String mPhone; // 手机号码
    private TextView tv_rg_start; // 一个文本视图
    private Button btn_rg_start; // 创建按钮对象
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_beach);
        mPhone = getIntent().getStringExtra("phone");
        tv_rg_start = findViewById(R.id.tv_rg_start);
        btn_rg_start = findViewById(R.id.btn_rg_start);

        // 给开始按钮添加监听器
        btn_rg_start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_rg_start) {
            Intent intent = new Intent();
            intent.setClass(this, RegisterSelect_MainActivity.class);
            intent.putExtra("phone", mPhone);
            startActivity(intent);
        }
    }


}