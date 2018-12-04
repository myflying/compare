package com.yc.compare.api;

import com.yc.compare.bean.CategoryInfoRet;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by myflying on 2018/12/3.
 */
public interface CategoryInfoServiceApi {

    @POST("getCategoryInfoByType")
    Observable<CategoryInfoRet> getCategoryInfoList(@Body RequestBody requestBody);
}
