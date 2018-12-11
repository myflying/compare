package com.yc.compare.ui;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yc.compare.R;
import com.yc.compare.ui.base.BaseFragmentActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by myflying on 2018/12/3.
 */
public class NewsDetailActivity extends BaseFragmentActivity {

    @BindView(R.id.layout_user_comment)
    LinearLayout mUserCommentLayout;

    BottomSheetDialog bottomSheetDialog;

    @Override
    protected int getContextViewId() {
        return R.layout.activity_news_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    public void initViews() {
        bottomSheetDialog = new BottomSheetDialog(this);
        View userCommentView = LayoutInflater.from(NewsDetailActivity.this).inflate(R.layout.user_comment_list_view, null);
        bottomSheetDialog.setContentView(userCommentView);
    }

    @OnClick(R.id.layout_user_comment)
    void commentDialog() {
        bottomSheetDialog.show();
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
