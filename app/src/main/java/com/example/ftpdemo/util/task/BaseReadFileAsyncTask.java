package com.example.ftpdemo.util.task;

import android.os.AsyncTask;

import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.moudle.BaseMoudle;

import java.util.List;

public abstract class BaseReadFileAsyncTask extends AsyncTask<FileBean, Void, List<FileBean>> {

    protected BaseMoudle.GetFileListCallback callback;

    public BaseReadFileAsyncTask(BaseMoudle.GetFileListCallback callback) {
        this.callback = callback;
    }

    @Override
    protected void onPostExecute(List<FileBean> fileBeans) {
        callback.onCallback(fileBeans);
    }
}
