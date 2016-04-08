package com.chinajsbn.venus.net.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Finder;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.util.ArrayList;

/**
 * 酒店列表
 * Created by 13510 on 2015/8/27.
 */
@Table(name = "Hotel")
public class Hotel {

    @Column(column = "id")
    private int id;

    private String appDetailImages;

    @Column(column = "address")
    private String address;

    @Column(column = "banquetHalNum")
    private String banquetHalNum;

    private ArrayList<Banquet> banquetHall;

    @Column(column = "setMealDetail")
    private String setMealDetail;//菜品

    @Column(column = "lableDetail")
    private String lableDetail;//活动

    @Column(column = "capacityPerTable")
    private int capacityPerTable;

    @Column(column = "featureLable")
    private String featureLable;

    @Column(column = "highestConsumption")
    private int highestConsumption;

    @Column(column = "introduction")
    private String introduction;

    @Id
    @Column(column = "hotelId")
    private String hotelId;

    @Column(column = "hotelName")
    private String name;

    @Column(column = "imageUrl")
    private String coverUrlApp;

    @Column(column = "isDiscount")
    private String isDiscount;

    @Column(column = "isGift")
    private String isGift;

    @Column(column = "listCount")
    private String listCount;

    @Column(column = "lowestConsumption")
    private String lowestConsumption;

    @Column(column = "typeName")
    private String typeName;

    @Column(column = "cityId")
    private int cityId;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAppDetailImages() {
        return appDetailImages;
    }

    public void setAppDetailImages(String appDetailImages) {
        this.appDetailImages = appDetailImages;
    }

    public ArrayList<Banquet> getBanquetHall() {
        return banquetHall;
    }

    public void setBanquetHall(ArrayList<Banquet> banquetHall) {
        this.banquetHall = banquetHall;
    }

    public String getBanquetHalNum() {
        return banquetHalNum;
    }

    public void setBanquetHalNum(String banquetHalNum) {
        this.banquetHalNum = banquetHalNum;
    }

    public int getCapacityPerTable() {
        return capacityPerTable;
    }

    public void setCapacityPerTable(int capacityPerTable) {
        this.capacityPerTable = capacityPerTable;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCoverUrlApp() {
        return coverUrlApp;
    }

    public void setCoverUrlApp(String coverUrlApp) {
        this.coverUrlApp = coverUrlApp;
    }

    public String getFeatureLable() {
        return featureLable;
    }

    public void setFeatureLable(String featureLable) {
        this.featureLable = featureLable;
    }

    public int getHighestConsumption() {
        return highestConsumption;
    }

    public void setHighestConsumption(int highestConsumption) {
        this.highestConsumption = highestConsumption;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(String isDiscount) {
        this.isDiscount = isDiscount;
    }

    public String getIsGift() {
        return isGift;
    }

    public void setIsGift(String isGift) {
        this.isGift = isGift;
    }

    public String getLableDetail() {
        return lableDetail;
    }

    public void setLableDetail(String lableDetail) {
        this.lableDetail = lableDetail;
    }

    public String getListCount() {
        return listCount;
    }

    public void setListCount(String listCount) {
        this.listCount = listCount;
    }

    public String getLowestConsumption() {
        return lowestConsumption;
    }

    public void setLowestConsumption(String lowestConsumption) {
        this.lowestConsumption = lowestConsumption;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSetMealDetail() {
        return setMealDetail;
    }

    public void setSetMealDetail(String setMealDetail) {
        this.setMealDetail = setMealDetail;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Hotel() {

    }

    public Hotel(String address, String appDetailImages, ArrayList<Banquet> banquetHall, String banquetHalNum, int capacityPerTable, int cityId, String coverUrlApp, String featureLable, int highestConsumption, String hotelId, int id, String introduction, String isDiscount, String isGift, String lableDetail, String listCount, String lowestConsumption, String name, String setMealDetail, String typeName) {

        this.address = address;
        this.appDetailImages = appDetailImages;
        this.banquetHall = banquetHall;
        this.banquetHalNum = banquetHalNum;
        this.capacityPerTable = capacityPerTable;
        this.cityId = cityId;
        this.coverUrlApp = coverUrlApp;
        this.featureLable = featureLable;
        this.highestConsumption = highestConsumption;
        this.hotelId = hotelId;
        this.id = id;
        this.introduction = introduction;
        this.isDiscount = isDiscount;
        this.isGift = isGift;
        this.lableDetail = lableDetail;
        this.listCount = listCount;
        this.lowestConsumption = lowestConsumption;
        this.name = name;
        this.setMealDetail = setMealDetail;
        this.typeName = typeName;
    }
}
