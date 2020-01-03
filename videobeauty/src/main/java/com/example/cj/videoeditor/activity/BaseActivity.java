package com.example.cj.videoeditor.activity;

import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cj.videoeditor.widget.LoadingDialog;

/**
 * Created by sunst 2020年1月3日,希望大家尊重版权和劳动成果，本开源精神 开源出来可以提供给大家使用和帮助，
 * 但也请关注本人唯一知乎：https://zhihu.com/people/qydq 解锁更多内容
 */
public class BaseActivity extends AppCompatActivity {
    public LoadingDialog loading;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (loading != null) {
            loading.dismiss();
        }
    }

    /**
     * 修改加载提示信息
     *
     * @param tips
     */
    public void setLoading(String tips) {
        if (null == loading) {
            loading = new LoadingDialog(this);
            loading.setTips(tips);
            loading.show();
        } else {
            loading.setTips(tips);
        }
    }

    /**
     * 显示加载提示
     *
     * @param tips
     */
    public void showLoading(final String tips) {
        if (null == loading || loading.isWithTitle())
            loading = new LoadingDialog(this);

        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            loading.setTips(tips);
            loading.show();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loading.setTips(tips);
                    loading.show();
                }
            });

        }
    }

    /**
     * @param tips
     * @param cancelable 是否可取消  false 不可以  true 可以
     */
    public void showLoading(final String tips, final boolean cancelable) {
        if (isDestroyed()) {
            return;
        }
        endLoading();
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            loading = new LoadingDialog(this, tips, cancelable);
            loading.show();
        } else {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loading = new LoadingDialog(BaseActivity.this, tips, cancelable);
                    loading.show();
                }
            });
        }
    }

    /**
     * 显示加载提示
     *
     * @param title 标题
     * @param tips  提示
     */
    public void showLoading(String title, String tips) {
        endLoading();
        loading = new LoadingDialog(this, title, tips);
        loading.show();
    }

    /**
     * 是否响应back键
     *
     * @param cancelable true：响应，false：不响应
     */
    public void setLoadingCancelable(boolean cancelable) {
        if (null != loading) {
            loading.setCancelable(cancelable);
        }
    }

    /**
     * 隐藏loading
     */
    public void endLoading() {
        if (null != loading) {
            loading.dismiss();
        }
    }

    public boolean isLoading() {
        return loading != null && loading.isShowing();
    }
}
