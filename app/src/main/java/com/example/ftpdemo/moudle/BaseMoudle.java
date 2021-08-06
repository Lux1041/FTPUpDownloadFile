package com.example.ftpdemo.moudle;

import com.example.ftpdemo.bean.FileBean;

import java.util.List;

public abstract class BaseMoudle {

    public abstract void getFileList(GetFileListCallback callback);

    public abstract void getFileByPath(FileBean bean, GetFileListCallback callback);

    public abstract List<String> getfilePathData();

    public abstract void dealFile(FileBean bean, OnLoadFileResultListener callback);


    public interface GetFileListCallback{
        void onCallback(List<FileBean> data);
    }

    public interface OnLoadFileResultListener{
        void onLoadFileResultListener(boolean result);
    }
}
