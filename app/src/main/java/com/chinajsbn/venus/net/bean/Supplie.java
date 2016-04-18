package com.chinajsbn.venus.net.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by 13510 on 2015/12/15.
 */
@Table(name = "Supplie")
public class Supplie implements Serializable{

    @Column(column = "content")
    private String content;

    @Column(column = "coverUrlApp")
    private String coverUrlApp;

    @Column(column = "parameter")
    private String parameter;

    @Column(column = "appDetailImages")
    private String appDetailImages;

    @Column(column = "sellingPrice")
    private float sellingPrice;

    @Column(column = "marketPrice")
    private float marketPrice;

    @Column(column = "title")
    private String title;

    @Column(column = "weight")
    private int weight;

    @Column(column = "isUsed")
    private int isUsed;

    @Column(column = "coverUrl")
    private String coverUrl;

    @Column(column = "suppliesNumber")
    private String suppliesNumber;

    @Column(column = "description")
    private String description;

    @Column(column = "number")
    private String number;

    @Column(column = "weddingSuppliesTypeId")
    private int weddingSuppliesTypeId;

    @Column(column = "brandId")
    private int brandId;

    @Id
    @Column(column = "weddingSuppliesId")
    private int weddingSuppliesId;

    public String getAppDetailImages() {
        return appDetailImages;
    }

    public void setAppDetailImages(String appDetailImages) {
        this.appDetailImages = appDetailImages;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoverUrlApp() {
        return coverUrlApp;
    }

    public void setCoverUrlApp(String coverUrlApp) {
        this.coverUrlApp = coverUrlApp;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public float getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(float sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getSuppliesNumber() {
        return suppliesNumber;
    }

    public void setSuppliesNumber(String suppliesNumber) {
        this.suppliesNumber = suppliesNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWeddingSuppliesId() {
        return weddingSuppliesId;
    }

    public void setWeddingSuppliesId(int weddingSuppliesId) {
        this.weddingSuppliesId = weddingSuppliesId;
    }

    public int getWeddingSuppliesTypeId() {
        return weddingSuppliesTypeId;
    }

    public void setWeddingSuppliesTypeId(int weddingSuppliesTypeId) {
        this.weddingSuppliesTypeId = weddingSuppliesTypeId;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Supplie(){}
}
