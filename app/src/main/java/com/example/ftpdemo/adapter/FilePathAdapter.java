package com.example.ftpdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ftpdemo.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FilePathAdapter extends RecyclerView.Adapter<FilePathAdapter.MyFilePathViewHolder> {

    private Context mContext;

    private List<String> pathData = new ArrayList<>();

    OnFilePathClickListener listener;

    public FilePathAdapter(Context context, List<String> data) {
        mContext = context;
        if (data != null) {
            pathData = data;
        }
    }

    @NonNull
    @Override
    public MyFilePathViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext)
                .inflate(R.layout.item_for_file_path, parent, false);
        MyFilePathViewHolder holder = new MyFilePathViewHolder(root);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilePathAdapter.MyFilePathViewHolder holder, int position) {
        holder.img_rightarrow.setVisibility(
                position == (pathData.size() - 1) ? View.GONE : View.VISIBLE
        );
        holder.tv_filepath.setText(pathData.get(position));
    }

    @Override
    public int getItemCount() {
        return pathData.size();
    }

    public void setOnFilePathClickListener(OnFilePathClickListener listener) {
        this.listener = listener;
    }

    public String getClickPath(int position) {
        StringBuilder path = new StringBuilder();
        for (int i = 0 ; i <= position ; i ++) {
            if (i == 0) {
                if (!pathData.get(i).equals(File.separator)) {
                    path.append(pathData.get(i));
                }
            } else {
                path.append(File.separator).append(pathData.get(i));
            }
        }

        return path.toString();
    }

    public class MyFilePathViewHolder extends RecyclerView.ViewHolder {

        TextView tv_filepath;
        ImageView img_rightarrow;

        public MyFilePathViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_filepath = itemView.findViewById(R.id.tv_filepath);
            img_rightarrow = itemView.findViewById(R.id.img_rightarrow);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onFilePathClickListener(
                                getClickPath(getAdapterPosition())
                        );
                    }
                }
            });
        }
    }

    public interface OnFilePathClickListener {
        void onFilePathClickListener(String path);
    }
}
