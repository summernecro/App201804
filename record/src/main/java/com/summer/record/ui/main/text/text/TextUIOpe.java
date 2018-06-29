package com.summer.record.ui.main.text.text;

//by summer on 2018-03-27.

import android.databinding.ViewDataBinding;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.lib.GlideApp;
import com.android.lib.base.adapter.AppsDataBindingAdapter;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.base.ope.BaseUIOpe;
import com.android.lib.bean.AppViewHolder;
import com.android.lib.util.ScreenUtil;
import com.android.lib.util.StringUtil;
import com.summer.record.BR;
import com.summer.record.R;
import com.summer.record.data.Record;
import com.summer.record.data.text.Text;
import com.summer.record.databinding.FragMainTextBinding;

import java.util.ArrayList;

public class TextUIOpe extends BaseUIOpe<FragMainTextBinding> {

    @Override
    public void initUI() {
        super.initUI();
//        GlideApp.with(getActivity()).asBitmap()
//                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530074750645&di=7207ffa5d0f3e91b572405a981ef3825&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201512%2F15%2F20151215200047_tkjR4.thumb.700_0.jpeg")
//                .into(getBind().ivBg);
        getBind().ivBg.setBackgroundColor(getActivity().getResources().getColor(R.color.color_grey_200));
    }

    public void initTexts(ArrayList<Record> texts, ViewListener listener){
        getBind().recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        getBind().recycle.setAdapter(new AppsDataBindingAdapter(getActivity(), R.layout.item_text_text, BR.item_text_text,texts,listener){
            public void onBindViewHolder(AppViewHolder holder, int position) {
                ViewDataBinding viewDataBinding = holder.viewDataBinding;
                viewDataBinding.getRoot().setTag(com.android.lib.R.id.data, this.list.get(position));
                viewDataBinding.getRoot().setTag(com.android.lib.R.id.position, Integer.valueOf(position));
                viewDataBinding.getRoot().setOnClickListener(this);
                viewDataBinding.getRoot().setOnLongClickListener(this);
                viewDataBinding.setVariable(this.vari, this.list.get(position));
                viewDataBinding.executePendingBindings();
            }
        });
    }

    public void updateTitle(Object o){
        TextView textView = getView().findViewById(R.id.tv_lab);
        textView.setText(StringUtil.getStr(o));
    }
}
