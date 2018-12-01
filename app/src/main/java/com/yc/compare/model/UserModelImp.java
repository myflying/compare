package com.yc.compare.model;

import android.content.Context;

import com.yc.compare.api.UploadApi;
import com.yc.compare.api.UserServiceApi;
import com.yc.compare.base.BaseModel;
import com.yc.compare.base.IBaseRequestCallBack;
import com.yc.compare.bean.UserInfoBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 2017/3/13.
 */

public class UserModelImp extends BaseModel implements UserModel<RequestBody> {

    private Context context = null;
    private UserServiceApi userServiceApi;
    private UploadApi uploadApi;
    private CompositeSubscription mCompositeSubscription;

    public UserModelImp(Context mContext) {
        super();
        context = mContext;
        userServiceApi = mRetrofit.create(UserServiceApi.class);
        uploadApi = mRetrofit.create(UploadApi.class);
        mCompositeSubscription = new CompositeSubscription();
    }

    @Override
    public void validateUserInfo(String name, String password, final IBaseRequestCallBack iBaseRequestCallBack) {
        mCompositeSubscription.add(userServiceApi.validateUserInfo(name, password)  //将subscribe添加到subscription，用于注销subscribe
                .observeOn(AndroidSchedulers.mainThread())//指定事件消费线程
                .subscribeOn(Schedulers.io())  //指定 subscribe() 发生在 IO 线程
                .subscribe(new Subscriber<UserInfoBean>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        //onStart它总是在 subscribe 所发生的线程被调用 ,如果你的subscribe不是主线程，则会出错，则需要指定线程;
                        iBaseRequestCallBack.beforeRequest();
                    }

                    @Override
                    public void onCompleted() {
                        //回调接口：请求已完成，可以隐藏progress
                        iBaseRequestCallBack.requestComplete();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //回调接口：请求异常
                        iBaseRequestCallBack.requestError(e);
                    }

                    @Override
                    public void onNext(UserInfoBean userInfoBean) {
                        //回调接口：请求成功，获取实体类对象
                        iBaseRequestCallBack.requestSuccess(userInfoBean);
                    }
                }));

    }

    @Override
    public void registerUserInfo(RequestBody body, MultipartBody.Part part, final IBaseRequestCallBack iBaseRequestCallBack) {
        mCompositeSubscription.add(uploadApi.registerUserInfo(body, part)
                .observeOn(AndroidSchedulers.mainThread())//指定事件消费线程
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<RequestBody>() {
                    @Override
                    public void onCompleted() {
                        iBaseRequestCallBack.beforeRequest();
                    }

                    @Override
                    public void onError(Throwable e) {
                        iBaseRequestCallBack.requestComplete();
                    }

                    @Override
                    public void onNext(RequestBody requestBody) {
                        iBaseRequestCallBack.requestSuccess(requestBody);
                    }
                }));
    }

    @Override
    public void saveMorePics(RequestBody body, List<MultipartBody.Part> parts, final IBaseRequestCallBack<RequestBody> iBaseRequestCallBack) {
        mCompositeSubscription.add(uploadApi.saveMorePics(body, parts)
                .observeOn(AndroidSchedulers.mainThread())//指定事件消费线程
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<RequestBody>() {
                    @Override
                    public void onCompleted() {
                        iBaseRequestCallBack.beforeRequest();
                    }

                    @Override
                    public void onError(Throwable e) {
                        iBaseRequestCallBack.requestComplete();
                    }

                    @Override
                    public void onNext(RequestBody requestBody) {
                        iBaseRequestCallBack.requestSuccess(requestBody);
                    }
                }));
    }

    @Override
    public void saveMoreFiles(RequestBody body, Map<String, RequestBody> files, final IBaseRequestCallBack<RequestBody> iBaseRequestCallBack) {
        mCompositeSubscription.add(uploadApi.saveMoreFiles(body, files)
                .observeOn(AndroidSchedulers.mainThread())//指定事件消费线程
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<RequestBody>() {
                    @Override
                    public void onCompleted() {
                        iBaseRequestCallBack.beforeRequest();
                    }

                    @Override
                    public void onError(Throwable e) {
                        iBaseRequestCallBack.requestComplete();
                    }

                    @Override
                    public void onNext(RequestBody requestBody) {
                        iBaseRequestCallBack.requestSuccess(requestBody);
                    }
                }));
    }

    @Override
    public void onUnsubscribe() {
        //判断状态
        if (mCompositeSubscription.isUnsubscribed()) {
            mCompositeSubscription.clear();  //注销
            mCompositeSubscription.unsubscribe();
        }
    }
}
