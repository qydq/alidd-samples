package com.sunsty.alidd.take;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface NewUploadApi {
    //普通参数请求网络验证码
    @POST("/api/test/sunsta/getSmsCode")
    Observable<ResponseBody> getSmsCode(@Body Map<String, Object> ask);


    @Multipart
    @POST("/api/test/sunsta/upload")
    Observable<ResponseBody> normalRequest(@PartMap Map<String, RequestBody> map);

    //多图片上传
    @Multipart
    @POST("/api/test/sunsta/upload")
    Observable<ResponseBody> uploadFiles(@PartMap Map<String, RequestBody> map);


    // 视频上传
    @Multipart
    @POST("/api/test/sunsta/uploadMovieFiles")
    Observable<ResponseBody> uploadMovieFiles(@Part MultipartBody.Part file, @Part MultipartBody.Part params); //@PartMap Map<String, RequestBody> params
}
