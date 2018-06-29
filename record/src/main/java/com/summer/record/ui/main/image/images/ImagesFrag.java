package com.summer.record.ui.main.image.images;

//by summer on 2018-06-25.

import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.network.news.NetAdapter;
import com.android.lib.network.news.UINetAdapter;
import com.android.lib.util.NullUtil;
import com.summer.record.R;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.Tiplab;
import com.summer.record.ui.main.image.image.ImageFrag;

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.Optional;

public class ImagesFrag extends BaseUIFrag<ImagesUIOpe,ImagesDAOpe,ImagesValue> implements TextView.OnEditorActionListener,OnFinishListener{


    @Override
    public void initNow() {
        super.initNow();
        getP().getU().initFrag(this,getP().getD().getYears(getContext()),getP().getV().getImageFrags());
        getP().getU().initViewPager(getChildFragmentManager(),getContext(),getP().getV().getImageFrags());
    }


    @Optional
    @OnClick({ R.id.tv_refresh,R.id.tv_down,R.id.tv_search,R.id.iv_search_back})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_search_back:
                getP().getU().showHideSearch();
                ArrayList<Fragment> fragments = getP().getV().getImageFrags();
                for(int i=0;i<fragments.size();i++){
                    ImageFrag imageFrag = (ImageFrag) fragments.get(i);
                    imageFrag.getP().getV().getRecords().clear();
                    imageFrag.getP().getV().getRecords().addAll(imageFrag.getP().getV().getAllRecords());
                    imageFrag.getP().getU().loadImages(imageFrag.getP().getV().getRecords(),imageFrag);
                }
                break;
            case R.id.tv_search:
                getP().getU().showHideSearch();
                break;
                default:
                    getP().getU().getCurrentFrag(getP().getV().getImageFrags()).onClick(v);
                    break;
        }
    }

    @Override
    public int getBaseUILayout() {
        return R.layout.frag_base;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId== EditorInfo.IME_ACTION_GO){
            getP().getU().showHideSearch();
            return true;
        }
        return false;
    }

    @Override
    public void onFinish(Object o) {
        if(NullUtil.isStrEmpty(o.toString())){
            return;
        }

        NetDataWork.Tip.getLikeTiplab(getContext(),o.toString(),new NetAdapter<ArrayList<Tiplab>>(getContext()){

            @Override
            public void onSuccess(ArrayList<Tiplab> o) {
                super.onSuccess(o);
                getP().getV().getTiplabs().clear();
                if(o!=null&&o.size()>0){
                    getP().getV().getTiplabs().addAll(o);
                }
                getP().getU().refreshList(getP().getV().getTiplabs(), new ViewListener() {
                    @Override
                    public void onInterupt(final int i, View view) {
                        NetDataWork.Tip.getRecordsFromTip(getContext(), getP().getV().getTiplabs().get(i), new UINetAdapter<ArrayList<Record>>(getBaseUIFrag()) {
                            @Override
                            public void onSuccess(ArrayList<Record> o) {
                                super.onSuccess(o);
                                getP().getU().showHideSearch();
                                ImageFrag imageFrag = (ImageFrag) getP().getV().getImageFrags().get(0);
                                imageFrag.getP().getV().getRecords().clear();
                                imageFrag.getP().getV().getRecords().addAll(o);
                                imageFrag.getP().getD().initRecords(imageFrag.getP().getV().getRecords());
                                imageFrag.getP().getU().loadImages(o,imageFrag);

                            }
                        });
                    }
                });
            }
        });
    }
}
