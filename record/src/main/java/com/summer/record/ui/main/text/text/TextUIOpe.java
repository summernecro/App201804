package com.summer.record.ui.main.text.text;

//by summer on 2018-03-27.

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.lib.base.adapter.AppsDataBindingAdapter;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.base.ope.BaseUIOpe;
import com.android.lib.util.ScreenUtil;
import com.android.lib.util.StringUtil;
import com.summer.record.BR;
import com.summer.record.R;
import com.summer.record.data.Record;
import com.summer.record.data.text.Text;
import com.summer.record.databinding.FragMainTextBinding;

import java.util.ArrayList;

public class TextUIOpe extends BaseUIOpe<FragMainTextBinding> {

    public void initTexts(ArrayList<Record> texts, ViewListener listener){
        getBind().recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        getBind().recycle.setAdapter(new AppsDataBindingAdapter(getActivity(), R.layout.item_text_text, BR.item_text_text,texts,listener));
    }

    public void updateTitle(Object o){
        TextView textView = getView().findViewById(R.id.tv_lab);
        textView.setText(StringUtil.getStr(o));
    }
}
