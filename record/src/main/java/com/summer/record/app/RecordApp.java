package com.summer.record.app;

//by summer on 2018-03-28.

import android.graphics.Color;

import com.android.lib.GlideApp;
import com.android.lib.aplication.LibAplication;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.exception.exception.CrashHander;
import com.android.lib.network.bean.res.BaseResBean;
import com.android.lib.network.news.NetAdapter;
import com.android.lib.network.news.NetGet;
import com.android.lib.util.fragment.two.FragManager2;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.summer.record.data.Crash;
import com.summer.record.data.NetDataWork;

import org.xutils.common.Callback;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordApp extends LibAplication {

    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
        NetGet.setDeal(new NetGet.Deal() {
            @Override
            public void onSuccess(BaseResBean baseResBean) {
                if(baseResBean!=null){
                    baseResBean.setCode("200");
                }
                baseResBean.setResult(baseResBean.getData());
            }

            @Override
            public void onError(Throwable throwable, boolean b) {

            }

            @Override
            public void onCancelled(Callback.CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });

        CrashHander.getInstance().init(this, new OnFinishListener() {
            @Override
            public void onFinish(Object o) {
                Crash crash = new Crash();
                crash.setCreatedtime(System.currentTimeMillis());
                crash.setTimestr(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
                crash.setError(o.toString());
                crash.setPlatform(1);
                crash.setUser("tangjie");
                NetDataWork.Crash.insert(RecordApp.this,crash,new com.summer.record.ui.main.image.imagedetail.NetAdapter<Boolean>(RecordApp.this));
            }
        });


        NetAdapter.cache  = true;
    }


    @Override
    public void initImagePicker() {

    }
}
