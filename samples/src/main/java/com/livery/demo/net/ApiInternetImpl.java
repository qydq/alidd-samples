package com.livery.demo.net;

import com.sunsta.bear.presenter.net.InternetClient;
import com.sunsta.bear.presenter.net.InternetObserver;

import java.util.Map;

import retrofit2.http.FieldMap;

public class ApiInternetImpl {
    public LiveryInternetApi getNewUploadApi() {
        return InternetClient.getInstance().getRetrofit("https://zhuanlan.zhihu.com/api/").create(LiveryInternetApi.class);
    }

    public void get_observable_Zhihu_Columns(InternetObserver internetObserver, @FieldMap Map<String, Object> map) {
        internetObserver.request(getNewUploadApi().get_observable_Zhihu_Columns());
    }
}