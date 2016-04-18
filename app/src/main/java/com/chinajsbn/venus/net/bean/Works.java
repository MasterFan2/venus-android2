package com.chinajsbn.venus.net.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by MasterFan on 2016/4/12 16:30.
 * <p/>
 * description:作品
 */
@Table(name = "Works")
public class Works {

    @Column(column = "appDetailImages")
    private String appDetailImages;

    @Column(column = "coverUrlApp")
    private String coverUrlApp;

    private String createTime;
    private String description;
    private int dresserId;
    private int id;
    private int isUsed;

    @Column(column = "coverUrlApp")
    private String name;

    private int operater;
    private int photographerId;
    private int productId;
    private String updateTime;
    private int weight;

    @Column(column = "videoUrl")
    private String videoUrl;

    @Column(column = "tag")
    private String tag;

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getAppDetailImages() {
        return appDetailImages;
    }

    public void setAppDetailImages(String appDetailImages) {
        this.appDetailImages = appDetailImages;
    }

    public String getCoverUrlApp() {
        return coverUrlApp;
    }

    public void setCoverUrlApp(String coverUrlApp) {
        this.coverUrlApp = coverUrlApp;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDresserId() {
        return dresserId;
    }

    public void setDresserId(int dresserId) {
        this.dresserId = dresserId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOperater() {
        return operater;
    }

    public void setOperater(int operater) {
        this.operater = operater;
    }

    public int getPhotographerId() {
        return photographerId;
    }

    public void setPhotographerId(int photographerId) {
        this.photographerId = photographerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Works() { }

    public Works(String appDetailImages, String coverUrlApp, String createTime, String description, int dresserId, int id, int isUsed, String name, int operater, int photographerId, int productId, String updateTime, int weight) {

        this.appDetailImages = appDetailImages;
        this.coverUrlApp = coverUrlApp;
        this.createTime = createTime;
        this.description = description;
        this.dresserId = dresserId;
        this.id = id;
        this.isUsed = isUsed;
        this.name = name;
        this.operater = operater;
        this.photographerId = photographerId;
        this.productId = productId;
        this.updateTime = updateTime;
        this.weight = weight;
    }
}
