package com.yc.compare.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by myflying on 2018/12/3.
 */
public class GoodInfoWrapper {
    private List<BannerInfo> banner;

    private List<NewsInfo> news;

    @SerializedName("p_category")
    private List<CategoryInfo> categoryInfos;

    private List<GoodInfo> goodsList;

    public List<BannerInfo> getBanner() {
        return banner;
    }

    public void setBanner(List<BannerInfo> banner) {
        this.banner = banner;
    }

    public List<NewsInfo> getNews() {
        return news;
    }

    public void setNews(List<NewsInfo> news) {
        this.news = news;
    }

    public List<CategoryInfo> getCategoryInfos() {
        return categoryInfos;
    }

    public void setCategoryInfos(List<CategoryInfo> categoryInfos) {
        this.categoryInfos = categoryInfos;
    }

    public List<GoodInfo> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodInfo> goodsList) {
        this.goodsList = goodsList;
    }
}
