package com.example.ftpdemo.util;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

public class Util {

    /**
     * 检查权限
     * @param context 上下文对象
     * @param permissions 需要检查的权限数组
     * @return 检查结果，true为都有权限，false存在没有授权的权限
     */
    public static boolean checkPermission(Context context, String[] permissions) {
        boolean result = true;
        if (permissions != null && permissions.length > 0) {
            for (String permission: permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {
                    result = false;
                }
            }
        }
        return result;
    }
}
