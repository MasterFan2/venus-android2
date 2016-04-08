package com.chinajsbn.venus.net.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by MasterFan on 2015/7/2.
 * description:
 */
@Table(name = "Style")
public class Style {

    @Id
    @Column(column = "Id")
    private int id;

    @Column(column = "caseStyleId")
    private int caseStyleId;

    @Column(column = "name")
    private String name;

    public int getCaseStyleId() {
        return caseStyleId;
    }

    public void setCaseStyleId(int caseStyleId) {
        this.caseStyleId = caseStyleId;
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

    public Style() {

    }

    public Style(int caseStyleId, int id, String name) {

        this.caseStyleId = caseStyleId;
        this.id = id;
        this.name = name;
    }
}
