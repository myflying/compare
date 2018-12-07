package com.yc.compare.ui;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.example.zhouwei.library.CustomPopWindow;
import com.orhanobut.logger.Logger;
import com.yc.compare.R;
import com.yc.compare.bean.ImageInfo;
import com.yc.compare.ui.adapter.ImageInfoAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.ui.custom.LineChartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/6.
 */
public class GoodDetailActivity extends BaseFragmentActivity {

    @BindView(R.id.content_layout)
    CoordinatorLayout coordinatorLayout;

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

    @BindView(R.id.good_image_list)
    RecyclerView mGoodImageListView;

    ImageInfoAdapter imageInfoAdapter;

    private CustomPopWindow customPopWindow;

    private int[] dataArr = new int[]{150, 120, 200, 88, 139, 66};

    @Override
    protected int getContextViewId() {
        return R.layout.activity_good_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initGoodImageList();
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

        Logger.i("width--->" + ScreenUtils.getScreenWidth() / ScreenUtils.getScreenDensity() / 6);

        List<LineChartView.Data> datas = new ArrayList<>();
        for (int value : dataArr) {
            LineChartView.Data data = new LineChartView.Data(value);
            datas.add(data);
        }
        lineChart.setData(datas);
        lineChart.setRulerYSpace(30);
        lineChart.setStepSpace((int) ((ScreenUtils.getScreenWidth() / ScreenUtils.getScreenDensity() - 36) / 6));
        lineChart.setShowTable(true);

    }

    public void initGoodImageList() {
        String[] images = {"https://img30.360buyimg.com/sku/jfs/t6070/362/7109949100/46672/a0adbc0c/59797ea0N3b0c6829.jpg",
                "https://img30.360buyimg.com/sku/jfs/t6826/205/768925923/71577/d80aacee/59797ea0Nb0f3520a.jpg",
                "https://img30.360buyimg.com/sku/jfs/t6010/41/7528320653/112007/9f2b8142/597f0415N997e02ce.jpg",
                "https://img30.360buyimg.com/sku/jfs/t5932/232/7194714464/49401/c9d581e1/59797ea0Nd320bb69.jpg"};
        List<ImageInfo> list = new ArrayList<>();
        for (int i = 0; i < images.length; i++) {
            list.add(new ImageInfo((i + 1) + "", images[i]));
        }
        imageInfoAdapter = new ImageInfoAdapter(this, list);
        mGoodImageListView.setLayoutManager(new LinearLayoutManager(this));
        mGoodImageListView.setAdapter(imageInfoAdapter);
        mGoodImageListView.setNestedScrollingEnabled(false);
    }

    @OnClick(R.id.layout_params)
    void showParams() {
        View paramsView = LayoutInflater.from(this).inflate(R.layout.good_params_view, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(paramsView);
        bottomSheetDialog.show();
    }

    @OnClick(R.id.iv_share)
    void share() {
        View paramsView = LayoutInflater.from(this).inflate(R.layout.share_view, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(paramsView);
        bottomSheetDialog.show();
    }

}
