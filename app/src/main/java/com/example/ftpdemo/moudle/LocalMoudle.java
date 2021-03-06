package com.example.ftpdemo.moudle;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.util.task.MyLocalFileAsyncTask;
import com.example.ftpdemo.util.task.MyUploadFileTask;

import java.util.ArrayList;
import java.util.List;

public class LocalMoudle extends BaseMoudle {
    @Override
    public void getFileList(GetFileListCallback callback) {
        //本地数据查询
        String path = Environment.getExternalStorageDirectory().getPath();
        getFileByPath(new FileBean(path), callback);
    }

    @Override
    public synchronized void getFileByPath(FileBean fileBean, GetFileListCallback callback) {
        MyLocalFileAsyncTask task = new MyLocalFileAsyncTask(callback);
        task.execute(fileBean);
    }

    @Override
    public List<String> getfilePathData() {
        List<String> filePath = new ArrayList<>();
        String rootPath = Environment.getExternalStorageDirectory().getPath();
        Log.i(getClass().getSimpleName(), "rootpath = " + rootPath);
        String[] paths = rootPath.split("\\/");
        for (int i = 0; i < paths.length; i ++) {
            String path = paths[i];
            if (TextUtils.isEmpty(path)) {
                path = "/";
            }
            filePath.add(path);
        }
        return filePath;
    }

    @Override
    public void dealFile(FileBean bean, OnLoadFileResultListener callback) {
        //文件上传
        Log.i(getClass().getSimpleName(), "upload file = " + bean.getPath());
        MyUploadFileTask task = new MyUploadFileTask(callback);
        task.execute(bean);
    }
}
