package com.example.myhelloworldapplication.fragment;

import com.example.myhelloworldapplication.FragmentActivity;
import com.example.myhelloworldapplication.R;
import com.example.myhelloworldapplication.RegisterSelect_MainActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class TabFirstFragment extends Fragment{
    private static final String TAG = "TabFirstFragment";
    protected View mView; // 视图
    protected Context mContext; // 上下文
    private String mPhone; // 手机号码

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        // 设置当前fragment布局
        mView = inflater.inflate(R.layout.activity_register_beach, container, false);
        // 获得用户电话号码
        mPhone = FragmentActivity.phone;
        // 找到跳转到下一个Activity的按钮按钮
        Button register_start = (Button) mView.findViewById(R.id.btn_rg_start);
        register_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), RegisterSelect_MainActivity.class);
                intent.putExtra("phone", mPhone);
                startActivity(intent);
            }
        });
        return mView;
    }

}
