package com.livery.demo.module.pager.utils;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;

import com.livery.demo.R;

import com.sunsta.livery.engine.NavigationController;
import com.sunsta.livery.listener.OnTabItemSelectedListener;

/**
 * 底部导航栏适配官方Navigation组件的工具类
 * https://developer.android.com/topic/libraries/architecture/navigation/
 */
public class BottomNavigationUtils {

    private BottomNavigationUtils() {
    }

    public static void setupWithNavController(@NonNull final int[] pageIds, @NonNull final NavigationController navigationController, @NonNull final NavController navController) {

        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                onNavDestinationSelected(pageIds[index], navController);
            }

            @Override
            public void onRepeat(int index) {
            }
        });

        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
                int destinationId = destination.getId();
                for (int i = 0; i < pageIds.length; i++) {
                    if (destinationId == pageIds[i]) {
                        if (navigationController.getSelected() != i) {
                            navigationController.setSelect(i, false);
                        }
                        break;
                    }
                }
            }
        });
    }

    private static void onNavDestinationSelected(int id, @NonNull NavController navController) {
        NavOptions options = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.nav_default_enter_anim)
                .setExitAnim(R.anim.nav_default_exit_anim)
                .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
                .setPopUpTo(navController.getGraph().getStartDestination(), false)
                .build();
        try {
            navController.navigate(id, null, options);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

}
