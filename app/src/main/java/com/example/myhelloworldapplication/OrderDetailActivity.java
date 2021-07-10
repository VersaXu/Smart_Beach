package com.example.myhelloworldapplication;

//import com.example.myhelloworldapplication.bean.CartInfo_1;
//import com.example.myhelloworldapplication.bean.GoodsInfo_1;
//import com.example.myhelloworldapplication.database.OrderDBHelper_1;
//import com.example.myhelloworldapplication.database.GoodsDBHelper_1;

import com.example.myhelloworldapplication.bean.CartInfo;
import com.example.myhelloworldapplication.bean.GoodsInfo;
import com.example.myhelloworldapplication.database.GoodsDBHelper;
import com.example.myhelloworldapplication.database.OrderDBHelper;
import com.example.myhelloworldapplication.util.DateUtil;
import com.example.myhelloworldapplication.util.SharedUtil;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SetTextI18n")
public class OrderDetailActivity extends AppCompatActivity implements OnClickListener{
    private TextView tv_title;
    private TextView tv_count;
    private TextView tv_goods_price;
    private TextView tv_goods_desc;
    private ImageView iv_goods_pic;
    private Button btn_return; // 返回按钮
    private int mCount; // 购物车中的商品数量
    private long mGoodsId; // 当前商品的商品编号
    private GoodsDBHelper mGoodsHelper; // 声明一个商品数据库的帮助器对象
    private OrderDBHelper mOrderHelper; // 声明一个购物车数据库的帮助器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        tv_title = findViewById(R.id.tv_title);
        tv_count = findViewById(R.id.tv_count);
        tv_goods_price = findViewById(R.id.tv_goods_price);
        tv_goods_desc = findViewById(R.id.tv_goods_desc);
        iv_goods_pic = findViewById(R.id.iv_goods_pic);
        btn_return = findViewById(R.id.btn_return);
        findViewById(R.id.iv_cart).setOnClickListener(this);
        findViewById(R.id.btn_add_cart).setOnClickListener(this);
        // 获取共享参数保存的购物车中的商品数量
        mCount = Integer.parseInt(SharedUtil.getInstance(this).readShared("count", "0"));
        tv_count.setText("" + mCount);

        // 通过按钮实现结束当前页面
        btn_return.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_cart) { // 点击了购物车图标
            // 跳转到购物车页面
            Intent intent = new Intent(this, OrderActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_add_cart) { // 点击了“添加”按钮
            // 把该商品添加到购物车
            addToCart(mGoodsId);
            Toast.makeText(this, "Successfully add to your order ", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.btn_return) { // 点击了return按钮
            // 回到上一个界面
            finish();
        }
    }

    // 把指定编号的商品添加到购物车
    private void addToCart(long goods_id) {
        mCount++;
        tv_count.setText("" + mCount);
        // 把购物车中的商品数量写入共享参数
        SharedUtil.getInstance(this).writeShared("count", "" + mCount);
        // 根据商品编号查询购物车中的商品记录
        CartInfo info = mOrderHelper.queryByGoodsId(goods_id);
        if (info != null) { // 购物车已存在该商品记录
            info.count++; // 该商品的数量加一
            info.update_time = DateUtil.getNowDateTime("");
            // 更新购物车数据库中的商品记录信息
            mOrderHelper.update(info);
        } else { // 购物车不存在该商品记录
            info = new CartInfo();
            info.goods_id = goods_id;
            info.count = 1;
            info.update_time = DateUtil.getNowDateTime("");
            // 往购物车数据库中添加一条新的商品记录
            mOrderHelper.insert(info);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 获取商品数据库的帮助器对象
        mGoodsHelper = GoodsDBHelper.getInstance(this, 1);
        // 打开商品数据库的读连接
        mGoodsHelper.openReadLink();
        // 获取购物车数据库的帮助器对象
        mOrderHelper = OrderDBHelper.getInstance(this, 1);
        // 打开购物车数据库的写连接
        mOrderHelper.openWriteLink();
        // 展示商品详情
        showDetail();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 关闭商品数据库的数据库连接
        mGoodsHelper.closeLink();
        // 关闭购物车数据库的数据库连接
        mOrderHelper.closeLink();
    }

    private void showDetail() {
        // 获取前一个页面传来的商品编号
        mGoodsId = getIntent().getLongExtra("goods_id", 0L);
        if (mGoodsId > 0) {
            // 根据商品编号查询商品数据库中的商品记录
            GoodsInfo info = mGoodsHelper.queryById(mGoodsId);
            tv_title.setText(info.name);
            tv_goods_desc.setText(info.desc);
            tv_goods_price.setText("" + info.price + "￥");
            // 从指定路径读取图片文件的位图数据
            Bitmap pic = BitmapFactory.decodeFile(info.pic_path);
            iv_goods_pic.setImageBitmap(pic);
        }
    }

}