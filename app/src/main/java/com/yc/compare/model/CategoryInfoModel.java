package com.yc.compare.model;

import com.yc.compare.base.IBaseRequestCallBack;

/**
 * Created by iflying on 2018/1/9.
 */

public interface CategoryInfoModel<T> {
    void getCategoryInfoList(int level, IBaseRequestCallBack<T> iBaseRequestCallBack);
}
