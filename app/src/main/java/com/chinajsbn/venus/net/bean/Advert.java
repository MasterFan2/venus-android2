package com.chinajsbn.venus.net.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by 13510 on 2015/10/15.
 */
@Table(name = "Advert")
public class Advert {

    @Id
    @Column(column = "contentId")
    private String advertId;

    @Column(column = "contentUrl")
    private String coverUrlApp;

    public String getContentId() {
        return advertId;
    }

    public void setContentId(String contentId) {
        this.advertId = contentId;
    }

    public String getContentUrl() {
        return coverUrlApp;
    }

    public void setContentUrl(String contentUrl) {
        this.coverUrlApp = contentUrl;
    }
    public Advert(){}
    public Advert(String contentId, String contentUrl) {

        this.advertId = contentId;
        this.coverUrlApp = contentUrl;
    }
}
