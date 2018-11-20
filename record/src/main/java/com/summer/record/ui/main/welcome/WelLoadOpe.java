package com.summer.record.ui.main.welcome;

import android.content.Intent;

import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.ope.BaseDAOpe;
import com.android.lib.util.system.HandleUtil;
import com.summer.record.ui.main.main.MainAct;

public class WelLoadOpe extends BaseDAOpe {

    public static  void start(long start, OnFinishListener onFinishListener, final OnFinishListener onFinishListener2){
        onFinishListener.onFinish(null);
        long end = System.currentTimeMillis();
        long time =4000;
        if(4000<(end-start)){
            time = 100;
        }else{
            time =  4000-(end-start);
        }
        HandleUtil.getInstance().postDelayed(new Runnable() {
            @Override
            public void run() {
                onFinishListener2.onFinish(null);
            }
        },time);
    }


}
