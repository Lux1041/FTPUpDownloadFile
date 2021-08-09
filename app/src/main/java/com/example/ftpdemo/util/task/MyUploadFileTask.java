package com.example.ftpdemo.util.task;

import android.app.Notification;
import android.util.Log;

import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.moudle.BaseMoudle;
import com.example.ftpdemo.util.Constant;
import com.example.ftpdemo.util.NotificationUtil;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

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
        fileName = bean.getFileName();
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

//                return client.storeFile(remotePath, fis);

                int len = -1;
                long pContentLength = fis.available();
                Log.i(getClass().getSimpleName(), "pContentLength = " + pContentLength);
                long trans = 0;
                int bufferSize = client.getBufferSize();
                byte[] buffer = new byte[bufferSize];
                int progress = 0;
                OutputStream os = client.storeFileStream(
                        new String(
                                bean.getFileName().getBytes("utf-8"),
                                "iso-8859-1"
                        )
                );
                while ((len = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, len);
                    trans += len;
                    progress = (int) (trans * 1.0 / pContentLength * 100.0);
                    Log.i(getClass().getSimpleName(), "current progress = " + progress);
                    publishProgress(progress);
                }
                fis.close();
                os.flush();
                os.close();

                return true;
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

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        deal_file_id = getNotificationId();
        NotificationUtil.getInstance(null).initNotificationParams(
                deal_file_id, "文件上传", "文件上传"
        );
    }
}
