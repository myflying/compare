package com.yc.compare.presenter;

import android.content.Context;

import com.yc.compare.base.BasePresenterImp;
import com.yc.compare.bean.CategoryInfoRet;
import com.yc.compare.model.CategoryInfoModelImp;
import com.yc.compare.view.CategoryInfoView;

/**
 * Created by iflying on 2018/1/9.
 */

public class CategoryInfoPresenterImp extends BasePresenterImp<CategoryInfoView, CategoryInfoRet> implements CategoryInfoPresenter {
    private Context context = null;
    private CategoryInfoModelImp categoryInfoModelImp = null;

    /**
     * @param view 具体业务的视图接口对象
     * @descriptoin 构造方法
     */
    public CategoryInfoPresenterImp(CategoryInfoView view, Context context) {
        super(view);
        categoryInfoModelImp = new CategoryInfoModelImp(context);
    }

    @Override
    public void getCategoryInfoList(int level) {
        categoryInfoModelImp.getCategoryInfoList(level, this);
    }
}
