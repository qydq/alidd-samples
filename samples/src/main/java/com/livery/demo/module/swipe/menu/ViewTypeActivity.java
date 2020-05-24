package com.livery.demo.module.swipe.menu;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sunsta.bear.layout.swipe.OnItemMenuClickListener;
import com.sunsta.bear.layout.swipe.SwipeMenu;
import com.sunsta.bear.layout.swipe.SwipeMenuBridge;
import com.sunsta.bear.layout.swipe.SwipeMenuCreator;
import com.sunsta.bear.layout.swipe.SwipeMenuItem;
import com.sunsta.bear.layout.swipe.SwipeRecyclerView;
import com.livery.demo.R;
import com.sunsta.bear.model.adapter.SmartRecyclerAdapter;
import com.livery.demo.model.adapter.SwipeRecyclerAdapter;
import com.livery.demo.module.swipe.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 根据ViewType自定义菜单。
 */
public class ViewTypeActivity extends BaseActivity {

    private static final int VIEWTYPE_TWO = 1;
    private static final int VIEWTYPE_THREE = 2;
    private static final int VIEWTYPE_OTHER = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecyclerView.setSwipeMenuCreator(swipeMenuCreator);
        mRecyclerView.setOnItemMenuClickListener(mItemMenuClickListener);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged(mDataList);
    }

    @Override
    protected SmartRecyclerAdapter createAdapter() {
        return new SwipeRecyclerAdapter(this) {
            @Override
            public int getItemViewType(int position) {
                if (position % 3 == 0) {
                    return VIEWTYPE_THREE;
                } else if (position % 2 == 0) {
                    return VIEWTYPE_TWO;
                } else {
                    return VIEWTYPE_OTHER;
                }
            }
        };
    }

    /**
     * 菜单创建器，在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int position) {
            int width = getResources().getDimensionPixelSize(R.dimen.an_dimen_native_list);
            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;

            // 1. 根据ViewType来决定哪一个item该如何添加菜单。
            // 2. 更多的开发者需要的是根据position，因为不同的ViewType之间不会有缓存优化效果。
            int viewType = mAdapter.getItemViewType(position);
            if (viewType == VIEWTYPE_THREE) {
                SwipeMenuItem deleteItem = new SwipeMenuItem(ViewTypeActivity.this).setBackground(
                        R.drawable.selector_red)
                        .setImage(R.drawable.ic_white_delete)
                        .setText("删除")
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

                SwipeMenuItem closeItem = new SwipeMenuItem(ViewTypeActivity.this).setBackground(
                        R.drawable.selector_purple).setImage(R.drawable.ic_white_close).setWidth(width).setHeight(height);
                swipeRightMenu.addMenuItem(closeItem); // 添加菜单到右侧。

                SwipeMenuItem addItem = new SwipeMenuItem(ViewTypeActivity.this).setBackground(
                        R.drawable.selector_green)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            } else if (viewType == VIEWTYPE_TWO) {
                SwipeMenuItem closeItem = new SwipeMenuItem(ViewTypeActivity.this).setBackground(
                        R.drawable.selector_purple).setImage(R.drawable.ic_white_close).setWidth(width).setHeight(height);
                swipeRightMenu.addMenuItem(closeItem); // 添加菜单到右侧。

                SwipeMenuItem addItem = new SwipeMenuItem(ViewTypeActivity.this).setBackground(
                        R.drawable.selector_green)
                        .setText("添加")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(addItem); // 添加菜单到右侧。
            } else if (viewType == VIEWTYPE_OTHER) {
                SwipeMenuItem addItem = new SwipeMenuItem(ViewTypeActivity.this).setBackground(
                        R.drawable.selector_green).setImage(R.drawable.base_image_add).setWidth(width).setHeight(height);
                swipeLeftMenu.addMenuItem(addItem); // 添加菜单到左侧。

                SwipeMenuItem closeItem = new SwipeMenuItem(ViewTypeActivity.this).setBackground(
                        R.drawable.selector_red).setText("删除").setTextColor(Color.WHITE).setWidth(width).setHeight(height);
                swipeLeftMenu.addMenuItem(closeItem); // 添加菜单到左侧。
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
                Toast.makeText(ViewTypeActivity.this, "list第" + position + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT)
                        .show();
            } else if (direction == SwipeRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(ViewTypeActivity.this, "list第" + position + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT)
                        .show();
            }
        }
    };

    @Override
    protected List<String> createDataList() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            if (i % 3 == 0) {
                strings.add("我右侧有3个菜单");
            } else if (i % 2 == 0) {
                strings.add("我右侧有2个菜单");
            } else {
                strings.add("我左侧有2个菜单");
            }
        }
        return strings;
    }
}