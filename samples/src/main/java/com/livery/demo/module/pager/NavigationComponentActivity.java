package com.livery.demo.module.pager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.livery.demo.R;
import com.livery.demo.module.pager.utils.BottomNavigationUtils;

import com.sunsta.livery.engine.NavigationController;
import com.sunsta.livery.engine.PageNavigationView;

/**
 * 官方Navigation组件演示
 */
public class NavigationComponentActivity extends AppCompatActivity {

    private final int[] PAGE_IDS = {
            R.id.navigationComponentPageAFragment,
            R.id.navigationComponentPageBFragment,
            R.id.navigationComponentPageCFragment
    };

    private NavController mNavController;

    private PageNavigationView mNavigation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_activity_navigation_component);
        mNavigation = findViewById(R.id.navigation);

        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);

        initBottomNavigation(mNavigation);
    }

    /**
     * 初始化底部导航
     */
    private void initBottomNavigation(PageNavigationView pageNavigationView) {
        NavigationController navigationController = pageNavigationView.material()
                .addItem(R.drawable.pager_ic_favorite_gray_24dp, "A")
                .addItem(R.drawable.pager_ic_favorite_gray_24dp, "B")
                .addItem(R.drawable.pager_ic_favorite_gray_24dp, "C")
                .build();

        BottomNavigationUtils.setupWithNavController(PAGE_IDS, navigationController, mNavController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return mNavController.navigateUp();
    }


}
