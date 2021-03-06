package com.chinajsbn.venus.ui.other;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chinajsbn.venus.R;
import com.chinajsbn.venus.net.bean.Other;
import com.chinajsbn.venus.ui.base.ActivityFeature;
import com.chinajsbn.venus.ui.base.MBaseFragmentActivity;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

@ActivityFeature(layout = R.layout.activity_other_detail)
public class OtherDetailActivity extends MBaseFragmentActivity {

    @ViewInject(R.id.webView)
    private WebView webView;

    private  Other other ;

    @Override
    public void initialize() {

        other = (Other) getIntent().getSerializableExtra("data");

        WebSettings webSettings = webView.getSettings();

        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);

        //设置可以访问文件
        webSettings.setAllowFileAccess(true);

        //设置支持缩放
        webSettings.setBuiltInZoomControls(false);

        webSettings.setDefaultTextEncodingName("utf-8");

        //加载需要显示的网页
        webView.setWebViewClient(new webViewClient());

        if(other != null) {
            String content = other.getContent();
            webView.loadData(content.replace("<img ", "<img width='100%' "), "text/html;charset=UTF-8", null);
        }
    }

    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public boolean onKeydown() {
        animFinish();
        return false;
    }


    @OnClick(R.id.m_title_left_btn)
    public void onBack(View view) {
        animFinish();
    }

}
