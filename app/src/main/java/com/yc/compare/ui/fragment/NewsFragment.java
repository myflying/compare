package com.yc.compare.ui.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.yc.compare.R;
import com.yc.compare.ui.adapter.SubFragmentAdapter;
import com.yc.compare.ui.base.BaseFragment;
import com.yc.compare.ui.fragment.sub.NewsItemFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iflying on 2017/12/14.
 */

public class NewsFragment extends BaseFragment {

    @BindView(R.id.search_layout)
    LinearLayout mSearchLayout;

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    List<String> mTitleDataList;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_new_info, null);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    public void initViews() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth() - SizeUtils.dp2px(30), SizeUtils.dp2px(30));
        params.setMargins(SizeUtils.dp2px(15), BarUtils.getStatusBarHeight(), 0, 0);
        mSearchLayout.setLayoutParams(params);

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            fragments.add(NewsItemFragment.newInstance(i));
        }

        mTitleDataList = new ArrayList<>();
        mTitleDataList.add("全部");
        mTitleDataList.add("护肤品");
        mTitleDataList.add("化妆品");
        mTitleDataList.add("包包");
        mTitleDataList.add("手表");
        mTitleDataList.add("项链");
        mTitleDataList.add("护肤品");
        mTitleDataList.add("化妆品");
        mTitleDataList.add("包包");
        mTitleDataList.add("手表");
        mTitleDataList.add("项链");

        SubFragmentAdapter viewPageAdapter = new SubFragmentAdapter(getChildFragmentManager(), fragments,mTitleDataList);
        mViewPager.setAdapter(viewPageAdapter);

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        tabLayout.setupWithViewPager(mViewPager);
    }
}
