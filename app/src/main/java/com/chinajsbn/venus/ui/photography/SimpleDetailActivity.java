package com.chinajsbn.venus.ui.photography;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinajsbn.venus.R;
import com.chinajsbn.venus.net.bean.DoubleSimpleCustomStr;
import com.chinajsbn.venus.net.bean.MoreTag;
import com.chinajsbn.venus.net.bean.Simple;
import com.chinajsbn.venus.net.bean.SimplePhotographer;
import com.chinajsbn.venus.ui.base.ActivityFeature;
import com.chinajsbn.venus.ui.base.MBaseFragmentActivity;
import com.chinajsbn.venus.ui.base.OnRecyclerItemClickListener;
import com.chinajsbn.venus.ui.fragment.photography.MPhotoSimpleFragment;
import com.chinajsbn.venus.utils.DimenUtil;
import com.chinajsbn.venus.utils.T;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;
import com.tool.widget.MasterTitleView;
import com.tool.widget.MyRecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 样片详情
 */
@ActivityFeature(layout = R.layout.activity_simple_detail)
public class SimpleDetailActivity extends MBaseFragmentActivity implements OnRecyclerItemClickListener {

    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;

    @ViewInject(R.id.titleView)
    private MasterTitleView titleView;
    //---------------------------------------------------------
    private String name = "";
    private String date = "";
    private Simple headSimple;

    private String simpleOrCustom;


    private String f = "";
    private String m = "";

    private String styleName = null;

    private DbUtils db;

    private ArrayList<Simple> data;
    private ArrayList<String> orderList = new ArrayList<>();

    //------------------------------------------------------------------


