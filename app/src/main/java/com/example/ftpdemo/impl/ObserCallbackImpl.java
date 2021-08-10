package com.example.ftpdemo.impl;

import com.example.ftpdemo.present.BasePresenter;
import com.example.ftpdemo.util.observer.ObserCallback;

public class ObserCallbackImpl implements ObserCallback {

    BasePresenter mPresenter;

    public ObserCallbackImpl(BasePresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onReceiver(Object msg) {
        if (mPresenter != null) {
            mPresenter.refreshData();;
        }
    }
}
