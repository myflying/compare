package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.HomeDataInfoRet;
import com.yc.compare.model.HomeDataModelImp;
import com.yc.compare.view.HomeDataView;

import java.util.List;

import okhttp3.RequestBody;

/**
 * Created by iflying on 2018/1/9.
 */

public class HomeDataPresenterImp extends BasePresenterImp<HomeDataView, HomeDataInfoRet> implements HomeDataPresenter {
    private Context context = null;
    private HomeDataModelImp homeDataModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public HomeDataPresenterImp(HomeDataView view, Context context) {
        super(view);
        homeDataModelImp = new HomeDataModelImp(context);
    }

    @Override
    public void getHomeDataByPage(int page) {
        homeDataModelImp.getHomeDataByPage(page, this);
    }
}
