package com.example.myhelloworldapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RegisterSelect_SwitchActivity extends AppCompatActivity {
    private static final String TAG = "Register select";
    private String mPhone;
    private String mBeach = "Heisha";
    private String mRegion;
    private String mTime;

    private Button btn_switch_beach;
    private Button btn_switch_region;
    private Button btn_rg_time;
    private Button btn_next;
    private Button btn_rg_return; // 三个按钮对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_select_switch);

        mPhone = getIntent().getStringExtra("phone");
        System.out.println("In Switch Activity, mPhone is: " + mPhone);
        // 获取几个按钮的id
        btn_switch_beach = findViewById(R.id.btn_switch_beach);
        btn_switch_region = findViewById(R.id.btn_switch_region);
        btn_next = findViewById(R.id.btn_next);
        btn_rg_time = findViewById(R.id.btn_rg_time);
        btn_rg_return = findViewById(R.id.btn_rg_return);

        // 通过界面跳转实现地图切换，在跳转后终止当前页进程
        btn_switch_beach.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RegisterSelect_SwitchActivity.this, RegisterSelect_MainActivity.class);
                intent.putExtra("phone", mPhone);
                System.out.println("In switch select Activity, mPhone is: " + mPhone);
                startActivity(intent);
                finish();
            }
        });

        btn_switch_region.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 声明对话框的内容
                final String items[] = {"Heisha_A", "Heisha_B", "Heisha_C", "Heisha_D", "Heisha_E", "Heisha_F"};
                AlertDialog dialog = new AlertDialog.Builder(RegisterSelect_SwitchActivity.this)
                        .setTitle("Select a region ")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(RegisterSelect_SwitchActivity.this, items[which] + " is selected.", Toast.LENGTH_SHORT).show();
                                mRegion = items[which];
                                System.out.println("The region is selected successfully:【【 " + mRegion + "】】");
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create();
                dialog.show();
            }
        });

        // 通过按钮打开单选框
        // 通过按钮打开单选框
        btn_rg_time.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 声明对话框的内容
                final String items[] = {"9:00~10:00", "10:00~11:00", "11:00~12:00", "12:00~13:00", "13:00~14:00", "14:00~15:00", "15:00~16:00", "16:00~17:00",
                        "17:00~18:00"};
                final boolean checkedItems[] = {false, false, false, false, false, false, false, false, false};
                AlertDialog dialog = new AlertDialog.Builder(RegisterSelect_SwitchActivity.this)
                        .setTitle("Select period")//设置对话框的标题
                        .setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                checkedItems[which] = isChecked;
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i = 0; i < checkedItems.length; i++) {
                                    if (checkedItems[i]) {
                                        mTime += items[i] + "  ";
                                        Toast.makeText(RegisterSelect_SwitchActivity.this, " select " + items[i], Toast.LENGTH_SHORT).show();
                                    }
                                }
                                dialog.dismiss();
                            }

                        }).create();System.out.println("Successfully select time: 【【" +mTime +" 】】");
                dialog.show();
            }
        });
        // 通过按钮实现结束当前页面
        btn_rg_return.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });


        // 通过按钮，携带选择信息跳转至下一界面
        btn_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RegisterSelect_SwitchActivity.this, RegisterAdditionalServicesActivity.class);
                intent.putExtra("phone", mPhone);
                intent.putExtra("beach", mBeach);
                intent.putExtra("region", mRegion);
                intent.putExtra("time", mTime);
                startActivity(intent);
            }
        });

    }
}