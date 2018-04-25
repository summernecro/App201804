package com.summer.record.ui.main.image.image;

//by summer on 2018-03-27.

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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
import com.summer.record.databinding.FragBaseBinding;
import com.summer.record.databinding.FragMainImageBinding;
import com.summer.record.databinding.ItemImageImageBinding;
import com.summer.record.databinding.ItemVideoVideoBinding;
import com.summer.record.ui.main.video.video.VideoItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ImageUIOpe extends BaseUIOpe<FragMainImageBinding> {


    public void loadImages(final ArrayList<Record> images, ViewListener listener){

        final RequestOptions requestOptions = new RequestOptions();
        requestOptions.encodeQuality(10).centerCrop().placeholder(Color.WHITE).skipMemoryCache(false).override(200,200);

        getBind().recycle.setLayoutManager(new GridLayoutManager(getActivity(),4));
        getBind().recycle.setAdapter(new AppsDataBindingAdapter(getActivity(), R.layout.item_image_image, BR.item_image_image,images,listener){
            @Override
            public void onBindViewHolder(@NonNull AppViewHolder holder, int position, @NonNull List<Object> payloads) {
                super.onBindViewHolder(holder, position, payloads);
                if(payloads==null||payloads.size()==0){
                    onBindViewHolder(holder,position);
                }else{
                    ItemImageImageBinding item = (ItemImageImageBinding) holder.viewDataBinding;
                    if(images.get(position).isDoing()){
                        item.ivUpload.setVisibility(View.VISIBLE);
                        LogUtil.E("doing");
                    }else{
                        item.ivUpload.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onBindViewHolder(AppViewHolder holder, int position) {
                ItemImageImageBinding item = (ItemImageImageBinding) holder.viewDataBinding;
                item.getRoot().setTag(com.android.lib.R.id.data, this.list.get(position));
                item.getRoot().setTag(com.android.lib.R.id.position, Integer.valueOf(position));
                item.setVariable(this.vari, this.list.get(position));
                item.executePendingBindings();
                GlideApp.with(context).asBitmap().apply(requestOptions).load(images.get(position).isNull()?R.color.white:images.get(position).getUri()).into(item.ivVideo);
                if(!images.get(position).isNull()){
                    item.getRoot().setOnClickListener(this);
                    item.getRoot().setClickable(true);
                    item.getRoot().setAlpha(1f);
                    item.bg.setBackgroundColor(Color.WHITE);
                    switch (images.get(position).getStatus()){
                        case Record.本地无服务器有:
                            item.getRoot().setAlpha(0.3f);
                            break;
                        case Record.本地有服务器无:
                            item.bg.setBackgroundColor(Color.RED);
                            break;
                        case Record.本地有服务器有:

                            break;
                        default:
                            break;
                    }
                }else{
                    item.getRoot().setBackgroundColor(Color.WHITE);
                    item.bg.setBackgroundColor(Color.WHITE);
                    item.getRoot().setClickable(false);
                }

                if(images.get(position).isDoing()){
                    item.ivUpload.setVisibility(View.VISIBLE);
                    LogUtil.E("doing");
                }else{
                    item.ivUpload.setVisibility(View.GONE);
                }
            }
        });
        getBind().recycle.addItemDecoration(new VideoItemDecoration(images));
    }

    public void updateTitle(Object o){
        TextView textView = getView().findViewById(R.id.tv_lab);
        textView.setText(StringUtil.getStr(o));
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
}
