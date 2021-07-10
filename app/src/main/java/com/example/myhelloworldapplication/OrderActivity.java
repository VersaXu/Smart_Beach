package com.example.myhelloworldapplication;

import java.util.ArrayList;
import java.util.HashMap;

import androidx.appcompat.app.AlertDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myhelloworldapplication.bean.CartInfo;
import com.example.myhelloworldapplication.bean.GoodsInfo;
import com.example.myhelloworldapplication.bean.UserInfo;
import com.example.myhelloworldapplication.database.OrderDBHelper;
import com.example.myhelloworldapplication.database.GoodsDBHelper;
import com.example.myhelloworldapplication.database.UserDBHelper;
import com.example.myhelloworldapplication.util.FileUtil;
import com.example.myhelloworldapplication.util.SharedUtil;
import com.example.myhelloworldapplication.util.Utils;


@SuppressLint("SetTextI18n")
public class OrderActivity extends Activity implements OnClickListener {
    private final static String TAG = "OrderActivity";
    private ImageView iv_menu;
    private TextView tv_count;
    private TextView tv_total_price;
    private LinearLayout ll_content;
    private LinearLayout ll_cart;
    private LinearLayout ll_empty;
    private int mCount; // 订单中的商品数量
    private GoodsDBHelper mGoodsHelper; // 声明一个菜单数据库的帮助器
    private OrderDBHelper mOrderHelper; // 声明一个订单数据库的帮助器
//    private boolean debug=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        iv_menu = findViewById(R.id.iv_menu);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_total_price = findViewById(R.id.tv_total_price);
        tv_count = findViewById(R.id.tv_count);
        ll_content = findViewById(R.id.ll_content);
        ll_cart = findViewById(R.id.ll_cart);
        ll_empty = findViewById(R.id.ll_empty);
        iv_menu.setOnClickListener(this);
        findViewById(R.id.btn_order_channel).setOnClickListener(this);
        findViewById(R.id.btn_settle).setOnClickListener(this);
        iv_menu.setVisibility(View.VISIBLE);
        tv_title.setText("Your Order Form");
    }

    // 显示订单图标中的商品数量
    private void showCount(int count){
        mCount = count;
        tv_count.setText("" + mCount);
        if (mCount == 0){
            // 如果顾客的订单中没有任何餐饮选择，那么设置什么都没点为可见
            ll_content.setVisibility(View.GONE);
            ll_cart.removeAllViews();
            ll_empty.setVisibility(View.VISIBLE);
        } else {
            // 否则，显示该顾客的订单餐品
            ll_content.setVisibility(View.VISIBLE);
            ll_empty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v){
        if (v.getId() == R.id.iv_menu){
            //点击了菜单图标
            openOptionsMenu();
        } else if (v.getId() == R.id.btn_order_channel){
            // 点击了点餐按钮，跳转到点餐界面
            Intent intent = new Intent(this, OrderChannelActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btn_settle){
            // 点击了结算按钮
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Sorry");
            builder.setMessage("Payment function is not provided yet, please expect it in the future~");
            builder.setPositiveButton("OK", null);
            builder.create().show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // 从menu_cart.xml中构建菜单界面布局
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_order) { // 点击了菜单项“去菜单点单”
            // 跳转到菜单页面
            Intent intent = new Intent(this, OrderChannelActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_clear) { // 点击了菜单项“清空购物车”
            // 清空购物车数据库
            mOrderHelper.deleteAll();
            ll_cart.removeAllViews();
            // 把最新的商品数量写入共享参数
            SharedUtil.getInstance(this).writeShared("count", "0");
            // 显示最新的商品数量
            showCount(0);
            mCartGoods.clear();
            mGoodsMap.clear();
            Toast.makeText(this, " has been refreshed ", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.menu_return_home) { // 点击了菜单项“Home”
            Intent intent = new Intent(this, FragmentActivity.class);
            startActivity(intent);
        }
        return true;
    }

    // 声明一个根据视图编号查找菜品信息的映射
    private HashMap<Integer, CartInfo> mCartGoods = new HashMap<Integer, CartInfo>();
    // 声明一个触发上下文菜单的视图对象
    private View mContextView;

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        // 保存该商品行的视图，以便删除商品时一块从列表移除该行
        mContextView = v;
        // 从menu_goods.xml中构建菜单界面布局
        getMenuInflater().inflate(R.menu.menu_goods, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        CartInfo info = mCartGoods.get(mContextView.getId());
        int id = item.getItemId();
        if (id == R.id.menu_detail) { // 点击了菜单项“查看商品详情”
            // 跳转到查看商品详情页面
            goDetail(info.goods_id);
        } else if (id == R.id.menu_delete) { // 点击了菜单项“从购物车删除”
            long goods_id = info.goods_id;
            // 从购物车删除商品的数据库操作
            mOrderHelper.delete("goods_id=" + goods_id);
            // 从购物车列表中删除该商品行
            ll_cart.removeView(mContextView);
            // 更新购物车中的商品数量
            int left_count = mCount - info.count;
            for (int i = 0; i < mCartArray.size(); i++) {
                if (goods_id == mCartArray.get(i).goods_id) {
                    left_count = mCount - mCartArray.get(i).count;
                    mCartArray.remove(i);
                    break;
                }
            }
            // 把最新的商品数量写入共享参数
            SharedUtil.getInstance(this).writeShared("count", "" + left_count);
            // 显示最新的商品数量
            showCount(left_count);
            Toast.makeText(this, "has been removed from your order:  " + mGoodsMap.get(goods_id).name, Toast.LENGTH_SHORT).show();
            mGoodsMap.remove(goods_id);
            refreshTotalPrice();
        }
        return true;
    }

    // 跳转到商品详情页面
    private void goDetail(long rowid) {
        Intent intent = new Intent(this, OrderDetailActivity.class);
        intent.putExtra("goods_id", rowid);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 获取共享参数保存的购物车中的商品数量
        mCount = Integer.parseInt(SharedUtil.getInstance(this).readShared("count", "0"));
        showCount(mCount);
        // 获取商品数据库的帮助器对象
        mGoodsHelper = GoodsDBHelper.getInstance(this, 1);
        // 打开商品数据库的写连接
        mGoodsHelper.openWriteLink();
        // 获取购物车数据库的帮助器对象
        mOrderHelper = OrderDBHelper.getInstance(this, 1);
        // 打开购物车数据库的写连接
        mOrderHelper.openWriteLink();
        // 模拟从网络下载商品图片
        downloadGoods();
        // 展示购物车中的商品列表
        showCart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 关闭商品数据库的数据库连接
        mGoodsHelper.closeLink();
        // 关闭购物车数据库的数据库连接
        mOrderHelper.closeLink();
    }

    // 声明一个起始的视图编号
    private int mBeginViewId = 0x7F24FFE0;
    // 声明一个购物车中的商品信息队列
    private ArrayList<CartInfo> mCartArray = new ArrayList<CartInfo>();
    // 声明一个根据商品编号查找商品信息的映射
    private HashMap<Long, GoodsInfo> mGoodsMap = new HashMap<Long, GoodsInfo>();

    // 展示购物车中的商品列表
    private void showCart() {
        // 查询购物车数据库中所有的商品记录
        mCartArray = mOrderHelper.query("1=1");
        Log.d(TAG, "mCartArray.size()=" + mCartArray.size());
        if (mCartArray == null || mCartArray.size() <= 0) {
            return;
        }
        // 移除线性布局ll_cart下面的所有子视图
        ll_cart.removeAllViews();
        // 创建一个标题行的线性布局ll_row
        LinearLayout ll_row = newLinearLayout(LinearLayout.HORIZONTAL, LayoutParams.WRAP_CONTENT);
        ll_row.addView(newTextView(0, 2, Gravity.CENTER, "sample", Color.BLACK, 18));
        ll_row.addView(newTextView(0, 3, Gravity.CENTER, "item", Color.BLACK, 15));
        ll_row.addView(newTextView(0, 1, Gravity.CENTER, "number", Color.BLACK, 18));
        ll_row.addView(newTextView(0, 1, Gravity.CENTER, "price", Color.BLACK, 15));
        ll_row.addView(newTextView(0, 1, Gravity.CENTER, "total", Color.BLACK, 15));
        // 把标题行添加到购物车列表
        ll_cart.addView(ll_row);
        for (int i = 0; i < mCartArray.size(); i++) {
            final CartInfo info = mCartArray.get(i);
            // 根据商品编号查询商品数据库中的商品记录6
            GoodsInfo goods = mGoodsHelper.queryById(info.goods_id);
            Log.d(TAG, "name=" + goods.name + ",price=" + goods.price + ",desc=" + goods.desc);
            mGoodsMap.put(info.goods_id, goods);
            // 创建该商品行的水平线性布局，从左到右依次为商品小图、商品名称与描述、商品数量、商品单价、商品总价。
            ll_row = newLinearLayout(LinearLayout.HORIZONTAL, LayoutParams.WRAP_CONTENT);
            // 设置该线性布局的编号
            ll_row.setId(mBeginViewId + i);
            // 添加商品小图
            ImageView iv_thumb = new ImageView(this);
            LinearLayout.LayoutParams iv_params = new LinearLayout.LayoutParams(
                    0, Utils.dip2px(this, 85), 2);
            iv_thumb.setLayoutParams(iv_params);
            iv_thumb.setScaleType(ScaleType.FIT_CENTER);
            iv_thumb.setImageBitmap(MainApplication.getInstance().mIconMap.get(info.goods_id));
            ll_row.addView(iv_thumb);
            // 添加商品名称与描述
            LinearLayout ll_name = new LinearLayout(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, LayoutParams.MATCH_PARENT, 3);
            ll_name.setLayoutParams(params);
            ll_name.setOrientation(LinearLayout.VERTICAL);
            ll_name.addView(newTextView(-3, 1, Gravity.LEFT, goods.name, Color.BLACK, 15));
            ll_name.addView(newTextView(-3, 1, Gravity.LEFT, goods.desc, Color.GRAY, 12));
            ll_row.addView(ll_name);
            // 添加商品数量、单价和总价
            ll_row.addView(newTextView(1, 1, Gravity.CENTER, "" + info.count, Color.BLACK, 12));
            ll_row.addView(newTextView(1, 1, Gravity.RIGHT,"" + (int) goods.price + "￥", Color.BLACK, 15));
            ll_row.addView(newTextView(1, 1, Gravity.RIGHT, "" + (int) (info.count * goods.price) + "￥", Color.RED, 17));
            // 给商品行添加点击事件
            ll_row.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    goDetail(info.goods_id);
                }
            });
            // 给商品行注册上下文菜单，为防止重复注册，这里先注销再注册
            unregisterForContextMenu(ll_row);
            registerForContextMenu(ll_row);
            mCartGoods.put(ll_row.getId(), info);
            // 往购物车列表添加该商品行
            ll_cart.addView(ll_row);
        }
        // 重新计算购物车中的商品总金额
        refreshTotalPrice();
    }

    // 重新计算购物车中的商品总金额
    private void refreshTotalPrice() {
        int total_price = 0;
        for (CartInfo info : mCartArray) {
            GoodsInfo goods = mGoodsMap.get(info.goods_id);
            total_price += goods.price * info.count;
        }
        tv_total_price.setText("" + total_price + "￥");
    }

    // 创建一个线性布局的框架
    private LinearLayout newLinearLayout(int orientation, int height) {
        LinearLayout ll_new = new LinearLayout(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, height);
        ll_new.setLayoutParams(params);
        ll_new.setOrientation(orientation);
        ll_new.setBackgroundColor(Color.WHITE);
        return ll_new;
    }

    // 创建一个文本视图的模板
    private TextView newTextView(int height, float weight, int gravity, String text, int textColor, int textSize) {
        TextView tv_new = new TextView(this);
        if (height == -3) {  // 垂直排列
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, 0, weight);
            tv_new.setLayoutParams(params);
        } else {  // 水平排列
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, (height == 0) ? LayoutParams.WRAP_CONTENT : LayoutParams.MATCH_PARENT, weight);
            tv_new.setLayoutParams(params);
        }
        tv_new.setText(text);
        tv_new.setTextColor(textColor);
        tv_new.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv_new.setGravity(Gravity.CENTER | gravity);
        return tv_new;
    }

    private String mFirst = "true"; // 是否首次打开
    // 模拟网络数据，初始化数据库中的商品信息
    private void downloadGoods() {
        // 获取共享参数保存的是否首次打开参数
        mFirst = SharedUtil.getInstance(this).readShared("first", "true");
        // 获取当前App的私有存储路径
        String path = MainApplication.getInstance().getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS).toString() + "/";
        if (mFirst.equals("true")) { // 如果是首次打开
            ArrayList<GoodsInfo> goodsList = GoodsInfo.getDefaultList();
            for (int i = 0; i < goodsList.size(); i++) {
                GoodsInfo info = goodsList.get(i);
                // 往商品数据库插入一条该商品的记录
                long rowid = mGoodsHelper.insert(info);
                info.rowid = rowid;
                // 往全局内存写入商品小图
                Bitmap thumb = BitmapFactory.decodeResource(getResources(), info.thumb);
                MainApplication.getInstance().mIconMap.put(rowid, thumb);
                String thumb_path = path + rowid + "_s.jpg";
                FileUtil.saveImage(thumb_path, thumb);
                info.thumb_path = thumb_path;
                // 往SD卡保存商品大图
                Bitmap pic = BitmapFactory.decodeResource(getResources(), info.pic);
                String pic_path = path + rowid + ".jpg";
                FileUtil.saveImage(pic_path, pic);
                pic.recycle();
                info.pic_path = pic_path;
                // 更新商品数据库中该商品记录的图片路径
                mGoodsHelper.update(info);
            }
        } else { // 不是首次打开
            // 查询商品数据库中所有商品记录
            ArrayList<GoodsInfo> goodsArray = mGoodsHelper.query("1=1");
            for (int i = 0; i < goodsArray.size(); i++) {
                GoodsInfo info = goodsArray.get(i);
                // 从指定路径读取图片文件的位图数据
                Bitmap thumb = BitmapFactory.decodeFile(info.thumb_path);
                // 把该位图对象保存到应用实例的全局变量中
                MainApplication.getInstance().mIconMap.put(info.rowid, thumb);
            }
        }
        // 把是否首次打开写入共享参数
        SharedUtil.getInstance(this).writeShared("first", "false");
    }

}