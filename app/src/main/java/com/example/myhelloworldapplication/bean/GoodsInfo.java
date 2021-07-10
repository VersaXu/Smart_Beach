package com.example.myhelloworldapplication.bean;

import com.example.myhelloworldapplication.R;

import java.util.ArrayList;

public class GoodsInfo {
    public long rowid; // 行号
    public int xuhao; // 序号
    public String name; // 名称
    public String desc; // 描述
    public float price; // 价格
    public String thumb_path; // 小图的保存路径
    public String pic_path; // 大图的保存路径
    public int thumb; // 小图的资源编号
    public int pic; // 大图的资源编号

    public GoodsInfo() {
        rowid = 0L;
        xuhao = 0;
        name = "";
        desc = "";
        price = 0;
        thumb_path = "";
        pic_path = "";
        thumb = 0;
        pic = 0;
    }

    // 声明一个商品的名称数组
    private static String[] mNameArray = {
            "Blue mountain coffee", "Cappucino", "Ice Americano", "Ice coffee latte", "Mocha",
            "Coca cola", "Sprite", "German beer", "Manhattan", "Margarita", "Martini", "Mojito", "Schwarzbier",
            "Orange juice", "Mixed juice", "Bottle of water"
    };
    // 声明一个商品的描述数组
    private static String[] mDescArray = {
            "Top coffee raw materials, Originality drinking",
            "Top coffee raw materials, Originality drinking",
            "Top coffee raw materials, Originality drinking",
            "Top coffee raw materials, Originality drinking",
            "Top coffee raw materials, Originality drinking",
            "250ml",
            "250ml",
            "Germany imported classic beer, 500ml",
            "Master on-site bartending",
            "Master on-site bartending",
            "Master on-site bartending",
            "Master on-site bartending",
            "Master on-site bartending",
            "Freshly squeezed orange",
            "Freshly squeezed apple, orange and kiwi fruit",
            "750ml"
    };
    // 声明一个商品的价格数组
    private static float[] mPriceArray = {88,98,108,128,128,5,5,12,99,99,149,149,199,15,15,8};
    // 声明一个商品的小图数组
    private static int[] mThumbArray = {
            R.drawable.blue_mountain_coffee_s, R.drawable.cappucino_s, R.drawable.ice_americano_s,
            R.drawable.ice_coffee_latte_s, R.drawable.mocha_coffee_s, R.drawable.coca_cola_s, R.drawable.sprite_s,
            R.drawable.german_beer_s, R.drawable.manhattan_s, R.drawable.margarita_s, R.drawable.martini_s,
            R.drawable.mojito_s, R.drawable.schwarzbier_s, R.drawable.orange_juice_s, R.drawable.mixed_juice_s,
            R.drawable.water_bottle_s
    };
    // 声明一个商品的大图数组
    private static int[] mPicArray = {
            R.drawable.blue_mountain_coffee, R.drawable.cappucino, R.drawable.ice_americano,
            R.drawable.ice_coffee_latte, R.drawable.mocha_coffee, R.drawable.coca_cola, R.drawable.sprite,
            R.drawable.german_beer, R.drawable.manhattan, R.drawable.margarita, R.drawable.martini,
            R.drawable.mojito, R.drawable.schwarzbier, R.drawable.orange_juice, R.drawable.mixed_juice,
            R.drawable.water_bottle
    };

    // 获取默认的手机信息列表
    public static ArrayList<GoodsInfo> getDefaultList() {
        ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
        for (int i = 0; i < mNameArray.length; i++) {
            GoodsInfo info = new GoodsInfo();
            info.name = mNameArray[i];
            info.desc = mDescArray[i];
            info.price = mPriceArray[i];
            info.thumb = mThumbArray[i];
            info.pic = mPicArray[i];
            goodsList.add(info);
        }
        return goodsList;
    }

}