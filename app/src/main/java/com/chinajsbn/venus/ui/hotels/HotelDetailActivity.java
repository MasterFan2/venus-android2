package com.chinajsbn.venus.ui.hotels;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinajsbn.venus.R;
import com.chinajsbn.venus.net.HttpClients;
import com.chinajsbn.venus.net.bean.Banquet;
import com.chinajsbn.venus.net.bean.Base;
import com.chinajsbn.venus.net.bean.Hotel;
import com.chinajsbn.venus.ui.base.ActivityFeature;
import com.chinajsbn.venus.ui.base.MBaseFragmentActivity;
import com.chinajsbn.venus.utils.DimenUtil;
import com.chinajsbn.venus.utils.T;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;
import com.tool.widget.MasterTitleView;
import com.tool.widget.MyGridView;
import com.tool.widget.MyListView;
import com.tool.widget.indicator.CircleIndicator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 酒店详情界面
 */
@ActivityFeature(layout = R.layout.activity_hotel_detail)
public class HotelDetailActivity extends MBaseFragmentActivity implements View.OnClickListener {

    public static final String TAG = "HotelDetailActivity";

    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;

    @ViewInject(R.id.viewPager_indicator)
    private CircleIndicator indicator;

    @ViewInject(R.id.titleView)
    private MasterTitleView titleView;

    //------------------------------------------------
    @ViewInject(R.id.hotel_detail_type_txt)
    private TextView typeTxt;

    @ViewInject(R.id.hotel_detail_banquet_txt)
    private TextView banquetTxt;

    @ViewInject(R.id.hotel_detail_price_txt)
    private TextView priceTxt;

    @ViewInject(R.id.hotel_detail_capacity_txt)
    private TextView capacityTxt;

    @ViewInject(R.id.hotel_detail_addr_txt)
    private TextView addrTxt;

    @ViewInject(R.id.hotel_detail_introduction_txt)
    private TextView introductionTxt;

    @ViewInject(R.id.hotel_detail_listView)
    private MyListView listView;//套餐

    @ViewInject(R.id.hotel_active_listView)
    private MyListView activelistView;//活动

    @ViewInject(R.id.hotel_detail_gridView)
    private MyGridView gridView;

    @ViewInject(R.id.hotel_active_layout)
    private LinearLayout activeLayout;

    private ArrayList<Banquet> banquets;

    private int detailId;//酒店iD

    @Override
    public void initialize() {
        detailId = getIntent().getIntExtra("detailId", -1);
        titleView.setTitleText(getIntent().getStringExtra("name"));

        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
//        recyclerView.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        recyclerView.setLayoutManager(layoutManager);


        HttpClients.getInstance().hotelDetail(detailId, detailCallback);

        //厅 click events
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(context, BanquetDetailActivity.class);
                intent.putExtra("detailId", detailId);
                intent.putExtra("args", banquets.get(i));
                animStart(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                JSONObject combo = (JSONObject) adapterView.getAdapter().getItem(i);
                try {
                    JSONArray arr = combo.getJSONArray("dishesList");
                    if (arr == null || arr.length() <= 0) {
                        T.s(context, "没有详细菜单可供查看");
                        return;
                    }
                    Intent intent = new Intent(context, ComboActivity.class);
                    intent.putExtra("combo", combo.toString());
                    animStart(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @OnClick(R.id.m_title_left_btn)
    public void back(View view) {
        animFinish();
    }

    @Override
    public void onClick(View view) {

    }

    class MAdapter extends PagerAdapter {
        private String[] detailImgs;

        public MAdapter(String[] d) {
            this.detailImgs = d;
        }

        @Override
        public int getCount() {
            return detailImgs == null ? 0 : detailImgs.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView img = (ImageView) LayoutInflater.from(context).inflate(R.layout.item_single_img_hotel_head_layout, null);
            String url = detailImgs[position];
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

    private  Callback<Base<ArrayList<Hotel>>> detailCallback = new Callback<Base<ArrayList<Hotel>>>() {
        @Override
        public void success(Base<ArrayList<Hotel>> hotels, Response response) {
            String[] images = hotels.getData().get(0).getAppDetailImages().replace("[", "").replace("]", "").replace("\"", "").split(",");
            viewPager.setAdapter(new MAdapter(images));
            indicator.setViewPager(viewPager);

            Hotel detail = hotels.getData().get(0);
            banquets = detail.getBanquetHall();


            String guid = "规格类型：" + detail.getTypeName();
            SpannableStringBuilder guidStyle = new SpannableStringBuilder(guid);
            guidStyle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.pink)), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            guidStyle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.subtitle_text_color)), 5, guid.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            typeTxt.setText(guidStyle);

            String priceStr = "价        格：" + detail.getLowestConsumption() + " - " + detail.getHighestConsumption() + "元/桌";
            SpannableStringBuilder priceStyle = new SpannableStringBuilder(priceStr);
            priceStyle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.pink)), 0, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            priceStyle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.subtitle_text_color)), 11, priceStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            priceTxt.setText(priceStyle);

