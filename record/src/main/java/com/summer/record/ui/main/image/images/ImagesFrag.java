package com.summer.record.ui.main.image.images;

//by summer on 2018-06-25.

import android.content.Context;
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
import com.summer.record.ui.main.main.RefreshI;

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.Optional;

public class ImagesFrag extends BaseUIFrag<ImagesUIOpe,ImagesDAOpe,ImagesValue> implements RefreshI,View.OnClickListener {


    @Override
    public void initNow() {
        super.initNow();
        getP().getD().getMaxMinYear(getContext(), new OnFinishListener() {
            @Override
            public void onFinish(Object o) {
                getP().getU().initFrag(ImagesFrag.this,(ArrayList<String[]>)o,getP().getV().getImageFrags());
                getP().getU().initViewPager(getChildFragmentManager(),getContext(),getP().getV().getImageFrags(),getP().getV().getPos());
            }
        });
    }

    @Optional
    @OnClick({ R.id.tv_refresh,R.id.tv_down,R.id.tv_search})
    public void onClick(View v) {
        super.onClick(v);
        ImageFrag imageFrag = (ImageFrag) getP().getV().getImageFrags().get(getP().getV().getPos()[0]);
        imageFrag.onClick(v);
    }


    @Override
    public void refresh(ArrayList<Record> o){
        ImageFrag imageFrag = (ImageFrag) getP().getV().getImageFrags().get(0);
        if(o==null){
            imageFrag.getP().getV().getRecords().clear();
            imageFrag.getP().getV().getRecords().addAll(imageFrag.getP().getV().getAllRecords());
            imageFrag.getP().getD().initRecords(imageFrag.getP().getV().getRecords());
            imageFrag.getP().getU().loadImages(o,imageFrag);
            return;
        }
        ArrayList<Record> records = new ArrayList<>();
        for(int i=0;o!=null&&i<o.size();i++){
            if(o.get(i).getAtype().equals("image")){
                records.add(o.get(i));
            }
        }
        if(records.size()==0){
            return;
        }
        imageFrag.getP().getV().getRecords().clear();
        imageFrag.getP().getV().getRecords().addAll(imageFrag.getP().getD().dealRecord(records));
        imageFrag.getP().getD().initRecords(imageFrag.getP().getV().getRecords());
        imageFrag.getP().getU().loadImages(o,imageFrag);
    }
}
