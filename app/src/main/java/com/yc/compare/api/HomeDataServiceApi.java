package com.yc.compare.api;

import com.yc.compare.bean.HomeDataInfoRet;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by admin on 2017/3/13.
 */

public interface HomeDataServiceApi {

    @GET("index.php?m=Home&c=Zbsq&a=test1&version=3.1")
    Observable<HomeDataInfoRet> getDataByPage(@Query("page") String page);

}