            String tableStr = "容  桌  数：" + detail.getCapacityPerTable() + "桌";
            SpannableStringBuilder tableStyle = new SpannableStringBuilder(tableStr);
            tableStyle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.pink)), 0, 8, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            tableStyle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.subtitle_text_color)), 8, tableStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            capacityTxt.setText(tableStyle);

            String addrStr = "地        址：" + detail.getAddress();
            SpannableStringBuilder addrStyle = new SpannableStringBuilder(addrStr);
            addrStyle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.pink)), 0, 11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            addrStyle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.subtitle_text_color)), 11, addrStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            addrTxt.setText(addrStyle);

            String descStr = "酒店详情：" + detail.getIntroduction();
            SpannableStringBuilder descStyle = new SpannableStringBuilder(descStr);
            descStyle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.pink)), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            descStyle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.subtitle_text_color)), 5, descStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            introductionTxt.setText(descStyle);

            String banquetStr = "场厅数量：" + detail.getBanquetHall().size() + "个专用宴会厅";
            SpannableStringBuilder banquetStyle = new SpannableStringBuilder(banquetStr);
            banquetStyle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.pink)), 0, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            banquetStyle.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.subtitle_text_color)), 5, banquetStr.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            banquetTxt.setText(banquetStyle);

            try {
                JSONArray jsonArray = new JSONArray(detail.getSetMealDetail());
                //是否有菜品
                listView.setAdapter(new ComboAdapter(jsonArray));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            //宴会厅
            gridView.setAdapter(new MyGridViewAdapter(detail.getBanquetHall()));
//
            if(detail.getLableDetail() != null && detail.getLableDetail().length() > 0){
                //活动
                try {
                    JSONArray jsonArray = new JSONArray(detail.getLableDetail());
                    //是否有菜品
                    activelistView.setAdapter(new ActiveAdapter(jsonArray));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                activeLayout.setVisibility(View.GONE);
            }
        }

        @Override
        public void failure(RetrofitError error) {

        }
    };

    class MyGridViewAdapter extends BaseAdapter {

        private ArrayList<Banquet> banquetHallList;

        public MyGridViewAdapter(ArrayList<Banquet> banquet) {
            this.banquetHallList = banquet;
        }

        @Override
        public int getCount() {
            return banquetHallList == null ? 0 : banquetHallList.size();
        }

        @Override
        public Banquet getItem(int i) {
            return banquetHallList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final MViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_banquet_layout, viewGroup, false);
                holder = new MViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (MViewHolder) view.getTag();
            }

            final Banquet banquet = banquetHallList.get(i);
            if (banquet.getCoverUrlApp() != null) {
                String dimen = DimenUtil.getVertical();
                Picasso.with(context).load(banquet.getCoverUrlApp() + "@" + (DimenUtil.screenWidth / 2 - 42) + "w_" + (DimenUtil.screenWidth * 2 / 3 - 42) + "h_60q").into(holder.contentImg);
            }
            holder.nameTxt.setText(banquet.getName());
            holder.tablesTxt.setText("桌数:" + banquet.getMaxTableNum() + "桌");
            holder.priceTxt.setText("低消:" + banquet.getLowestConsumption() + "元/桌");

            holder.pillarTxt.setText("柱子:" + (banquet.getPillerNum().equals("1") ? "有" : "无"));
            holder.areaTxt.setText("面积:" + banquet.getArea() + "m²");

            holder.heightTxt.setText("层高:" + banquet.getHeight() + "m");
            holder.shapTxt.setText("形状:" + banquet.getShape());

            return view;
        }

        public class MViewHolder {
            public ImageView contentImg;
            public TextView nameTxt;

            public TextView tablesTxt;
            public TextView priceTxt;

            public TextView pillarTxt;
            public TextView areaTxt;

            public TextView heightTxt;
            public TextView shapTxt;

            public MViewHolder(View view) {
                contentImg = (ImageView) view.findViewById(R.id.item_banquet_content_img);
                nameTxt = (TextView) view.findViewById(R.id.item_banquet_name_txt);

                tablesTxt = (TextView) view.findViewById(R.id.item_banquet_tables_txt);
                priceTxt = (TextView) view.findViewById(R.id.item_banquet_price_txt);

                pillarTxt = (TextView) view.findViewById(R.id.item_banquet_pillar_txt);
                areaTxt = (TextView) view.findViewById(R.id.item_banquet_area_txt);

                heightTxt = (TextView) view.findViewById(R.id.item_banquet_height_txt);
                shapTxt = (TextView) view.findViewById(R.id.item_banquet_shap_txt);
            }
        }
    }


    public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MViewHolder> {

        private ArrayList<Banquet> banquetHallList;

        public MyRecyclerAdapter(ArrayList<Banquet> banquet) {
            this.banquetHallList = banquet;
        }

        @Override
        public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_banquet_layout, parent, false);
            return new MViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MViewHolder holder, final int position) {
            final Banquet banquet = banquetHallList.get(position);
            holder.nameTxt.setText(banquet.getName());
            holder.tablesTxt.setText("桌数:" + banquet.getCapacity());
            holder.priceTxt.setText("低消:" + banquet.getLowestConsumption());
        }

        @Override
        public int getItemCount() {
            return banquetHallList == null ? 0 : banquetHallList.size();
        }

        //自定义的ViewHolder，持有每个Item的的所有界面元素
        public class MViewHolder extends RecyclerView.ViewHolder {
            public ImageView contentImg;
            public TextView nameTxt;
            public TextView tablesTxt;
            public TextView priceTxt;

            public MViewHolder(View view) {
                super(view);
                contentImg = (ImageView) view.findViewById(R.id.item_banquet_content_img);
                nameTxt = (TextView) view.findViewById(R.id.item_banquet_name_txt);
                tablesTxt = (TextView) view.findViewById(R.id.item_banquet_tables_txt);
                priceTxt = (TextView) view.findViewById(R.id.item_banquet_price_txt);
            }
        }
    }

    /**
     * List view adapter
     */
    class ActiveAdapter extends BaseAdapter {

        JSONArray labels;

        public ActiveAdapter(JSONArray jsonArray) {
            this.labels = jsonArray;
        }

        @Override
        public int getCount() {
            return labels == null ? 0 : labels.length();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = LayoutInflater.from(context).inflate(R.layout.item_hotel_labelactive, viewGroup, false);
                holder.titleTxt = (TextView) view.findViewById(R.id.item_hotel_label_title_txt);
                holder.descTxt = (TextView) view.findViewById(R.id.item_hotel_label_desc_txt);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            try {
                JSONObject obj = labels.getJSONObject(i);
                holder.titleTxt.setText(obj.getString("name"));
                holder.descTxt.setText(Html.fromHtml(obj.getString("description")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return view;
        }

        class ViewHolder {
            TextView titleTxt;
            TextView descTxt;
        }
    }

    /**
     * List view adapter
     */
    class ComboAdapter extends BaseAdapter {
        private JSONArray dishArray;

        public ComboAdapter(JSONArray jsonArray) {
            this.dishArray = jsonArray;
        }

        @Override
        public int getCount() {
            return dishArray == null ? 0 : dishArray.length();
        }

        @Override
        public JSONObject getItem(int i) {
            try {
                return dishArray.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            final ViewHolder holder;

            if (view == null) {
                holder = new ViewHolder();
                view = LayoutInflater.from(context).inflate(R.layout.item_combo_layout, viewGroup, false);
                holder.txt = (TextView) view.findViewById(R.id.item_combo_txt);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            try {
                JSONObject combo = dishArray.getJSONObject(i);
                String content = combo.getString("name") + "(" + combo.getString("price") + "元/桌)";
                SpannableStringBuilder commboStr = new SpannableStringBuilder(content);
                commboStr.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.home_title_color)),  combo.getString("name").length(), content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.txt.setText(commboStr);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return view;
        }

        class ViewHolder {
            TextView txt;
        }
    }

    @Override
    public boolean onKeydown() {
        animFinish();
        return true;
    }
}
