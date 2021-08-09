package com.example.ftpdemo.util.task;

import android.text.TextUtils;
import android.util.Log;

import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.moudle.BaseMoudle;
import com.example.ftpdemo.util.Constant;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 读取远程文件列表
 */
public class MyRemoteFileAsyncTask extends BaseReadFileAsyncTask {
    public MyRemoteFileAsyncTask(BaseMoudle.GetFileListCallback callback) {
        super(callback);
    }

    @Override
    protected List<FileBean> doInBackground(FileBean... fbs) {
        FileBean bean = fbs[0];
        FTPClient client = new FTPClient();
        List<FileBean> fileBeans = new ArrayList<>();
        try {
            client.connect(
                    bean.getFtpBean().getIp(),
                    Integer.parseInt(bean.getFtpBean().getPort())
            );
            boolean isLoginSuccess = client.login(
                    bean.getFtpBean().getName(),
                    bean.getFtpBean().getPass()
            );
            if (isLoginSuccess) {
                FTPFile[] files;
                String workingdirectoryStr;
                if (Constant.REMOTE_FILE_ROOT_PATH.equals(bean.getPath())) {
                    files = client.listFiles();
                    workingdirectoryStr = client.printWorkingDirectory();
                } else {
                    if (TextUtils.isEmpty(bean.getFileName())) {
                        workingdirectoryStr = bean.getPath();
                    } else {
                        if (File.separator.equals(bean.getPath())) {
                            workingdirectoryStr = bean.getPath() + bean.getFileName();
                        } else {
                            workingdirectoryStr = bean.getPath() +
                                    File.separator +
                                    bean.getFileName();
                        }
                    }
                    files = client.listFiles(workingdirectoryStr);
                }
                Log.e("RemoteMoudle", "workingdirectory = " + workingdirectoryStr);
                if (files != null && files.length > 0) {
                    Log.i("RemoteMoudle", "remote files result = " + files.length);
                    for (FTPFile file: files) {
                        FileBean fileBean = new FileBean();
                        fileBean.setFileName(file.getName());
                        fileBean.setPath(workingdirectoryStr);
                        fileBean.setDir(file.isDirectory());
                        fileBean.setFtpBean(bean.getFtpBean());
                        fileBeans.add(fileBean);
                    }
                    Collections.sort(fileBeans);
                }
            }
        } catch (IOException e) {
            Log.i("RemoteMoudle", "get remote file list error");
        } finally {
            try {
                if (client.isConnected()) {
                    client.logout();
                    client.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileBeans;
    }
}
