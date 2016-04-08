package com.chinajsbn.venus.net.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by 13510 on 2015/12/3.
 */
@Table(name = "MasterFanSeason")
public class MasterFanSeason {

    @Column(column = "id")
    private int id;

    @Id
    @Column(column = "seasonId")
    private int seasonId;

    @Column(column = "coverUrl")
    private String coverUrl;

    @Column(column = "coverUrlApp")
    private String coverUrlApp;

    @Column(column = "name")
    private String name;

    public String getCoverUrlApp() {
        return coverUrlApp;
    }

    public void setCoverUrlApp(String coverUrlApp) {
        this.coverUrlApp = coverUrlApp;
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

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public String getCoverUrl() {

        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public MasterFanSeason(){}
}
