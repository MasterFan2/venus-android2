package com.chinajsbn.venus.net.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by 13510 on 2015/11/30.
 * 品牌
 */
@Table(name = "Brands")
public class Brands {
    @Column(column = "brandId")
    private int brandId;

    @Column(column = "description")
    private String description;

    @Column(column = "imageUrl")
    private String imageUrl;

    @Column(column = "coverUrlApp")
    private String coverUrlApp;

    @Column(column = "logoUrl")
    private String logoUrl;

    @Column(column = "name")
    private String name;

    @Column(column = "type")
    private int type;

    @Id
    @Column(column = "weddingDressBrandId")
    private int weddingDressBrandId;

    @Column(column = "weddingDressBrandName")
    private String weddingDressBrandName;

    @Column(column = "weight")
    private int weight;

    @Column(column = "tab")
    private int tab;//1              2.              3.

    public int getTab() {
        return tab;
    }

    public void setTab(int tab) {
        this.tab = tab;
    }

    public Brands() {}

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getDescription() {
        return description;
    }

    public String getCoverUrlApp() {
        return coverUrlApp;
    }

    public void setCoverUrlApp(String coverUrlApp) {
        this.coverUrlApp = coverUrlApp;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getWeddingDressBrandId() {
        return weddingDressBrandId;
    }

    public void setWeddingDressBrandId(int weddingDressBrandId) {
        this.weddingDressBrandId = weddingDressBrandId;
    }

    public String getWeddingDressBrandName() {
        return weddingDressBrandName;
    }

    public void setWeddingDressBrandName(String weddingDressBrandName) {
        this.weddingDressBrandName = weddingDressBrandName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

}
