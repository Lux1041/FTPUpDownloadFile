package com.example.ftpdemo.present;

import android.text.TextUtils;
import android.util.Log;

import com.example.ftpdemo.bean.FTPBean;
import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.moudle.BaseMoudle;
import com.example.ftpdemo.moudle.LocalMoudle;
import com.example.ftpdemo.moudle.RemoteMoudle;
import com.example.ftpdemo.util.Constant;
import com.example.ftpdemo.view.BaseView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileListFragPresenterImpl implements BasePresenter {

    private BaseView mView;

    BaseMoudle baseMoudle;

    int currentType;

    private FTPBean currentFTPBean;

    public FileListFragPresenterImpl(BaseView view, int type) {
        mView = view;
        currentType = type;
        if (type == Constant.LOCAL_DATA_SOURCE_TYPE) {
            baseMoudle = new LocalMoudle();
        } else if (type == Constant.REMOTE_DATA_SOURCE_TYPE) {
            baseMoudle = new RemoteMoudle();
        } else {
            Log.e(getClass().getSimpleName(), "baseMoudle is null");
        }
    }

    @Override
    public void refreshData() {
        if (baseMoudle != null) {
            baseMoudle.getFileList(new BaseMoudle.GetFileListCallback() {
                @Override
                public void onCallback(List<FileBean> data) {
                    mView.refreshData(data);
                    List<String> pathData = baseMoudle.getfilePathData();
                    mView.refreshPathData(pathData);
                }
            });
        }
    }

    @Override
    public void queryPathFiles(FileBean bean) {
        if (Constant.REMOTE_FILE_ROOT_PATH.equals(bean.getPath()) &&
                bean.getFtpBean() != null) {
            currentFTPBean = bean.getFtpBean();
        } else if (bean.getPath().startsWith(Constant.REMOTE_FILE_ROOT_PATH) &&
                !Constant.REMOTE_FILE_ROOT_PATH.equals(bean.getPath())) {
            bean.setFtpBean(currentFTPBean);
            String newPath = bean.getPath()
                    .replace(Constant.REMOTE_FILE_ROOT_PATH, "");
            newPath = newPath.replace(currentFTPBean.getIp(), "");
            if (TextUtils.isEmpty(newPath)) {
                newPath = "/";
            }
            bean.setPath(newPath);
        }

        baseMoudle.getFileByPath(bean, new BaseMoudle.GetFileListCallback() {
            @Override
            public void onCallback(List<FileBean> data) {
                mView.refreshData(data);
                //远程的地址需要以返回的结果
                if (currentType == Constant.REMOTE_DATA_SOURCE_TYPE) {
                    if (bean.getFtpBean() != null &&
                            data != null && data.size() > 0) {
                        mView.refreshPathData(
                                dealFileList(Constant.REMOTE_FILE_ROOT_PATH
                                        + bean.getFtpBean().getIp()
                                        + data.get(0).getPath())
                        );
                    }
                }
            }
        });
    }

    @Override
    public void dealFile(FileBean bean) {
        baseMoudle.dealFile(bean, new BaseMoudle.OnLoadFileResultListener() {
            @Override
            public void onLoadFileResultListener(boolean result) {
                mView.uploadResult(result);
            }
        });
    }

    /**
     * 路径解析
     * @param pathStr
     * @return 路径解析后的集合
     */
    private List<String> dealFileList(String pathStr) {
        List<String> pathData = new ArrayList<>();
        String[] paths = pathStr.split("\\/");
        Log.i(getClass().getSimpleName(), "path = " + pathStr);
        for (int i = 0 ; i < paths.length; i ++) {
            String content = paths[i];
            if (TextUtils.isEmpty(content)) {
                content = Constant.REMOTE_FILE_ROOT_PATH;
            }
            pathData.add(content);
        }
        return pathData;
    }
}
