package com.chinajsbn.venus.net.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by 13510 on 2015/9/6.
 */
@Table(name = "Scheme")
public class Scheme implements Serializable {

    @Column(column = "schemeName")
    private String schemeName;

    @Column(column = "name")
    private String name;

    private String caseStyleName;

    @Column(column = "coverUrlApp")
    private String coverUrlApp;

    @Column(column = "description")
    private String description;

    @Column(column = "senceCost")
    private int senceCost;

    @Column(column = "totalCost")
    private int totalCost;

    @Column(column = "hdpcCost")
    private int hdpcCost;

    @Column(column = "color")
    private String color;

    @Column(column = "appDetailImages")
    private String appDetailImages;

    @Column(column = "styleName")
    private String styleName;

    @Column(column = "totalPrice")
    private int totalPrice;

    @Column(column = "theme")
    private String theme;

    @Column(column = "hotelName")
    private String hotelName;

    @Column(column = "banquetHallName")
    private String banquetHallName;

    @Id
    @Column(column = "caseId")
    private int caseId;

    @Column(column = "weddingCaseImage")
    private String weddingCaseImage;

    @Column(column = "weddingDate")
    private String weddingDate;

    @Column(column = "weight")
    private int weight;

    @Column(column = "tag")
    private String tag;

    @Column(column = "personDescription")
    private String personDescription;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getBanquetHallName() {
        return banquetHallName;
    }

    public void setBanquetHallName(String banquetHallName) {
        this.banquetHallName = banquetHallName;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getPersonDescription() {
        return personDescription;
    }

    public void setPersonDescription(String personDescription) {
        this.personDescription = personDescription;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public int getHdpcCost() {
        return hdpcCost;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setHdpcCost(int hdpcCost) {
        this.hdpcCost = hdpcCost;
    }

    public int getSenceCost() {
        return senceCost;
    }

    public void setSenceCost(int senceCost) {
        this.senceCost = senceCost;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getWeddingCaseImage() {
        return weddingCaseImage;
    }

    public void setWeddingCaseImage(String weddingCaseImage) {
        this.weddingCaseImage = weddingCaseImage;
    }

    public String getWeddingDate() {
        return weddingDate;
    }

    public void setWeddingDate(String weddingDate) {
        this.weddingDate = weddingDate;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Scheme(){}

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCaseId() {
        return caseId;
    }

    public void setCaseId(int caseId) {
        this.caseId = caseId;
    }

    public String getCaseStyleName() {
        return caseStyleName;
    }

    public void setCaseStyleName(String caseStyleName) {
        this.caseStyleName = caseStyleName;
    }
}
