package com.sunsty.alidd;

import android.content.Context;
import android.util.Log;

import androidx.multidex.MultiDex;

import com.ali.AnApplication;
import com.example.cj.videoeditor.Constants;

public class AliddApplication extends AnApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("thread","  线程值  "+Thread.currentThread());
        Constants.init(this);
    }
}
