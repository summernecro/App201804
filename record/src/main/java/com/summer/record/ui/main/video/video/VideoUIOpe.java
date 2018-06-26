package com.summer.record.ui.main.video.video;

//by summer on 2018-03-27.

import android.databinding.DataBindingUtil;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.lib.GlideApp;
import com.android.lib.base.adapter.AppsDataBindingAdapter;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.base.ope.BaseUIOpe;
import com.android.lib.bean.AppViewHolder;
import com.android.lib.util.LogUtil;
import com.android.lib.util.ScreenUtil;
import com.android.lib.util.StringUtil;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.summer.record.BR;
import com.summer.record.R;
import com.summer.record.data.Record;
import com.summer.record.databinding.FragMainVideoBinding;
import com.summer.record.databinding.ItemVideoVideoBinding;
import com.summer.record.ui.main.image.image.ImageUIOpe;

import java.util.ArrayList;
import java.util.List;

public class VideoUIOpe extends BaseUIOpe<FragMainVideoBinding> {

    ImageUIOpe imageUIOpe;

    @Override
    public void initUI() {
        super.initUI();
        imageUIOpe = new ImageUIOpe();
        imageUIOpe.init(getActivity());
        ((ViewGroup)(getBind().getRoot())).addView(imageUIOpe.getBind().getRoot());
    }

    public void loadVideos(final ArrayList<Record> videos, ViewListener listener){

        final RequestOptions requestOptions = new RequestOptions();
        requestOptions.encodeQuality(10).centerCrop().placeholder(Color.WHITE).skipMemoryCache(false).override(200,200);

        getBind().recycle.setLayoutManager(new GridLayoutManager(getActivity(),4));
        getBind().recycle.setAdapter(new AppsDataBindingAdapter(getActivity(), R.layout.item_video_video, BR.item_video_video,videos,listener){

            @Override
            public int getItemViewType(int position) {
                return videos.get(position).isNull()?0:1;
            }

            @Override
            public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                switch (viewType){
                    case 1:
                        return new AppViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_video_video, parent, false));
                }
                return new AppViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_null, parent, false));
            }

            @Override
            public void onBindViewHolder(AppViewHolder holder, int position) {
                LogUtil.E(position+":"+getItemViewType(position));
                switch (getItemViewType(position)){
                    case 1:
                        ItemVideoVideoBinding itemVideoVideoBinding = (ItemVideoVideoBinding) holder.viewDataBinding;
                        itemVideoVideoBinding.getRoot().setTag(com.android.lib.R.id.data, this.list.get(position));
                        itemVideoVideoBinding.getRoot().setTag(com.android.lib.R.id.position, Integer.valueOf(position));
                        itemVideoVideoBinding.setVariable(this.vari, this.list.get(position));
                        itemVideoVideoBinding.executePendingBindings();
                        GlideApp.with(context).asBitmap().apply(requestOptions).load(videos.get(position).getUri()).into(itemVideoVideoBinding.ivVideo);
                        itemVideoVideoBinding.getRoot().setOnClickListener(this);
                        itemVideoVideoBinding.getRoot().setClickable(true);
                        itemVideoVideoBinding.getRoot().setAlpha(1f);
                        itemVideoVideoBinding.bg.setBackgroundColor(Color.WHITE);
                        switch (videos.get(position).getStatus()){
                            case Record.本地无服务器有:
                                itemVideoVideoBinding.getRoot().setAlpha(0.3f);
                                break;
                            case Record.本地有服务器无:
                                itemVideoVideoBinding.bg.setBackgroundColor(Color.WHITE);
                                break;
                            case Record.本地有服务器有:

                                break;
                            default:
                                break;
                        }
                        break;
                        default:

                            break;
                }
            }
        });
        getBind().recycle.addItemDecoration(new VideoItemDecoration(getActivity(),videos));
    }


    public void scrollToPos(ArrayList<Record> records,Record record){
        LogUtil.E(record.getId());
        GridLayoutManager gridLayoutManager = (GridLayoutManager) getBind().recycle.getLayoutManager();
        gridLayoutManager.scrollToPositionWithOffset(record.getId(),0);
        for(int i=0;i<records.size();i++){
            record.setIsDoing(0);
        }
        records.get(record.getId()).setIsDoing(1);
        getBind().recycle.getAdapter().notifyItemChanged(record.getId(),record);
    }

    public void scrollToPos(Record record){
        LogUtil.E(record.getId());
        GridLayoutManager gridLayoutManager = (GridLayoutManager) getBind().recycle.getLayoutManager();
        gridLayoutManager.scrollToPositionWithOffset(record.getId(),0);
        getBind().recycle.getAdapter().notifyItemChanged(record.getId(),record);
    }
}
