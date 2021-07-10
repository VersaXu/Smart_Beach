package com.example.myhelloworldapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

public class RegisterAdditionalServicesActivity extends AppCompatActivity {
    private static final String TAG = "Register additional services";
    private Button btn_as_next;
    private Button btn_rg_return;
    // previous values
    private String mPhone;
    private String mBeach;
    private String mRegion;
    private String mTime;
    private String mBathroom;
    private String mUmbrella;
    private String mChair;
    private String mVehicle;
    private String mSoccer;
    private String mShoes;

    private Intent mIntent = new Intent();
    private ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_additional_services);

        mPhone =  getIntent().getStringExtra("phone");
        mBeach = getIntent().getStringExtra("beach");
        mRegion = getIntent().getStringExtra("region");
        mTime = getIntent().getStringExtra("time");

        System.out.println("In additional service Activity, mPhone is: " + mPhone);
        System.out.println("In additional service Activity, mBeach is: " + mBeach);
        System.out.println("In additional service Activity, mRegion is: " + mRegion);
        System.out.println("In additional service Activity, mTime is: " + mTime);
        mIntent.putExtra("phone", mPhone);
        mIntent.putExtra("beach", mBeach);
        mIntent.putExtra("region", mRegion);
        mIntent.putExtra("time", mTime);

        btn_as_next = findViewById(R.id.btn_as_next);
        btn_as_next.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                mIntent.setClass(RegisterAdditionalServicesActivity.this, RegisterFinishActivity.class);
                System.out.println(mIntent.toString());
                startActivity(mIntent);
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

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_chair:
                if (checked){
                    mChair = "need";
                    mIntent.putExtra("chair", mChair);
                }
            else{
                    mChair = "no need";
                    mIntent.putExtra("chair", mChair);
                }
                break;
            case R.id.checkbox_shoes:
                if (checked){
                    mShoes = "need";
                    mIntent.putExtra("shoes", mShoes);
                }
                else{
                    mShoes = "no need";
                    mIntent.putExtra("shoes", mShoes);
                }
                break;
            case R.id.checkbox_umbrella:
                if (checked){
                    mUmbrella = "need";
                    mIntent.putExtra("umbrella", mUmbrella);
                }
                else{
                    mUmbrella = "no need";
                    mIntent.putExtra("umbrella", mUmbrella);
                }
                break;
            case R.id.checkbox_vehicle:
                if (checked){
                    mVehicle = "need";
                    mIntent.putExtra("vehicle", mVehicle);
                }
                else {
                    mVehicle = "no need";
                    mIntent.putExtra("vehicle", mVehicle);
                }
                break;
            case R.id.checkbox_soccer:
                if (checked){
                    mSoccer = "need";
                    mIntent.putExtra("soccer", mSoccer);
                }
                else {
                    mSoccer = "no need";
                    mIntent.putExtra("soccer", mSoccer);
                }
                break;
            case R.id.checkbox_bathroom:
                if (checked){
                    mBathroom = "need";
                    mIntent.putExtra("bathroom", mBathroom);
                }
                else {
                    mBathroom = "no need";
                    mIntent.putExtra("bathroom", mBathroom);
                }
                break;
            // TODO: Veggie sandwich
        }
    }



}