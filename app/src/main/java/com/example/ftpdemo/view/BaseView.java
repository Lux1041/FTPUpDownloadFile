package com.example.ftpdemo.view;

import com.example.ftpdemo.bean.FileBean;

import java.util.List;

public interface BaseView {

    void refreshData(List<FileBean> data);

    void refreshPathData(List<String> pathData);

    void uploadResult(boolean result);
}
