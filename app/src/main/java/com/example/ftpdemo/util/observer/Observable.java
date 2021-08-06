package com.example.ftpdemo.util.observer;

import java.util.ArrayList;
import java.util.List;

public class Observable {

    private List<ObserCallback> observer = new ArrayList<>();

    public void add(ObserCallback obj) {
        observer.add(obj);
    }

    public void sendMsg(Object msg) {
        for (ObserCallback callback : observer) {
            callback.onReceiver(msg);
        }
    }

    public void remove(ObserCallback obj) {
        observer.remove(obj);
    }
}
