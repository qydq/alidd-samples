package com.livery.demo.module.md;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.livery.demo.R;
import com.livery.demo.module.md.sheet.CustomFragmentSheetDialog;
import com.livery.demo.module.md.sheet.FullFragmentSheetDialog;
import com.sunsta.bear.view.AliActivity;

/**
 * create with sunst
 * refs：https://blog.csdn.net/XG1057415595/article/details/86741186
 * 【仿网易云歌手资料页面的实现-NestedScrolling】
 * https://blog.csdn.net/XG1057415595/article/details/81155391
 */
public class BottomSheetBehaviorActivity extends AliActivity {
    BottomSheetBehavior behavior;

    @Override
    public void initView() {
        setContentView(R.layout.md_activity_sheet);
        View bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setPeekHeight(150);
        behavior.setHideable(true);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
            }

            @Override
            public void onSlide(@NonNull View view, float v) {
            }
        });
        /*
         *这是本activity中显示的sheet
         * */
        Button button = findViewById(R.id.button_show);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    Log.e("clidk", "onClick: collapsed");
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    Log.e("clidk", "onClick: expanded");
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });

        /*
         *这是两个fragmentDialog形式的dialog
         * */
        Button showCustomFragmentDialog = findViewById(R.id.showCustomFragmentDialog);
        showCustomFragmentDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomFragmentSheetDialog fullSheetDialogFragment = new CustomFragmentSheetDialog();
                fullSheetDialogFragment.show(getSupportFragmentManager(), "fullFragment");
            }
        });

        Button showFullSheetDialog = findViewById(R.id.showFullSheetDialog);
        showFullSheetDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FullFragmentSheetDialog fullSheetDialogFragment = new FullFragmentSheetDialog();
                fullSheetDialogFragment.show(getSupportFragmentManager(), "fullFragment");
            }
        });

        /*
         * 这是一个对话框形式的Nest
         * */
        Button showDialogBottomSheet = findViewById(R.id.showDialogBottomSheet);
        showDialogBottomSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//原BottomSheetDialogActivity
                BottomSheetDialog dialog;
                View view = getLayoutInflater().inflate(R.layout.item_sheet_dialog, null);
                dialog = new BottomSheetDialog(BottomSheetBehaviorActivity.this, R.style.BottomSheetEdit);
                dialog.setContentView(view);
                View parent = (View) view.getParent();
                BottomSheetBehavior behavior = BottomSheetBehavior.from(parent);
// behavior.setPeekHeight(400);//首次弹出的高度
                behavior.setPeekHeight(getWindowHeight());//首次弹出的高度
                behavior.setHideable(false);

                behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View view, int i) {
// if (i == BottomSheetBehavior.STATE_HIDDEN) {
// dialog.dismiss();
// behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
// }
                    }

                    @Override
                    public void onSlide(@NonNull View view, float v) {

                    }
                });

                dialog.show();
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, 1600);
                dialog.getWindow().setGravity(Gravity.BOTTOM);

                Button button = findViewById(R.id.button_show);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialog.isShowing()) {
                            Log.e("clidk", "onClick: collapsed");
                            dialog.dismiss();
                        } else {
                            Log.e("clidk", "onClick: expanded");
                            dialog.show();
                        }
                    }
                });

            }
        });
    }

    private int getWindowHeight() {
// return ScreenUtils.getDeviceHeight(this);
// Resources res = getResources();
// DisplayMetrics displayMetrics = res.getDisplayMetrics();
// int heightPieels = displayMetrics.heightPixels;
// return displayMetrics.heightPixels;

        int peekHeight = getResources().getDisplayMetrics().heightPixels;
//设置弹窗高度为屏幕高度的3/4
        return peekHeight - peekHeight / 3;
    }
}