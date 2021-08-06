package com.example.ftpdemo;

import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ftpdemo.util.Constant;
import com.example.ftpdemo.util.observer.ObserverManager;

public abstract class BaseActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CODE = 1000;

    protected void requestPermissions(String[] permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean result = true;
        for (int grant : grantResults) {
            if (grant == PackageManager.PERMISSION_DENIED) {
                result = false;
            }
        }
        if (result) {
            ObserverManager.sendMessage(Constant.PERMISSION_GET_STATUS, null);
        }
    }
}
