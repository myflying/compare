package com.yc.compare.api;

import com.yc.compare.bean.NewsInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by myflying on 2018/12/3.
 */
public interface NewsInfoServiceApi {

    @POST("v1.common/hotBrandList")
    Observable<NewsInfoRet> getBrandInfoList(@Body RequestBody requestBody);
}
