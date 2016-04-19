package com.chinajsbn.venus.ui.fragment.photography;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinajsbn.venus.R;
import com.chinajsbn.venus.net.HttpClients;
import com.chinajsbn.venus.net.bean.Base;
import com.chinajsbn.venus.net.bean.MoreTag;
import com.chinajsbn.venus.net.bean.Sence;
import com.chinajsbn.venus.net.bean.Simple;
import com.chinajsbn.venus.net.bean.SimpleStyles;
import com.chinajsbn.venus.net.bean.Style;
import com.chinajsbn.venus.ui.base.BaseFragment;
import com.chinajsbn.venus.ui.base.FragmentFeature;
import com.chinajsbn.venus.ui.base.OnRecyclerItemClickListener;
import com.chinajsbn.venus.ui.photography.SimpleDetailActivity;
import com.chinajsbn.venus.utils.DimenUtil;
import com.chinajsbn.venus.utils.NetworkUtil;
import com.chinajsbn.venus.utils.S;
import com.chinajsbn.venus.utils.T;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.squareup.picasso.Picasso;
import com.tool.widget.CircleImageView;
import com.tool.widget.dialog.MTDialog;
import com.tool.widget.dialog.OnClickListener;
import com.tool.widget.dialog.ViewHolder;
import com.tool.widget.fabtoolbar.FabToolbar;
import com.tool.widget.mt_listview.MasterListView;
import com.tool.widget.mt_listview.MyListView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * 样片 [used]
 * Created by 13510 on 2015/10/9.
 */
@FragmentFeature(layout = R.layout.fragment_photo_simple)
public class MPhotoSimpleFragment extends BaseFragment implements MasterListView.OnRefreshListener, OnClickListener {

    public static final String TAG = "MPhotoSimpleFragment";

    @ViewInject(R.id.fab_toolbar)
    private FabToolbar fabToolbar;

    @ViewInject(R.id.myListView)
    private MyListView listView;
    private MyAdapter adapter;

    @ViewInject(R.id.fab)
    private TextView fab;

    @ViewInject(R.id.sheet)
    private View sheet;

    @ViewInject(R.id.overlay)
    private View overlay;

    @ViewInject(R.id.listView)
    private ListView lv;

    //fab_tablayout
    @ViewInject(R.id.all_txt)
    private TextView allTxt;

    @ViewInject(R.id.style_txt)
    private TextView styleTxt;

    @ViewInject(R.id.place_txt)
    private TextView placeTxt;

    ///////////////////Dialog///////////////////
    private MTDialog networkDialog;//无网络提示

    private int tab_checked = 0; // 0 所有     1：风格      2：场景

    //Dialog
    private AlertDialog dialog;
    private MTDialog mtdialog;

    //---------------data---------------
    private List<Simple> dataList;


    //--------------page-----------------
    private int pageIndex = 1;
    private int pageSize = 10;
    private boolean isNextPage = false;//true:获取下一页数据    false:获取第一页数据
    private DataType dataType = DataType.FIRST_PAGE;
    //--------------page end-----------------


    //--------------refresh--------------------
    public static String refreshed = "false";
    public static int styleId = -1;
    public static int senceId = -1;

    private int simpleDetailStyleId = -1;
    //--------------refresh end----------------


    //------------------params------------------
    //客片、样片
    private String simpleOrCustom = "simple";
    //------------------params end------------------

    private List<Style> styleList;
    private List<Sence> senceList;


    //------------------Cache------------------
    private DbUtils db;

    //------------------Cache end---------------

