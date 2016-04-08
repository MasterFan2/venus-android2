package com.chinajsbn.venus.net.bean.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 13510 on 2016/4/1.
 */
public class MenuGenerator {
    public static List<Menu> createMenu() {
        List<Menu> menuList = new ArrayList<>();

        /**婚纱摄影*/
        ArrayList<Menu> tempHSSY = new ArrayList<>();
        Menu mZPXS = new Menu("27232", "作品欣赏", null);//作品欣赏
        Menu mKPXS = new Menu("27233", "客片欣赏", null);//客片欣赏
        Menu mTXBJ = new Menu("27234", "套系报价", null);//套系报价
        Menu mHSJS = new Menu("42304", "婚纱纪实", null);//婚纱纪实
        Menu mHZJQ = new Menu("42305", "婚照技巧", null);//婚照技巧
        tempHSSY.add(mZPXS);
        tempHSSY.add(mKPXS);
        tempHSSY.add(mTXBJ);
        tempHSSY.add(mHSJS);
        tempHSSY.add(mHZJQ);
        Menu mHSSY = new Menu("27201", "婚纱摄影", tempHSSY);

        /**婚宴预订*/
        ArrayList<Menu> tempHYYD = new ArrayList<>();
        Menu mYDSY = new Menu("42306", "预订首页", null);//预订首页
        Menu mHYZS = new Menu("42307", "婚宴知识", null);//婚宴知识
        tempHYYD.add(mYDSY);
        tempHYYD.add(mHYZS);
        Menu mHYYD = new Menu("27202", "婚宴预订", tempHYYD);

        /**婚庆定制*/
        ArrayList<Menu> tempHQDZ = new ArrayList<>();
        Menu mSJAL = new Menu("27239", "实景案例", null);//实景案例
        Menu mHLGP = new Menu("42308", "婚礼跟拍", null);//婚礼跟拍
        Menu mHLSP = new Menu("42309", "婚礼视频", null);//婚礼视频
        Menu mXHLR = new Menu("38490", "选婚礼人", null);//选婚礼人
        Menu mHLXT = new Menu("42310", "婚礼学堂", null);//婚礼学堂
        tempHQDZ.add(mSJAL);
        tempHQDZ.add(mHLGP);
        tempHQDZ.add(mHLSP);
        tempHQDZ.add(mXHLR);
        tempHQDZ.add(mHLXT);
        Menu mHQDZ = new Menu("27203", "婚庆定制", tempHQDZ);

        /**婚纱礼服*/
        ArrayList<Menu> tempHSLF = new ArrayList<>();
        Menu mLFSY = new Menu("42313", "礼服首页", null);//礼服首页
        Menu mLFZS = new Menu("42314", "礼服知识", null);//礼服知识
        tempHSLF.add(mLFSY);
        tempHSLF.add(mLFZS);
        Menu mHSLF = new Menu("27204", "婚纱礼服", tempHSLF);

        /**婚戒钻石*/
        Menu mHJZS = new Menu("27205", "婚戒钻石", null);

        /**微电影*/
        ArrayList<Menu> tempWDY = new ArrayList<>();
        Menu mAQWD = new Menu("42315", "爱情微电影", null);//爱情微电影
        Menu mAQMV = new Menu("42316", "爱情MV",     null);//爱情MV
        Menu mBYJQ = new Menu("42317", "表演技巧",   null);//表演技巧
        tempWDY.add(mAQWD);
        tempWDY.add(mAQMV);
        tempWDY.add(mBYJQ);
        Menu mWDY  = new Menu("27206", "微电影", tempWDY);

        /**婚礼用品*/
        ArrayList<Menu> tempHLYP = new ArrayList<>();
        Menu mYPSY = new Menu("42318", "用品首页", null);//用品首页
        Menu mYPTS = new Menu("42319", "用品贴士", null);//用品贴士
        tempHLYP.add(mYPSY);
        tempHLYP.add(mYPTS);
        Menu mHLYP  = new Menu("27207", "婚礼用品", tempHLYP);

        /**婚车租赁*/
        ArrayList<Menu> tempHCZL = new ArrayList<>();
        Menu mZLSY = new Menu("42320", "租赁首页", null);//租赁首页
        Menu mZCJY = new Menu("42321", "租车经验", null);//爱情微电影
        tempHCZL.add(mZLSY);
        tempHCZL.add(mZCJY);
        Menu mHCZL  = new Menu("27208", "婚车租赁", tempHCZL);

        menuList.add(mHSSY);//婚纱摄影
        menuList.add(mHYYD);//婚宴预订
        menuList.add(mHQDZ);//婚庆定制
        menuList.add(mHSLF);//婚纱礼服
        menuList.add(mHJZS);//婚戒钻石
        menuList.add(mWDY); //微电影
        menuList.add(mHLYP);//婚礼用品
        menuList.add(mHCZL);//婚车租赁

        return menuList;
    }
}
