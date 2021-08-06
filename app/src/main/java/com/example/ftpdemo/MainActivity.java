package com.example.ftpdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.os.Bundle;

import com.example.ftpdemo.adapter.MyFragmentsAdapter;
import com.example.ftpdemo.fragment.FileListFragment;
import com.example.ftpdemo.util.Constant;
import com.example.ftpdemo.util.Util;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private TabLayout tablayout;
    private ViewPager viewpager;

    String[] titles = new String[]{
            "本地",
            "远程"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tablayout = findViewById(R.id.tablayout);
        viewpager = findViewById(R.id.viewpager);

        String[] permissions = new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        if (!Util.checkPermission(this, permissions)) {
            requestPermissions(permissions);
        }

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(FileListFragment.newInstance(Constant.LOCAL_DATA_SOURCE_TYPE));
        fragments.add(FileListFragment.newInstance(Constant.REMOTE_DATA_SOURCE_TYPE));
        MyFragmentsAdapter adapter = new MyFragmentsAdapter(
                getSupportFragmentManager(),
                fragments,
                titles
        );
        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);
    }
}