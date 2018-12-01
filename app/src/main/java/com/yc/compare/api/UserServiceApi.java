package com.yc.compare.api;

import com.yc.compare.bean.UserInfoBean;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by admin on 2017/3/13.
 */

public interface UserServiceApi {

    @FormUrlEncoded
    @POST("validateUserInfo?")
    Observable<UserInfoBean> validateUserInfo(@Field("name") String name, @Field("password") String password);
}
