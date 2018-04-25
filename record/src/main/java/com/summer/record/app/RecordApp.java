package com.summer.record.app;

//by summer on 2018-03-28.

import com.android.lib.aplication.LibAplication;
import com.android.lib.network.bean.res.BaseResBean;
import com.android.lib.network.news.NetAdapter;
import com.android.lib.network.news.NetGet;
import com.android.lib.util.fragment.two.FragManager2;
import com.raizlabs.android.dbflow.config.FlowManager;

import org.xutils.common.Callback;

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
    }


    @Override
    public void initImagePicker() {

    }
}
