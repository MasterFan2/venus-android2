package com.chinajsbn.venus.net.bean;

import java.io.Serializable;

/**
 * 婚纱
 * Created by 13510 on 2015/11/30.
 */
public class Dress implements Serializable {
    private int id;
    private String description;
    private String imageUrl;
    private String name;
    private String weddingDressNo;

    public Dress() {
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeddingDressNo() {
        return weddingDressNo;
    }

    public void setWeddingDressNo(String weddingDressNo) {
        this.weddingDressNo = weddingDressNo;
    }
}
