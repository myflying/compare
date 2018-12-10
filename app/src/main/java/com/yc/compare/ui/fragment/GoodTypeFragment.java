package com.yc.compare.ui.fragment;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;
import com.yanzhenjie.recyclerview.swipe.widget.StickyNestedScrollView;
import com.yc.compare.R;
import com.yc.compare.bean.CategoryInfo;
import com.yc.compare.bean.CategoryWrapperRet;
import com.yc.compare.bean.CountryWrapper;
import com.yc.compare.presenter.CategoryWrapperPresenterImp;
import com.yc.compare.ui.GoodListActivity;
import com.yc.compare.ui.adapter.CategoryContentAdapter;
import com.yc.compare.ui.adapter.CategoryLeftAdapter;
import com.yc.compare.ui.base.BaseFragment;
import com.yc.compare.view.CategoryWrapperView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by iflying on 2017/12/14.
 */

public class GoodTypeFragment extends BaseFragment implements CategoryWrapperView{

    @BindView(R.id.scroll_view)
    StickyNestedScrollView scrollView;

    @BindView(R.id.left_type_list)
    RecyclerView mLeftTypeListView;

    @BindView(R.id.type_content_list)
    SwipeMenuRecyclerView mTypeContentListView;

    @BindView(R.id.type_collect_list_view)
    RecyclerView mCommonTypeListView;

    @BindView(R.id.search_layout)
    LinearLayout mSearchLayout;

    CategoryLeftAdapter categoryLeftAdapter;

    CategoryContentAdapter categoryContentAdapter;

    private CategoryWrapperPresenterImp categoryWrapperPresenterImp;

    private int lastPosition = 0;

    private int level = 1;

    private int categoryType = 1;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_good_type, null);
        ButterKnife.bind(this, root);
        initViews();
        return root;
    }

//    @Override
//    protected boolean translucentFull() {
//        return true;
//    }

    public void initViews() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ScreenUtils.getScreenWidth() - SizeUtils.dp2px(30), SizeUtils.dp2px(30));
        params.setMargins(SizeUtils.dp2px(15), BarUtils.getStatusBarHeight(), 0, 0);
        mSearchLayout.setLayoutParams(params);

        categoryWrapperPresenterImp = new CategoryWrapperPresenterImp(this, getActivity());
        categoryLeftAdapter = new CategoryLeftAdapter(getActivity(), null);
        categoryLeftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (lastPosition == 0 || lastPosition != position) {
                    categoryLeftAdapter.getData().get(lastPosition).setSelected(false);
                }
                categoryLeftAdapter.getData().get(position).setSelected(true);
                categoryLeftAdapter.notifyDataSetChanged();
                lastPosition = position;
                getNextCategoryList(position);
            }
        });
        mLeftTypeListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLeftTypeListView.setAdapter(categoryLeftAdapter);

        categoryContentAdapter = new CategoryContentAdapter(getActivity(), null);
        mCommonTypeListView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mCommonTypeListView.setAdapter(categoryContentAdapter);

        categoryContentAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), GoodListActivity.class);
                startActivity(intent);
            }
        });

        categoryWrapperPresenterImp.getCategoryInfoList(level, "");
    }

    public void getNextCategoryList(int pos) {
        CategoryInfo firstCategory = categoryLeftAdapter.getData().get(pos);
        firstCategory.setSelected(true);
        level = 2;
        categoryType = firstCategory.getType();
        categoryWrapperPresenterImp.getCategoryWrapperList(level, firstCategory.getId(), firstCategory.getType() + "");
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(CategoryWrapperRet tData) {
        if (level == 1 && tData != null) {
            Logger.i(JSONObject.toJSONString(tData));

            if (tData.getData() != null && tData.getData().getList() != null && tData.getData().getList().size() > 0) {
                categoryLeftAdapter.setNewData(tData.getData().getList());

                //默认查询第一个类型下的子类型合集
                getNextCategoryList(0);
            }
        }
        if (level == 2 && tData != null) {
            Logger.i(JSONObject.toJSONString(tData));

            if (categoryType == 2) {
                scrollView.setVisibility(View.VISIBLE);
                mCommonTypeListView.setVisibility(View.GONE);
                mTypeContentListView.setNestedScrollingEnabled(false);
                mTypeContentListView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mTypeContentListView.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(getActivity(), R.color.line_color)));

                GroupAdapter gAdapter = new GroupAdapter();
                mTypeContentListView.setAdapter(gAdapter);
                gAdapter.setListItems(tData.getData().getAllCountry());
            } else {
                scrollView.setVisibility(View.GONE);
                mCommonTypeListView.setVisibility(View.VISIBLE);
                categoryContentAdapter.setNewData(tData.getData().getList());
            }
        }
    }

    @Override
    public void loadDataError(Throwable throwable) {

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

    protected List<String> createDataList() {
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            strings.add("第" + i + "个Item");
        }
        return strings;
    }

}
