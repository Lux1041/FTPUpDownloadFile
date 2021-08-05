package com.example.ftpdemo.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.moudle.BaseMoudle;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyRemoteFileAsyncTask extends BaseFileAsyncTask {
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
                            workingdirectoryStr = bean.getPath() + File.separator + bean.getFileName();
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
                        Log.i("RemoteMoudle", "file params begin =======================");
                        Log.i("RemoteMoudle", "file group = " + file.getGroup());
                        Log.i("RemoteMoudle", "file link = " + file.getLink());
                        Log.i("RemoteMoudle", "file name = " + file.getName());
                        Log.i("RemoteMoudle", "file rawListing = " + file.getRawListing());
                        Log.i("RemoteMoudle", "file user = " + file.getUser());
                        Log.i("RemoteMoudle", "file hardlinkcount = " + file.getHardLinkCount());
                        Log.i("RemoteMoudle", "file size = " + file.getSize());
                        Log.i("RemoteMoudle", "file timestamp = " + file.getTimestamp());
                        Log.i("RemoteMoudle", "file type = " + file.getType());
                        Log.i("RemoteMoudle", "file isDir = " + file.isDirectory());
                        Log.i("RemoteMoudle", "file toFormattedstring = " + file.toFormattedString());
                        Log.i("RemoteMoudle", "file params  end  =======================");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
