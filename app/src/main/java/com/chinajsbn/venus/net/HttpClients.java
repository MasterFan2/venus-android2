package com.chinajsbn.venus.net;

import android.content.Context;
import android.text.TextUtils;

import com.chinajsbn.venus.net.bean.AddrStyleResp;
import com.chinajsbn.venus.net.bean.Advert;
import com.chinajsbn.venus.net.bean.BanquetDetail;
import com.chinajsbn.venus.net.bean.Base;
import com.chinajsbn.venus.net.bean.Brands;
import com.chinajsbn.venus.net.bean.Car;
import com.chinajsbn.venus.net.bean.CarLevel;
import com.chinajsbn.venus.net.bean.CarModule;
import com.chinajsbn.venus.net.bean.CarParam;
import com.chinajsbn.venus.net.bean.CarType;
import com.chinajsbn.venus.net.bean.Custom;
import com.chinajsbn.venus.net.bean.Dress;
import com.chinajsbn.venus.net.bean.F4;
import com.chinajsbn.venus.net.bean.Film;
import com.chinajsbn.venus.net.bean.FilterBean;
import com.chinajsbn.venus.net.bean.Hotel;
import com.chinajsbn.venus.net.bean.MasterFanSeason;
import com.chinajsbn.venus.net.bean.Other;
import com.chinajsbn.venus.net.bean.Scheme;
import com.chinajsbn.venus.net.bean.SearchHotelParam;
import com.chinajsbn.venus.net.bean.Sence;
import com.chinajsbn.venus.net.bean.Simple;
import com.chinajsbn.venus.net.bean.Style;
import com.chinajsbn.venus.net.bean.Supplie;
import com.chinajsbn.venus.net.bean.WeddingSuit;
import com.squareup.okhttp.OkHttpClient;
import com.tool.PreferenceUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by 13510 on 2016/4/1.
 */
public class HttpClients {

//    private static final String BASE_URL = "http://app.jsbn.com/api";//开发服务器
    private static final String BASE_URL = " http://cq.jsbn.com/api";//开发服务器
//    private static final String BASE_URL = " http://192.168.1.5:8088/api";//开发服务器

    private Context mContext;
    private RestAdapter restAdapter = null;
    private NetInterface netInterface = null;

    private static final String CLIENT = "wx";
    private static HttpClients instance;

    public HttpClients() {}

    public static HttpClients getInstance() {
        if (instance == null) {
            instance = new HttpClients();
        }
        return instance;
    }

