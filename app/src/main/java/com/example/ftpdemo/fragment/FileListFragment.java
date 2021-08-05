package com.example.ftpdemo.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftpdemo.R;
import com.example.ftpdemo.adapter.FileAdapter;
import com.example.ftpdemo.adapter.FilePathAdapter;
import com.example.ftpdemo.bean.FTPBean;
import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.present.BasePresenter;
import com.example.ftpdemo.present.FileListFragPresenterImpl;
import com.example.ftpdemo.util.Constant;
import com.example.ftpdemo.util.SPUtil;
import com.example.ftpdemo.util.Util;
import com.example.ftpdemo.view.BaseView;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件展示界面
 */
public class FileListFragment extends Fragment implements BaseView {
    private static final String DATA_SOURCE_TYPE = "DATA_SOURCE_TYPE";

    int currentType = Constant.LOCAL_DATA_SOURCE_TYPE;

    //文件路径
    RecyclerView file_path;
    //文件列表
    RecyclerView file_list;

    BasePresenter mPresenter;

    FileAdapter fileAdapter;

    FilePathAdapter pathAdapter;

    private List<FileBean> fileBeans = new ArrayList<>();

    private List<String> filePaths = new ArrayList<>();

    ProgressBar progress_bar;

    Util.OnDialogConfirmClickListener onDialogConfirmClickListener = new Util.OnDialogConfirmClickListener() {
        @Override
        public void onDialogConfirmClickListener(FTPBean ftpBean) {
            FileBean bean = new FileBean(ftpBean);
            fileBeans.add(0, bean);
            SPUtil.getInstance(null).setFTPParams(fileBeans);
            fileAdapter.notifyDataSetChanged();
        }
    };

    FileAdapter.OnFileClickListener fileClickListener = new FileAdapter.OnFileClickListener() {
        @Override
        public void onFileClickListener(FileBean bean) {
            if (bean.isDir()) {
                if (bean.isAddFtp()) {
                    //2021/8/4 添加ftp地址
                    Util.showInputFTPDialog(getActivity(), onDialogConfirmClickListener);
                } else {
                    //刷新地址
                    showLoading();
                    if (currentType == Constant.LOCAL_DATA_SOURCE_TYPE) {
                        refreshPath(bean.getPath());
                    }
                    file_path.scrollToPosition(filePaths.size() - 1);
                    mPresenter.queryPathFiles(bean);
                }
            } else {
                // TODO: 2021/8/4 选择文件上传/下载操作
                mPresenter.dealFile(bean);
            }
        }
    };

    FilePathAdapter.OnFilePathClickListener filePathClickListener = new FilePathAdapter.OnFilePathClickListener() {
        @Override
        public void onFilePathClickListener(String path) {
            showLoading();
            refreshPath(path);
            mPresenter.queryPathFiles(new FileBean(path));
        }
    };

    public static Fragment newInstance(int type) {
        FileListFragment fragment = new FileListFragment();
        Bundle b = new Bundle();
        b.putInt(DATA_SOURCE_TYPE, type);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentType = getArguments().getInt(DATA_SOURCE_TYPE);
        }
        SPUtil.getInstance(getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_file_list, container, false);
        file_path = rootView.findViewById(R.id.file_path);
        file_list = rootView.findViewById(R.id.file_list);
        progress_bar = rootView.findViewById(R.id.progress_bar);

        mPresenter = new FileListFragPresenterImpl(this, currentType);

        LinearLayoutManager horizontalManager = new LinearLayoutManager(getActivity());
        horizontalManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        file_path.setLayoutManager(horizontalManager);
        pathAdapter = new FilePathAdapter(getActivity(), filePaths);
        file_path.setAdapter(pathAdapter);
        pathAdapter.setOnFilePathClickListener(filePathClickListener);
        ((DefaultItemAnimator)(file_path.getItemAnimator())).setSupportsChangeAnimations(false);

        LinearLayoutManager verticalManager = new LinearLayoutManager(getActivity());
        verticalManager.setOrientation(GridLayoutManager.VERTICAL);
        file_list.setLayoutManager(verticalManager);
        fileAdapter = new FileAdapter(getActivity(), fileBeans);
        file_list.setAdapter(fileAdapter);
        fileAdapter.setFileClickListener(fileClickListener);

        showLoading();
        mPresenter.refreshData();

        return rootView;
    }

    @Override
    public void refreshData(List<FileBean> data) {
        dismissLoading();
        fileBeans.clear();
        if (data != null) {
            fileBeans.addAll(data);
        }
        fileAdapter.notifyDataSetChanged();
    }

    @Override
    public void refreshPathData(List<String> pathData) {
        if (pathData != null) {
            filePaths.clear();
            filePaths.addAll(pathData);
            pathAdapter.notifyDataSetChanged();
        }
    }

    private void refreshPath(String rootPath) {
        List<String> tempPath = new ArrayList<>();
        String[] paths = rootPath.split("\\/");
        for (String s : paths) {
            String path = s;
            if (TextUtils.isEmpty(path)) {
                path = "/";
            }
            tempPath.add(path);
        }
        refreshPathData(tempPath);
    }

    private synchronized void showLoading() {
        if (progress_bar.getVisibility() == View.GONE) {
            progress_bar.setVisibility(View.VISIBLE);
        }
    }

    private synchronized void dismissLoading() {
        if (progress_bar.getVisibility() == View.VISIBLE) {
            progress_bar.setVisibility(View.GONE);
        }
    }
}
