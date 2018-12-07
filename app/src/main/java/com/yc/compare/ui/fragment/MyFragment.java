package com.yc.compare.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.yc.compare.R;
import com.yc.compare.ui.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by iflying on 2017/12/14.
 */

public class MyFragment extends BaseFragment {

    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_my, null);
        ButterKnife.bind(this, root);
        QMUIStatusBarHelper.translucent(getActivity());
        return root;
    }

}
