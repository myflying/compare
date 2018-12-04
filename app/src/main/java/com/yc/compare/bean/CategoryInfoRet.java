package com.yc.compare.bean;

import java.util.List;

/**
 * Created by myflying on 2018/12/3.
 */
public class CategoryInfoRet extends ResultInfo {
    private List<CategoryInfo> data;

    public List<CategoryInfo> getData() {
        return data;
    }

    public void setData(List<CategoryInfo> data) {
        this.data = data;
    }
}
