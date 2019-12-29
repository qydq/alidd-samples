package com.sunsty.alidd;

import android.content.Context;

import androidx.multidex.MultiDex;

import com.ali.AnApplication;

public class AliddApplication extends AnApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
