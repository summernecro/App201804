package com.summer.record.ui.main.main;

//by summer on 2018-03-27.

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.android.lib.base.activity.BaseUIActivity;
import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.view.OnAppItemSelectListener;
import com.android.lib.util.LogUtil;
import com.android.lib.util.ToastUtil;
import com.android.lib.util.fragment.two.FragManager2;
import com.android.lib.util.system.PermissionUtil;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.summer.record.service.ClipSevice;
import com.summer.record.ui.main.image.image.ImageFrag;
import com.summer.record.ui.main.sett.SettFrag;
import com.summer.record.ui.main.text.TextFrag;
import com.summer.record.ui.main.video.video.VideoFrag;

import java.util.ArrayList;

public class MainAct extends BaseUIActivity<MainUIOpe,MainDAOpe> implements OnAppItemSelectListener {



    @Override
    protected void initNow() {
        super.initNow();

        if(!new PermissionUtil().go检查权限(this,getP().getD().getPermissions())){
            return;
        }
        doThing();
    }

    public void doThing(){
        FragManager2.getInstance().start(this,MainValue.视频,MainValue.视频ID,new VideoFrag());
        FragManager2.getInstance().start(this,MainValue.图片,MainValue.图片ID,new ImageFrag());
        FragManager2.getInstance().start(this,MainValue.文字,MainValue.文字ID,new TextFrag());
        FragManager2.getInstance().start(this,MainValue.设置,MainValue.设置ID,new SettFrag());
        startService(new Intent(this, ClipSevice.class));
        getP().getU().showView(0);
    }

    @Override
    public void onAppItemSelect(ViewGroup viewGroup, View view, int i) {
        getP().getU().showView(i);
        setMoudle(MainValue.模块[i]);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(new PermissionUtil().onRequestPermissionsResult(this,requestCode,grantResults)){
            doThing();
        }
    }

    @Override
    public void onBackPressed() {
        BaseUIFrag baseUIFrag = FragManager2.getInstance().getCurrentFrag(getMoudle());
        if(baseUIFrag!=null&&baseUIFrag.getFragM()!=null) {
            FragManager2 fragManager2 = baseUIFrag.getFragM();
            if (!fragManager2.finish(getActivity(), getMoudle(), true)) {//清除当前页面
                super.onBackPressed();//当前模块没有可清除界面
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragManager2.getInstance().clear();
    }
}
