package com.summer.record.ui.main.video.video;

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
import com.summer.record.ui.main.image.image.ImageFrag;
import com.summer.record.ui.main.main.MainValue;
import com.summer.record.ui.main.record.RecordDAOpe;
import com.summer.record.ui.main.video.videoplay.VideoPlayFrag;

import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.Optional;

public class VideoFrag extends BaseUIFrag<VideoUIOpe,RecordDAOpe> implements ViewListener{

    @Override
    public void initdelay() {
        super.initdelay();
        startLoading();
        getP().getD().getVideos(getBaseAct(), new OnFinishWithObjI() {
            @Override
            public void onNetFinish(Object o) {
                getP().getD().setRecords((ArrayList<Record>) o);
                getP().getU().loadVideos(getP().getD().getRecords(),VideoFrag.this);
                stopLoading();
            }
        });

        NetDataWork.Data.getRecordInfo(getBaseUIAct(), Record.ATYPE_VIDEO, new UINetAdapter<Records>(getBaseUIFrag()) {
            @Override
            public void onSuccess(Records o) {
                super.onSuccess(o);
                getP().getD().setRecordsInfo(o);
                getP().getU().updateTitle(o.getDoneNum()+"/"+o.getAllNum());
            }
        });

    }
    @Optional
    @OnClick({R.id.iv_add,R.id.tv_refresh,R.id.tv_upload})
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
                                        NetDataWork.Data.getAllRecords(getBaseAct(), Record.ATYPE_VIDEO,new UINetAdapter<ArrayList<Record>>(getBaseUIFrag(),UINetAdapter.Loading) {
                                            @Override
                                            public void onSuccess(ArrayList<Record> o) {
                                                super.onSuccess(o);
                                                getP().getD().setRecords(getP().getD().dealRecord(o));
                                                getP().getU().loadVideos(getP().getD().getRecords(),VideoFrag.this);
                                            }

                                            @Override
                                            public void onNetFinish(boolean haveData, String url, BaseResBean baseResBean) {
                                                super.onNetFinish(haveData, url, baseResBean);
                                                getP().getU().updateTitle(baseResBean.getOther());
                                            }
                                        });
                                    }else{
                                        Record record = (Record) o;
                                        getP().getU().scrollToPos(getP().getD().getRecords(), record);
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
            case R.id.tv_upload:
                v.setSelected(!v.isSelected());
                getP().getD().setIndex(0);
                if(!v.isSelected()){
                    getP().getD().setIndex(getP().getD().getNoNullRecords(getP().getD().getRecords()).size());
                    return;
                }

                getP().getD().uploadRecords(getBaseUIAct(),getP().getD().getNoNullRecords(getP().getD().getRecords()) , new OnFinishListener() {
                    @Override
                    public void onFinish(Object o) {
                        Record record = (Record) o;
                        getP().getU().scrollToPos(getP().getD().getRecords(), record);
                        if(getP().getD().getRecordsInfo()!=null){
                            getP().getU().updateTitle(record.getPos()+"/"+getP().getD().getRecordsInfo().getAllNum());
                        }
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
                FragManager2.getInstance().start(getBaseUIAct(), MainValue.视频, VideoPlayFrag.getInstance((Record) view.getTag(R.id.data)));
                break;
        }
    }

    @Override
    public int getBaseUILayout() {
        return R.layout.frag_base;
    }
}
