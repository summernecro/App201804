package com.summer.record.ui.main.record.record;

//by summer on 2018-03-27.

import android.view.View;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.interf.OnLoadingAdapter;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.bean.databean.XYBean;
import com.android.lib.network.bean.res.BaseResBean;
import com.android.lib.network.news.UINetAdapter;
import com.android.lib.util.ToastUtil;
import com.android.lib.util.fragment.two.FragManager2;
import com.summer.record.R;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.Records;
import com.summer.record.tool.DateUtil;
import com.summer.record.ui.main.record.image.ImageDetailFrag;
import com.summer.record.ui.main.record.records.RecordsFrag;
import com.summer.record.ui.main.main.MainAct;
import com.summer.record.ui.main.main.MainValue;
import com.summer.record.ui.main.record.video.VideoPlayFrag;
import com.summer.record.ui.view.UpdateIndicator;

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.Optional;

public class RecordFrag extends BaseUIFrag<RecordUIOpe,RecordDAOpe,RecordValue> implements ViewListener{


    public static RecordFrag getInstance(String[] time, String type, RecordsFrag recordsFrag,ArrayList<Record> records){
        RecordFrag recordFrag = new RecordFrag();
        if(time!=null){
            recordFrag.getPV().getTimedu()[0] = time[0];
            recordFrag.getPV().getTimedu()[1] = time[1];
            recordFrag.getPV().setYear(DateUtil.toYear(time[0]));
        }
        recordFrag.getPV().setRecordsFrag(recordsFrag);
        recordFrag.getPV().setType(type);
        recordFrag.getPV().setOriRecords(records);
        return recordFrag;
    }

