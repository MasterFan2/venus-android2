package com.chinajsbn.venus.net.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by 13510 on 2015/12/14.
 */
@Table(name = "Car")
public class Car implements Serializable {

    @Id
    @Column(column = "weddingCarRentalId")
    private int id;

    @Column(column = "isUsed")
    private int isUsed;

    @Column(column = "weight")
    private int weight;

    @Column(column = "content")
    private String content;

    @Column(column = "coverUrlApp")
    private String coverUrlApp;

    @Column(column = "appDetailImages")
    private String appDetailImages;

    @Column(column = "marketRentalPrice")
    private int marketRentalPrice;

    @Column(column = "levelId")
    private int levelId;

    @Column(column = "title")
    private String title;

    @Column(column = "rentalPrice")
    private int rentalPrice;

    @Column(column = "parameter")
    private String parameter;

    @Column(column = "description")
    private String description;

    @Column(column = "modelsId")
    private int modelsId;

    @Column(column = "carNature")
    private String carNature;

    @Column(column = "brandId")
    private int brandId;

    public String getAppDetailImages() {
        return appDetailImages;
    }

    public void setAppDetailImages(String appDetailImages) {
        this.appDetailImages = appDetailImages;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getCarNature() {
        return carNature;
    }

    public void setCarNature(String carNature) {
        this.carNature = carNature;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public int getMarketRentalPrice() {
        return marketRentalPrice;
    }

    public void setMarketRentalPrice(int marketRentalPrice) {
        this.marketRentalPrice = marketRentalPrice;
    }

    public int getModelsId() {
        return modelsId;
    }

    public void setModelsId(int modelsId) {
        this.modelsId = modelsId;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public int getRentalPrice() {
        return rentalPrice;
    }

    public void setRentalPrice(int rentalPrice) {
        this.rentalPrice = rentalPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Car(){}
}
