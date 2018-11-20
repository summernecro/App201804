package com.summer.record.ui.main.record.record;

//by summer on 2018-04-03.

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.android.lib.util.ScreenUtil;
import com.summer.record.R;
import com.summer.record.data.Record;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class VideoItemDecoration extends RecyclerView.ItemDecoration {
    
    ArrayList<Record> records;

    final Paint paint = new Paint();

    int linecolor  = R.color.white;
    int num = 3;
    
    public VideoItemDecoration(Context context,ArrayList<Record> records,int num){
        this.records = records;
        this.num = num;
        paint.setColor(Color.DKGRAY);
        paint.setTextSize(ScreenUtil.字宽度*14);
        paint.setAntiAlias(true);
        linecolor= context.getResources().getColor(R.color.color_blue_300);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for(int i=0;i<parent.getChildCount();i++){
            int pos = parent.getChildAdapterPosition(parent.getChildAt(i));
            if(pos>=records.size()){
                return;
            }
            if(records.get(pos).isFrist()&&pos%num==0){
//                paint.setColor(linecolor);
//                c.drawRect(ScreenUtil.最小DIMEN*2,parent.getChildAt(i).getTop()-ScreenUtil.最小DIMEN*25,parent.getChildAt(i).getWidth(),parent.getChildAt(i).getTop(),paint);
                //paint.setColor(Color.GRAY);
                c.drawText(records.get(pos).getDateStr(),parent.getChildAt(i).getLeft()+ ScreenUtil.最小DIMEN*2,parent.getChildAt(i).getTop()-ScreenUtil.字宽度*9,paint);
            }
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        outRect.left = (int) (ScreenUtil.最小DIMEN*2);
        outRect.right = (int) (ScreenUtil.最小DIMEN*2);
        outRect.top = (int) (ScreenUtil.最小DIMEN*2);
        outRect.bottom = (int) (ScreenUtil.最小DIMEN*2);

        if(pos>=records.size()){
            return;
        }

        if(records.get(pos).isFrist()){
            outRect.top = (int) (ScreenUtil.最小DIMEN*30);
        }
    }
}
