package com.yc.compare.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.orhanobut.logger.Logger;
import com.yc.compare.R;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.ui.custom.LineChartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by myflying on 2018/12/6.
 */
public class GoodDetailActivity extends BaseFragmentActivity {

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tv_detail_title)
    TextView mTitleTextView;

    @BindView(R.id.iv_back)
    ImageView mBackImageView;

    @BindView(R.id.iv_follow)
    ImageView mFollowImageView;

    @BindView(R.id.iv_share)
    ImageView mShareImageView;

    @BindView(R.id.line_chart_view)
    LineChartView lineChart;

    private int[] dataArr = new int[]{150, 120, 200, 88, 139, 66};

    @Override
    protected int getContextViewId() {
        return R.layout.activity_good_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Logger.i("bar height --->" + appBarLayout.getHeight() + "---verticalOffset--->" + verticalOffset);
                if (-verticalOffset >= appBarLayout.getHeight() - SizeUtils.dp2px(72)) {
                    mTitleTextView.setVisibility(View.VISIBLE);
                    mBackImageView.setImageResource(R.mipmap.back_icon);
                    mFollowImageView.setImageResource(R.mipmap.white_follow);
                    mShareImageView.setImageResource(R.mipmap.white_share);
                } else {
                    mTitleTextView.setVisibility(View.INVISIBLE);
                    mBackImageView.setImageResource(R.mipmap.detail_back);
                    mFollowImageView.setImageResource(R.mipmap.detail_follow);
                    mShareImageView.setImageResource(R.mipmap.detail_share);
                }
            }
        });


        List<LineChartView.Data> datas = new ArrayList<>();
        for (int value : dataArr) {
            LineChartView.Data data = new LineChartView.Data(value);
            datas.add(data);
        }
        lineChart.setData(datas);
        lineChart.setRulerYSpace(30);
        lineChart.setStepSpace((ScreenUtils.getScreenWidth() - SizeUtils.dp2px(24)) / 5);
        lineChart.setShowTable(true);
        //lineChart.setCubePoint(true);

    }

}
