package com.chinajsbn.venus.net.bean;

/**
 * Created by MasterFan on 2016/4/14 17:35.
 * <p/>
 * description:级别
 */
public class CarLevel {
    private String description;
    private int id;
    private int isUsed;
    private int levelId;
    private int name;

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

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public CarLevel() {

    }
}
