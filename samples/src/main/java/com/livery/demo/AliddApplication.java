package com.livery.demo;

import android.content.Context;
import android.util.Log;

import com.sunsta.bear.AnApplication;
import com.example.cj.videoeditor.Constants;

public class AliddApplication extends AnApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("thread","  线程值  "+Thread.currentThread());
        Constants.init(this);
    }
}
