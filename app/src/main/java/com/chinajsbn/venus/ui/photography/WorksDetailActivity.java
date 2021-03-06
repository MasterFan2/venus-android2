package com.chinajsbn.venus.ui.photography;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinajsbn.venus.R;
import com.chinajsbn.venus.net.bean.Base;
import com.chinajsbn.venus.net.bean.WorksDetail;
import com.chinajsbn.venus.net.bean.WorksDetailImage;
import com.chinajsbn.venus.ui.base.ActivityFeature;
import com.chinajsbn.venus.ui.base.MBaseFragmentActivity;
import com.chinajsbn.venus.utils.DimenUtil;
import com.chinajsbn.venus.utils.T;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;
import com.tool.widget.MasterTitleView;
import com.tool.widget.TouchImageView;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

@ActivityFeature(layout = R.layout.activity_works_detail)
public class WorksDetailActivity extends MBaseFragmentActivity {

    private final String TAG = "WorksDetailActivity";

    @ViewInject(R.id.works_detail_viewPager)
    private ViewPager viewPager;

    @ViewInject(R.id.works_detail_bottom_layout)
    private RelativeLayout bottomLayout;

    @ViewInject(R.id.works_detail_digit_indicator_txt)
    private TextView digitTxt;

    @ViewInject(R.id.titleView)
    private MasterTitleView titleView;

    private List<WorksDetailImage> worksList;

    private String personId;
    private String worksId;

    //标识文字是否    显示/隐藏    true:显示     false:隐藏
    private boolean show = true;
    private int size = 0;

    //////////////////////////////////////
    private  String[] dataList;

    ////////////////////cache//////////////////
    private DbUtils db;

    @Override
    public void initialize() {

        db = DbUtils.create(context);
        db.configAllowTransaction(true);

        String tempStr = getIntent().getStringExtra("list");
        tempStr = tempStr.replace("[", "").replace("]", "").replace("\"", "");
        dataList = tempStr.split(",");
        size = dataList.length;
        setDigitPosition(0);

//        if(isPlanner){
//            if(!NetworkUtil.hasConnection(context)){
//                T.s(context, "您处于离线模式, 请连网");
//                return;
//            }
//            dataList = getIntent().getStringArrayExtra("list");
//            viewPager.setAdapter(new MAdapter(dataList, 0));
//            size = dataList.length;
//            setDigitPosition(0);
//        }else {
//            personId = getIntent().getStringExtra("personId");
//            worksId = getIntent().getStringExtra("worksId");
//
//            if(NetworkUtil.hasConnection(context)){
//                HttpClient.getInstance().getPhotographerWorks(personId, worksId, cb);
//            }else {
//                try {
//                    worksList = db.findAll(Selector.from(WorksDetailImage.class).where("uniqueTag", "=", personId+"@" + worksId));
//
//                    viewPager.setAdapter(new MAdapter(worksList));
//                    size = worksList.size();
//                    setDigitPosition(0);
//                } catch (DbException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setDigitPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setAdapter(new MAdapter());

    }

    @OnClick(R.id.m_title_left_btn)
    public void onback(View view) {
        animFinish();
    }

    private void setDigitPosition(int position) {
        digitTxt.setText((position + 1) + "/" + size);
    }

    private Callback<Base<WorksDetail>> cb = new Callback<Base<WorksDetail>>() {

        @Override
        public void success(Base<WorksDetail> resp, Response response) {

            if (resp.getCode() == 200) {
                if (resp.getData() != null && resp.getData().getDetailedImages() != null && resp.getData().getDetailedImages().size() > 0) {
                    worksList = resp.getData().getDetailedImages();

                    try {

                            for (WorksDetailImage w: worksList){
                                w.setUniqueTag(personId + "@" + worksId);
                            }
                            db.dropTable(WorksDetailImage.class);
                            db.saveAll(worksList);

                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                    viewPager.setAdapter(new MAdapter());
                    size = worksList.size();
                    setDigitPosition(0);
                }
            } else {
                //
                T.s(context, "服务器维护中...");
            }
        }

        @Override
        public void failure(RetrofitError error) {
            T.s(context, "获取数据错误");
        }
    };

    class MAdapter extends PagerAdapter {

        public MAdapter() {
        }


        @Override
        public int getCount() {
            return dataList == null ? 0 : dataList.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TouchImageView img = (TouchImageView) LayoutInflater.from(context).inflate(R.layout.item_single_img_layout_centercrop, null);
            String url = dataList[position];

            if (!TextUtils.isEmpty(url)) {
                if(DimenUtil.isHorizontal(url)){
                    Picasso.with(context).load(url + DimenUtil.getHorizontalListViewStringDimension80Q() + DimenUtil.getSuffixUTF8()).placeholder(R.drawable.loading).into(img);
                }else {
                    Picasso.with(context).load(url + DimenUtil.getVerticalListViewStringDimension80Q() + DimenUtil.getSuffixUTF8()).placeholder(R.drawable.loading).into(img);
                }

            }
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (show) {
                        hide();
                    } else {
                        show();
                    }
                    show = !show;
                }
            });
            container.addView(img);
            return img;
        }

        private void show() {
            titleView .animate().translationY( 0).setInterpolator(new DecelerateInterpolator(2)) ;
            bottomLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
        }

        private void hide() {
            titleView .animate().translationY(-titleView.getHeight()).setInterpolator(new AccelerateInterpolator(2));
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) bottomLayout.getLayoutParams();
            int fabBottomMargin = lp.bottomMargin;
            bottomLayout.animate().translationY(bottomLayout.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
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
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}
