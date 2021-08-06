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
import com.example.ftpdemo.bean.FTPBean;
import com.example.ftpdemo.adapter.FTPAdapter.MyFTPViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FTPAdapter extends RecyclerView.Adapter<MyFTPViewHolder> {

    List<FTPBean> data = new ArrayList<>();
    Map<Integer, Boolean> dataStatus = new HashMap<>();

    private Context mContext;

    public FTPAdapter(Context context, List<FTPBean> data) {
        mContext = context;
        if (data != null) {
            this.data = data;
            for (int i = 0 ; i < data.size() ; i ++) {
                dataStatus.put(i, false);
            }
        }
    }

    @NonNull
    @Override
    public MyFTPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.item_for_ftp_list, parent,
                false
        );
        MyFTPViewHolder holder = new MyFTPViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyFTPViewHolder holder, int position) {
        FTPBean bean = data.get(position);
        Boolean status = dataStatus.get(position);
        String content = bean.getName() + "@" + bean.getIp();
        holder.tv_ftp_name.setText(content);
        holder.cb_ftp.setSelected(status);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public FTPBean getChooseFTPBean() {
        for (int i = 0 ; i < dataStatus.size() ; i ++) {
            if (dataStatus.get(i)) {
                return data.get(i);
            }
        }
        return null;
    }

    private void changeDataStatus(int position) {
        for (int i = 0 ; i < dataStatus.size(); i ++) {
            if (i != position) {
                dataStatus.put(i, false);
            } else {
                dataStatus.put(i, !dataStatus.get(i));
            }
        }
        notifyDataSetChanged();
    }

    public class MyFTPViewHolder extends RecyclerView.ViewHolder {

        TextView tv_ftp_name;

        ImageView cb_ftp;

        public MyFTPViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_ftp_name = itemView.findViewById(R.id.tv_ftp_name);
            cb_ftp = itemView.findViewById(R.id.cb_ftp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    changeDataStatus(position);
                }
            });
        }
    }
}
