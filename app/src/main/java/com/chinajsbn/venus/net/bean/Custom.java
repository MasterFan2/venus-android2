package com.chinajsbn.venus.net.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Foreign;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Unique;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 样片 /客片
 *
 * Created by MasterFan on 2015/6/26.
 * description:
 */
@Table(name = "Custom")
public class Custom implements Serializable {

    private int id;

    @Column(column = "headPhotographyUrl")
    private String headPhotographyUrl;

    @Column(column = "headPhotographyName")
    private String headPhotographyName;

    @Column(column = "headStylistUrl")
    private String headStylistUrl;

    @Column(column = "headStylistName")
    private String headStylistName;

    @Column(column = "headStyles")
    private String headStyles;

    @Column(column = "actorFemaleName")
    private String actorFemaleName;

    @Column(column = "actorMaleName")
    private String actorMaleName;

    @Column(column = "contentId")
    private String contentId;

    @Column(column = "name")
    private String name;

    @Column(column = "coverUrlApp")
    private String coverUrlApp;

    @Column(column = "createDate")
    private String createDate;

    @Column(column = "shootingStyleId")
    private String shootingStyleId;

    @Column(column = "shootingStyleName")
    private String shootingStyleName;

    private String appDetailImages;//详情列表

    @Foreign(column = "photographerId", foreign = "personId")
    private SimplePhotographer photographer;

    @Column(column = "seasonId")
    private String seasonId;

    public String getActorFemaleName() {
        return actorFemaleName;
    }

    public void setActorFemaleName(String actorFemaleName) {
        this.actorFemaleName = actorFemaleName;
    }

    public String getActorMaleName() {
        return actorMaleName;
    }

    public void setActorMaleName(String actorMaleName) {
        this.actorMaleName = actorMaleName;
    }

    public String getAppDetailImages() {
        return appDetailImages;
    }

    public void setAppDetailImages(String appDetailImages) {
        this.appDetailImages = appDetailImages;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
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

    public String getHeadPhotographyName() {
        return headPhotographyName;
    }

    public void setHeadPhotographyName(String headPhotographyName) {
        this.headPhotographyName = headPhotographyName;
    }

    public String getHeadPhotographyUrl() {
        return headPhotographyUrl;
    }

    public void setHeadPhotographyUrl(String headPhotographyUrl) {
        this.headPhotographyUrl = headPhotographyUrl;
    }

    public String getHeadStyles() {
        return headStyles;
    }

    public void setHeadStyles(String headStyles) {
        this.headStyles = headStyles;
    }

    public String getHeadStylistName() {
        return headStylistName;
    }

    public void setHeadStylistName(String headStylistName) {
        this.headStylistName = headStylistName;
    }

    public String getHeadStylistUrl() {
        return headStylistUrl;
    }

    public void setHeadStylistUrl(String headStylistUrl) {
        this.headStylistUrl = headStylistUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SimplePhotographer getPhotographer() {
        return photographer;
    }

    public void setPhotographer(SimplePhotographer photographer) {
        this.photographer = photographer;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(String seasonId) {
        this.seasonId = seasonId;
    }

    public String getShootingStyleId() {
        return shootingStyleId;
    }

    public void setShootingStyleId(String shootingStyleId) {
        this.shootingStyleId = shootingStyleId;
    }

    public String getShootingStyleName() {
        return shootingStyleName;
    }

    public void setShootingStyleName(String shootingStyleName) {
        this.shootingStyleName = shootingStyleName;
    }

    public Custom() {

    }

    public Custom(String actorFemaleName, String actorMaleName, String appDetailImages, String contentId, String coverUrlApp, String createDate, String headPhotographyName, String headPhotographyUrl, String headStyles, String headStylistName, String headStylistUrl, int id, String name, SimplePhotographer photographer, String seasonId, String shootingStyleId, String shootingStyleName) {

        this.actorFemaleName = actorFemaleName;
        this.actorMaleName = actorMaleName;
        this.appDetailImages = appDetailImages;
        this.contentId = contentId;
        this.coverUrlApp = coverUrlApp;
        this.createDate = createDate;
        this.headPhotographyName = headPhotographyName;
        this.headPhotographyUrl = headPhotographyUrl;
        this.headStyles = headStyles;
        this.headStylistName = headStylistName;
        this.headStylistUrl = headStylistUrl;
        this.id = id;
        this.name = name;
        this.photographer = photographer;
        this.seasonId = seasonId;
        this.shootingStyleId = shootingStyleId;
        this.shootingStyleName = shootingStyleName;
    }
}