    @Override
    public void initialize() {

        db = DbUtils.create(context);
        db.configAllowTransaction(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        simpleOrCustom = getIntent().getStringExtra("simpleOrCustom");
        if (simpleOrCustom.equals("custom")) {
            titleView.setTitleText("客片详情");
//            HttpClient.getInstance().getDayCustomDetail(parentId, contentId, cb);
        } else if (simpleOrCustom.equals("simple")) {
            titleView.setTitleText("作品详情");
//            HttpClient.getInstance().getSimpleDetail(parentId, contentId, cb);
        }
        name = getIntent().getStringExtra("name");
        date = getIntent().getStringExtra("date");

        f = getIntent().getStringExtra("f");
        m = getIntent().getStringExtra("m");

        styleName = getIntent().getStringExtra("style");

        String images = getIntent().getStringExtra("images");
        generateData(images);
    }

    /**
     * 生成列表数据
     * @param res
     */
    private void generateData(String res) {
        if(TextUtils.isEmpty(res))
            return;
        String ret = res.replace("[", "").replace("]", "").replace("\"", "");
        String retArr[] = ret.split(",");
        List<String> dataList = Arrays.asList(retArr);

        ArrayList<String> hList = new ArrayList<>();
        ArrayList<String> vList = new ArrayList<>();
        int size = dataList.size();
        for (int i = 0; i < size; i++) {
            String url = dataList.get(i);
            url = url.substring(url.lastIndexOf("_") + 1, url.lastIndexOf("."));
            int width = Integer.parseInt(url.split("x")[0]);
            int height = Integer.parseInt(url.split("x")[1]);

            if (width >= height)     hList.add(dataList.get(i));
            else if (width < height) vList.add(dataList.get(i));
        }

        //2.合并
        ArrayList<DoubleSimpleCustomStr> finalList = new ArrayList<>();
        int h_size = hList.size();
        int v_size = vList.size();

        if(h_size <= 0 && v_size <= 0){
            T.s(context, "无数据");
            return;
        }

        int j = 0;

        if(h_size > v_size || h_size == v_size){//模图比较多
            for (int i = 0; i < h_size; i++) {
                //----------------------------
                if (i == 0) {//第一二行
                    ///////////////////////第一行//////////////////////
                    finalList.add(new DoubleSimpleCustomStr(hList.get(i), null));

                    ///////////////////////第二行//////////////////////
                    if(v_size > 0) {
                        finalList.add(new DoubleSimpleCustomStr(null, vList.get(j++)));
                    } else {
                        i++;
                        finalList.add(new DoubleSimpleCustomStr(null, hList.get(i)));
                    }
                } else {
                    if (j + 2 < v_size) {//本次充足
                        finalList.add(new DoubleSimpleCustomStr(hList.get(i), null));
                        finalList.add(new DoubleSimpleCustomStr(vList.get(j++), vList.get(j++)));
                    } else if (j + 1 < v_size) {
                        finalList.add(new DoubleSimpleCustomStr(hList.get(i), null));
                        finalList.add(new DoubleSimpleCustomStr(vList.get(j++), vList.get(j++)));
                    } else if (j < v_size) {
                        finalList.add(new DoubleSimpleCustomStr(hList.get(i), null));
                        finalList.add(new DoubleSimpleCustomStr(vList.get(j++), null));
                    } else {
                        finalList.add(new DoubleSimpleCustomStr(hList.get(i), null));
                    }
                }
            }
        } else {     //竖图比较多

            if(h_size <= 0){//没有横图， 全是竖图
                for (int i = 0; i < v_size; i++) {
                    if (i == 0) {//第一二行
                        finalList.add(new DoubleSimpleCustomStr(hList.get(i), null));
                        i++;
                        finalList.add(new DoubleSimpleCustomStr(null, vList.get(i)));
                    } else {
                        finalList.add(new DoubleSimpleCustomStr(vList.get(i), null));
                    }
                }

            }else {//多个横图
                for (int i = 0; i < v_size; i++) {
                    if (i == 0) {//第一二行
                        finalList.add(new DoubleSimpleCustomStr(hList.get(j), null));
                        j++;
                        finalList.add(new DoubleSimpleCustomStr(null, vList.get(i)));
                    } else {

                        if(j < h_size) {//判断横图
                            finalList.add(new DoubleSimpleCustomStr(hList.get(j++), null));
                            if(i + 1 < v_size){
                                finalList.add(new DoubleSimpleCustomStr(vList.get(i), vList.get(++i)));
                            }else {
                                finalList.add(new DoubleSimpleCustomStr(vList.get(i), null));
                            }
                        }else {
                            if(i+ 1 < v_size){
                                finalList.add(new DoubleSimpleCustomStr(vList.get(i), vList.get(++i)));
                            }else {
                                finalList.add(new DoubleSimpleCustomStr(vList.get(i), null));
                            }
                        }
                    }
                }
            }//end else 多个横图
        }// end else /竖图比较多

        j = 0;
        recyclerView.setAdapter(new MyAdapter(finalList, SimpleDetailActivity.this));

        final int f_size = finalList.size();
        for (int i = 0; i < f_size; i++) {
            DoubleSimpleCustomStr temp = finalList.get(i);
            if(temp.getData1() != null && temp.getData2() != null){
                orderList.add(temp.getData1());
                orderList.add(temp.getData2());
            }else if(temp.getData1() != null){
                orderList.add(temp.getData1());
            }else if(temp.getData2() != null){
                orderList.add(temp.getData2());
            }
        }

    }// end method generateData(String)

    @Override
    public void onRecyclerItemClick(View v, int position) {
//        T.s(context, "position:" + position);
    }

    class MyAdapter extends MyRecyclerView.Adapter<MyAdapter.MViewHolder> implements View.OnClickListener {

        private ArrayList<DoubleSimpleCustomStr> dataList;
        private OnRecyclerItemClickListener onRecyclerItemClickListener;

        public MyAdapter(ArrayList<DoubleSimpleCustomStr> list, OnRecyclerItemClickListener listener) {
            this.dataList = list;
            this.onRecyclerItemClickListener = listener;
        }

        @Override
        public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_custom_layout, parent, false);
            return new MViewHolder(itemView, onRecyclerItemClickListener);
        }

