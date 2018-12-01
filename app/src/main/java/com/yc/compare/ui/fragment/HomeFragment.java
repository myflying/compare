package com.yc.compare.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.orhanobut.logger.Logger;
import com.yc.compare.R;
import com.yc.compare.bean.HomeDataInfoRet;
import com.yc.compare.ui.adapter.SubFragmentAdapter;
import com.yc.compare.ui.base.BaseFragment;
import com.yc.compare.ui.custom.GlideImageLoader;
import com.yc.compare.view.HomeDataView;
import com.youth.banner.Banner;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iflying on 2017/12/14.
 */

public class HomeFragment extends BaseFragment implements HomeDataView {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @BindView(R.id.magic_indicator)
    MagicIndicator magicIndicator;

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.top_content_layout)
    FrameLayout mTopContentLayout;

    @BindView(R.id.banner)
    Banner mBanner;

    List<String> mTitleDataList;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_home, null);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

    public void initViews() {

        mTopContentLayout.setLayoutParams(new CollapsingToolbarLayout.LayoutParams(CollapsingToolbarLayout.LayoutParams.MATCH_PARENT,SizeUtils.dp2px(270)));

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                //LogUtil.msg("TAG  " + verticalOffset + "--" + appBarLayout.getHeight() + " --" + collapsingToolbarLayout.getHeight());
                Logger.i("bar height --->" + appBarLayout.getHeight() + "---verticalOffset--->" + verticalOffset);
                if (-verticalOffset >= appBarLayout.getHeight() - SizeUtils.dp2px(120)) {
                    toolbar.setVisibility(View.VISIBLE);
                    mTopContentLayout.setVisibility(View.INVISIBLE);
                } else {
                    mTopContentLayout.setVisibility(View.VISIBLE);
                    toolbar.setVisibility(View.INVISIBLE);
                }

            }
        });

        initBanner();

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            fragments.add(HotFragment.newInstance(i));
        }
        SubFragmentAdapter viewPageAdapter = new SubFragmentAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(viewPageAdapter);

        mTitleDataList = new ArrayList<>();
        mTitleDataList.add("页面1");
        mTitleDataList.add("页面2");
        mTitleDataList.add("页面3");

        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(Color.GRAY);
                colorTransitionPagerTitleView.setSelectedColor(Color.BLACK);
                colorTransitionPagerTitleView.setText(mTitleDataList.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                return indicator;
            }
        });

        magicIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magicIndicator, mViewPager);
    }

    public void initBanner(){
        List<Integer> urls = new ArrayList<>();
        urls.add(R.mipmap.b1);
        urls.add(R.mipmap.b2);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(urls);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
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
     * @param homeDataInfoRet 数据类型
     * @descriptoin 请求数据成功
     */
    @Override
    public void loadDataSuccess(HomeDataInfoRet homeDataInfoRet) {
        LogUtils.i("data--->" + homeDataInfoRet.getData().size());
    }

    /**
     * @param throwable 异常类型
     * @descriptoin 请求数据错误
     */
    @Override
    public void loadDataError(Throwable throwable) {

    }
}