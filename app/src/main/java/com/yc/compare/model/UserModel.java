package com.yc.compare.model;


import com.yc.compare.base.IBaseRequestCallBack;

/**
 * Created by admin on 2017/3/13.
 */

public interface UserModel<T> {

    void userLogin(String name, String password, String type, IBaseRequestCallBack<T> iBaseRequestCallBack);

    void register(String name, String password, IBaseRequestCallBack<T> iBaseRequestCallBack);

    void sendSms(String name, String smsCode, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