        @Override
        public void onBindViewHolder(MViewHolder holder, int position) {
            final DoubleSimpleCustomStr doubleSimpleCustom = dataList.get(position);
            if (position == 1) {
                holder.twoLayout.setVisibility(View.GONE);
                holder.styleLayout.setVisibility(View.VISIBLE);
                holder.contentImg2.setVisibility(View.VISIBLE);
                holder.contentImg1.setVisibility(View.GONE);
                holder.styleTxt.setText("风格：\n" + styleName);
                holder.nameTxt.setText(name);
                if (simpleOrCustom.equals("custom")) {
                    holder.dateTxt.setText(date.split(" ")[0]);
                    holder.moreTxt.setVisibility(View.GONE);
                    holder.styleTxt.setVisibility(View.GONE);
                } else {
                    holder.dateTxt.setVisibility(View.GONE);
                    holder.styleTxt.setVisibility(View.VISIBLE);
                    //查看更多
                    holder.moreTxt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (simpleOrCustom.equals("simple")) {
                                try {
                                    db.saveOrUpdate(new MoreTag("simpleMore", "load_more"));
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                                MPhotoSimpleFragment.refreshed = "true";
                            }
                            animFinish();
                        }
                    });
                }

                    holder.photographerStylistContainer.setVisibility(View.GONE);
                    holder.stylistContainer.setVisibility(View.GONE);

