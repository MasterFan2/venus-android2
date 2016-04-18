package com.chinajsbn.venus.net.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13510 on 2015/10/16.
 * 四大金刚数据
 */
@Table(name = "F4")
public class F4 {

    private int id;

    @Column(column = "description")
    private String description;

    @Column(column = "personGender")
    private String personGender;

    @Column(column = "personId")
    private int personId;

    @Column(column = "personName")
    private String personName;

    @Column(column = "nickName")
    private String nickName;

    @Column(column = "photoUrl")
    private String photoUrl;

    private List<Works> workList;

    private int points;

    @Column(column = "serviceInfo")
    private String serviceInfo;

    @Column(column = "skillPrice")
    private int skillPrice;

    @Column(column = "tag")
    private String tag ;// for cache

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPersonGender() {
        return personGender;
    }

    public void setPersonGender(String personGender) {
        this.personGender = personGender;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(String serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public int getSkillPrice() {
        return skillPrice;
    }

    public void setSkillPrice(int skillPrice) {
        this.skillPrice = skillPrice;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<Works> getWorkList() {
        return workList;
    }

    public void setWorkList(List<Works> workList) {
        this.workList = workList;
    }

    public F4() {}
}
