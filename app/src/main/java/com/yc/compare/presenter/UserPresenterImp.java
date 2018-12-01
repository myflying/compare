package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.model.UserModelImp;
import com.yc.compare.view.UserView;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by admin on 2017/3/13.
 */

public class UserPresenterImp extends BasePresenterImp<UserView,RequestBody> implements UserPresenter{

    private Context context = null;
    private UserModelImp userModelImp = null;

    public UserPresenterImp(UserView view,Context context){
        super(view);
        this.context = context;
        this.userModelImp = new UserModelImp(context);
    }

    @Override
    public void validateUserInfo(String name, String password) {
        userModelImp.validateUserInfo(name,password,this);
    }

    @Override
    public void registerUserInfo(RequestBody body,MultipartBody.Part part) {
        userModelImp.registerUserInfo(body,part,this);
    }

    @Override
    public void saveMorePics(RequestBody body, List<MultipartBody.Part> parts) {
        userModelImp.saveMorePics(body,parts,this);
    }

    @Override
    public void saveMoreFiles(RequestBody body, Map<String, RequestBody> files) {
        userModelImp.saveMoreFiles(body,files,this);
    }

    @Override
    public void unSubscribe() {
        userModelImp.onUnsubscribe();
    }
}
