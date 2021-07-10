package com.example.myhelloworldapplication.util;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionUtil {
    private final static String TAG = "PermissionUtil";

    // 检查某个权限， true表示已启用该权限，false表示未启用
    public static boolean checkPermission(Activity act, String permission, int requestCode) {
        Log.d(TAG, "checkPermission: " + permission);
        boolean result = true;
        // Android 6.0 以上系统进行校验
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 检查app是否开启名为permission的权限
            int check = ContextCompat.checkSelfPermission(act, permission);
            if (check != PackageManager.PERMISSION_GRANTED) {
                // 若未开启，让用户选择是否开启权限
                ActivityCompat.requestPermissions(act, new String[]{permission}, requestCode);
                result = false;
            }
        }
        return result;
    }

    // 检查是否允许修改系统设置
    public static boolean checkWriteSettings(Activity act, int requestCode) {
        Log.d(TAG, "checkWriteSettings: ");
        boolean result = true;
        // 只对Android6.0及Android7.0系统进行校验
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            // 检查当前App是否允许修改系统设置
            if(!Settings.System.canWrite(act)){
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                        Uri.parse("package:" + act.getPackageName()));
                act.startActivityForResult(intent, requestCode);
                Toast.makeText(act, "需要允许设置权限才能调节亮度噢", Toast.LENGTH_SHORT).show();
                result = false;
            }
        }
        return result;
    }

    public static void goActivity(Context ctx, Class<?> cls) {
        Intent intent = new Intent(ctx, cls);
        ctx.startActivity(intent);
    }
}
