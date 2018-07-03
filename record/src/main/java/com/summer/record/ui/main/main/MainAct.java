package com.summer.record.ui.main.main;

//by summer on 2018-03-27.

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.android.lib.base.activity.BaseUIActivity;
import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.interf.view.OnAppItemSelectListener;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.network.news.NetAdapter;
import com.android.lib.network.news.UINetAdapter;
import com.android.lib.util.NullUtil;
import com.android.lib.util.fragment.two.FragManager2;
import com.android.lib.util.system.PermissionUtil;
import com.summer.record.R;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.Tiplab;
import com.summer.record.service.ClipSevice;
import com.summer.record.ui.main.image.image.ImageFrag;
import com.summer.record.ui.main.image.images.ImagesFrag;
import com.summer.record.ui.main.sett.SettFrag;
import com.summer.record.ui.main.text.text.TextFrag;
import com.summer.record.ui.main.video.video.VideoFrag;
import com.summer.record.ui.main.video.videoplay.VideoPlayFrag;
import com.summer.record.ui.main.video.videos.VideosFrag;

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.Optional;

public class MainAct extends BaseUIActivity<MainUIOpe,MainDAOpe,MainValue> implements OnAppItemSelectListener,View.OnClickListener,TextView.OnEditorActionListener,OnFinishListener {



    @Override
    protected void initNow() {
        super.initNow();
        if(!new PermissionUtil().go检查权限(this,getP().getD().getPermissions())){
            return;
        }
        doThing();
    }

    public void doThing(){
        getP().getU().initFrag(this,getP().getV().getFragments(),MainValue.模块ID,MainValue.模块);
        stopService(new Intent(this, ClipSevice.class));
        startService(new Intent(this, ClipSevice.class));
        onAppItemSelect(null,null,0);
    }

    @Override
    public void onAppItemSelect(ViewGroup viewGroup, View view, int i) {
        getP().getU().showView(i);
        setMoudle(MainValue.模块[i]);
        getP().getV().setPos(i);
    }


    @Optional
    @OnClick({ R.id.tv_refresh,R.id.tv_down,R.id.tv_search,R.id.iv_search_back})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_search_back:
                getP().getU().showHideSearch();
                for(int i=0;i<getP().getV().getFragments().size();i++){
                    ((RefreshI)getP().getV().getFragments().get(i)).refresh(null);
                }
                break;
            case R.id.tv_search:
                getP().getU().showHideSearch();
                break;
            default:
               getP().getV().getFragments().get(getP().getV().getPos()).onClick(v);
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


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId== EditorInfo.IME_ACTION_GO){
            getP().getU().showHideSearch();
            switch (FragManager2.getInstance().getCurrentFrag(getMoudle()).getClass().getSimpleName()){
                case "ImageDetailFrag":
                case "TextDetailFrag":
                case "VideoPlayFrag":
                    ((AddTipI)FragManager2.getInstance().getCurrentFrag(getMoudle())).addTip(v.getText().toString());
                    break;
            }
            return true;
        }
        return false;
    }

    @Override
    public void onFinish(Object o) {
        if(NullUtil.isStrEmpty(o.toString())){
            return;
        }

        NetDataWork.Tip.getLikeTiplab(getBaseContext(),o.toString(),new NetAdapter<ArrayList<Tiplab>>(getBaseContext()){

            @Override
            public void onSuccess(ArrayList<Tiplab> o) {
                super.onSuccess(o);
                getP().getV().getTiplabs().clear();
                if(o!=null&&o.size()>0){
                    getP().getV().getTiplabs().addAll(o);
                }
                getP().getU().refreshList(getP().getV().getTiplabs(), new ViewListener() {
                    @Override
                    public void onInterupt(final int i, final View view) {
                        int pos = (int) view.getTag(R.id.position);
                        NetDataWork.Tip.getRecordsFromTip(getBaseContext(), getP().getV().getTiplabs().get(pos), new UINetAdapter<ArrayList<Record>>(getBaseContext()) {
                            @Override
                            public void onSuccess(ArrayList<Record> o) {
                                super.onSuccess(o);
                                getP().getU().showHideSearch();
                                switch (FragManager2.getInstance().getCurrentFrag(getMoudle()).getClass().getSimpleName()){
                                    case "ImageDetailFrag":
                                    case "TextDetailFrag":
                                    case "VideoPlayFrag":
                                        TextView textView = view.findViewById(R.id.iv_text);
                                        ((AddTipI)FragManager2.getInstance().getCurrentFrag(getMoudle())).addTip(textView.getText().toString());
                                        break;
                                    case "VideosFrag":
                                    case "ImagesFrag":
                                        for(int i=0;i<getP().getV().getFragments().size();i++){
                                            ((RefreshI)getP().getV().getFragments().get(i)).refresh(o);
                                        }
                                        break;
                                }
                            }
                        });
                    }
                });
            }
        });
    }

}
