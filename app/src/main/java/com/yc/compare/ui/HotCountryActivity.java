package com.yc.compare.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;
import com.yc.compare.R;
import com.yc.compare.bean.CountryInfoRet;
import com.yc.compare.common.Constants;
import com.yc.compare.presenter.CountryInfoPresenterImp;
import com.yc.compare.ui.adapter.HotCountryAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.view.CountryInfoView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/3.
 */
public class HotCountryActivity extends BaseFragmentActivity implements CountryInfoView {

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.hot_country_list)
    RecyclerView mHotCountryListView;

    private HotCountryAdapter hotCountryAdapter;

    private CountryInfoPresenterImp countryInfoPresenterImp;

    private ProgressDialog progressDialog = null;

    private int pageSize = 12;

    private int currentPage = 1;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_hot_country;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews() {
        countryInfoPresenterImp = new CountryInfoPresenterImp(this, this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在查询");

        hotCountryAdapter = new HotCountryAdapter(this, null);
        mHotCountryListView.setLayoutManager(new LinearLayoutManager(this));
        mHotCountryListView.setAdapter(hotCountryAdapter);

        avi.show();
        countryInfoPresenterImp.getCountryInfoList(currentPage);

        hotCountryAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;
                countryInfoPresenterImp.getCountryInfoList(currentPage);
            }
        }, mHotCountryListView);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(CountryInfoRet tData) {
        avi.hide();
        Logger.i(JSONObject.toJSONString(tData));
        if (tData != null && tData.getCode() == Constants.SUCCESS) {
            hotCountryAdapter.setNewData(tData.getData());
            hotCountryAdapter.loadMoreEnd();
        } else {
            ToastUtils.showLong("数据异常,请重试");
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {
        avi.hide();
    }

    @OnClick(R.id.iv_back)
    void back() {
        popBackStack();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        popBackStack();
    }
}
