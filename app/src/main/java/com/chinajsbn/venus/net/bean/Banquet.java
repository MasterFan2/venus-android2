package com.chinajsbn.venus.net.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * 宴会厅
 * Created by 13510 on 2015/8/27.
 */
@Table(name = "Banquet")
public class Banquet implements Serializable {

    private int id;

    @Column(column = "area")
    private String area;

    private String appDetailImages;

    private String coverUrlApp;

    @Column(column = "lowestConsumption")
    private String lowestConsumption;

    @Column(column = "shape")
    private String shape;

    @Id
    @Column(column = "banquetHallId")
    private String banquetHallId;

    @Column(column = "name")
    private String name;

    @Column(column = "capacity")
    private String capacity;

    @Column(column = "height")
    private String height;

    @Column(column = "hotelId")
    private String hotelId;

    @Column(column = "pillarNumber")
    private String pillerNum;//是否有柱子

    private int maxTableNum;

    public Banquet() {
    }

    public String getAppDetailImages() {

        return appDetailImages;
    }

    public void setAppDetailImages(String appDetailImages) {
        this.appDetailImages = appDetailImages;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBanquetHallId() {
        return banquetHallId;
    }

    public void setBanquetHallId(String banquetHallId) {
        this.banquetHallId = banquetHallId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getCoverUrlApp() {
        return coverUrlApp;
    }

    public void setCoverUrlApp(String coverUrlApp) {
        this.coverUrlApp = coverUrlApp;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
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

    public String getLowestConsumption() {
        return lowestConsumption;
    }

    public void setLowestConsumption(String lowestConsumption) {
        this.lowestConsumption = lowestConsumption;
    }

    public int getMaxTableNum() {
        return maxTableNum;
    }

    public void setMaxTableNum(int maxTableNum) {
        this.maxTableNum = maxTableNum;
    }

    public String getPillerNum() {
        return pillerNum;
    }

    public void setPillerNum(String pillerNum) {
        this.pillerNum = pillerNum;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public Banquet(String appDetailImages, String area, String banquetHallId, String name, String capacity, String coverUrlApp, String height, String hotelId, int id, String lowestConsumption, int maxTableNum, String pillerNum, String shape) {

        this.appDetailImages = appDetailImages;
        this.area = area;
        this.banquetHallId = banquetHallId;
        this.name = name;
        this.capacity = capacity;
        this.coverUrlApp = coverUrlApp;
        this.height = height;
        this.hotelId = hotelId;
        this.id = id;
        this.lowestConsumption = lowestConsumption;
        this.maxTableNum = maxTableNum;
        this.pillerNum = pillerNum;
        this.shape = shape;
    }
}
