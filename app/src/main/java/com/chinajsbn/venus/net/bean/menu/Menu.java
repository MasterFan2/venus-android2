package com.chinajsbn.venus.net.bean.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单
 * v2
 * Created by 13510 on 2016/4/1.
 */
public class Menu implements Serializable {

    private String contentId;
    private String moduleName;
    private ArrayList<Menu> subModule;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Menu(String contentId, String moduleName, ArrayList<Menu> subModule) {
        this.contentId = contentId;
        this.moduleName = moduleName;
        this.subModule = subModule;
    }

    public ArrayList<Menu> getSubModule() {

        return subModule;
    }

    public void setSubModule(ArrayList<Menu> subModule) {
        this.subModule = subModule;
    }

    public Menu() {

    }
}
