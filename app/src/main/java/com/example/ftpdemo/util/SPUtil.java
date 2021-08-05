package com.example.ftpdemo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ftpdemo.bean.FTPBean;
import com.example.ftpdemo.bean.FileBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class SPUtil {

    private static final String SHARE_NAME = "com.example.ftpdemo";

    private static volatile SPUtil INSTANCE;

    private Context mContext;

    private SPUtil(Context context) {
        mContext = context;
    }

    public static SPUtil getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SPUtil.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SPUtil(context);
                }
            }
        }
        return INSTANCE;
    }

    public void setFTPParams(List<FileBean> beans) {
        JSONArray array = new JSONArray();
        for (FileBean b : beans) {
            if (b.getFtpBean() == null) {
                continue;
            }
            array.put(b.getFtpBean().toJsonObject());
        }
        SharedPreferences.Editor editor =
                mContext.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE)
                .edit();
        editor.putString(Constant.FTP_SERVER_PARAMS_KEY, array.toString());
        editor.apply();
    }

    public String getFTPParams() {
        SharedPreferences sp = mContext.getSharedPreferences(SHARE_NAME, Context.MODE_PRIVATE);
        String dataStr = sp.getString(Constant.FTP_SERVER_PARAMS_KEY, "");
        return dataStr;
    }
}
