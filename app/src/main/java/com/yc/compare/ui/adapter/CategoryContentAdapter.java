package com.yc.compare.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yc.compare.R;
import com.yc.compare.bean.CategoryInfo;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class CategoryContentAdapter extends BaseQuickAdapter<CategoryInfo, BaseViewHolder> {

    private Context mContext;

    public CategoryContentAdapter(Context context, List<CategoryInfo> datas) {
        super(R.layout.category_content_item, datas);
        this.mContext = context;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CategoryInfo item) {
        helper.setText(R.id.tv_category_name, item.getName());
        Glide.with(mContext).load(item.getTypeImage()).into((ImageView) helper.itemView.findViewById(R.id.iv_category));
    }
}