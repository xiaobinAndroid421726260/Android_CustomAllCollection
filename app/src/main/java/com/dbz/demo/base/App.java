package com.dbz.demo.base;

import android.app.Application;

public class App extends Application {

    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static App getInstance(){
        if (sInstance == null){
            synchronized (App.class){
                if (sInstance == null){
                    sInstance = new App();
                }
            }
        }
        return sInstance;
    }
}