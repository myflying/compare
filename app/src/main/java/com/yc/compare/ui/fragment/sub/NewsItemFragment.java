package com.yc.compare.ui.fragment.sub;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.yc.compare.R;
import com.yc.compare.ui.base.BaseFragment;

import butterknife.ButterKnife;

/**
 * Created by iflying on 2018/2/6.
 */

public class NewsItemFragment extends BaseFragment {

    /**
     * onCreateView
     */
    @Override
    protected View onCreateView() {
        View root = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_item_news, null);
        ButterKnife.bind(this, root);
        return root;
    }

    public static NewsItemFragment newInstance(int i) {
        NewsItemFragment fragment = new NewsItemFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category_id", i == 0 ? "" : i + "");
        fragment.setArguments(bundle);
        return fragment;
    }

}
