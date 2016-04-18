package com.chinajsbn.venus.net.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Finder;
import com.lidroid.xutils.db.annotation.Foreign;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 样片 /客片
 *
 * Created by MasterFan on 2015/6/26.
 * description:
 */
@Table(name = "Simple")
public class Simple implements Serializable {

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

    @Column(column = "actorNameFemale")
    private String actorNameFemale;

    @Column(column = "actorNameMale")
    private String actorNameMale;

    @Column(column = "contentId")
    private String contentId;

    @Id
    @Column(column = "contentName")
    private String name;

    @Column(column = "coverUrlApp")
    private String coverUrlApp;

    @Column(column = "createDate")
    private String createDate;

    @Foreign(column = "photographerId", foreign = "personId")
    private SimplePhotographer photographer;

    @Column(column = "appDetailImages")
    private String appDetailImages;

    ////////////////cache/////////////////////
    @Column(column = "styleId")
    private String styleId;

    @Column(column = "styleName")
    private String styleName;

    @Column(column = "addressId")
    private String addressId;

    @Column(column = "addressName")
    private String addressName;

    private List<ShootingExteriors> shootingExteriors;

    public List<ShootingExteriors> getShootingExteriors() {
        return shootingExteriors;
    }

    public void setShootingExteriors(List<ShootingExteriors> shootingExteriors) {
        this.shootingExteriors = shootingExteriors;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    //    @Finder(valueColumn = "shootingStylesId", targetColumn = "shootingStyleId")
    private ArrayList<SimpleStyles> shootingStyles;

    @Foreign(column = "stylistId", foreign = "personId")
    private SimplePhotographer stylist;

    public String getStyleId() {
        return styleId;
    }

    public void setStyleId(String styleId) {
        this.styleId = styleId;
    }

    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    public SimplePhotographer getPhotographer() {
        return photographer;
    }

    public void setPhotographer(SimplePhotographer photographer) {
        this.photographer = photographer;
    }

    public String getHeadPhotographyUrl() {
        return headPhotographyUrl;
    }

    public void setHeadPhotographyUrl(String headPhotographyUrl) {
        this.headPhotographyUrl = headPhotographyUrl;
    }

    public String getHeadPhotographyName() {
        return headPhotographyName;
    }

    public void setHeadPhotographyName(String headPhotographyName) {
        this.headPhotographyName = headPhotographyName;
    }

    public String getHeadStylistUrl() {
        return headStylistUrl;
    }

    public void setHeadStylistUrl(String headStylistUrl) {
        this.headStylistUrl = headStylistUrl;
    }

    public String getHeadStylistName() {
        return headStylistName;
    }

    public void setHeadStylistName(String headStylistName) {
        this.headStylistName = headStylistName;
    }

    public String getHeadStyles() {
        return headStyles;
    }

    public void setHeadStyles(String headStyles) {
        this.headStyles = headStyles;
    }

    public ArrayList<SimpleStyles> getShootingStyles() {
        return shootingStyles;
    }

    public void setShootingStyles(ArrayList<SimpleStyles> shootingStyles) {
        this.shootingStyles = shootingStyles;
    }

    public SimplePhotographer getStylist() {
        return stylist;
    }

    public void setStylist(SimplePhotographer stylist) {
        this.stylist = stylist;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppDetailImages() {
        return appDetailImages;
    }

    public void setAppDetailImages(String appDetailImages) {
        this.appDetailImages = appDetailImages;
    }

    public Simple() {

    }

    public String getCoverUrlApp() {
        return coverUrlApp;
    }

    public void setCoverUrlApp(String coverUrlApp) {
        this.coverUrlApp = coverUrlApp;
    }

    public Simple(String actorNameFemale, String actorNameMale, String addressId, String addressName, String contentId, String coverUrlApp, String createDate, String headPhotographyName, String headPhotographyUrl, String headStyles, String headStylistName, String headStylistUrl, String name, SimplePhotographer photographer, List<ShootingExteriors> shootingExteriors, ArrayList<SimpleStyles> shootingStyles, String styleId, String styleName, SimplePhotographer stylist) {

        this.actorNameFemale = actorNameFemale;
        this.actorNameMale = actorNameMale;
        this.addressId = addressId;
        this.addressName = addressName;
        this.contentId = contentId;
        this.coverUrlApp = coverUrlApp;
        this.createDate = createDate;
        this.headPhotographyName = headPhotographyName;
        this.headPhotographyUrl = headPhotographyUrl;
        this.headStyles = headStyles;
        this.headStylistName = headStylistName;
        this.headStylistUrl = headStylistUrl;
        this.name = name;
        this.photographer = photographer;
        this.shootingExteriors = shootingExteriors;
        this.shootingStyles = shootingStyles;
        this.styleId = styleId;
        this.styleName = styleName;
        this.stylist = stylist;
    }

    public String getActorNameFemale() {

        return actorNameFemale;
    }

    public void setActorNameFemale(String actorNameFemale) {
        this.actorNameFemale = actorNameFemale;
    }

    public String getActorNameMale() {
        return actorNameMale;
    }

    public void setActorNameMale(String actorNameMale) {
        this.actorNameMale = actorNameMale;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
