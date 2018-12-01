package com.yc.compare.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yc.compare.R;
import com.yc.compare.ui.adapter.MyFragmentAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;

import butterknife.BindView;

/**
 * Created by iflying on 2017/12/14.
 */

public class MainActivity extends BaseFragmentActivity {


    @BindView(R.id.tablayout)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    private final int[] TITLES = new int[]{R.string.tab_home, R.string.tab_fight, R.string.tab_show, R.string.tab_my};

    private final int[] IMAGES = new int[]{R.drawable.tab_home_selector, R.drawable.tab_fight_selector, R.drawable.tab_show_selector, R.drawable.tab_my_selector};

    private MyFragmentAdapter adapter;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initTopBar();
        initViews();
    }

    private void initTopBar() {
//        mTopBar.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popBackStack();
//            }
//        });

        //mTopBar.setTitle(getResources().getString(R.string.tab_home));
        //mTopBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        //View topView = getLayoutInflater().inflate(R.layout.main_top_view,null);
        //mTopBar.setCenterView(topView);
    }

    public void initViews() {
        setTabs(tabLayout, this.getLayoutInflater(), TITLES, IMAGES);
        adapter = new MyFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    public void initData() {}

    public void setTabs(TabLayout tabLayout, LayoutInflater layoutInflater, int[] titles, int[] images) {
        for (int i = 0; i < titles.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = layoutInflater.inflate(R.layout.tab_custom, null);
            tab.setCustomView(view);
            TextView tabText = view.findViewById(R.id.tv_tab);
            tabText.setText(titles[i]);
            ImageView tabImage = view.findViewById(R.id.iv_tab);
            tabImage.setImageResource(images[i]);
            tabLayout.addTab(tab);
        }
    }
}
