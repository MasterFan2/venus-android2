package com.chinajsbn.venus.ui.supplies;

import android.graphics.Paint;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinajsbn.venus.R;
import com.chinajsbn.venus.net.bean.Supplie;
import com.chinajsbn.venus.ui.base.ActivityFeature;
import com.chinajsbn.venus.ui.base.MBaseFragmentActivity;
import com.chinajsbn.venus.utils.DimenUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;
import com.tool.widget.MasterTitleView;
import com.tool.widget.indicator.CircleIndicator;

@ActivityFeature(layout = R.layout.activity_car_detail)
public class SupplieDetailActivity extends MBaseFragmentActivity {

    @ViewInject(R.id.webView)
    private WebView webView;

    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;

    @ViewInject(R.id.viewPager_indicator)
    private CircleIndicator indicator;

    @ViewInject(R.id.titleView)
    private MasterTitleView titleView;

    @ViewInject(R.id.car_detail_title_txt)
    private TextView titleTxt;

    @ViewInject(R.id.car_detail_desc_txt)
    private TextView descTxt;

    @ViewInject(R.id.car_detail_parameter_txt)
    private TextView parameterTxt;

    @ViewInject(R.id.car_detail_newPrice_txt)
    private TextView newPriceTxt;

    @ViewInject(R.id.car_detail_oldPrice_txt)
    private TextView oldPriceTxt;

    @ViewInject(R.id.viewPager_layout)
    private View pagerLayout;

    private Supplie supplie;

    @Override
    public void initialize() {

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DimenUtil.screenWidth, DimenUtil.screenWidth);
        pagerLayout.setLayoutParams(params);

        supplie = (Supplie) getIntent().getSerializableExtra("supply");

        titleView.setTitleText("用品详情");

        if (supplie != null) {
            initWebView(supplie);
            newPriceTxt.setText("￥" + supplie.getSellingPrice());
            oldPriceTxt.setText("￥" + supplie.getMarketPrice());
            titleTxt.setText(supplie.getTitle());
            descTxt.setText(supplie.getDescription());

            String strParams = supplie.getParameter();
            if(strParams.contains("#")){
                strParams = strParams.split("#")[0].replace("|", "\n").replace(" ", "");
            }else{
                strParams = strParams.replace("|", "\n");
            }
            parameterTxt.setText(strParams);

            if(supplie.getAppDetailImages() != null){
                String tempStr = supplie.getAppDetailImages();
                tempStr = tempStr.replace("[", "").replace("]", "").replace("\"", "");
                viewPager.setAdapter(new MyViewPagerAdapter(tempStr.split(",")));
                indicator.setViewPager(viewPager);
            }else{
                pagerLayout.setVisibility(View.GONE);
            }

            webView.loadData(supplie.getContent().replace("<img ", "<img width='100%' "), "text/html;charset=UTF-8", null);
        }
    }

    @OnClick(R.id.m_title_left_btn)
    public void back(View view){
        animFinish();
    }

    private void initWebView(Supplie supplie){

        if(supplie.getSellingPrice() == supplie.getMarketPrice()){
            oldPriceTxt.setVisibility(View.GONE);
        }

        //中间删除线
        oldPriceTxt.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG ); //中间横线
        oldPriceTxt.getPaint().setAntiAlias(true);// 抗锯齿

        WebSettings webSettings = webView.getSettings();

        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);

        //设置可以访问文件
        webSettings.setAllowFileAccess(true);

        //设置支持缩放
        webSettings.setBuiltInZoomControls(false);

        webSettings.setDefaultTextEncodingName("utf-8");

        //加载需要显示的网页
        webView.setWebViewClient(new MyWebViewClient());
    }

    //Web视图
    private class MyWebViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    class MyViewPagerAdapter extends PagerAdapter {
        private String[] imgList;

        public MyViewPagerAdapter(String[] imgs) {
            this.imgList = imgs;
        }

        @Override
        public int getCount() {
            return imgList == null ? 0 : imgList.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView img = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_single_img_hotel_head_layout, null);
            String url = imgList[position];
            if (!TextUtils.isEmpty(url)) {
                Picasso.with(context).load(url + DimenUtil.getHorizontal() + DimenUtil.getSuffixUTF8()).placeholder(R.drawable.loading).into(img);
            }
            container.addView(img);
            return img;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }

    @Override
    public boolean onKeydown() {
        animFinish();
        return false;
    }
}
