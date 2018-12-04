package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.GoodInfoRet;
import com.yc.compare.model.GoodInfoModelImp;
import com.yc.compare.view.GoodInfoView;

/**
 * Created by iflying on 2018/1/9.
 */

public class GoodInfoPresenterImp extends BasePresenterImp<GoodInfoView, GoodInfoRet> implements GoodInfoPresenter {
    private Context context = null;
    private GoodInfoModelImp goodInfoModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public GoodInfoPresenterImp(GoodInfoView view, Context context) {
        super(view);
        goodInfoModelImp = new GoodInfoModelImp(context);
    }

    @Override
    public void getGoodInfoByType(int page, String cid) {
        goodInfoModelImp.getGoodInfoByType(page, cid, this);
    }
}
