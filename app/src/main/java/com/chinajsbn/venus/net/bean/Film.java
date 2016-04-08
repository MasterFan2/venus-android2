package com.chinajsbn.venus.net.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by 13510 on 2015/11/26.
 */
@Table(name = "Film")
public class Film {

    private int id;

    @Column(column = "coverImage")
    private CoverImage coverImage;

    @Column(column = "createDate")
    private String createDate;

    @Column(column = "hits")
    private int hits;

    @Column(column = "isUsed")
    private int isUsed;

    @Column(column = "name")
    private String name;

    @Column(column = "remark")
    private String remark;

    @Column(column = "seasonId")
    private int seasonId;

    @Column(column = "imgUrl")
    private String imgUrl;

    @Column(column = "coverUrlApp")
    private String coverUrlApp;

    @Id
    @Column(column = "videoId")
    private int videoId;

    @Column(column = "tag")
    private String tag;

    public CoverImage getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(CoverImage coverImage) {
        this.coverImage = coverImage;
    }

    public String getCoverUrlApp() {
        return coverUrlApp;
    }

    public void setCoverUrlApp(String coverUrlApp) {
        this.coverUrlApp = coverUrlApp;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public Film() {

    }

    public Film(CoverImage coverImage, String coverUrlApp, String createDate, int hits, int id, String imgUrl, int isUsed, String name, String remark, int seasonId, String tag, int videoId) {

        this.coverImage = coverImage;
        this.coverUrlApp = coverUrlApp;
        this.createDate = createDate;
        this.hits = hits;
        this.id = id;
        this.imgUrl = imgUrl;
        this.isUsed = isUsed;
        this.name = name;
        this.remark = remark;
        this.seasonId = seasonId;
        this.tag = tag;
        this.videoId = videoId;
    }
}
