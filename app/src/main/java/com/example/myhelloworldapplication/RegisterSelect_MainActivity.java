package com.example.myhelloworldapplication;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class RegisterSelect_MainActivity extends AppCompatActivity {
    private static final String TAG = "Register select";
    private String mPhone;
    private String mBeach = "Luhuan";
    private String mRegion;
    private String mTime;

    //    private RadioGroup rg_select_map; // 声明一个单选组对象
//    private RadioButton rb_heisha;// 声明两个单选按钮对象
//    private RadioButton rb_zhupai;
    private Button btn_switch_beach;
    private Button btn_switch_region;
    private Button btn_next;
    private Button btn_rg_return;
    private Button btn_rg_time;
    // 四个按钮对象


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_select_main);

//         获取用户手机号码
        mPhone = FragmentActivity.phone;
        System.out.println("In main select Activity, mPhone is: " + mPhone);
        // 获取几个按钮的id
        btn_switch_beach = findViewById(R.id.btn_switch_beach);
        btn_switch_region = findViewById(R.id.btn_switch_region);
        btn_next = findViewById(R.id.btn_next);
        btn_rg_return = findViewById(R.id.btn_rg_return);
        btn_rg_time = findViewById(R.id.btn_rg_time);

        // 通过界面跳转实现地图切换，在跳转后终止当前页进程
        btn_switch_beach.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(RegisterSelect_MainActivity.this, RegisterSelect_SwitchActivity.class);
                intent.putExtra("phone", mPhone);
                startActivity(intent);
                finish();
            }
        });

        btn_switch_region.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 声明对话框的内容
                final String items[] = {"Zhuwan_A", "Zhuwan_B", "Zhuwan_C", "Zhuwan_D", "Zhuwan_E", "Zhuwan_F"};
                AlertDialog dialog = new AlertDialog.Builder(RegisterSelect_MainActivity.this)
                        .setTitle("Select a region ")
                        .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(RegisterSelect_MainActivity.this, items[which] + " is selected.", Toast.LENGTH_SHORT).show();
                                mRegion = items[which].toString();
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

        // 通过按钮打开复选框
        btn_rg_time.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // 声明对话框的内容
                final String items[] = {"9:00~10:00", "10:00~11:00", "11:00~12:00", "12:00~13:00", "13:00~14:00", "14:00~15:00", "15:00~16:00", "16:00~17:00",
                        "17:00~18:00"};
                final boolean checkedItems[] = {false, false, false, false, false, false, false, false, true};
                AlertDialog dialog = new AlertDialog.Builder(RegisterSelect_MainActivity.this)
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
                                        Toast.makeText(RegisterSelect_MainActivity.this, " select " + items[i], Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(RegisterSelect_MainActivity.this, RegisterAdditionalServicesActivity.class);
                intent.putExtra("phone", mPhone);
                intent.putExtra("beach", mBeach);
                intent.putExtra("region", mRegion);
                intent.putExtra("time", mTime);
                startActivity(intent);
            }
        });

    }
}
