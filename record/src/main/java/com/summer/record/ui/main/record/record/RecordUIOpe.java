package com.summer.record.ui.main.record.record;

//by summer on 2018-03-27.

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.lib.GlideApp;
import com.android.lib.base.adapter.AppsDataBindingAdapter;
import com.android.lib.base.ope.BaseUIOpe;
import com.android.lib.bean.AppViewHolder;
import com.android.lib.util.LogUtil;
import com.android.lib.util.ToastUtil;
import com.android.lib.util.system.HandleUtil;
import com.bumptech.glide.request.RequestOptions;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.summer.record.BR;
import com.summer.record.R;
import com.summer.record.data.Folder;
import com.summer.record.data.Record;
import com.summer.record.data.RecordURL;
import com.summer.record.databinding.FragMainImageBinding;
import com.summer.record.databinding.ItemImageImageBinding;
import com.summer.record.databinding.ItemRecordFolderBinding;
import com.summer.record.ui.main.record.image.NetAdapter;
import com.summer.record.ui.main.record.records.RecordsFrag;

import java.io.File;
import java.util.ArrayList;

public class RecordUIOpe extends BaseUIOpe<FragMainImageBinding> {

    public void loadImages(final ArrayList<Record> images, View.OnClickListener listener) {

        if (getBind().recycle.getAdapter() == null) {
            final RequestOptions requestOptions = new RequestOptions();
            requestOptions.encodeQuality(10).centerCrop().placeholder(R.color.color_TRANSPARENT).skipMemoryCache(false).override(100, 100);
            getBind().recycle.setLayoutManager(new GridLayoutManager(getActivity(), RecordValue.num));
            getBind().recycle.setAdapter(new AppsDataBindingAdapter(getActivity(), R.layout.item_image_image, BR.item_image_image, images, listener) {

                @Override
                public int getItemViewType(int position) {
                    return images.get(position).isNull() ? 0 : 1;
                }

                @Override
                public AppViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    switch (viewType) {
                        case 1:
                            return new AppViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_image_image, parent, false));
                    }
                    return new AppViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_null, parent, false));
                }

                @Override
                public void onBindViewHolder(AppViewHolder holder, int position) {
                    switch (getItemViewType(position)) {
                        case 1:
                            ItemImageImageBinding item = (ItemImageImageBinding) holder.viewDataBinding;
                            item.getRoot().setTag(com.android.lib.R.id.data, this.list.get(position));
                            item.getRoot().setTag(com.android.lib.R.id.position, Integer.valueOf(position));

//                            File file = new File(images.get(position).getLocpath());
//                            if(file.exists()){
//                                GlideApp.with(context).asBitmap().centerCrop().load(images.get(position).getLocpath()).into(item.ivVideo);
//                            }else{
//                                GlideApp.with(context).asBitmap().centerCrop().load(RecordURL.getNetUrl(images.get(position).getNetpath())).into(item.ivVideo);
//                            }
                            //GlideApp.with(context).asBitmap().centerCrop().load(images.get(position).getLocpath()).into(item.ivVideo);
                            GlideApp.with(context).asBitmap().centerCrop().load(images.get(position).getUri()).into(item.ivVideo);

                            item.getRoot().setOnClickListener(this);
                            item.getRoot().setClickable(true);
                            item.getRoot().setAlpha(1f);
                            switch (images.get(position).getStatus()) {
                                case Record.本地无服务器有:
                                    item.getRoot().setAlpha(0.3f);
                                    break;
                                case Record.本地有服务器无:
                                    item.bg.setBackgroundColor(Color.WHITE);
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
            final RecordFrag recordFrag = (RecordFrag) getFrag();
            getBind().recycle.addItemDecoration(new VideoItemDecoration(getActivity(), images, RecordValue.num));
            getBind().recycle.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    int i= getBind().recycle.getChildAdapterPosition(recyclerView.getChildAt(0));
                    if(i>=0){
                        //recordFrag.getPV().getRecordsFrag().getPU().updateTitle(images.get(i).getDateStr());
                    }
                }
            });
        } else {
            getBind().recycle.getAdapter().notifyDataSetChanged();
        }
    }


    public void notifyDataSetChangedRecylce(){
        if (getBind().recycle.getAdapter() != null) {
            getBind().recycle.getAdapter().notifyDataSetChanged();
        }
    }

    public void scrollToPos(ArrayList<Record> records, Record record) {
        LogUtil.E(record.getId());
        GridLayoutManager gridLayoutManager = (GridLayoutManager) getBind().recycle.getLayoutManager();
        gridLayoutManager.scrollToPositionWithOffset(record.getId(), 0);
        for (int i = 0; i < records.size(); i++) {
            record.setIsDoing(0);
        }
        records.get(record.getId()).setIsDoing(1);
        getBind().recycle.getAdapter().notifyItemChanged(record.getId(), record);
    }


    public void scrollToPos(final Record record) {
        LogUtil.E(record.getId());
        final GridLayoutManager gridLayoutManager = (GridLayoutManager) getBind().recycle.getLayoutManager();
        gridLayoutManager.scrollToPositionWithOffset(record.getId(), 0);
        View view = gridLayoutManager.findViewByPosition(record.getId());
        if(view!=null){
            ViewAnimator.animate(view).scale(0.3f,1f).duration(1000).start().onStop(new AnimationListener.Stop() {
                @Override
                public void onStop() {
                    getBind().recycle.getAdapter().notifyItemChanged(record.getId(), record);
                }
            });
        }
    }

    public void scrollToPosBefore(final Record record) {
        final GridLayoutManager gridLayoutManager = (GridLayoutManager) getBind().recycle.getLayoutManager();
        gridLayoutManager.scrollToPositionWithOffset(record.getId(), 0);
        View view = gridLayoutManager.findViewByPosition(record.getId());
        if(view!=null){
            ViewAnimator.animate(view).alpha(0,1,0,1).duration(1000).start();
        }

    }

    public void setTitle(){

    }
}
