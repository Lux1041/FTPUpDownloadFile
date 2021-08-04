package com.example.ftpdemo.moudle;

import com.example.ftpdemo.bean.FileBean;

import java.util.List;

public abstract class BaseMoudle {

    public abstract List<FileBean> getFileList();

    public abstract List<FileBean> getFileByPath(String path);

    public abstract List<String> getfilePathData();
}
