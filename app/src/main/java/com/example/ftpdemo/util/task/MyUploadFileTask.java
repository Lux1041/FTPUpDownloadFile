package com.example.ftpdemo.util.task;

import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.moudle.BaseMoudle;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 文件上传
 */
public class MyUploadFileTask extends BaseLoadFileAsyncTask {


    public MyUploadFileTask(BaseMoudle.OnLoadFileResultListener listener) {
        super(listener);
    }

    @Override
    protected Boolean doInBackground(FileBean... fileBeans) {
        FileBean bean = fileBeans[0];
        FTPClient client = new FTPClient();
        FileInputStream fis = null;
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
                String remotePath = client.printWorkingDirectory() +
                        File.separator +
                        bean.getFileName();
                FTPFile[] files = client.listFiles(remotePath);
                if (files.length != 0) {
                    //存在远程文件，删除
                    client.deleteFile(remotePath);
                }
                client.setBufferSize(1024);
                client.setControlEncoding("UTF-8");
                client.enterLocalPassiveMode();
                client.setFileType(FTPClient.BINARY_FILE_TYPE);
                fis = new FileInputStream(bean.getPath());

                return client.storeFile(remotePath, fis);
            }
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (client.isConnected()) {
                    client.logout();
                    client.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
