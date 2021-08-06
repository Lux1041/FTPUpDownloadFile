package com.example.ftpdemo.util.task;

import android.os.AsyncTask;

import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.moudle.BaseMoudle.OnLoadFileResultListener;

public abstract class BaseLoadFileAsyncTask extends AsyncTask<FileBean, Integer, Boolean> {
    OnLoadFileResultListener listener;
    public BaseLoadFileAsyncTask(OnLoadFileResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        listener.onLoadFileResultListener(aBoolean);
    }
}
