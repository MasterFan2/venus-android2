package com.chinajsbn.venus.ui.photography;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.chinajsbn.venus.R;
import com.chinajsbn.venus.net.bean.WeddingSuit;
import com.chinajsbn.venus.ui.base.ActivityFeature;
import com.chinajsbn.venus.ui.base.MBaseFragmentActivity;
import com.chinajsbn.venus.ui.fragment.detail.PhotoSuitTagCosmeticsFragment;
import com.chinajsbn.venus.ui.fragment.detail.PhotoSuitTagDetailFragment;
import com.chinajsbn.venus.ui.fragment.detail.PhotoSuitTagSceneryFragment;
import com.chinajsbn.venus.ui.fragment.detail.PhotoSuitTagclothingFragment;
import com.chinajsbn.venus.utils.DimenUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;
import com.tool.widget.dragtop.DragTopLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;

/**
 * create by master fan on 15/07/17
 * suit detail
 */
@ActivityFeature(layout = R.layout.activity_suit_detail)
public class SuitDetailActivity extends MBaseFragmentActivity {

    @ViewInject(R.id.drag_layout)
    private DragTopLayout dragLayout;

    @ViewInject(R.id.view_pager)
    private ViewPager viewPager;

    private ModelPagerAdapter adapter;

    @ViewInject(R.id.tabs)
    private PagerSlidingTabStrip pagerSlidingTabStrip;

    //head element
    @ViewInject(R.id.suit_detail_head_img)
    private ImageView topImageView;

    @ViewInject(R.id.suit_detail_price_txt)
    private TextView priceTxt;

    @ViewInject(R.id.suit_detail_order_price_txt)
    private TextView orderTxt;

    @ViewInject(R.id.suit_detail_name_txt)
    private TextView nameTxt;

    @ViewInject(R.id.suit_detail_select_photographer_stylist_txt)
    private TextView selectPhotographerStylistTxt;

    private PagerModelManager factory;

    private WeddingSuit data;

    @Override
    public void initialize() {
        data = (WeddingSuit) getIntent().getSerializableExtra("suit");
        if (data != null)
            Picasso.with(context).load(data.getCoverUrlApp() + DimenUtil.getHorizontal() + DimenUtil.getSuffixUTF8()).placeholder(R.drawable.loading).into(topImageView);
        setData(data);
    }

    @Override
    public boolean onKeydown() {
        animFinish();
        return true;
    }

    @OnClick(R.id.m_title_left_btn)
    public void onBack(View view) {
        animFinish();
    }

    /**
     * @param dat
     */
    private void setData(WeddingSuit dat) {
        if (dat == null) return;
        Picasso.with(context).load(dat.getCoverUrlApp() + DimenUtil.getHorizontal()).into(topImageView);
        priceTxt.setText("￥ " + dat.getSalePrice());
        orderTxt.setText("订金￥ " + dat.getSalePrice());
        nameTxt.setText(dat.getName());
        String str = "";
        if (dat.getIsOptionalStylist() == 1) {
            str = "可自选造型师";
        }


        try {
            String temp = dat.getAppDetailImages();
            JSONObject data = new JSONObject(temp);

            factory = new PagerModelManager();
            factory.addCommonFragment(getFragments(data), getTitles());
            adapter = new ModelPagerAdapter(getSupportFragmentManager(), factory);
            viewPager.setAdapter(adapter);
            pagerSlidingTabStrip.setViewPager(viewPager);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (dat.getIsOptionalCameraman() == 1) {
            if (TextUtils.isEmpty(str)) {
                str = "可自选摄影师";
            } else {
                str += "/摄影师";
            }
        }

        if (TextUtils.isEmpty(str)) {
            selectPhotographerStylistTxt.setText("不可自选造型师/摄影师");
        } else {
            selectPhotographerStylistTxt.setText("(" + str + ")");
        }
    }

    // Handle scroll event from fragments
    public void onEvent(Boolean b) {
        dragLayout.setTouchMode(b);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private List<String> getTitles() {
        ArrayList<String> lists = new ArrayList<>();
        lists.add("详情");
        lists.add("服务");
        lists.add("服装");
        lists.add("化妆品");
        lists.add("景点");
        lists.add("流程");
        return lists;
    }

    private List<Fragment> getFragments(JSONObject data) {
        try {

            List<Fragment> list = new ArrayList<>();
            //详情
            Fragment tagDetailFragment = new PhotoSuitTagDetailFragment();
            Bundle detailArgs = new Bundle();
            detailArgs.putString("detailArgs", data.getString("app_detailImages"));
            tagDetailFragment.setArguments(detailArgs);
            list.add(tagDetailFragment);

            //服务
            Fragment tagServiceFragment = new PhotoSuitTagDetailFragment();
            Bundle serviceArgs = new Bundle();
            serviceArgs.putString("detailArgs", data.getString("app_serviceImages"));
            tagServiceFragment.setArguments(serviceArgs);
            list.add(tagServiceFragment);

            //服装
            Fragment tagClothingFragment = new PhotoSuitTagclothingFragment();
            Bundle clothingArgs = new Bundle();
            clothingArgs.putString("clothingArgs", data.getString("app_clothShootImages"));
            tagClothingFragment.setArguments(clothingArgs);
            list.add(tagClothingFragment);

            //化妆品
            Fragment tagCosmeticsFragment = new PhotoSuitTagCosmeticsFragment();
            Bundle cosmeticsArgs = new Bundle();
            cosmeticsArgs.putString("cosmeticsArgs", data.getString("app_cosmeticImages"));
            tagCosmeticsFragment.setArguments(cosmeticsArgs);
            list.add(tagCosmeticsFragment);

            //景点
            Fragment tagSceneryFragment = new PhotoSuitTagSceneryFragment();
            Bundle sceneryArgs = new Bundle();
            sceneryArgs.putString("suitArgs", data.getString("app_baseSampleImages"));
            tagSceneryFragment.setArguments(sceneryArgs);
            list.add(tagSceneryFragment);

            //流程
            Fragment tagProcedureFragment = new PhotoSuitTagSceneryFragment();
            Bundle procedureArgs = new Bundle();
            procedureArgs.putString("suitArgs", data.getString("app_processImages"));
            tagProcedureFragment.setArguments(procedureArgs);
            list.add(tagProcedureFragment);

            return list;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
