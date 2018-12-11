package com.yc.compare.ui.fragment.sub;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;
import com.yc.compare.R;
import com.yc.compare.bean.NewsInfo;
import com.yc.compare.bean.NewsInfoRet;
import com.yc.compare.presenter.NewsInfoPresenterImp;
import com.yc.compare.ui.adapter.NewsInfoAdapter;
import com.yc.compare.ui.base.BaseFragment;
import com.yc.compare.ui.custom.RecycleViewDivider;
import com.yc.compare.view.NewsInfoView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iflying on 2018/2/6.
 */

public class NewsItemFragment extends BaseFragment implements NewsInfoView {

    private NewsInfoPresenterImp newsInfoPresenterImp;

    private NewsInfoAdapter newsInfoAdapter;

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.news_list)
    RecyclerView mNewsListView;

    /**
     * onCreateView
     */
    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_item_page, null);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    public static NewsItemFragment newInstance(int i) {
        NewsItemFragment fragment = new NewsItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category_id", i == 0 ? "" : i + "");
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initViews() {
        avi.hide();
        //newsInfoPresenterImp = new NewsInfoPresenterImp(this, getActivity());
        //newsInfoPresenterImp.getNewsInfoList(1);
        List<NewsInfo> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(new NewsInfo());
        }
        newsInfoAdapter = new NewsInfoAdapter(getActivity(), list);
        mNewsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mNewsListView.addItemDecoration(new RecycleViewDivider(
                getActivity(), LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.line_color)));
        mNewsListView.setAdapter(newsInfoAdapter);
    }

    /**
     * @descriptoin 请求前加载progress
     */
    @Override
    public void showProgress() {

    }

    /**
     * @descriptoin 请求结束之后隐藏progress
     */
    @Override
    public void dismissProgress() {

    }

    /**
     * @param tData 数据类型
     * @descriptoin 请求数据成功
     */
    @Override
    public void loadDataSuccess(NewsInfoRet tData) {

    }

    /**
     * @param throwable 异常类型
     * @descriptoin 请求数据错误
     */
    @Override
    public void loadDataError(Throwable throwable) {

    }
}
