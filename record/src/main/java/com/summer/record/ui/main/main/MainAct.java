package com.summer.record.ui.main.main;

//by summer on 2018-03-27.

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.android.lib.base.activity.BaseUIActivity;
import com.android.lib.base.fragment.FragUtil;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.interf.view.OnAppItemSelectListener;
import com.android.lib.bean.BaseBean;
import com.android.lib.service.main.KeepLiveService;
import com.android.lib.util.LogUtil;
import com.android.lib.util.system.PermissionUtil;
import com.summer.record.data.Record;
import com.summer.record.service.ClipSevice;
import com.summer.record.service.PhotoMoniter;
import com.summer.record.tool.FileTool;
import com.summer.record.ui.main.record.image.NetAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class MainAct extends BaseUIActivity<MainUIOpe,MainValue> implements OnAppItemSelectListener,View.OnClickListener {


    @Override
    protected void initNow() {
        super.initNow();
        if(!new PermissionUtil().go检查权限(this,getPV().getMainDAOpe().getPermissions())){
            return;
        }
        doThing();
    }

    public void doThing(){
        KeepLiveService.startBackGroundOne(getActivity());
        IntentFilter filter = new IntentFilter();
        filter.addAction(KeepLiveService.class.getSimpleName());
        getActivity().registerReceiver(getPV().getOneReceiver(), filter);


        getPU().initFrag(this,getPV().getFragments(),MainValue.模块ID,MainValue.模块);
        stopService(new Intent(this, ClipSevice.class));
        startService(new Intent(this, ClipSevice.class));
        onAppItemSelect(null,null,0);
        PhotoMoniter photoMoniter = PhotoMoniter.registerContentObserver(this, new OnFinishListener() {
            @Override
            public void onFinish(Object o) {
                Record record = FileTool.getLastRecord(getBaseContext());
                ArrayList<Record> records = new ArrayList<>();
                records.add(record);
                getPV().getRecordDAOpe().updateRecords(getActivity(), records, new NetAdapter<ArrayList<Record>>(getActivity()){
                    @Override
                    public void onSuccess(ArrayList<Record> o) {
                        super.onSuccess(o);
                        getPV().getRecordDAOpe().uploadRecord(getActivity(), o.get(0), new NetAdapter<BaseBean>(getActivity()));
                    }
                });
            }
        });
        getPV().setPhotoMoniter(photoMoniter);
    }

    @Override
    public void onAppItemSelect(ViewGroup viewGroup, View view, int i) {
        getPU().showView(i,getPV().getPos());
        setMoudle(MainValue.模块[i]);
        getPV().setPos(i);
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
        FragUtil.getInstance().activityFinish(this,getMoudle(),true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragUtil.getInstance().clear();
        PhotoMoniter.unregisterContentObserver(this, getPV().getPhotoMoniter());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void deal(TitleBus titleBus){
        getPU().updateTitle(getPV().getPos(), titleBus.str);
    }

    @Override
    protected boolean registerEventBus() {
        return true;
    }
}
