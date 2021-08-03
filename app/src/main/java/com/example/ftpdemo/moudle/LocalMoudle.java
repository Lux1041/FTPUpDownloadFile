package com.example.ftpdemo.moudle;

import android.os.Environment;
import android.util.Log;

import com.example.ftpdemo.bean.FileBean;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class LocalMoudle extends BaseMoudle {
    @Override
    public List<FileBean> getFileList() {
        //本地数据查询
        Log.i(getClass().getSimpleName(), "begin time = " + System.currentTimeMillis());
        List<FileBean> fileBeans = new ArrayList<>();
        File root = Environment.getExternalStorageDirectory();
        File[] files = root.listFiles();
        for (File file : files) {
            String name = file.getName();
            if (name.startsWith(".")) {//隐藏文件暂不显示
                continue;
            }
            FileBean bean = new FileBean();
            bean.setDir(file.isDirectory());
            bean.setFileName(file.getName());
            bean.setPath(file.getPath());
            fileBeans.add(bean);
        }
        Log.i(getClass().getSimpleName(), "end time = " + System.currentTimeMillis());
        Collections.sort(fileBeans);
        return fileBeans;
    }
}
