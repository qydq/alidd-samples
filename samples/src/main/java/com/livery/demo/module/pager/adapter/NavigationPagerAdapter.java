package com.livery.demo.module.pager.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.livery.demo.comm.fagment.AFragment;
import com.livery.demo.view.fragment.MainFragment;

public class NavigationPagerAdapter extends FragmentPagerAdapter {

    private int size;

    public NavigationPagerAdapter(FragmentManager fm, int size) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.size = size;
    }

    @Override
    public Fragment getItem(int position) {
        return AFragment.newInstance(position + "");
    }

    @Override
    public int getCount() {
        return size;
    }
}
