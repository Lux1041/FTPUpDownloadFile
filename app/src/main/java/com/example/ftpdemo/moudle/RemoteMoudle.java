package com.example.ftpdemo.moudle;

import com.example.ftpdemo.bean.FileBean;

import java.util.ArrayList;
import java.util.List;

public class RemoteMoudle extends BaseMoudle {
    @Override
    public List<FileBean> getFileList() {
        List<FileBean> data = new ArrayList<>();
        FileBean bean = new FileBean();
        bean.setAddFtp(true);
        bean.setDir(true);
        bean.setFileName("添加ftp地址");
        bean.setPath("FTP/0");
        data.add(bean);
        return data;
    }

    @Override
    public List<FileBean> getFileByPath(String path) {
        return null;
    }

    @Override
    public List<String> getfilePathData() {
        List<String> pathData = new ArrayList<>();
        pathData.add("FTP");
        return pathData;
    }
}
