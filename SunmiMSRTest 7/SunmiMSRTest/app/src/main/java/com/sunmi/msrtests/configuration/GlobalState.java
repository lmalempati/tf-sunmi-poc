package com.sunmi.msrtests.configuration;

import android.app.Application;
import android.content.Context;

import java.util.UUID;

public class GlobalState {
    private static volatile GlobalState singleton;
    public static GlobalState getInstance(){
        if (singleton == null) {
            synchronized (GlobalState.class) {
                if (singleton == null) {
                    singleton = new GlobalState();
                }
                return singleton;
            }
        }
        return singleton;
    }
    private GlobalState() {
    }


    private Context _appContext = null;
    public void setAppContext(Application applicationContext)
    {
        _appContext = applicationContext;
    }
    public android.content.Context getContext() { return _appContext; }
}
