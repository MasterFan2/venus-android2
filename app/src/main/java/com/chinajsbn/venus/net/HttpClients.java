package com.chinajsbn.venus.net;

import android.content.Context;
import android.text.TextUtils;

import com.chinajsbn.venus.net.bean.AddrStyleResp;
import com.chinajsbn.venus.net.bean.Advert;
import com.chinajsbn.venus.net.bean.BanquetDetail;
import com.chinajsbn.venus.net.bean.Base;
import com.chinajsbn.venus.net.bean.Custom;
import com.chinajsbn.venus.net.bean.Film;
import com.chinajsbn.venus.net.bean.Hotel;
import com.chinajsbn.venus.net.bean.MasterFanSeason;
import com.chinajsbn.venus.net.bean.SearchHotelParam;
import com.chinajsbn.venus.net.bean.Simple;
import com.chinajsbn.venus.net.bean.Style;
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
        @GET("/hotel/" + CLIENT + "_hotel_list")
        void searchHotels(@QueryMap Map<String, Object> params,  Callback<Base<ArrayList<Hotel>>> cb);//酒店列表搜索

        @GET("/hotel/detail/{id}")
        void hotelDetail(@Path("id")int detailId, Callback<Base<ArrayList<Hotel>>> cb);//酒店详情

        @GET("/banquetHall/detail")
        void getBanquetDetail(@Query("id") int id, Callback<Base<ArrayList<BanquetDetail>>> cb);//获取宴会厅详情
        /**========end酒店===============*/


        /**============婚纱摄影===============*/
        @GET("/caseStyle/all")                      //风格样式
        void styleList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<Style>>> cb);

        @GET("/sample/"+ CLIENT +"_samples_list")   //作品列表【样片列表】
        void sampleList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<Simple>>> cb);

        @GET("/pringlesSeason/all")
        void customSeason( Callback<Base<ArrayList<MasterFanSeason>>> cb);

        @GET("/pringles/"+ CLIENT +"_pringles_list")//客片列表
        void customList(@QueryMap Map<String, Object> params, Callback<Base<ArrayList<Custom>>> cb);

        @GET("/suite/"+ CLIENT +"_suite_list")      //套系列表
        void suitList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<WeddingSuit>>> cb);

        @GET("/recordvideo/"+ CLIENT +"_record_video_list")//婚纱纪实
        void documentaryList(@Query("pageIndex")int pageIndex, @Query("pageSize")int pageSize, Callback<Base<ArrayList<Film>>> cb);
        /**===========end婚纱摄影=============*/
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
     * 作品列表
     * @param pageIndex
     * @param pageSize
     * @param cb
     */
    public void sampleList(int pageIndex, int pageSize, Callback<Base<ArrayList<Simple>>> cb) {
        netInterface.sampleList(pageIndex, pageSize, cb);
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
        if(seasonId > 0){
            params.put("seasonId", seasonId);
        }
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
}
