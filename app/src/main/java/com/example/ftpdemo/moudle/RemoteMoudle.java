package com.example.ftpdemo.moudle;

import android.text.TextUtils;
import android.util.Log;

import com.example.ftpdemo.bean.FTPBean;
import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.util.Constant;
import com.example.ftpdemo.util.MyRemoteFileAsyncTask;
import com.example.ftpdemo.util.SPUtil;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RemoteMoudle extends BaseMoudle {
    @Override
    public void getFileList(GetFileListCallback callback) {
        String fileData = SPUtil.getInstance(null).getFTPParams();
        List<FileBean> data = new ArrayList<>();
        if (!TextUtils.isEmpty(fileData)) {
            try {
                JSONArray array = new JSONArray(fileData);
                for (int i = 0 ; i < array.length() ; i ++) {
                    JSONObject obj = array.optJSONObject(i);
                    if (obj == null) {
                        continue;
                    }
                    FTPBean ftpBean = new FTPBean(obj);
                    FileBean fileBean = new FileBean(ftpBean);
                    data.add(fileBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        FileBean bean = new FileBean();
        bean.setAddFtp(true);
        bean.setDir(true);
        bean.setFileName("添加ftp地址");
        bean.setPath(Constant.REMOTE_FILE_ROOT_PATH);
        data.add(bean);
        callback.onCallback(data);
    }

    @Override
    public void getFileByPath(final FileBean bean, GetFileListCallback callback) {
        if (bean.getFtpBean() == null) {
            getFileList(callback);
            return;
        }
        MyRemoteFileAsyncTask task = new MyRemoteFileAsyncTask(callback);
        task.execute(bean);
    }

    @Override
    public List<String> getfilePathData() {
        List<String> pathData = new ArrayList<>();
        pathData.add("FTP");
        return pathData;
    }
}
