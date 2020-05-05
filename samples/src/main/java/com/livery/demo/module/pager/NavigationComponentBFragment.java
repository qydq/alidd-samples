package com.livery.demo.module.pager;

import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * Created by mjj on 2018/9/26
 */
public class NavigationComponentBFragment extends NavigationComponentAFragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mTvText.setText("B");
    }
}