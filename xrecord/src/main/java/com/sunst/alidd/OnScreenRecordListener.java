package com.sunst.alidd;


/**
 * 请求视频录制结果
 */
public interface OnScreenRecordListener {
    void onSuccess(); // 同意录屏
    void onFailed(); // 拒绝录屏
}