package com.summer.record.ui.main.image.image;

//by summer on 2018-03-27.

import android.view.View;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.interf.OnFinishWithObjI;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.bean.BaseBean;
import com.android.lib.network.bean.res.BaseResBean;
import com.android.lib.network.news.UINetAdapter;
import com.android.lib.util.GsonUtil;
import com.android.lib.util.LogUtil;
import com.android.lib.util.fragment.two.FragManager2;
import com.summer.record.R;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.Records;
import com.summer.record.ui.main.image.imagedetail.ImageDetailFrag;
import com.summer.record.ui.main.main.MainValue;
import com.summer.record.ui.main.record.RecordDAOpe;

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.Optional;

public class ImageFrag  extends BaseUIFrag<ImageUIOpe,RecordDAOpe> implements ViewListener{

    @Override
    public void initdelay() {
        super.initdelay();
        startLoading();
        getP().getD().getImages(getBaseAct(), new OnFinishWithObjI() {
            @Override
            public void onNetFinish(Object o) {
                getP().getD().setRecords((ArrayList<Record>) o);
                getP().getU().loadImages(getP().getD().getRecords(),ImageFrag.this);
                stopLoading();
            }
        });


        NetDataWork.Data.getRecordInfo(getBaseUIAct(), Record.ATYPE_IMAGE, new UINetAdapter<Records>(getBaseUIFrag()) {
            @Override
            public void onSuccess(Records o) {
                super.onSuccess(o);
                getP().getD().setRecordsInfo(o);
                getP().getU().updateTitle(o.getDoneNum()+"/"+o.getAllNum());
            }
        });
    }
    @Optional
    @OnClick({R.id.iv_add,R.id.tv_refresh,R.id.tv_down})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_add:

                break;
            case R.id.tv_refresh:
                ArrayList<Record>  list = getP().getD().getNoNullRecords(getP().getD().getRecords());
                getP().getD().setIndex(0);
                final ArrayList<Record> records = new ArrayList<>();
                getP().getD().updateRecordsStep(records,getBaseUIFrag(), list, new OnFinishListener() {
                    @Override
                    public void onFinish(Object o) {
                        if(!(o instanceof String)){
                            getP().getD().setIndex(0);
                            getP().getD().uploadRecords(getBaseUIAct(),records , new OnFinishListener() {
                                @Override
                                public void onFinish(Object o) {
                                    if(o==null){
                                        NetDataWork.Data.getAllRecords(getBaseAct(), Record.ATYPE_IMAGE,new UINetAdapter<ArrayList<Record>>(getBaseUIFrag(),UINetAdapter.Loading) {
                                            @Override
                                            public void onSuccess(ArrayList<Record> o) {
                                                super.onSuccess(o);
                                                getP().getD().setRecords(getP().getD().dealRecord(o));
                                                getP().getU().loadImages(getP().getD().getRecords(),ImageFrag.this);
                                            }

                                            @Override
                                            public void onNetFinish(boolean haveData, String url, BaseResBean baseResBean) {
                                                super.onNetFinish(haveData, url, baseResBean);
                                                getP().getU().updateTitle(baseResBean.getOther());
                                            }
                                        });
                                    }else{
                                        Record record = (Record) o;
//                                    getP().getU().scrollToPos(getP().getD().getRecords(), record);
                                        if(getP().getD().getRecordsInfo()!=null){
                                            getP().getU().updateTitle(record.getPos()+"/"+getP().getD().getRecordsInfo().getAllNum());
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.tv_down:
                v.setSelected(!v.isSelected());
                NetDataWork.Data.getAllRecords(getBaseAct(), Record.ATYPE_IMAGE,new UINetAdapter<ArrayList<Record>>(getBaseUIFrag(),UINetAdapter.Loading) {
                    @Override
                    public void onSuccess(ArrayList<Record> o) {
                        super.onSuccess(o);
                        getP().getD().setRecords(getP().getD().dealRecord(o));
                        getP().getU().loadImages(getP().getD().getRecords(),ImageFrag.this);


                    }

                    @Override
                    public void onNetFinish(boolean haveData, String url, BaseResBean baseResBean) {
                        super.onNetFinish(haveData, url, baseResBean);
                        getP().getU().updateTitle(baseResBean.getOther());
                    }
                });
                break;
        }
    }


    @Override
    public void onInterupt(int i, View view) {
        switch (i){
            case ViewListener.TYPE_ONCLICK:
                Record record = (Record) view.getTag(R.id.data);
                if(record.getLocpath()==null){
                    return;
                }
                FragManager2.getInstance().start(getBaseUIAct(), MainValue.图片,ImageDetailFrag.getInstance(getP().getD().getNoNullRecords(getP().getD().getRecords()),record.getPos() ));
                break;
        }
    }

    @Override
    public int getBaseUILayout() {
        return R.layout.frag_base;
    }


}
