package com.example.ftpdemo.util.observer;

import java.util.HashMap;
import java.util.Map;

public class ObserverManager {

    private static final Map<String, Observable> registers = new HashMap<>();

    public static void registerObserver(String tag, ObserCallback obj) {
        if (registers.containsKey(tag)) {
            registers.get(tag).add(obj);
        } else {
            Observable observable = new Observable();
            observable.add(obj);
            registers.put(tag, observable);
        }
    }

    public static void sendMessage(String tag, Object msg) {
        if (registers.containsKey(tag)) {
            registers.get(tag).sendMsg(msg);
        }
    }

    public static void unregisterObserver(String tag, ObserCallback obj) {
        if (registers.containsKey(tag)) {
            registers.get(tag).remove(obj);
        }
    }
}
