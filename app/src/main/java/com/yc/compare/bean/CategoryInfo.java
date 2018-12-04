package com.yc.compare.bean;

/**
 * Created by myflying on 2018/12/3.
 * 分类信息实体类
 */
public class CategoryInfo {
    private String id;
    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类类型
     */
    private String type;

    /**
     * 分类等级
     */
    private String level;

    /**
     * 分类图标
     */
    private String typeImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getTypeImage() {
        return typeImage;
    }

    public void setTypeImage(String typeImage) {
        this.typeImage = typeImage;
    }
}
