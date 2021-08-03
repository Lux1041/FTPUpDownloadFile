package com.example.ftpdemo.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftpdemo.R;
import com.example.ftpdemo.adapter.FileAdapter;
import com.example.ftpdemo.bean.FileBean;
import com.example.ftpdemo.present.BasePresenter;
import com.example.ftpdemo.present.FileListFragPresenterImpl;
import com.example.ftpdemo.util.Constant;
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

    private List<FileBean> fileBeans = new ArrayList<>();

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
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_file_list, container, false);
        file_path = rootView.findViewById(R.id.file_list);
        file_list = rootView.findViewById(R.id.file_list);

        mPresenter = new FileListFragPresenterImpl(this, currentType);

        LinearLayoutManager horizontalManager = new LinearLayoutManager(getActivity());
        horizontalManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        file_path.setLayoutManager(horizontalManager);


        LinearLayoutManager verticalManager = new LinearLayoutManager(getActivity());
        verticalManager.setOrientation(GridLayoutManager.VERTICAL);
        file_list.setLayoutManager(verticalManager);
        fileAdapter = new FileAdapter(getActivity(), fileBeans);
        file_list.setAdapter(fileAdapter);

        mPresenter.refreshData();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.refreshData();
    }

    @Override
    public void refreshData(List<FileBean> data) {
        if (data != null) {
            fileBeans.clear();
            fileBeans.addAll(data);
            fileAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void refreshPathData(List<String> pathData) {

    }
}
