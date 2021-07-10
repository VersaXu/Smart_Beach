package com.example.myhelloworldapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("DefaultLocale")
public class login_forget extends AppCompatActivity implements View.OnClickListener{
    private EditText et_password_first; // 声明一个编辑框
    private EditText et_password_second;
    private EditText et_verifycode; // 验证码编辑框
    private String mVertifyCode; // 验证码
    private String mPhone; // 手机号码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_forget_activity);
        // 从布局文件中获取名叫et_password_first的编辑框
        et_password_first = findViewById(R.id.et_password_first);
        // 从布局文件中获取名叫et_password_second的编辑框
        et_password_second = findViewById(R.id.et_password_second);
        // 从布局文件中获取名叫et_verifycode的编辑框
        et_verifycode = findViewById(R.id.et_verifycode);
        findViewById(R.id.btn_verifycode).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        // 从前一个页面获取要修改密码的手机号码
        mPhone = getIntent().getStringExtra("phone");
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_verifycode) { // 点击“验证码” 按钮
            if (mPhone == null || mPhone.length() < 11) {
                Toast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                return;
            }
            // 生成六位随机数字，作为验证码
            mVertifyCode = String.format("%06d", (int) (Math.random() * 1000000 % 1000000));
            // 提醒弹出对话框, 提示验证数字
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("请记住验证码");
            builder.setMessage("手机号码" + mPhone + ", 本次验证码为: " + mVertifyCode + ", 请输入验证码");
            builder.setPositiveButton("好的", null);
            AlertDialog alert = builder.create();
            alert.show();
        } else if (v.getId() == R.id.btn_confirm){ // 点击了“确定” 按钮
            String password_first = et_password_first.getText().toString();
            String password_second = et_password_second.getText().toString();
            if (password_first.length() < 6 || password_second.length() < 6) {
                Toast.makeText(this,"请输入正确的新密码", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!password_first.equals(password_second)) { //两次输入密码不一致
                Toast.makeText(this,"两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!et_verifycode.getText().toString().equals(mVertifyCode)){
                Toast.makeText(this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
                // 把修改好的密码返回给前一个界面
                Intent intent = new Intent();
                intent.putExtra("new_password", password_first);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

        }
    }
}