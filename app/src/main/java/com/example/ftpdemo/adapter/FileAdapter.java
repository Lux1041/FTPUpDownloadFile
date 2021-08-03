package com.example.ftpdemo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftpdemo.R;
import com.example.ftpdemo.bean.FileBean;

import java.util.ArrayList;
import java.util.List;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.MyViewHolder> {

    private Context mContext;
    private List<FileBean> data = new ArrayList<>();

    public FileAdapter(Context context, List<FileBean> data) {
        mContext = context;
        if (data != null) {
            this.data = data;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_for_file_list, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FileAdapter.MyViewHolder holder, int position) {
        FileBean bean = data.get(position);
        holder.img_file.setImageResource(
                bean.isDir() ?
                R.drawable.format_folder_smartlock :
                R.drawable.format_unkown
        );
        String fileName = bean.getFileName();
        if (null == fileName) {
            fileName = "";
        }
        holder.tv_filename.setText(fileName);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img_file;
        TextView tv_filename;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_file = itemView.findViewById(R.id.img_file);
            tv_filename = itemView.findViewById(R.id.tv_filename);
        }
    }
}
