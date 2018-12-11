package com.yc.compare.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.BrandInfo;
import com.yc.compare.bean.NewsInfo;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class NewsInfoAdapter extends BaseQuickAdapter<NewsInfo, BaseViewHolder> {

    private Context mContext;

    public NewsInfoAdapter(Context context, List<NewsInfo> datas) {
        super(R.layout.fragment_news_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final NewsInfo item) {

    }
}