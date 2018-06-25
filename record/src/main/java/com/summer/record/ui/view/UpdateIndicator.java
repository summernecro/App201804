package com.summer.record.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import android.animation.ValueAnimator;

import com.android.lib.util.ScreenUtil;
import com.summer.record.R;
import com.wang.avi.Indicator;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jack on 2015/10/17.
 */
public class UpdateIndicator extends Indicator {

    float scaleFloat=1,degrees;

    String progress = "";

    Paint.FontMetrics fontMetrics;

    Context context;

    int color1 =  -1;

    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(ScreenUtil.字宽度*20);
        paint.setTextAlign(Paint.Align.CENTER);
        float circleSpacing=5;
        float x=getWidth()/2;
        float y=getHeight()/2;
        if(color1!=-1){
            paint.setColor(color1);
        }
        if(fontMetrics==null){
            fontMetrics = paint.getFontMetrics();
        }
        canvas.save();

        canvas.translate(x, y);
        canvas.scale(scaleFloat, scaleFloat);
        canvas.rotate(degrees);

        //draw two big arc
        float[] bStartAngles=new float[]{135,-45};
        for (int i = 0; i < 2; i++) {
            RectF rectF=new RectF(-x+circleSpacing,-y+circleSpacing,x-circleSpacing,y-circleSpacing);
            canvas.drawArc(rectF, bStartAngles[i], 90, false, paint);
        }

        canvas.restore();

        canvas.save();
        paint.setStyle(Paint.Style.FILL);
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.drawText(progress,0,-(fontMetrics.top+fontMetrics.bottom)/2,paint);
        canvas.restore();

    }

    @Override
    public ArrayList<ValueAnimator> onCreateAnimators() {
        ArrayList<ValueAnimator> animators=new ArrayList<>();
        ValueAnimator scaleAnim=ValueAnimator.ofFloat(1,0.6f,1);
        scaleAnim.setDuration(1000);
        scaleAnim.setRepeatCount(-1);
        addUpdateListener(scaleAnim,new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scaleFloat = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });

        ValueAnimator rotateAnim=ValueAnimator.ofFloat(0, 180,360);
        rotateAnim.setDuration(1000);
        rotateAnim.setRepeatCount(-1);
        addUpdateListener(rotateAnim,new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degrees = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animators.add(scaleAnim);
        animators.add(rotateAnim);
        return animators;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
        postInvalidate();
    }

    public void setContext(Context context) {
        this.context = context;
        color1 = context.getResources().getColor(R.color.color_red_500);
    }
}