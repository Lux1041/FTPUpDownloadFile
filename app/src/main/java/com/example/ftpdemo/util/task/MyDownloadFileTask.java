package com.example.ftpdemo.util.task;

import android.os.Environment;

import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.moudle.BaseMoudle;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件下载
 */
public class MyDownloadFileTask extends BaseLoadFileAsyncTask {

    public MyDownloadFileTask(BaseMoudle.OnLoadFileResultListener listener) {
        super(listener);
    }

    @Override
    protected Boolean doInBackground(FileBean... fileBeans) {
        FileBean bean = fileBeans[0];
        FTPClient client = new FTPClient();
        FileOutputStream fos = null;
        try {
            StringBuilder localPath = new StringBuilder();
            localPath.append(Environment.getExternalStorageDirectory().getPath())
                    .append(File.separator)
                    .append(bean.getFtpBean().getIp());
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
                client.setBufferSize(1024);
                client.setControlEncoding("UTF-8");
                client.enterLocalPassiveMode();
                client.setFileType(FTPClient.BINARY_FILE_TYPE);

                File fileDir = new File(localPath.toString());
                if (!fileDir.exists()) {
                    fileDir.mkdir();
                }
                localPath.append(File.separator).append(bean.getFileName());
                File file = new File(localPath.toString());
                fos = new FileOutputStream(file);
                return client.retrieveFile(remotePath, fos);
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
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