    /**
     * @param context 上下文
     * @return false:初期化失败 true:初期化成功
     */
    public boolean initialize(Context context) {
        mContext = context;
        OkHttpClient client = new OkHttpClient().setCookieHandler(new CustomCookieManager(mContext));
        client.setConnectTimeout(30, TimeUnit.SECONDS);//连接超时
        client.setReadTimeout(30, TimeUnit.SECONDS);   //读取超时

        // 创建适配器
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(BASE_URL)
                .setClient(new OkClient(client))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {

                        String temp = PreferenceUtils.getValue(mContext, PreferenceUtils.PREFERENCE_SESSION_ID,
                                PreferenceUtils.DataType.STRING);
                        if (temp != null && !temp.equals("")) {
                            TextUtils.isEmpty("");
                            // 设置JSESSIONID
                            request.addHeader("Set-Cookie", "JSESSIONID=" + temp);
                        }

                        temp = PreferenceUtils.getValue(mContext,
                                PreferenceUtils.PREFERENCE_TOKEN,
                                PreferenceUtils.DataType.STRING);
                        if (temp != null && !temp.equals("")) {
                            // 设置TOKEN
                            request.addHeader("Set-Cookie",
                                    "TOKEN=" + temp);
                        }

                        temp = PreferenceUtils.getValue(mContext,
                                PreferenceUtils.PREFERENCE_TICKET,
                                PreferenceUtils.DataType.STRING);
                        if (temp != null && !temp.equals("")) {
                            // 设置TICKET
                            request.addHeader("Set-Cookie", "TICKET=" + temp);
                        }
                    }
                }).build();
        if (restAdapter == null) {
            return false;
        }
        netInterface = restAdapter.create(NetInterface.class);
        return netInterface != null;
    }

    //接口
    interface NetInterface {

        @GET("/adv/" + CLIENT + "_index_top")
        void getHomeAdvert(Callback<Base<ArrayList<Advert>>> cb);//获取首页广告

        /**============酒店===============*/
        @GET("/hotel/" + CLIENT + "_hotel_list")//酒店列表
        void searchHotels(@QueryMap Map<String, Object> params,  Callback<Base<ArrayList<Hotel>>> cb);

        @GET("/hotelDistrict/all") //酒店区域列表
        void districtList(Callback<Base<ArrayList<FilterBean>>> cb);

        @GET("/hotel/detail/{id}")//酒店详情
        void hotelDetail(@Path("id")int detailId, Callback<Base<ArrayList<Hotel>>> cb);

        @GET("/banquetHall/detail")//获取宴会厅详情
        void getBanquetDetail(@Query("id") int id, Callback<Base<ArrayList<BanquetDetail>>> cb);
        /**========end酒店===============*/


        /**============婚纱摄影===============*/
        @GET("/shootStyle/all")                      //风格样式
        void styleList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<Style>>> cb);

        @GET("/exterior/all")                      //场景
        void senceList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<Sence>>> cb);

        @GET("/sample/"+ CLIENT +"_samples_list")   //作品列表【样片列表】
        void sampleList(@QueryMap Map<String, Object> params, Callback<Base<ArrayList<Simple>>> cb);

        @GET("/pringlesSeason/all")                 //客片分季列表
        void customSeason( Callback<Base<ArrayList<MasterFanSeason>>> cb);

        @GET("/pringles/"+ CLIENT +"_pringles_list")//客片列表
        void customList(@QueryMap Map<String, Object> params, Callback<Base<ArrayList<Custom>>> cb);

        @GET("/suite/"+ CLIENT +"_suite_list")      //套系列表
        void suitList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<WeddingSuit>>> cb);

        @GET("/recordvideo/"+ CLIENT +"_record_video_list")//婚纱纪实
        void documentaryList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<Film>>> cb);
        /**===========end婚纱摄影=============*/


        /**============案例欣赏===============*/
        @GET("/caseStyle/all")//实景案例风格列表
        void caseStyleList(Callback<Base<ArrayList<Style>>> cb);

        @GET("/cases/"+ CLIENT +"_scheme_list")//实景案例
        void schemeList(@QueryMap Map<String, Object> params, Callback<Base<ArrayList<Scheme>>> cb);

        @GET("/followPhoto/"+ CLIENT +"_weddingpat_list")//婚礼跟拍
        void gpList(@QueryMap Map<String, Object> params, Callback<Base<ArrayList<Scheme>>> cb);

        @GET("/followVideo/"+ CLIENT +"_weddingvideo_list")//婚礼视频
        void gpVideoList(@QueryMap Map<String, Object> params, Callback<Base<ArrayList<Film>>> cb);

        ////////////////////选婚礼人//////////////////
        @GET("/f4/host")//主持人
        void f4HostList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<F4>>> cb);

        @GET("/f4/dresser")//化妆师
        void f4DresserList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<F4>>> cb);

        @GET("/f4/photographer")//摄影师
        void f4PhotographerList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<F4>>> cb);

        @GET("/f4/camera")//摄像师
        void f4CameramanList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<F4>>> cb);
        /**============end案例欣赏===============*/


        /**============婚纱礼服===============*/
        @GET("/dressType/all")//礼服类型
        void dressTypeList(Callback<Base<ArrayList<F4>>> cb);

        @GET("/dressBrand/all")//礼服品牌
        void dressBrandList(@Query("typeId")int typeId, Callback<Base<ArrayList<Brands>>> cb);

        @GET("/dress/"+ CLIENT +"_dress_list")//礼服列表
        void dressList(@QueryMap Map<String, Object> params, Callback<Base<ArrayList<Dress>>> cb);
        /**============end 婚纱礼服===============*/


        /**============微电影===============*/
        @GET("/video/"+ CLIENT +"_movie_love_movies")//微电影
        void lovefilmList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<Film>>> cb);

        @GET("/video/"+ CLIENT +"_movie_love_mv")   //MV
        void mvfilmList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<Film>>> cb);
        /**============end 微电影===============*/


        /**============用品===============*/
        @GET("/suppliesBrand/all")//用品类型列表
        void suppliesTypeList(Callback<Base<ArrayList<CarType>>> cb);

        @GET("/weddingsupplies/"+ CLIENT +"_supplies_list")//用品列表
        void suppliesList(@QueryMap Map<String, Object> params, Callback<Base<ArrayList<Supplie>>> cb);
        /**============end 用品===============*/


        /**============租车===============*/
        @GET("/carBrand/all")//租车品牌列表
        void carTypeList(Callback<Base<ArrayList<CarType>>> cb);

        @GET("/carModels/all")//租车型号列表
        void carModelList(Callback<Base<ArrayList<CarModule>>> cb);

        @GET("/carLevel/all")//租车档次列表
        void carLevel(Callback<Base<ArrayList<CarLevel>>> cb);

        @GET("/car/"+ CLIENT +"_car_list")//车列表
        void carList(@QueryMap Map<String, Object> params, Callback<Base<ArrayList<Car>>> cb);
        /**============end 租车===============*/


        /**============婚礼课堂===============*/
        @GET("/weddingroom/"+ CLIENT +"_suite_class_list")//婚照技巧
        void photographerRoomList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<Other>>> cb);

        @GET("/weddingroom/"+ CLIENT +"_hotel_class_list")//婚宴知识
        void hotelRoomList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<Other>>> cb);

        @GET("/weddingroom/"+ CLIENT +"_scheme_class_list")//婚礼学堂
        void schemeRoomList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<Other>>> cb);

        @GET("/weddingroom/"+ CLIENT +"_dress_class_list")//礼服知识
        void dressRoomList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<Other>>> cb);

        @GET("/weddingroom/"+ CLIENT +"_movie_class_list")//表演技巧
        void movieRoomList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<Other>>> cb);

        @GET("/weddingroom/"+ CLIENT +"_supplies_class_list")//用品贴士
        void suppliesRoomList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<Other>>> cb);

        @GET("/weddingroom/"+ CLIENT +"_car_class_list")//租车经验
        void carRoomList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<Other>>> cb);
        /**============end婚礼课堂===============*/
    }

    /**
     * 获取首页广告
     *
     * @param cb
     */
    public void getHomeAdvert(Callback<Base<ArrayList<Advert>>> cb) {
        netInterface.getHomeAdvert(cb);
    }

    /**
     * 酒店区域列表
     * @param cb
     */
    public void districtList(Callback<Base<ArrayList<FilterBean>>> cb) {
        netInterface.districtList(cb);
    }

    /**
     * 正式酒店搜索
     * @param p
     */
    public void searchHotels(SearchHotelParam p,  Callback<Base<ArrayList<Hotel>>> cb) {

        Map<String, Object> params = new HashMap<>();

        if (p.minTable != -1) {          params.put("minTable", p.minTable);params.put("maxTable", p.maxTable);}
        if (p.minPrice != -1) {          params.put("minPrice", p.minPrice); params.put("maxPrice", p.maxPrice);}
        if (p.isDisaccount == 1)         params.put("isDisaccount", "1");
        if (p.isGift == 1)               params.put("isGift", "1");
        if (!TextUtils.isEmpty(p.order)) params.put("order", p.order);
        if (!TextUtils.isEmpty(p.sort))  params.put("short", p.sort);
        if(p.cityId > 0)                 params.put("cityId", p.cityId);

        params.put("pageIndex", p.pageIndex);
        params.put("pageSize", p.pageSize);

        netInterface.searchHotels(params, cb);
    }

    /**
     * 获取酒店详情
     * @param detailId
     * @param cb
     */
    public void hotelDetail(int detailId, Callback<Base<ArrayList<Hotel>>> cb) {
        netInterface.hotelDetail(detailId, cb);
    }

    /**
     * 获取宴会厅详情
     * @param id
     * @param cb
     */
    public void getBanquetDetail( int id, Callback<Base<ArrayList<BanquetDetail>>> cb){
        netInterface.getBanquetDetail(id, cb);
    }

    /**
     * 风格样式列表
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    public void styleList(int pageIndex, int pageSize, Callback<Base<ArrayList<Style>>> cb) {
        netInterface.styleList(pageIndex, pageSize, cb);
    }

    /**
     * 场景列表
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    public void senceList(int pageIndex, int pageSize, Callback<Base<ArrayList<Sence>>> cb) {
        netInterface.senceList(pageIndex, pageSize, cb);
    }


    /**
     * 作品列表
     * @param exteriorId
     * @param shootStyleId
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    public void sampleList(int exteriorId, int shootStyleId, int pageIndex, int pageSize, Callback<Base<ArrayList<Simple>>> cb) {
        Map<String, Object> params = new HashMap<>();
        if(exteriorId   > 0) params.put("exteriorId", exteriorId);
        if(shootStyleId > 0) params.put("shootStyleId", shootStyleId);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        netInterface.sampleList(params, cb);
    }

    /**
     * 客片分季列表
     * @param cb
     */
    public void customSeason( Callback<Base<ArrayList<MasterFanSeason>>> cb){
        netInterface.customSeason(cb);
    }
    /**
     * 客片列表
     * @param seasonId
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    public void customList(int seasonId, int pageIndex, int pageSize, Callback<Base<ArrayList<Custom>>> cb) {
        Map<String, Object> params = new HashMap<>();
        if(seasonId > 0) params.put("seasonId", seasonId);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        netInterface.customList(params, cb);
    }

    /**
     * 套系列表
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    public void suitList(int pageIndex, int pageSize, Callback<Base<ArrayList<WeddingSuit>>> cb) {
        netInterface.suitList(pageIndex, pageSize, cb);
    }

    /**
     * 婚纱纪实
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    public void documentaryList(int pageIndex, int pageSize, Callback<Base<ArrayList<Film>>> cb){
        netInterface.documentaryList(pageIndex, pageSize, cb);
    }

    /**
     * 案例风格
     * @param cb
     */
    public void caseStyleList(Callback<Base<ArrayList<Style>>> cb) {
        netInterface.caseStyleList(cb);
    }
    /**
     * 案例列表
     * @param styleId
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    public void schemeList(int styleId, int pageIndex, int pageSize, Callback<Base<ArrayList<Scheme>>> cb) {
        Map<String, Object> params = new HashMap<>();
        if(styleId > 0) params.put("styleId", styleId);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        netInterface.schemeList(params, cb);
    }

    /**
     * 婚礼跟拍
     * @param seasonId
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    public void gpList(int seasonId, int pageIndex, int pageSize, Callback<Base<ArrayList<Scheme>>> cb) {
        Map<String, Object> params = new HashMap<>();
        if(seasonId > 0) params.put("seasonId", seasonId);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        netInterface.gpList(params, cb);
    }

    /**
     * 婚礼视频
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    public void gpVideoList(int pageIndex, int pageSize, Callback<Base<ArrayList<Film>>> cb) {
        Map<String, Object> params = new HashMap<>();
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        netInterface.gpVideoList(params, cb);
    }

    /**
     * 主持人
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
   public void f4HostList(int pageIndex, int pageSize, Callback<Base<ArrayList<F4>>> cb) {
       netInterface.f4HostList(pageIndex, pageSize, cb);
   }

    /**
     * 化妆师
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    public void f4DresserList(int pageIndex, int pageSize, Callback<Base<ArrayList<F4>>> cb){
        netInterface.f4DresserList(pageIndex, pageSize, cb);
    }

    /**
     * 摄影师
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    public void f4PhotographerList(int pageIndex,int pageSize, Callback<Base<ArrayList<F4>>> cb){
        netInterface.f4PhotographerList(pageIndex, pageSize, cb);
    }

    /**
     * 摄像师
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    public void f4CameramanList(int pageIndex,int pageSize, Callback<Base<ArrayList<F4>>> cb){
        netInterface.f4CameramanList(pageIndex, pageSize, cb);
    }

    /**
     * 礼服类型
     * @param cb
     */
    public void dressTypeList(Callback<Base<ArrayList<F4>>> cb) {
        netInterface.dressTypeList(cb);
    }

    /**
     * 礼服品牌
     * @param cb
     */
    public void dressBrandList(int typeId, Callback<Base<ArrayList<Brands>>> cb) {
        netInterface.dressBrandList(typeId, cb);
    }

    /**
     * 礼服列表
     * @param brandId
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    public void dressList(int brandId, int pageIndex,int pageSize, Callback<Base<ArrayList<Dress>>> cb) {
        Map<String, Object> params = new HashMap<>();
        if(brandId > 0) params.put("brandId", brandId);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        netInterface.dressList(params, cb);
    }

    /**
     * 爱情微电影
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    public void lovefilmList(int pageIndex, int pageSize, Callback<Base<ArrayList<Film>>> cb) {
        netInterface.lovefilmList(pageIndex, pageSize, cb);
    }

    /**
     * 爱情MV
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    public void mvfilmList(int pageIndex, int pageSize, Callback<Base<ArrayList<Film>>> cb) {
        netInterface.mvfilmList(pageIndex, pageSize, cb);
    }

    /**
     * 用品类型列表
     * @param cb
     */
    public void suppliesTypeList(Callback<Base<ArrayList<CarType>>> cb) {
        netInterface.suppliesTypeList(cb);
    }

    /**
     * 用品列表
     * @param brandId
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    public void suppliesList(int brandId, int pageIndex, int pageSize, Callback<Base<ArrayList<Supplie>>> cb) {
        Map<String, Object> params = new HashMap<>();
        if(brandId > 0) params.put("brandId", brandId);
        params.put("pageIndex", pageIndex);
        params.put("pageSize", pageSize);
        netInterface.suppliesList(params, cb);
    }

    /**
     * 租车品牌列表
     * @param cb
     */
    public void carTypeList(Callback<Base<ArrayList<CarType>>> cb) {
        netInterface.carTypeList(cb);
    }

    /**
     * 租车型号列表
     * @param cb
     */
    public void carModelList(Callback<Base<ArrayList<CarModule>>> cb) {
        netInterface.carModelList(cb);
    }

    /**
     * 租车档次列表
     * @param cb
     */
    public void carLevel(Callback<Base<ArrayList<CarLevel>>> cb) {
        netInterface.carLevel(cb);
    }

    /**
     * 车列表
     * @param p
     * @param cb
     */
    public void carList(CarParam p, Callback<Base<ArrayList<Car>>> cb) {
        Map<String, Object> params = new HashMap<>();

        if (p.brandId != -1)   params.put("brandId", p.brandId);
        if (p.carNature != -1) params.put("carNature", p.carNature);
        if (p.levelId != -1)   params.put("levelId", p.levelId);
        if (p.modelsId != -1)  params.put("modelsId", p.modelsId);

        if (p.minPrice != -1)  params.put("minPrice", p.minPrice);
        if (p.maxPrice != -1)  params.put("maxPrice", p.maxPrice);

        params.put("pageIndex", p.pageIndex);
        params.put("pageSize",  p.pageSize);
        netInterface.carList(params, cb);
    }

    /**
     * 婚照技巧
     * @param cb
     */
    public void photographerRoomList(int pageIndex, int pageSize, Callback<Base<ArrayList<Other>>> cb) {
        netInterface.photographerRoomList(pageIndex, pageSize, cb);
    }

    /**
     * 婚宴知识
     * @param cb
     */
    public void hotelRoomList(int pageIndex, int pageSize,Callback<Base<ArrayList<Other>>> cb) {
        netInterface.hotelRoomList(pageIndex, pageSize, cb);
    }

    /**
     * 婚礼学堂
     * @param cb
     */
    public void schemeRoomList(int pageIndex, int pageSize,Callback<Base<ArrayList<Other>>> cb) {
        netInterface.schemeRoomList(pageIndex, pageSize, cb);
    }

    /**
     * 礼服知识
     * @param cb
     */
    public void dressRoomList(int pageIndex, int pageSize,Callback<Base<ArrayList<Other>>> cb) {
        netInterface.dressRoomList(pageIndex, pageSize, cb);
    }

    /**
     * 表演技巧
     * @param cb
     */
    public void movieRoomList(int pageIndex, int pageSize,Callback<Base<ArrayList<Other>>> cb) {
        netInterface.movieRoomList(pageIndex, pageSize, cb);
    }

    /**
     * 用品贴士
     * @param cb
     */
    public void suppliesRoomList(int pageIndex, int pageSize,Callback<Base<ArrayList<Other>>> cb) {
        netInterface.suppliesRoomList(pageIndex, pageSize, cb);
    }

    /**
     * 租车经验
     * @param cb
     */
    public void carRoomList(int pageIndex, int pageSize,Callback<Base<ArrayList<Other>>> cb) {
        netInterface.carRoomList(pageIndex, pageSize, cb);
    }
}
