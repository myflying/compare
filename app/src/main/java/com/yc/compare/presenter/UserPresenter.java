package com.yc.compare.presenter;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by admin on 2017/3/13.
 */

public interface UserPresenter {
    void validateUserInfo(String name, String password);

    void registerUserInfo(RequestBody body, MultipartBody.Part part);

    void saveMorePics(RequestBody body, List<MultipartBody.Part> parts);

    void saveMoreFiles(RequestBody body, Map<String, RequestBody> files);

    void unSubscribe();
}
