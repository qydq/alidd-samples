package com.sunsty.alidd.view.activity.download;

public interface DownloadListener {
    public void onSuccess();//

    public void onFailed();//

    public void onPaused();//

    public void onCanceled();//

    public void onProgress(int progress);//
}
