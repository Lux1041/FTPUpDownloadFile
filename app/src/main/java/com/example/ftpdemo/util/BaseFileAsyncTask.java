package com.example.ftpdemo.util;

import android.os.AsyncTask;

import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.moudle.BaseMoudle;

import java.util.List;

public abstract class BaseFileAsyncTask extends AsyncTask<FileBean, Void, List<FileBean>> {

    protected BaseMoudle.GetFileListCallback callback;

    public BaseFileAsyncTask(BaseMoudle.GetFileListCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(List<FileBean> fileBeans) {
        callback.onCallback(fileBeans);
    }
}
