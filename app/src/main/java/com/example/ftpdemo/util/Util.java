package com.example.ftpdemo.util;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;

import com.example.ftpdemo.R;
import com.example.ftpdemo.bean.FTPBean;
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

    public static void showInputFTPDialog(Context context, OnDialogConfirmClickListener listener){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);
        View view = LayoutInflater.from(context).inflate(
                R.layout.dialog_for_input_ftp_params, null
        );
        EditText et_input_ip = view.findViewById(R.id.et_input_ip);
        EditText et_input_port = view.findViewById(R.id.et_input_port);
        EditText et_input_name = view.findViewById(R.id.et_input_name);
        EditText et_input_pass = view.findViewById(R.id.et_input_pass);
        builder.setView(view);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ipStr = et_input_ip.getText().toString().trim();
                String portStr = et_input_port.getText().toString().trim();
                String userName = et_input_name.getText().toString().trim();
                String pass = et_input_pass.getText().toString().trim();
                if (TextUtils.isEmpty(ipStr) ||
                        TextUtils.isEmpty(portStr) ||
                        TextUtils.isEmpty(userName) ||
                        TextUtils.isEmpty(pass)) {
                    return;
                }
                if (listener != null) {
                    FTPBean bean = new FTPBean(ipStr, portStr, userName, pass);
                    listener.onDialogConfirmClickListener(bean);
                }
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public interface OnDialogConfirmClickListener {
        void onDialogConfirmClickListener(FTPBean ftpBean);
    }
}
