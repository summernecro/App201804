package com.summer.record.ui.main.record.folder;

//by summer on 2018-09-25.

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.android.lib.base.adapter.AppsDataBindingAdapter;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.base.ope.BaseUIOpe;
import com.android.lib.bean.AppViewHolder;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.summer.record.BR;
import com.summer.record.R;
import com.summer.record.data.Folder;
import com.summer.record.databinding.FragRecordFolderBinding;
import com.summer.record.databinding.ItemRecordFolderBinding;
import com.summer.record.tool.TitleUtil;

import java.util.ArrayList;

public class FolderUIOpe extends BaseUIOpe<FragRecordFolderBinding> {

    /**
     * 按文件夹排序
     * @param folders
     */
    public void loadRecordByFolder(final ArrayList<Folder> folders,ViewListener listener){
        getBind().recycle.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        getBind().recycle.setAdapter(new AppsDataBindingAdapter(getActivity(), R.layout.item_record_folder, BR.item_record_folder,folders,listener){
            @Override
            public void onBindViewHolder(AppViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ItemRecordFolderBinding binding = (ItemRecordFolderBinding) holder.viewDataBinding;
                binding.tvName.setText(folders.get(position).getName());
                binding.count.setText(folders.get(position).getRecords().size()+"");
            }
        });
    }
}