    @Override
    public void initNow() {
        super.initNow();
        if(getPV().getOriRecords()!=null&&getPV().getOriRecords().size()>0){
            getP().getD().dealRecord(getPV().getOriRecords(), RecordValue.num, new OnFinishListener() {
                @Override
                public void onFinish(Object o) {
                    getPV().reAddAllDateRecord((ArrayList<Record>) o);
                    getPU().loadImages(getPV().getUsedRecords(),RecordFrag.this);
                    getPV().getLoadUtil().stopLoading(getBaseUIRoot());
                }
            });
        }else{
            getPD().getImages(getBaseAct(),RecordValue.num,getPV().getType(),getPV().getTimedu(), new OnLoadingAdapter() {
                @Override
                public void onStarLoading(Object o) {
                    ((UpdateIndicator)getPV().getLoadUtil().getIndicator()).setContext(getContext());
                    getPV().getLoadUtil().startLoadingDefault(getContext(), getBaseUIRoot(),getResources().getColor(R.color.color_red_500));
                }

                @Override
                public void onStopLoading(Object o) {
                    getPV().setOriRecords((ArrayList<Record>) o);
                    getP().getD().dealRecord(getPV().getOriRecords(), RecordValue.num, new OnFinishListener() {
                        @Override
                        public void onFinish(Object o) {
                            getPV().reAddAllDateRecord((ArrayList<Record>) o);
                            getPU().loadImages(getPV().getUsedRecords(),RecordFrag.this);
                            getPV().getLoadUtil().stopLoading(getBaseUIRoot());
                        }
                    });
                }

                @Override
                public void onProgress(Object o) {
                    super.onProgress(o);
                    ((UpdateIndicator)getPV().getLoadUtil().getIndicator()).setProgress(o.toString());
                }
            });


            NetDataWork.Data.getRecordInfo(getBaseUIAct(),getPV().getType(),getPV().getTimedu(), new UINetAdapter<Records>(getBaseUIFrag()) {
                @Override
                public void onSuccess(Records o) {
                    super.onSuccess(o);
                    if(o==null){
                        return;
                    }
                    getPD().setRecordsInfo(o);
                    getPV().setTitleStr(o.getDoneNum()+"/"+o.getAllNum()+getPV().getYear());
                    getPV().getRecordsFrag().getPU().updateTitle(getPV().getTitleStr());
                }
            });
        }

    }
    @Optional
    @OnClick({R.id.video_refresh,R.id.video_upload,R.id.video_down,R.id.video_search,R.id.video_sort,
            R.id.image_refresh,R.id.image_upload,R.id.image_down,R.id.image_search,R.id.image_sort,
            R.id.text_refresh,R.id.text_upload,R.id.text_down,R.id.text_search,R.id.text_sort,
            R.id.iv_search_back})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.image_upload:
            case R.id.video_upload:
                ArrayList<Record>  list = getPD().getNoNullRecords(getPV().getUsedRecords());
                final ArrayList<Record> records = new ArrayList<>();
                getPD().updateRecordsStep(0,records,getBaseUIFrag(), list, new OnFinishListener() {
                    @Override
                    public void onFinish(Object o) {
                        if(!(o instanceof String)){
                            getPD().uploadRecords(0,getBaseUIAct(),records , new OnFinishListener() {
                                @Override
                                public void onFinish(Object o) {
                                    if(o==null){
//                                        NetDataWork.Data.getAllRecords(getBaseAct(), Record.ATYPE_IMAGE,getPV().getTimedu(),new UINetAdapter<ArrayList<Record>>(getBaseUIFrag(),UINetAdapter.Loading) {
//                                            @Override
//                                            public void onSuccess(ArrayList<Record> o) {
//                                                super.onSuccess(o);
//                                                getPV().reAddDateUsedRecord(getPD().dealRecord(o));
//                                                getPU().loadImages(getPV().getUsedRecords(),RecordFrag.this);
//                                            }
//
//                                            @Override
//                                            public void onNetFinish(boolean haveData, String url, BaseResBean baseResBean) {
//                                                super.onNetFinish(haveData, url, baseResBean);
//                                                getPV().setTitleStr(baseResBean.getOther().toString()+getPV().getYear());
//                                                ( (MainAct)getBaseUIAct()).getPU().updateTitle(getPV().getTitleStr());
//                                            }
//                                        });
                                    }else{
                                        Record record = (Record) o;
//                                    getPU().scrollToPos(getPD().getRecords(), record);
                                        if(getPD().getRecordsInfo()!=null){
                                            getPV().setTitleStr(record.getPos()+"/"+getPD().getRecordsInfo().getAllNum()+getPV().getYear());
                                            ( (MainAct)getBaseUIAct()).getPU().updateTitle(getPV().getTitleStr());
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
                break;
            case R.id.video_down:
            case R.id.image_down:
                v.setSelected(!v.isSelected());
                NetDataWork.Data.getAllRecords(getBaseAct(), getPV().getType(),getPV().getTimedu(),new UINetAdapter<ArrayList<Record>>(getBaseUIFrag(),UINetAdapter.Loading) {
                    @Override
                    public void onSuccess(ArrayList<Record> o) {
                        super.onSuccess(o);
                        if(o==null){
                            return;
                        }
                        getPV().reAddDateUsedRecord(getPD().dealRecord(o,RecordValue.num));
                        getPU().loadImages(getPV().getUsedRecords(),RecordFrag.this);
                        getPD().downLoadRecords(0,getBaseUIFrag(),getPD().getUnDownloadRecords(getPD().getNoNullRecords(getPV().getUsedRecords())), new OnFinishListener<Object>() {
                            @Override
                            public void onFinish(Object o) {
                                if(o!=null){
                                    Record record = (Record) o;
                                    ToastUtil.getInstance().showShort(getBaseAct(),record.getLocpath());
                                    //getPU().scrollToPos(record);
                                }
                            }
                        });

                    }

                    @Override
                    public void onNetFinish(boolean haveData, String url, BaseResBean baseResBean) {
                        super.onNetFinish(haveData, url, baseResBean);
                        getPV().setTitleStr(baseResBean.getOther()+getPV().getYear());
                        ( (MainAct)getBaseUIAct()).getPU().updateTitle(getPV().getTitleStr());
                    }
                });
                break;
            case R.id.tv_search:

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
                if(getPV().getType().equals("image")){
                    XYBean xyBean = new XYBean((view.getLeft()+view.getRight())/2,(view.getTop()+view.getBottom())/2);
                    FragManager2.getInstance().setAnim(false).start(getBaseUIAct(),
                            MainValue.图片,
                            ImageDetailFrag.getInstance(getPD().getNoNullRecords(getPV().getUsedRecords()),record.getPos(),xyBean));
                }else{
                    FragManager2.getInstance().setStartAnim(R.anim.fade_in,R.anim.fade_out).setFinishAnim(R.anim.fade_in,R.anim.fade_out).start(getBaseUIAct(),
                            MainValue.视频,
                            VideoPlayFrag.getInstance((Record) view.getTag(R.id.data)));
                }

                break;
        }
    }
}