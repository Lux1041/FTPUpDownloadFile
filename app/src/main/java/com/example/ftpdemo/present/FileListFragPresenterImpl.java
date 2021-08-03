package com.example.ftpdemo.present;

import android.util.Log;

import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.moudle.BaseMoudle;
import com.example.ftpdemo.moudle.LocalMoudle;
import com.example.ftpdemo.moudle.RemoteMoudle;
import com.example.ftpdemo.util.Constant;
import com.example.ftpdemo.view.BaseView;

import java.util.ArrayList;
import java.util.List;

public class FileListFragPresenterImpl implements BasePresenter {

    private BaseView mView;

    BaseMoudle baseMoudle;

    public FileListFragPresenterImpl(BaseView view, int type) {
        mView = view;
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
            List<FileBean> data = baseMoudle.getFileList();
            List<String> pathData = new ArrayList<>();
            if (data != null && data.size() > 0) {
                String pathStr = data.get(0).getPath();
                String[] paths = pathStr.split("\\/");
                Log.i(getClass().getSimpleName(), "path = " + pathStr);
                for (int i = 1 ; i < paths.length - 1; i ++) {
                    pathData.add(paths[i]);
                }
                mView.refreshPathData(pathData);
            }
            mView.refreshData(data);
        }
    }

    @Override
    public void queryPathFiles(String path) {

    }
}
