package com.example.ftpdemo.present;

import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.view.BaseView;

import java.util.List;

public interface BasePresenter {

    /**
     * 数据刷新
     */
    void refreshData();

    /**
     * 按地址查询文件
     * @param bean
     */
    void queryPathFiles(FileBean bean);

    /**
     * 文件处理，包括上传和下载操作
     * @param bean
     */
    void dealFile(FileBean bean);

}
