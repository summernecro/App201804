package com.summer.record.ui.main.record.tip;


import android.view.View;

import com.android.lib.base.adapter.AppsDataBindingAdapter;
import com.android.lib.base.ope.BaseUIOpe;
import com.android.lib.bean.AppViewHolder;
import com.android.lib.util.LoadUtil;
import com.summer.record.BR;
import com.summer.record.R;
import com.summer.record.data.Folder;
import com.summer.record.data.Tiplab;
import com.summer.record.databinding.FragRecordTipBinding;
import com.summer.record.databinding.ItemRecordFolderBinding;
import com.summer.record.databinding.ItemRecordTiplabBinding;
import com.summer.record.ui.view.UpdateIndicator;

import java.util.ArrayList;

import androidx.recyclerview.widget.GridLayoutManager;
import lombok.Getter;

public class TipUIOpe extends BaseUIOpe<FragRecordTipBinding> {

    @Getter
    UpdateIndicator updateIndicator = new UpdateIndicator();

    @Override
    public void initUI() {
        super.initUI();
    }

    /**
     * 按标签排序
     * @param folders
     */
    public void loadRecordByFolder(final ArrayList<Tiplab> folders, View.OnClickListener listener){
        getBind().recycle.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        getBind().recycle.setAdapter(new AppsDataBindingAdapter(getActivity(), R.layout.item_record_tiplab, BR.itemRecordTiplab,folders,listener){
            @Override
            public void onBindViewHolder(AppViewHolder holder, int position) {
                super.onBindViewHolder(holder, position);
                ItemRecordTiplabBinding binding = (ItemRecordTiplabBinding) holder.viewDataBinding;
                binding.tvName.setText(folders.get(position).getContent());
            }
        });
    }
}
