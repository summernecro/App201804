package com.summer.record.ui.main.main;

//by summer on 2018-03-27.

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.android.lib.base.activity.BaseUIActivity;
import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.view.OnAppItemSelectListener;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.util.fragment.two.FragManager2;
import com.android.lib.util.system.PermissionUtil;
import com.summer.record.R;
import com.summer.record.data.Record;
import com.summer.record.service.ClipSevice;
import com.summer.record.ui.main.record.folder.FolderFrag;
import com.summer.record.ui.main.record.record.RecordFrag;
import com.summer.record.ui.main.record.records.RecordsFrag;

import butterknife.OnClick;
import butterknife.Optional;

public class MainAct extends BaseUIActivity<MainUIOpe,MainDAOpe,MainValue> implements OnAppItemSelectListener,View.OnClickListener {



    @Override
    protected void initNow() {
        super.initNow();
        if(!new PermissionUtil().go检查权限(this,getPD().getPermissions())){
            return;
        }
        doThing();
    }

    public void doThing(){
        getPU().initFrag(this,getPV().getFragments(),MainValue.模块ID,MainValue.模块);
        stopService(new Intent(this, ClipSevice.class));
        startService(new Intent(this, ClipSevice.class));
        onAppItemSelect(null,null,0);
    }

    @Override
    public void onAppItemSelect(ViewGroup viewGroup, View view, int i) {
        getPU().showView(i,getPV().getPos());
        setMoudle(MainValue.模块[i]);
        getPV().setPos(i);
    }


    @Optional
    @OnClick({ R.id.video_refresh,R.id.video_upload,R.id.video_down,R.id.video_search,R.id.video_sort,
            R.id.image_refresh,R.id.image_upload,R.id.image_down,R.id.image_search,R.id.image_sort,
            R.id.text_refresh,R.id.text_upload,R.id.text_down,R.id.text_search,
            R.id.iv_search_back})
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.video_sort:
            case R.id.image_sort:
                getPU().switchSort(v.getId(),getPV().getSorts(), new ViewListener() {
                    @Override
                    public void onInterupt(int i, View view) {
                        getPU().switchSort(v.getId(),getPV().getSorts(), null) ;
                        switch (i){
                            case ViewListener.TYPE_ONCLICK:
                                FragManager2.getInstance().clear(getActivity(),getMoudle());
                                switch ((int)view.getTag(R.id.position)){
                                    //按日期排序
                                    case 0:
                                        FragManager2.getInstance().setAnim(false).start(getActivity(),getMoudle() ,MainValue.模块ID[getPV().getPos()],RecordsFrag.getInstance(getMoudle()));
                                        break;
                                    //按文件夹排序
                                    case 2:
                                        FragManager2.getInstance().setAnim(false).start(getActivity(),getMoudle(),MainValue.模块ID[getPV().getPos()], FolderFrag.getInstance(getMoudle()));
                                        break;
                                }
                                break;
                        }
                    }
                });
                break;
            default:
                (FragManager2.getInstance().getCurrentFrag(getMoudle())).onClick(v);
                break;
        }
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
