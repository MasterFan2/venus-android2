package com.chinajsbn.venus.net.bean;

/**
 * Created by MasterFan on 2016/4/14 16:53.
 * <p/>
 * description:
 */
public class CarParam {
    public int minPrice  = -1;
    public int maxPrice  = -1;
    public int brandId   = -1;//品牌
    public int carNature = -1;//车队：单车/个人
    public int modelsId  = -1;//型号：加长型。。。
    public int levelId   = -1;//级别
    public int pageSize  = 10;
    public int pageIndex = 1;

    //重置所有设置
    public void reset() {
        minPrice  = -1;
        maxPrice  = -1;
        brandId   = -1;//品牌
        carNature = -1;//车队：单车/个人
        modelsId  = -1;//型号：加长型。。。
        levelId   = -1;//级别
    }


}
