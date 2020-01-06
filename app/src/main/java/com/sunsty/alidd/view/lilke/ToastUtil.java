package com.sunsty.alidd.view.lilke;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.AnApplication;
import com.sunsty.alidd.R;


public class ToastUtil {

    private static Toast toast;

    public static void showToast(CharSequence msg) {
        show(msg, 0, Toast.LENGTH_SHORT);
    }

    public static void showToast(CharSequence msg, int res) {
        show(msg, res, Toast.LENGTH_SHORT);
    }

    public static void showLongToast(CharSequence msg) {
        show(msg, 0, Toast.LENGTH_LONG);
    }

    public static void showLongToast(CharSequence msg, int res) {
        show(msg, res, Toast.LENGTH_LONG);
    }

    /**
     * 自定义toast
     */
    private static void show(CharSequence tvStr, int res, int duration) {
        if (toast == null) {
            toast = new Toast(AnApplication.getInstance());
        }
        View view = LayoutInflater.from(AnApplication.getInstance()).inflate(R.layout.item_toast, null);
        TextView tv_toast_msg = view.findViewById(R.id.tvToast);
        tv_toast_msg.setText(tvStr);
        ImageView iv_toast = view.findViewById(R.id.ivToast);
        if (res > 0) {
            iv_toast.setImageResource(res);
        }
        toast.setView(view);
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void cancel() {
        if (null != toast) {
            toast.cancel();
        }
    }
}