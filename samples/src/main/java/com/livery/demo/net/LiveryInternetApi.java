package com.livery.demo.net;

import com.livery.demo.model.ReplyGithubUserMode;
import com.livery.demo.model.ReplyMasterMenuInfoMode;
import com.livery.demo.model.TBaseResponseMode;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LiveryInternetApi {
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

    @FormUrlEncoded
    @POST("live/api/homepage/master/init_master_submenu")
    Observable<ResponseBody> uploadDeviceInfo(@FieldMap Map<String, Object> parameter);

    /*以下为已经统一标准*/
    @GET("columns/qyddai")
    <T> Call<ResponseBody> get_tCall_Zhihu_Columns();

    @GET("columns/qyddai")
    Observable<ResponseBody> get_observable_Zhihu_Columns();

    @GET("banners")
    Observable<ResponseBody> get_observable_GankBanners();

    @GET("master/init_master_submenu")
    Observable<TBaseResponseMode<ReplyMasterMenuInfoMode>> get_observable_master_submenu();

    /*rx && AddDispose && entity mode*/
    @GET("article/list/1/json")
    Observable<ResponseBody> get_observable_json();

// @GET("index.php?g=Wwapi&m=Startbanner&a=index")
// Observable<ResponseBody> obtainAd();

    /*rx && AddDispose && entity mode*/
    @GET("article/list/{pageNum}/{json}")
    Observable<ResponseBody> get_observable_json_inparam(@Path("pageNum") int pageNum, @Path("json") String json);

    /**
     * 以下为使用的请求链接，地址可以重复，统一使用
     */
    @Headers("BaseUrl:javaBase")
    @FormUrlEncoded
    @POST("api/play/recommend/query_recommend_list")
    Observable<ResponseBody> post_observable_videoPullUrl(@FieldMap Map<String, Object> map);

    @GET("repos")
    Observable<ReplyGithubUserMode> get_observable_github_user(@Query("page") String page, @Query("per_page") String per_page);
}