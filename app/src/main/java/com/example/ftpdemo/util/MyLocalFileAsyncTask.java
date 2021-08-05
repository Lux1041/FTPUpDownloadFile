package com.example.ftpdemo.util;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.moudle.BaseMoudle;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MyLocalFileAsyncTask extends BaseFileAsyncTask {

    public MyLocalFileAsyncTask(BaseMoudle.GetFileListCallback callback) {
        super(callback);
    }

    @Override
    protected List<FileBean> doInBackground(FileBean... fbs) {
        FileBean fileBean = fbs[0];
        Log.i(getClass().getSimpleName(), "begin time = " + System.currentTimeMillis());
        List<FileBean> fileBeans = new ArrayList<>();
        File root = new File(fileBean.getPath());
        File[] files = root.listFiles();
        if (files != null) {
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
        }
        return fileBeans;
    }
}
