package com.example.ftpdemo.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.ftpdemo.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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

    public static void showInputFTPDialog(Context context){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_for_input_ftp_params, null
        );
        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
