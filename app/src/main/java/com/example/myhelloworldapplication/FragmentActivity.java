package com.example.myhelloworldapplication;

import com.example.myhelloworldapplication.fragment.TabFirstFragment;
import com.example.myhelloworldapplication.fragment.TabSecondFragment;
import com.example.myhelloworldapplication.fragment.TabThirdFragment;
import com.example.myhelloworldapplication.fragment.TabForthFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.view.View;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class FragmentActivity extends AppCompatActivity {
    private static final String TAG = "TabFragmentActivity";
    private FragmentTabHost tabHost; // 声明一个碎片标签栏对象
    public static String phone; // 声明用户的手机号码作为用户id
    private static int id = 1200;// notification channel id



//    public String getPhone(){
//        // 获取当前用户手机号码
//        phone = getIntent().getStringExtra("phone");
//        return phone;
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        phone = getIntent().getStringExtra("phone");
        System.out.println("In Fragment Activity, mPhone is: " + phone);

        Bundle bundle = new Bundle();
        bundle.putString("tag", TAG); //往包裹里添加名为tag的标记
        // 从布局文件中获取名为tabhost的标签栏
        tabHost = findViewById(android.R.id.tabhost);
        // 把实际内容框架添加进碎片标签栏
        tabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        // 往标签栏添加第一个标签，内容展示TabFirstFragment
        tabHost.addTab(getTabView(R.string.menu_first, R.drawable.tab_first_selector), TabFirstFragment.class, bundle);
        // 往标签栏添加第二个标签，内容展示TabSecondFragment
        tabHost.addTab(getTabView(R.string.menu_second, R.drawable.tab_second_selector), TabSecondFragment.class, bundle);
        // 往标签栏添加第三个标签，内容展示TabThirdFragment
        tabHost.addTab(getTabView(R.string.menu_third, R.drawable.tab_third_selector), TabThirdFragment.class, bundle);
        // 往标签栏添加第四个标签，内容展示TabForthFragment
        tabHost.addTab(getTabView(R.string.menu_forth, R.drawable.tab_forth_selector), TabForthFragment.class, bundle);

        // 不显示各标签之间的分割线
        tabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

    }

    // 根据字符串和图标的资源编号，获得相对应的标签规格
    private TabSpec getTabView(int textId, int imgId){
        // 获取字符串对象
        String text = getResources().getString(textId);
        // 获取图像对象
        Drawable drawable = getResources().getDrawable(imgId);
        // 设置图形边界和大小，这一步必须执行，否则无法显示图标
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        // 根据item_tabbar.xml生成按钮对象
        View item_tabbar = getLayoutInflater().inflate(R.layout.item_tabbar, null);
        TextView tv_item = item_tabbar.findViewById(R.id.tv_item_tabbar);
        tv_item.setText(text);
        // 在文字上方显示标签的图标
        tv_item.setCompoundDrawables(null, drawable, null, null);
        // 生成并返回该标签按钮对应的标签规格
        return tabHost.newTabSpec(text).setIndicator(item_tabbar);
    }

    private String createNotificationChannel (String channelID, String channelNAME,int level){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel(channelID, channelNAME, level);
//            //设置提示音
//            channel.setSound();
            // 开启指示灯
            channel.enableLights(true);
            //开启震动
            channel.enableVibration(true);
//            //设置锁屏展示
//            channel.setLockscreenVisibility();
            //设置渠道描述
            channel.setDescription("A notification channel for warning the user.");

            manager.createNotificationChannel(channel);
            return channelID;
        } else {
            return null;
        }
    }
}