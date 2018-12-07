package com.yc.compare.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.zhouwei.library.CustomPopWindow;
import com.orhanobut.logger.Logger;
import com.wang.avi.AVLoadingIndicatorView;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;
import com.yc.compare.R;
import com.yc.compare.base.IBaseView;
import com.yc.compare.bean.BrandInfo;
import com.yc.compare.bean.CategoryWrapperRet;
import com.yc.compare.bean.Condition;
import com.yc.compare.bean.CountryWrapper;
import com.yc.compare.bean.GoodInfoRet;
import com.yc.compare.presenter.CategoryWrapperPresenterImp;
import com.yc.compare.presenter.GoodInfoPresenterImp;
import com.yc.compare.ui.adapter.GoodInfoAdapter;
import com.yc.compare.ui.adapter.SearchBrandAdapter;
import com.yc.compare.ui.base.BaseFragmentActivity;
import com.yc.compare.ui.custom.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/3.
 */
public class GoodListActivity extends BaseFragmentActivity implements IBaseView {

    @BindView(R.id.draw)
    DrawerLayout drawerLayout;

    @BindView(R.id.avi)
    AVLoadingIndicatorView avi;

    @BindView(R.id.good_list)
    RecyclerView mGoodListView;

    @BindView(R.id.search_layout)
    LinearLayout mSearchLayout;

    @BindView(R.id.condition_layout)
    LinearLayout mConditionLayout;

    @BindView(R.id.layout_brand)
    LinearLayout mBrandLayout;

    @BindView(R.id.pop_bg_layout)
    LinearLayout popBgLayout;

    @BindView(R.id.search_country_list)
    SwipeMenuRecyclerView mTypeContentListView;

    private GoodInfoAdapter goodInfoAdapter;

    private GoodInfoPresenterImp goodInfoPresenterImp;

    private CategoryWrapperPresenterImp categoryWrapperPresenterImp;

    private ProgressDialog progressDialog = null;

    private CustomPopWindow customPopWindow;

    private int pageSize = 12;

    private int currentPage = 1;

    private Condition condition;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_good_list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews() {
        condition = new Condition();

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth() - SizeUtils.dp2px(30), SizeUtils.dp2px(30));
        params.setMargins(SizeUtils.dp2px(10), BarUtils.getStatusBarHeight(), 0, 0);
        mSearchLayout.setLayoutParams(params);

