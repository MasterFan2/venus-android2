package com.chinajsbn.venus.net.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.annotation.Transient;

/**
 * Created by MasterFan on 2016/4/14 17:35.
 * <p/>
 * description:型号
 */
@Table(name = "CarModule")
public class CarModule {

    @Column(column = "description")
    private String description;

    @Id
    @Column(column = "id")
    private int id;

    @Column(column = "isUsed")
    private int isUsed;

    @Column(column = "modelsId")
    private int modelsId;

    @Column(column = "name")
    private String name;

    @Transient
    private boolean checked;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
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

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public int getModelsId() {
        return modelsId;
    }

    public void setModelsId(int modelsId) {
        this.modelsId = modelsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CarModule() {

    }
}
