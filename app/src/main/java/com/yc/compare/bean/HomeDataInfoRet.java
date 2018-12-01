package com.yc.compare.bean;

import java.util.List;

/**
 * Created by admin on 2018/1/8.
 */

public class HomeDataInfoRet {

    private String message;
    private int errCode;
    private boolean status;
    private List<HomeDataInfo> data;
    private int totalPage;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<HomeDataInfo> getData() {
        return data;
    }

    public void setData(List<HomeDataInfo> data) {
        this.data = data;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
