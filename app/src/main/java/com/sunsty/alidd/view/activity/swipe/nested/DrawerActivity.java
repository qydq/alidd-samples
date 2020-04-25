package com.sunsty.alidd.view.activity.swipe.nested;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ali.layout.swipe.OnItemMenuClickListener;
import com.ali.layout.swipe.SwipeMenu;
import com.ali.layout.swipe.SwipeMenuBridge;
import com.ali.layout.swipe.SwipeMenuCreator;
import com.ali.layout.swipe.SwipeMenuItem;
import com.ali.layout.swipe.SwipeRecyclerView;
import com.sunsty.alidd.R;
import com.sunsty.alidd.view.activity.swipe.BaseActivity;

/**
 * 和DrawerLayout结合使用。
 */
public class DrawerActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerView.setSwipeMenuCreator(mSwipeMenuCreator);
        mRecyclerView.setOnItemMenuClickListener(mItemMenuClickListener);

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.app_name,
                R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged(mDataList);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_menu_drawer;
    }

    @Override
    public void onItemClick(View itemView, int position) {
        Toast.makeText(this, "第" + position + "个", Toast.LENGTH_SHORT).show();
    }

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.an_dimen_native_list);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 因为左边要DrawerLayout的侧滑，所以不添加左侧菜单，添加后DrawerLayout将滑不动，但是菜单可以滑动。

            // 只添加Item右侧的菜单。
            {
                SwipeMenuItem closeItem = new SwipeMenuItem(DrawerActivity.this).setBackground(
                        R.drawable.selector_purple).setImage(R.drawable.picture_icon_close).setWidth(width).setHeight(height);
                swipeRightMenu.addMenuItem(closeItem); // 添加菜单到左侧。

                SwipeMenuItem addItem = new SwipeMenuItem(DrawerActivity.this).setBackground(R.drawable.selector_green)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到左侧。
            }
        }
    };

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private OnItemMenuClickListener mItemMenuClickListener = new OnItemMenuClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge, int position) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(DrawerActivity.this, "list第" + position + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT)
                        .show();
            } else if (direction == SwipeRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(DrawerActivity.this, "list第" + position + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT)
                        .show();
            }
        }

    };
}