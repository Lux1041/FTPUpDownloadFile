package com.example.ftpdemo.util.task;

import android.os.Environment;
import android.util.Log;

import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.moudle.BaseMoudle;
import com.example.ftpdemo.util.NotificationUtil;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件下载
 */
public class MyDownloadFileTask extends BaseLoadFileAsyncTask {

    private static int download_file_id = 2;

    public MyDownloadFileTask(BaseMoudle.OnLoadFileResultListener listener) {
        super(listener);
    }

    @Override
    protected Boolean doInBackground(FileBean... fileBeans) {
        FileBean bean = fileBeans[0];
        fileName = bean.getFileName();
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

                long pContentLength = client.listFiles(remotePath)[0].getSize();
                Log.i(getClass().getSimpleName(), "pContentlength = " + pContentLength);

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
                if (file.exists()) {
                    file.delete();
                }
                fos = new FileOutputStream(file);

//                return client.retrieveFile(remotePath, fos);

                Log.i(getClass().getSimpleName(), "remotePath = " + remotePath);
                InputStream is = client.retrieveFileStream(remotePath);
                int len = -1;
                byte[] buffer = new byte[client.getBufferSize()];
                long trans = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    trans += len;
                    int progress = (int) (trans * 1.0 / pContentLength * 100.0);
                    publishProgress(progress);
                }
                fos.flush();
                fos.close();
                is.close();

                return true;
            }
        } catch (IOException e) {
//            e.printStackTrace();
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

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        deal_file_id = getNotificationId();
        NotificationUtil.getInstance(null).initNotificationParams(
                deal_file_id, "文件下载", "文件下载"
        );
    }
}