                //内容
                Picasso.with(context).load(doubleSimpleCustom.getData2() + DimenUtil.getVertical50Q()+ DimenUtil.getSuffixUTF8()).into(holder.contentImg2);
                holder.contentImg2.setTag(doubleSimpleCustom.getData2());
                holder.contentImg2.setOnClickListener(this);

            } else {

                if (position == 0 && simpleOrCustom.equals("custom")) {//显示新郎新娘
                    holder.twoLayout.setVisibility(View.VISIBLE);
                    holder.fTxt.setText(f);
                    holder.mTxt.setText(m);
                } else {
                    holder.twoLayout.setVisibility(View.GONE);
                }
                holder.styleLayout.setVisibility(View.GONE);

                if (doubleSimpleCustom.getData1() != null && doubleSimpleCustom.getData2() != null) {//显示两个
                    holder.contentImg1.setVisibility(View.VISIBLE);
                    holder.contentImg2.setVisibility(View.VISIBLE);
                    Picasso.with(context).load(doubleSimpleCustom.getData1() + DimenUtil.getVertical50Q()+ DimenUtil.getSuffixUTF8()).placeholder(R.drawable.loading).into(holder.contentImg1);
                    holder.contentImg1.setTag(doubleSimpleCustom.getData1());
                    Picasso.with(context).load(doubleSimpleCustom.getData2() + DimenUtil.getVertical50Q() + DimenUtil.getSuffixUTF8()).placeholder(R.drawable.loading).into(holder.contentImg2);
                    holder.contentImg2.setTag(doubleSimpleCustom.getData2());
                    holder.contentImg2.setOnClickListener(this);
                    holder.contentImg1.setOnClickListener(this);
                } else {//显示一个
                    holder.contentImg2.setVisibility(View.GONE);
                    holder.contentImg1.setVisibility(View.VISIBLE);

                    if (DimenUtil.isHorizontal(doubleSimpleCustom.getData1())) {
                        Picasso.with(context).load(doubleSimpleCustom.getData1() + DimenUtil.getHorizontal50Q() + DimenUtil.getSuffixUTF8()).placeholder(R.drawable.loading).into(holder.contentImg1);
                    } else {//只有一个， 又是竖图。
//                        Picasso.with(context).load(doubleSimpleCustom.getData1().getContentUrl() + "@" + DimenUtil.screenWidth + "w_" + DimenUtil.screenWidth * 3 + "h_40Q").into(holder.contentImg1);
                        Picasso.with(context).load(doubleSimpleCustom.getData1() + "@" + DimenUtil.screenWidth + "w_" + DimenUtil.screenWidth * 3 / 2 + "h_40Q" + DimenUtil.getSuffixUTF8()).placeholder(R.drawable.loading).into(holder.contentImg1);
                    }

                    holder.contentImg1.setTag(doubleSimpleCustom.getData1());
                    holder.contentImg1.setOnClickListener(this);
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return 0;
            } else {
                return 1;
            }
        }

        @Override
        public int getItemCount() {
            return dataList == null ? 0 : dataList.size();
        }

        @Override
        public void onClick(View view) {
            String url = view.getTag().toString();
            if (!TextUtils.isEmpty(url)) {
                Intent intent = new Intent(context, SimpleDetailPictureActivity.class);
                intent.putExtra("dataList", orderList);
                intent.putExtra("url", url);
                animStart(intent);
            }
        }

        public final class MViewHolder extends MyRecyclerView.ViewHolder implements View.OnClickListener {

            ImageView contentImg1;
            ImageView contentImg2;
            LinearLayout styleLayout;

            //摄影师造型师
            LinearLayout photographerStylistContainer;
            LinearLayout photographerContainer;
            LinearLayout stylistContainer;
            ImageView photographerImg;
            TextView photographerTxt;
            ImageView stylistImg;
            TextView stylistTxt;

            //新郎新娘名字
            LinearLayout twoLayout;
            TextView fTxt;
            TextView mTxt;

            //样式 风格
            TextView nameTxt;
            TextView dateTxt;
            TextView styleTxt;
            TextView moreTxt;


            OnRecyclerItemClickListener onRecyclerItemClickListener;

            public MViewHolder(View itemView, OnRecyclerItemClickListener listener) {
                super(itemView);

                contentImg1 = (ImageView) itemView.findViewById(R.id.img1);
                contentImg2 = (ImageView) itemView.findViewById(R.id.img2);

                styleLayout = (LinearLayout) itemView.findViewById(R.id.styleLayout);

                twoLayout = (LinearLayout) itemView.findViewById(R.id.two_person_layout);
                fTxt = (TextView) itemView.findViewById(R.id.two_person_f_txt);
                mTxt = (TextView) itemView.findViewById(R.id.two_person_m_txt);

                nameTxt = (TextView) itemView.findViewById(R.id.nameTxt);
                dateTxt = (TextView) itemView.findViewById(R.id.dateTxt);
                styleTxt = (TextView) itemView.findViewById(R.id.styleTxt);
                moreTxt = (TextView) itemView.findViewById(R.id.moreTxt);

                //
                photographerStylistContainer = (LinearLayout) itemView.findViewById(R.id.photographer_stylist_container);
                photographerContainer = (LinearLayout) itemView.findViewById(R.id.photographer_container);
                stylistContainer = (LinearLayout) itemView.findViewById(R.id.stylist_container);
                photographerImg = (ImageView) itemView.findViewById(R.id.photographer_head_img);
                photographerTxt = (TextView) itemView.findViewById(R.id.photographer_head_txt);
                stylistImg = (ImageView) itemView.findViewById(R.id.stylist_head_img);
                stylistTxt = (TextView) itemView.findViewById(R.id.stylist_head_txt);

                onRecyclerItemClickListener = listener;
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                onRecyclerItemClickListener.onRecyclerItemClick(v, getLayoutPosition());
            }
        }
    }

    @Override
    public boolean onKeydown() {
//        if (fab.getVisibility() != View.VISIBLE) {
//            FabTransformation.with(fab).setOverlay(overlay).transformFrom(sheet);
//            return false;
//        }
        animFinish();
        return true;
    }

    @OnClick(R.id.m_title_left_btn)
    public void onBack(View view) {
        animFinish();
    }

    //    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        System.gc();
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        setContentView(R.layout.activity_empty);
    }
}
