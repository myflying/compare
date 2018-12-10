package com.yc.compare.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.yc.compare.R;
import com.yc.compare.ui.AboutActivity;
import com.yc.compare.ui.SuggestActivity;
import com.yc.compare.ui.UserInfoActivity;
import com.yc.compare.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by iflying on 2017/12/14.
 */

public class MyFragment extends BaseFragment {

    @BindView(R.id.layout_tucao)
    RelativeLayout mTucaoLayout;

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this, root);
        QMUIStatusBarHelper.translucent(getActivity());
        return root;
    }

    @OnClick(R.id.layout_tucao)
    void suggest() {
        Intent intent = new Intent(getActivity(),SuggestActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_update_info)
    void updateUserInfo() {
        Intent intent = new Intent(getActivity(),UserInfoActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.layout_about)
    void about() {
        Intent intent = new Intent(getActivity(),AboutActivity.class);
        startActivity(intent);
    }

}
