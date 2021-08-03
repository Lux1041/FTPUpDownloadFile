package com.example.ftpdemo.present;

import com.example.ftpdemo.view.BaseView;

import java.util.List;

public interface BasePresenter {

    /**
     * 数据刷新
     */
    void refreshData();

    void queryPathFiles(String path);

}
