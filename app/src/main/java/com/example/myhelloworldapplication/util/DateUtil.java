package com.example.myhelloworldapplication.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ouyangshen on 2017/9/14.
 * Here it is referenced by huimchien
 */
public class DateUtil {
    public static String getNowDateTime(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date());
    }

    public static String getNowTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

}