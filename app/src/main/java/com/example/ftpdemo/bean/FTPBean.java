package com.example.ftpdemo.bean;

public class FTPBean {
    private String ip;
    private String port;
    private String name;
    private String pass;

    public FTPBean() {
    }

    public FTPBean(String ip, String port, String name, String pass) {
        this.ip = ip;
        this.port = port;
        this.name = name;
        this.pass = pass;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
