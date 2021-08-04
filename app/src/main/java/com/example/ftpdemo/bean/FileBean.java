package com.example.ftpdemo.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class FileBean implements Comparable<FileBean>{
    private String fileName;
    private String path;
    private boolean isDir;

    //添加ftp，默认false不添加
    private boolean addFtp = false;

    public boolean isAddFtp() {
        return addFtp;
    }

    public void setAddFtp(boolean addFtp) {
        this.addFtp = addFtp;
    }

    public boolean isDir() {
        return isDir;
    }

    public void setDir(boolean dir) {
        isDir = dir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public int compareTo(FileBean o) {
        if (o == null) {
            return 1;
        }
        if ((isDir && o.isDir) || (!isDir && !o.isDir)) {
            return fileName.charAt(0) - o.fileName.charAt(0) > 0 ? 1 : -1;
        } else {
            if (isDir) {
                return -1;
            } else {
                return 1;
            }
        }
    }
}
