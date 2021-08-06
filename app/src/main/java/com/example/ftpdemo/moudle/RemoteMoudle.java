package com.example.ftpdemo.moudle;

import com.example.ftpdemo.bean.FTPBean;
import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.util.Constant;
import com.example.ftpdemo.util.task.MyDownloadFileTask;
import com.example.ftpdemo.util.task.MyRemoteFileAsyncTask;
import com.example.ftpdemo.util.Util;

import java.util.ArrayList;
import java.util.List;

public class RemoteMoudle extends BaseMoudle {
    @Override
    public void getFileList(GetFileListCallback callback) {
        List<FileBean> data = new ArrayList<>();
        List<FTPBean> ftpbeans = Util.getLocalFTPServices();
        for (FTPBean b : ftpbeans) {
            FileBean fileBean = new FileBean(b);
            data.add(fileBean);
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

    @Override
    public void dealFile(FileBean bean, OnLoadFileResultListener callback) {
        //文件下载
        MyDownloadFileTask task = new MyDownloadFileTask(callback);
        task.execute(bean);
    }
}
