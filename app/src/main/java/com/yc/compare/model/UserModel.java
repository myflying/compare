package com.yc.compare.model;


import com.yc.compare.base.IBaseRequestCallBack;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by admin on 2017/3/13.
 */

public interface UserModel<T> {

    void validateUserInfo(String name, String password, IBaseRequestCallBack<T> iBaseRequestCallBack);

    void registerUserInfo(RequestBody body, MultipartBody.Part part, IBaseRequestCallBack<T> iBaseRequestCallBack);

    void saveMorePics(RequestBody body, List<MultipartBody.Part> parts, IBaseRequestCallBack<T> iBaseRequestCallBack);

    void saveMoreFiles(RequestBody body, Map<String, RequestBody> files, IBaseRequestCallBack<T> iBaseRequestCallBack);

    void onUnsubscribe();
}
