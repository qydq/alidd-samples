package com.livery.demo.model.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.livery.demo.comm.fagment.AFragment;
import com.livery.demo.view.fragment.MainFragment;
import com.livery.demo.view.fragment.OtherFragment;
import com.livery.demo.view.fragment.SecondFragment;


/*
 * (1).sunst提供：[an专栏]情景系列，livery框架示例主入口adapter
 *     如果有帮助到你，真诚的邀请你，关注我的知乎<a href="https://zhihu.com/people/qydq">Bgwan</a>
 *     2020年05月：目前需要集齐1000+个关注者，感谢🙏
 * */
public class MainAdapter extends FragmentPagerAdapter {

    private int size;

    public MainAdapter(FragmentManager fm, int size) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.size = size;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new MainFragment();
        } else if (position == 1) {
            return new SecondFragment();
        } else if (position == 2) {
            return new OtherFragment();
        } else {
            return AFragment.newInstance(position + "an情景系列的demo还在继续开发，敬请期待\n" +
                    "欢迎关注知乎https://zhihu.com/people/qydq");
        }
    }

    @Override
    public int getCount() {
        return size;
    }
}
