package com.chinajsbn.venus.ui.fragment.detail;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chinajsbn.venus.R;
import com.chinajsbn.venus.net.bean.DetailSubImg;
import com.chinajsbn.venus.ui.base.BaseFragment;
import com.chinajsbn.venus.ui.base.FragmentFeature;
import com.chinajsbn.venus.ui.base.OnRecyclerItemClickListener;
import com.chinajsbn.venus.utils.DimenUtil;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by master fan on 15/7/17
 * Description :tag detail
 */
@FragmentFeature(layout = R.layout.fragment_suit_tag_detail)
public class PhotoSuitTagDetailFragment extends BaseFragment implements OnRecyclerItemClickListener {

    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;

    @Override
    public void initialize() {

        //固定写法。
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        String imgs =  getArguments().getString("detailArgs");
        imgs = imgs.replace("[", "").replace("]", "").replace("\"", "").replace("\\", "");
        String imgArr[] = imgs.split(",");

        recyclerView.setAdapter(new MyAdapter(imgArr, PhotoSuitTagDetailFragment.this));
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void onRecyclerItemClick(View v, int position) {
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MViewHolder> {

        private String imgArr[];
        private OnRecyclerItemClickListener onRecyclerItemClickListener;

        public MyAdapter(String list[], OnRecyclerItemClickListener listener){
            this.imgArr = list;
            this.onRecyclerItemClickListener = listener;
        }

        @Override
        public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ImageView itemView = (ImageView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_img_suit_detail, parent, false);
            return new MViewHolder(itemView, onRecyclerItemClickListener);
        }

        @Override
        public void onBindViewHolder(MViewHolder holder, int position) {
            if(TextUtils.isEmpty(imgArr[position])){
                Picasso.with(getActivity()).load(R.mipmap.ic_launcher).into(holder.contentImg);
            }else{
                Picasso.with(getActivity()).load(imgArr[position]+ DimenUtil.getHorizontal() + DimenUtil.getSuffixUTF8()).error(getResources().getDrawable(R.mipmap.ic_launcher)).placeholder(R.drawable.loading).into(holder.contentImg);
            }
        }

        @Override
        public int getItemCount() {
            return imgArr == null ? 0 : imgArr.length;
        }

        public final class MViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ImageView contentImg;
            OnRecyclerItemClickListener onRecyclerItemClickListener;

            public MViewHolder(ImageView imgView, OnRecyclerItemClickListener listener) {
                super(imgView);
                contentImg = imgView;
//                contentImg = (ImageView) itemView.findViewById(R.id.item_simple_pic_img);
                onRecyclerItemClickListener = listener;
                contentImg.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                 onRecyclerItemClickListener.onRecyclerItemClick(v, getLayoutPosition());
            }
        }
    }
}
