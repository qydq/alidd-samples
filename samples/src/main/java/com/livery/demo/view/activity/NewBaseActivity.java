package com.livery.demo.view.activity;

import com.sunsta.bear.view.ParallaxActivity;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class NewBaseActivity extends ParallaxActivity {
    public CompositeDisposable mCompositeDisposable;

    @Override
    public void initView() {

    }

    /**
     * 将 {@link Disposable} 添加到 {@link CompositeDisposable} 中统一管理
     * 可在 Activity#onDestroy() 中使用 #unDispose() 停止正在执行的 RxJava 任务, 避免内存泄漏(框架已自行处理)
     *
     * @param disposable
     */
    public void addDispose(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);//将所有 Disposable 放入集中处理
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    public void unDispose(boolean isDestroy) {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear(); // 保证 Activity 结束时取消所有正在执行的订阅
        }
        if (isDestroy) {
            this.mCompositeDisposable = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unDispose(true);

    }
}
