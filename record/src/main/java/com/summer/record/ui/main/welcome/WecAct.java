package com.summer.record.ui.main.welcome;

import android.content.Intent;

import com.android.lib.GlideApp;
import com.android.lib.base.activity.BaseUIActivity;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.exception.exception.CrashHander;
import com.android.lib.network.bean.res.BaseResBean;
import com.android.lib.network.news.NetAdapter;
import com.android.lib.network.news.NetGet;
import com.android.lib.network.news.UINetAdapter;
import com.android.lib.util.GsonUtil;
import com.android.lib.util.LogUtil;
import com.android.lib.util.SPUtil;
import com.android.lib.util.system.HandleUtil;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.summer.record.app.RecordApp;
import com.summer.record.data.Crash;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Welcome;
import com.summer.record.ui.main.main.MainAct;

import org.xutils.common.Callback;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WecAct extends BaseUIActivity<WelUIOpe,WelValue> {

    @Override
    protected void initNow() {
        super.initNow();
        getPU().load(getPV().getUrl());
        NetDataWork.Welcome.getWelUrl(this, new UINetAdapter<Welcome>(this) {
            @Override
            public void onNetFinish(boolean haveData, String url, BaseResBean baseResBean) {
                final Welcome w=new Welcome();
                w.setUrl(getPV().getUrl());
                if(haveData){
                    Welcome welcome = GsonUtil.getInstance().fromJson(GsonUtil.getInstance().toJson(baseResBean.getResult()), Welcome.class);
                    w.setUrl(welcome.getUrl()==null?getPV().getUrl():welcome.getUrl());
                    SPUtil.getInstance().saveStr(WelValue.welurl, welcome.getUrl());
                }

                WelLoadOpe.start(System.currentTimeMillis(), new OnFinishListener() {
                    @Override
                    public void onFinish(Object o) {
                        getPU().load(w.getUrl());
                        init();
                    }
                }, new OnFinishListener() {
                    @Override
                    public void onFinish(Object o) {
                        startActivity(new Intent(getActivity(),MainAct.class));
                        finish();
                    }
                });
            }
        });

    }

    public void init(){
        FlowManager.init(getApplicationContext());
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


        CrashHander.getInstance().init(getApplicationContext(), new OnFinishListener() {
            @Override
            public void onFinish(Object o) {
                Crash crash = new Crash();
                crash.setCreatedtime(System.currentTimeMillis());
                crash.setTimestr(new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date()));
                crash.setError(o.toString());
                crash.setPlatform(1);
                crash.setUser("tangjie");
                NetDataWork.Crash.insert(getApplicationContext(),crash,new com.summer.record.ui.main.record.image.NetAdapter<Boolean>(getApplicationContext()));
            }
        });
        NetAdapter.cache  = true;
        LogUtil.CAN_LOGIN = true;
        NetGet.test = false;
    }

    @Override
    public void onBackPressed() {

    }
}