        goodInfoPresenterImp = new GoodInfoPresenterImp(this, this);
        categoryWrapperPresenterImp = new CategoryWrapperPresenterImp(this, this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在查询");

        goodInfoAdapter = new GoodInfoAdapter(this, null);
        mGoodListView.setLayoutManager(new GridLayoutManager(this, 2));

        mGoodListView.addItemDecoration(new GridSpacingItemDecoration(2, SizeUtils.dp2px(5), true));
        mGoodListView.setHasFixedSize(true);
        mGoodListView.setAdapter(goodInfoAdapter);

        avi.show();

        condition.setPage(currentPage+"");

        goodInfoPresenterImp.getGoodInfoByParams(condition);

        goodInfoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                currentPage++;
                condition.setPage(currentPage+"");
                goodInfoPresenterImp.getGoodInfoByParams(condition);
            }
        }, mGoodListView);

        //查询城市列表
        categoryWrapperPresenterImp.getCategoryWrapperList(2, "-2", "2");
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(Object tData) {
        avi.hide();
        if (tData instanceof GoodInfoRet) {
            try {
                if (currentPage == 1) {
                    goodInfoAdapter.setNewData(((GoodInfoRet) tData).getData().getGoodsList());
                } else {
                    goodInfoAdapter.addData(((GoodInfoRet) tData).getData().getGoodsList());
                    if (((GoodInfoRet) tData).getData().getGoodsList().size() == pageSize) {
                        goodInfoAdapter.loadMoreComplete();
                    } else {
                        goodInfoAdapter.loadMoreEnd();
                    }
                }
            } catch (NullPointerException e) {
                goodInfoAdapter.loadMoreEnd();
                LogUtils.e("loadDataSuccess error --->" + e.getMessage());
            }
        }

        if (tData instanceof CategoryWrapperRet) {
            mTypeContentListView.setNestedScrollingEnabled(false);
            mTypeContentListView.setLayoutManager(new LinearLayoutManager(this));
            mTypeContentListView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.line_color)));

            GroupAdapter gAdapter = new GroupAdapter();
            mTypeContentListView.setAdapter(gAdapter);
            gAdapter.setListItems(((CategoryWrapperRet) tData).getData().getAllCountry());
        }

    }

    @Override
    public void loadDataError(Throwable throwable) {
        avi.hide();
    }

    @OnClick(R.id.layout_country)
    void showCountry() {
        drawerLayout.openDrawer(Gravity.RIGHT);
    }

    @OnClick(R.id.layout_brand)
    void showPopListView() {

        View contentView = LayoutInflater.from(this).inflate(R.layout.search_brand_top, null);
        //处理popWindow 显示内容
        handleListView(contentView);
        //创建并显示popWindow
        customPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                .setView(contentView)
                .size(ViewGroup.LayoutParams.MATCH_PARENT, SizeUtils.dp2px(248))//显示大小
                .create()
                .showAsDropDown(mConditionLayout, 0, 0);
        popBgLayout.setVisibility(View.VISIBLE);

        customPopWindow.getPopupWindow().setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                popBgLayout.setVisibility(View.GONE);
            }
        });

    }

    private void handleListView(View contentView) {

        List<BrandInfo> brandInfos = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            BrandInfo brandInfo = new BrandInfo();
            brandInfo.setBrandName("熊猫牌" + (i + 1));
            brandInfos.add(brandInfo);
        }

        RecyclerView brandListView = contentView.findViewById(R.id.search_brand_list);
        SearchBrandAdapter searchBrandAdapter = new SearchBrandAdapter(this, brandInfos);
        brandListView.setLayoutManager(new GridLayoutManager(this, 2));
        brandListView.setAdapter(searchBrandAdapter);
    }

    private static class GroupAdapter extends RecyclerView.Adapter<GroupViewHolder> {

        static final int VIEW_TYPE_NON_STICKY = R.layout.item_menu_main;
        static final int VIEW_TYPE_STICKY = R.layout.item_menu_sticky;

        private List<ListItem> mListItems = new ArrayList<>();

        @Override
        public GroupViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(viewType, parent, false);
            return new GroupViewHolder(view);
        }

        @Override
        public void onBindViewHolder(GroupViewHolder holder, int position) {
            holder.bind(mListItems.get(position));
        }

        @Override
        public int getItemViewType(int position) {
            if (mListItems.get(position) instanceof StickyListItem) {
                return VIEW_TYPE_STICKY;
            }
            return VIEW_TYPE_NON_STICKY;
        }

        @Override
        public int getItemCount() {
            return mListItems.size();
        }

        void setListItems(List<CountryWrapper> wrappers) {
            mListItems.clear();

            for (int i = 0; i < wrappers.size(); i++) {
                CountryWrapper countryWrapper = wrappers.get(i);
                for (int j = 0; j < countryWrapper.getList().size(); j++) {
                    mListItems.add(new ListItem(countryWrapper.getList().get(j).getCountryName()));
                }
            }

            //在特定位置增加分组索引
            StickyListItem firstSticky = new StickyListItem(wrappers.get(0).getName());
            mListItems.add(0, firstSticky);

            int tempIndex = 0;
            for (int m = 1; m < wrappers.size(); m++) {
                CountryWrapper countryWrapper = wrappers.get(m);
                tempIndex += wrappers.get(m - 1).getList().size() + 1;
                Logger.i("temp index--->" + tempIndex);
                StickyListItem stickyListItem = new StickyListItem(countryWrapper.getName());
                mListItems.add(tempIndex, stickyListItem);
            }

            notifyDataSetChanged();
        }
    }

    private static class GroupViewHolder extends RecyclerView.ViewHolder {

        private TextView text;

        GroupViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.tv_title);
        }

        void bind(ListItem item) {
            text.setText(item.cname);
        }
    }

    private static class ListItem {

        private String cname;

        ListItem(String name) {
            this.cname = name;
        }
    }

    private static class StickyListItem extends ListItem {

        StickyListItem(String text) {
            super(text);
        }
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