    @Override
    public void initialize() {

        db = DbUtils.create(getActivity());
        db.configAllowTransaction(true);

        fabToolbar.setColor(getResources().getColor(R.color.blue_pressed));
        fabToolbar.attachToListView(listView);

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_not_network_layout, null);
        ViewHolder holder = new ViewHolder(view);
        mtdialog = new MTDialog.Builder(getActivity())
                .setContentHolder(holder)
                .setCancelable(false)
                .setGravity(MTDialog.Gravity.BOTTOM)
                .setOnClickListener(MPhotoSimpleFragment.this)
                .create();

        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_loading_layout, null);
        dialog = new AlertDialog.Builder(getActivity()).setView(dialogView).setCancelable(false).create();

        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(false);
        listView.setOnRefreshListener(MPhotoSimpleFragment.this, 0);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                if(NetworkUtil.hasConnection(getActivity())){
                    Simple simple = adapter.getItem(position);
                    //SimpleDetailResp
                    Intent intent = new Intent(getActivity(), SimpleDetailActivity.class);
                    intent.putExtra("simpleOrCustom", simpleOrCustom);
                    intent.putExtra("images", simple.getAppDetailImages());
                    intent.putExtra("date", simple.getCreateDate());
                    intent.putExtra("name", simple.getName());

                    String style = simple.getShootingStyle();
                    String _styleName = null;
                    if (style.contains(",")) {
                        String[] styles = style.split(",");
                        final int size = styleList == null ? 0 : styleList.size();
                        int _styleId = Integer.parseInt(styles[0]);
                        for (int i = 0; i < size; i++) {
                            if(styleList.get(i).getId() == _styleId ) {
                                _styleName = styleList.get(i).getName();
                                break;
                            }
                        }
                    }

                    intent.putExtra("style", _styleName);

//                    simpleDetailStyleId = (styles == null || styles.size() == 0) ? "-1" : styles.get(0).getShootingStyleId();

                    getActivity().startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.roll_up, R.anim.roll);
                }else{
                    handler.sendEmptyMessageDelayed(10, 10);
                    handler.sendEmptyMessageDelayed(11, 4000);
                }
            }
        });

        adapter = new MyAdapter();
        listView.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sheet.setVisibility(View.GONE);
                overlay.setVisibility(View.GONE);
                fabToolbar.hide();
                listView.smoothScrollToPosition(0);
                dataType = DataType.FIRST_PAGE;

                if(tab_checked == 0){//all
                    styleId = -1;
                    senceId = -1;
                    getFirstPages();
                }else if(tab_checked == 1){//style  XXA
                    styleId = ((StyleAdapter)lv.getAdapter()).getItem(position).getId();
                    if (NetworkUtil.hasConnection(getActivity())) {
                        dialog.show();
                        getPagesByStyle();
                    } else {
                        getPagesByStyleFromLocal();
                    }
                }else if(tab_checked == 2){//address
                    senceId = ((AddressAdapter)lv.getAdapter()).getItem(position).getId();
                    if (NetworkUtil.hasConnection(getActivity())) {
                        dialog.show();
                        getPagesByAddress();
                    } else {
                        getPagesByAddressFromLocal();
                    }
                }
            }
        });

        dialog.show();

        getFirstPages();

        if (NetworkUtil.hasConnection(getActivity())) {
            fabToolbar.setVisibility(View.VISIBLE);
            HttpClients.getInstance().styleList(1, 100, styleListCallback);
            HttpClients.getInstance().senceList(1, 100, senceListCallback);
        }else {
            fabToolbar.setVisibility(View.INVISIBLE);
            try {
                styleList = db.findAll(Style.class);
                senceList = db.findAll(Sence.class);
//                List<Address> addressList = db.findAll(Address.class);
//                addrStyle.setAddress(addressList);

            } catch (DbException e) {
                e.printStackTrace();
            }
        }
    }


    @OnClick(R.id.all_txt)
    public void allClick(View view){
        fabToolbar.hide();
        overlay.setVisibility(View.GONE);
        if(sheet.isShown()) sheet.setVisibility(View.GONE);
        listView.smoothScrollToPosition(0);
        styleId   = -1;
        senceId = -1;
        getFirstPages();
    }

    @OnClick(R.id.style_txt)
    public void styleClick(View view){
//            FabTransformation.with(fabToolbar).setOverlay(overlay).transformTo(sheet);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ABOVE, R.id.fab_toolbar);
        sheet.setLayoutParams(params);
        sheet.setVisibility(View.VISIBLE);
        overlay.setVisibility(View.VISIBLE);

        tab_checked = 1;

        ArrayList<Style> dataList = new ArrayList<>();
        dataList.add(new Style(-1, "全部风格"));
        dataList.addAll(styleList);
        lv.setAdapter(new StyleAdapter(dataList));
    }

    @OnClick(R.id.place_txt)
    public void placeClick(View view){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.ABOVE, R.id.fab_toolbar);
        sheet.setLayoutParams(params);
        sheet.setVisibility(View.VISIBLE);
        overlay.setVisibility(View.VISIBLE);

        tab_checked = 2;

        ArrayList<Sence> dataList = new ArrayList<>();
        dataList.add(new Sence(-1, "全部场景"));
        dataList.addAll(senceList);

        lv.setAdapter(new AddressAdapter(dataList));
    }

    /**
     * Call back
     */
    private Callback<Base<ArrayList<Simple>>> callback = new Callback<Base<ArrayList<Simple>>>() {
        @Override
        public void success(Base<ArrayList<Simple>> simpleResp, Response response) {

            listView.setPullRefreshEnable(true);
            listView.stopRefresh();
            if (dialog != null && dialog.isShowing()) dialog.dismiss();

            if (simpleResp.getCode() == 200) {
                if (dataType == DataType.FIRST_PAGE) {
                    dataList = simpleResp.getData();
                    if(dataList != null && dataList.size() > 0){

                        for (Simple s : dataList){
                            if(s.getShootingExteriors() != null && s.getShootingExteriors().size() > 0) {
                                s.setAddressId(s.getShootingExteriors().get(0).getShootingExteriorId());
                                s.setAddressName(s.getShootingExteriors().get(0).getShootingExteriorName());
                            }
                        }
                        try {
                            db.dropTable(Simple.class);
                            db.saveAll(dataList);
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                    }
                } else if (dataType == DataType.NEXT) {
                    dataList.addAll(simpleResp.getData());
//                    try {
//                        for (Simple s : simpleResp.getData()){
//                            if(s.getShootingStyles() != null && s.getShootingStyles().size() > 0){
//                                s.setStyleId(s.getShootingStyles().get(0).getShootingStyleId());
//                                s.setStyleName(s.getShootingStyles().get(0).getShootingStyleName());
//                            }
//                            if(s.getShootingExteriors() != null && s.getShootingExteriors().size() > 0) {
//                                s.setAddressId(s.getShootingExteriors().get(0).getShootingExteriorId());
//                                s.setAddressName(s.getShootingExteriors().get(0).getShootingExteriorName());
//                            }
//                        }
////                        db.saveAll(simpleResp.getData());
//                    } catch (DbException e) {
//                        e.printStackTrace();
//                    }
                }

                //
                adapter.notifyDataSetChanged();

                //是否有下一页
                if (simpleResp.getData() != null && simpleResp.getData().size() > 0) {
                    if (simpleResp.getData().size() < pageSize) {
                        listView.setPullLoadEnable(false);
                    } else {
                        listView.setPullLoadEnable(true);
                    }
                } else {
                    listView.setPullLoadEnable(false);
                }
            }
        }

        @Override
        public void failure(RetrofitError error) {
            listView.stopRefresh();
            if (dialog != null && dialog.isShowing()) dialog.dismiss();
        }
    };

    /**
     * 风格列表
     */
    private Callback<Base<ArrayList<Style>>> styleListCallback = new Callback<Base<ArrayList<Style>>>() {
        @Override
        public void success(Base<ArrayList<Style>> addrStyleResp, Response response) {
            if(addrStyleResp.getCode() == 200) {
                styleList = addrStyleResp.getData();
                try {
                    db.deleteAll(Style.class);
                    db.saveAll(styleList);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void failure(RetrofitError error) {}
    };

    /**
     * 场景列表
     */
    private Callback<Base<ArrayList<Sence>>> senceListCallback = new Callback<Base<ArrayList<Sence>>>() {
        @Override
        public void success(Base<ArrayList<Sence>> addrStyleResp, Response response) {
            if(addrStyleResp.getCode() == 200) {
                senceList = addrStyleResp.getData();
                try {
                    db.deleteAll(Sence.class);
                    db.saveAll(senceList);
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void failure(RetrofitError error) {}
    };


    private void getFirstPages() {
        isNextPage = false;
        pageIndex = 1;
        dataType = DataType.FIRST_PAGE;

        if (NetworkUtil.hasConnection(getActivity())) {
            listView.startRefresh();
            listView.saveRefreshStrTime();
            if (styleId == -1 && senceId == -1) { //全部数据
                HttpClients.getInstance().sampleList(0, 0, pageIndex, pageSize, callback);
            } else if(styleId != -1) {                   //通过style筛选
                HttpClients.getInstance().sampleList(0, styleId, pageIndex, pageSize, callback);
            }else if(senceId != -1) {                   //通过style筛选
                HttpClients.getInstance().sampleList(senceId, 0, pageIndex, pageSize, callback);
            }
        } else {//没有网络
            listView.setPullLoadEnable(false);
            listView.setPullRefreshEnable(false);
            try {
                dataList = db.findAll(Simple.class);
                if (dataList != null && dataList.size() > 0) {
                    //
                    adapter.notifyDataSetChanged();
                } else {
                    handler.sendEmptyMessageDelayed(10, 1000);
                    handler.sendEmptyMessageDelayed(11, 4000);
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
            if (dialog != null && dialog.isShowing()) dialog.dismiss();
        }
    }

    private void getPagesByStyle() {
        if (NetworkUtil.hasConnection(getActivity())) {
            pageIndex = 1;
            if (dataList != null && dataList.size() > 0) {
                listView.smoothScrollToPosition(0);
            }
            HttpClients.getInstance().sampleList(0, styleId, pageIndex, pageSize, callback);
        }else {
            getPagesByStyleFromLocal();
        }
    }

    private void getPagesByAddress() {
        if (NetworkUtil.hasConnection(getActivity())) {
            pageIndex = 1;
            if (dataList != null && dataList.size() > 0) {
                listView.smoothScrollToPosition(0);
            }
            HttpClients.getInstance().sampleList(senceId, 0, pageIndex, pageSize, callback);
        }else {
            getPagesByAddressFromLocal();
        }
    }

    /**
     * 本地数据库获取
     */
    private void getPagesByStyleFromLocal(){
        try {
            dataList = db.findAll(Selector.from(Simple.class).where("styleId", "=", styleId));
            adapter.notifyDataSetChanged();
            if(dataList == null || dataList.size() <= 0) {
                T.s(getActivity(), "离线模式，请连网");
                return;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 本地数据库获取
     */
    private void getPagesByAddressFromLocal(){
        try {
            dataList = db.findAll(Selector.from(Simple.class).where("senceId", "=", senceId));
            adapter.notifyDataSetChanged();
            if(dataList == null || dataList.size() <= 0) {
                T.s(getActivity(), "离线模式，请连网");
                return;
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10://show
                    mtdialog.show();
                    break;
                case 11://close
                    if(mtdialog != null && mtdialog.isShowing()) mtdialog.dismiss();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onRefresh(int id) {
        getFirstPages();
    }

    @Override
    public void onLoadMore(int id) {
        getNextPages();
    }

    private void getNextPages() {
        isNextPage = true;
        dataType = DataType.NEXT;
        pageIndex++;

        if (styleId == -1 && senceId == -1) { //全部数据
            HttpClients.getInstance().sampleList(senceId, styleId, pageIndex, pageSize, callback);
        } else if (styleId != -1) {                   //通过style筛选
            HttpClients.getInstance().sampleList(0, styleId, pageIndex, pageSize, callback);
        } else if (senceId != -1) {                   //通过address筛选
            HttpClients.getInstance().sampleList(senceId, 0, pageIndex, pageSize, callback);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        S.o(":::MPhotoSimpleFragment:::onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            List<MoreTag> moreTagList = db.findAll(Selector.from(MoreTag.class).where("name", "=", "simpleMore"));//new MoreTag("simpleMore", "load_more")
            if(moreTagList != null && moreTagList.size() > 0){
                MoreTag moreTag = moreTagList.get(0);
                if("load_more".equals(moreTag.getValu())){
                    styleId = simpleDetailStyleId;
                    getPagesByStyle();
                }
                moreTag.setValu("none");
                db.saveOrUpdate(moreTag);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        styleId = -1;
        super.onDestroy();
    }

    @Override
    public void onClick(MTDialog dialog, View view) {
        switch (view.getId()) {
            case R.id.network_confirm_button:
                dialog.dismiss();
                break;
        }
    }

    class MyAdapter extends BaseAdapter {

        public MyAdapter() {
        }

        @Override
        public int getCount() {
            return dataList == null ? 0 : dataList.size();
        }

        @Override
        public Simple getItem(int i) {
            return dataList.get(i - 1);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if (view == null) {
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_simple_layout, viewGroup, false);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }

//            if (dataList.get(position).getShootingStyles() != null && dataList.get(position).getShootingStyles().size() > 0)
//                holder.styleTxt.setText("风格: " + dataList.get(position).getShootingStyles().get(0).getShootingStyleName());
//            else
//                holder.styleTxt.setText("风格: " + dataList.get(position).getStyleName());

            holder.nameTxt.setText(dataList.get(position).getName());

            //加载摄影师
            if (dataList.get(position).getPhotographer() != null && !TextUtils.isEmpty(dataList.get(position).getPhotographer().getPhotoUrl())) {
                holder.headImgLayout.setVisibility(View.VISIBLE);
                Picasso.with(getActivity()).load(dataList.get(position).getPhotographer().getPhotoUrl()).error(getResources().getDrawable(R.mipmap.ic_launcher)).into(holder.photographerImg);
                //加造型师
                if (dataList.get(position).getStylist() != null && !TextUtils.isEmpty(dataList.get(position).getStylist().getPhotoUrl())) {
                    Picasso.with(getActivity()).load(dataList.get(position).getStylist().getPhotoUrl()).error(getResources().getDrawable(R.mipmap.ic_launcher)).into(holder.stylistImg);
                }
            } else {
                holder.headImgLayout.setVisibility(View.GONE);
            }

            //加载内容
            if (TextUtils.isEmpty(dataList.get(position).getCoverUrlApp())) {
                Picasso.with(getActivity()).load(R.mipmap.ic_launcher).into(holder.contentImg);
            } else {
                Picasso.with(getActivity()).load(dataList.get(position).getCoverUrlApp() + DimenUtil.getHorizontalListViewStringDimension(DimenUtil.screenWidth)).error(getResources().getDrawable(R.mipmap.ic_launcher)).resize(DimenUtil.screenWidth, DimenUtil.targetHeight).into(holder.contentImg);
            }

            return view;
        }

        class ViewHolder {
            TextView styleTxt;
            TextView nameTxt;
            ImageView contentImg;
            CircleImageView photographerImg;
            CircleImageView stylistImg;
            LinearLayout headImgLayout;
            OnRecyclerItemClickListener onRecyclerItemClickListener;

            public ViewHolder(View itemView) {
                contentImg = (ImageView) itemView.findViewById(R.id.item_simple_pic_img);
                photographerImg = (CircleImageView) itemView.findViewById(R.id.item_simple_photographer_head_img);
                stylistImg = (CircleImageView) itemView.findViewById(R.id.item_simple_stylist_head_img);
                styleTxt = (TextView) itemView.findViewById(R.id.item_simple_styles_txt);
                nameTxt = (TextView) itemView.findViewById(R.id.item_simple_name_txt);
                headImgLayout = (LinearLayout) itemView.findViewById(R.id.item_simple_headimg_layout);
            }
        }
    }

    class AddressAdapter extends BaseAdapter {

        List<Sence> data;

        public AddressAdapter(List<Sence> list) {
            data = list;
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Sence getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_single_textview_layout, viewGroup, false);
                holder.txt = (TextView) view.findViewById(R.id.item_single_text);
//                holder.layout = (MaterialRippleLayout) view.findViewById(R.id.item_single_text_ripple);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.txt.setText(data.get(i).getName());
            holder.txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sheet.setVisibility(View.GONE);
                    overlay.setVisibility(View.GONE);
                    fabToolbar.hide();
                    listView.smoothScrollToPosition(0);
                    dataType = DataType.FIRST_PAGE;
                    pageIndex = 1;
                    if (i == 0) {
                        senceId = -1;
                        getFirstPages();
                    } else {
                        isNextPage = false;
                        senceId = data.get(i).getId();
                        if(NetworkUtil.hasConnection(getActivity())){
                            dialog.show();
                            HttpClients.getInstance().sampleList(senceId, styleId, pageIndex, pageSize, callback);
                        }else {
                            getPagesByAddressFromLocal();
//                            getPagesByStyleFromLocal();
                        }
                    }
                }
            });
            return view;
        }

        class ViewHolder {
            TextView txt;
//            MaterialRippleLayout layout;
        }
    }

    class StyleAdapter extends BaseAdapter {

        List<Style> data;

        public StyleAdapter(List<Style> list) {
            data = list;
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.size();
        }

        @Override
        public Style getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            ViewHolder holder = null;
            if (view == null) {
                holder = new ViewHolder();
                view = LayoutInflater.from(getActivity()).inflate(R.layout.item_single_textview_layout, viewGroup, false);
                holder.txt = (TextView) view.findViewById(R.id.item_single_text);
//                holder.layout = (MaterialRippleLayout) view.findViewById(R.id.item_single_text_ripple);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            holder.txt.setText(data.get(i).getName());
            holder.txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sheet.setVisibility(View.GONE);
                    overlay.setVisibility(View.GONE);
                    fabToolbar.hide();
                    listView.smoothScrollToPosition(0);
                    isNextPage = false;
                    dataType = DataType.FIRST_PAGE;
                    if (i == 0) {
                        styleId = -1;
                        getFirstPages();
                    } else {
                        styleId = data.get(i).getId();
                        if(NetworkUtil.hasConnection(getActivity())){
                            dialog.show();
                            getPagesByStyle();
                        }else {
                            getPagesByStyleFromLocal();
                        }
                    }
                }
            });
            return view;
        }

        class ViewHolder {
            TextView txt;
//            MaterialRippleLayout layout;
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    enum DataType {
        FIRST_PAGE,//第一次，通过styleId第一次筛选
        NEXT,      //下一页
    }
}
