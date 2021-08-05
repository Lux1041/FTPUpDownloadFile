package com.example.ftpdemo.bean;

import org.json.JSONException;
import org.json.JSONObject;

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

    public FTPBean(JSONObject obj) {
        this.ip = obj.optString("ip");
        this.port = obj.optString("port");
        this.name = obj.optString("name");
        this.pass = obj.optString("pass");
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

    @Override
    public String toString() {
        return toJsonObject().toString();
    }

    public JSONObject toJsonObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("ip", ip);
            obj.put("port", port);
            obj.put("name", name);
            obj.put("pass", pass);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
