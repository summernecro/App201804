package com.summer.record.ui.main.text;

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

    public void initTexts(ArrayList<Record> texts){
        getBind().recycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        getBind().recycle.setAdapter(new AppsDataBindingAdapter(getActivity(), R.layout.item_text_text, BR.item_text_text,texts));
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.GRAY);
        paint.setStrokeWidth(ScreenUtil.最小DIMEN);
        getBind().recycle.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDrawOver(c, parent, state);
                for(int i=0;i<parent.getChildCount();i++){
                    View view = parent.getChildAt(i);
                    c.drawLine(parent.getLeft(),view.getTop(),parent.getRight(),view.getTop(),paint);
                }
            }

            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.top = (int) (ScreenUtil.最小DIMEN*1);
            }
        });
    }

    public void updateTitle(Object o){
        TextView textView = getView().findViewById(R.id.tv_lab);
        textView.setText(StringUtil.getStr(o));
    }
}
