package com.example.ftpdemo.util.task;

import android.os.AsyncTask;

import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.moudle.BaseMoudle.OnLoadFileResultListener;
import com.example.ftpdemo.util.Constant;
import com.example.ftpdemo.util.NotificationUtil;

public abstract class BaseLoadFileAsyncTask extends AsyncTask<FileBean, Integer, Boolean> {

    OnLoadFileResultListener listener;

    protected int deal_file_id = Constant.UPLOAD_AND_DOWNLOAD_FILE_ID;

    protected String fileName;

    public BaseLoadFileAsyncTask(OnLoadFileResultListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        listener.onLoadFileResultListener(aBoolean);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        NotificationUtil.getInstance(null).notifyNotificationProgress(
                deal_file_id, progress, fileName
        );
    }

    protected int getNotificationId() {
        Constant.UPLOAD_AND_DOWNLOAD_FILE_ID += 1;
        return Constant.UPLOAD_AND_DOWNLOAD_FILE_ID;
    }
}
