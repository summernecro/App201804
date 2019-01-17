package com.summer.record.ui.main.record.record;

//by summer on 2018-03-27.

import android.os.Bundle;
import android.view.View;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.fragment.FragUtil;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.interf.OnLoadingAdapter;
import com.android.lib.bean.databean.XYBean;
import com.android.lib.constant.ValueConstant;
import com.android.lib.network.bean.res.BaseResBean;
import com.android.lib.network.news.UINetAdapter;
import com.android.lib.util.LogUtil;
import com.android.lib.util.ToastUtil;
import com.android.lib.util.system.HandleUtil;
import com.summer.record.R;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.Records;
import com.summer.record.tool.DBTool;
import com.summer.record.tool.DateUtil;
import com.summer.record.tool.FileTool;
import com.summer.record.tool.TitleUtil;
import com.summer.record.ui.main.main.AView;
import com.summer.record.ui.main.record.image.ImageDetailFrag;
import com.summer.record.ui.main.record.image.NetAdapter;
import com.summer.record.ui.main.record.records.RecordsFrag;
import com.summer.record.ui.main.main.MainAct;
import com.summer.record.ui.main.main.MainValue;
import com.summer.record.ui.main.record.video.VideoPlayFrag;
import com.summer.record.ui.view.UpdateIndicator;

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.Optional;

public class RecordFrag extends BaseUIFrag<RecordUIOpe,RecordValue>{


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
        Bundle bundle = new Bundle();
        //recordFrag.getArguments().putSerializable(ValueConstant.DATA_DATA,recordFrag.getPV());
        return recordFrag;
    }

    @Override
    public void initNow() {
        super.initNow();
        if(getPV().getOriRecords()!=null&&getPV().getOriRecords().size()>0){
            getPV().getRecordDAOpe().dealRecord(getPV().getOriRecords(), RecordValue.num, new OnFinishListener() {
                @Override
                public void onFinish(Object o) {
                    getPV().reAddAllDateRecord((ArrayList<Record>) o);
                    getPU().loadImages(getPV().getUsedRecords(),RecordFrag.this);
                    getPV().getLoadUtil().stopLoading(getBaseUIRoot());
                }
            });
        }else{
            getPV().getRecordDAOpe().getImages(getBaseUIAct(),RecordValue.num,getPV().getType(),getPV().getTimedu(), new OnLoadingAdapter() {
                @Override
                public void onStarLoading(Object o) {
                    ((UpdateIndicator)getPV().getLoadUtil().getIndicator()).setContext(getContext());
                    getPV().getLoadUtil().startLoadingDefault(getContext(), getBaseUIRoot(),getResources().getColor(R.color.color_red_500));
                }

                @Override
                public void onStopLoading(Object o) {
                    getPV().setOriRecords((ArrayList<Record>) o);
                    getPV().getRecordDAOpe().dealRecord(getPV().getOriRecords(), RecordValue.num, new OnFinishListener() {
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
                    getPV().getRecordDAOpe().setRecordsInfo(o);
                    getPV().setTitleStr(o.getDoneNum()+"/"+o.getAllNum()+getPV().getYear());
                }
            });
        }

    }

    @Optional
    @OnLongClick({R.id.tv_refresh})
    public boolean onLongClick(View v) {
        switch (v.getId()){
            case R.id.tv_refresh:
                //长按
                v.setTag(R.id.data1,false);
                onClick(v);
                break;
        }
        return true;
    }



    @Optional
    @OnClick({ R.id.tv_refresh,R.id.tv_upload,R.id.tv_down,R.id.tv_search,R.id.tv_sort,R.id.iv_search_back})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.bg:
            Record record = (Record) v.getTag(R.id.data);
            if(record.getLocpath()==null){
                return;
            }
            if(getPV().getType().equals("image")){
                XYBean xyBean = new XYBean((v.getLeft()+v.getRight())/2,(v.getTop()+v.getBottom())/2);
                FragUtil.getInstance().start(getBaseUIAct(),
                        getPV().getRecordsFrag(),
                        MainValue.图片,
                        ImageDetailFrag.getInstance(getPV().getRecordDAOpe().getNoNullRecords(getPV().getUsedRecords()),record.getPos(),xyBean));
            }else{
                FragUtil.getInstance().start(getBaseUIAct(),
                        getPV().getRecordsFrag(),
                        MainValue.视频,
                        VideoPlayFrag.getInstance((Record) v.getTag(R.id.data)));
            }
                break;
            case R.id.tv_upload:
                startLoading();
                ArrayList<Record>  list = getPV().getRecordDAOpe().getNoNullRecords(getPV().getUsedRecords());
                final ArrayList<Record> records = new ArrayList<>();
                getPV().getRecordDAOpe().updateRecordsStep(0,records,getBaseUIFrag(), list, new OnFinishListener() {
                    @Override
                    public void onFinish(Object o) {
                        if(!(o instanceof String)){
                            stopLoading();
                            ToastUtil.getInstance().showShort(getActivity(),"后台正在上传文件" );
                            getPV().getRecordDAOpe().uploadRecords(0,getBaseUIAct(),records , new OnFinishListener() {
                                @Override
                                public void onFinish(Object o) {
                                    if(o==null){
                                        NetDataWork.Data.getAllRecords(getBaseUIAct(), Record.ATYPE_IMAGE,getPV().getTimedu(),new UINetAdapter<ArrayList<Record>>(getBaseUIFrag(),UINetAdapter.Loading) {
                                            @Override
                                            public void onSuccess(ArrayList<Record> o) {
                                                super.onSuccess(o);
                                                getPV().reAddDateUsedRecord(getPV().getRecordDAOpe().dealRecord(o,6));
                                                getPU().loadImages(getPV().getUsedRecords(),RecordFrag.this);
                                            }

                                            @Override
                                            public void onNetFinish(boolean haveData, String url, BaseResBean baseResBean) {
                                                super.onNetFinish(haveData, url, baseResBean);
                                                getPV().setTitleStr(baseResBean.getOther().toString()+getPV().getYear());
                                                ( (MainAct)getBaseUIAct()).getPU().updateTitle(getPV().getTitleStr());
                                            }
                                        });
                                    }else{
                                        Record record = (Record) o;
//                                    getPU().scrollToPos(getPD().getRecords(), record);
                                        if(getPV().getRecordDAOpe().getRecordsInfo()!=null){
                                            getPV().setTitleStr(record.getPos()+"/"+getPV().getRecordDAOpe().getRecordsInfo().getAllNum()+getPV().getYear());
                                            ( (MainAct)getBaseUIAct()).getPU().updateTitle(getPV().getTitleStr());
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
                NetDataWork.Data.getAllRecords(getBaseUIAct(), getPV().getType(),getPV().getTimedu(),new UINetAdapter<ArrayList<Record>>(getBaseUIFrag(),UINetAdapter.Loading) {
                    @Override
                    public void onSuccess(ArrayList<Record> o) {
                        super.onSuccess(o);
                        if(o==null){
                            return;
                        }
                        getPV().reAddDateUsedRecord(getPV().getRecordDAOpe().dealRecord(o,RecordValue.num));
                        getPU().loadImages(getPV().getUsedRecords(),RecordFrag.this);
                        getPV().getRecordDAOpe().downLoadRecords(0, getBaseUIFrag(), getPV().getRecordDAOpe().getUnDownloadRecords(getPV().getRecordDAOpe().getNoNullRecords(getPV().getUsedRecords())), new OnFinishListener() {
                            @Override
                            public void onFinish(Object o) {
                                if(o!=null){
                                    Record record = (Record) o;
                                   // getPU().scrollToPosBefore(record);
                                }
                            }
                        },new OnFinishListener<Object>() {
                            @Override
                            public void onFinish(Object o) {
                                if(o!=null){
                                    Record record = (Record) o;
                                    ToastUtil.getInstance().showShort(getBaseUIAct(),record.getLocpath());
                                    getPU().scrollToPos(record);
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
            case R.id.iv_search_back:
                break;
            case R.id.tv_search:
                break;
                //刷新按钮
            case R.id.tv_refresh:
                if(getPV().isIsdoing()){
                    ToastUtil.getInstance().showShort(getActivity(),"请勿频繁操作" );
                    return;
                }
                getPV().setIsdoing(true);
                //从相册获取所有图片文件记录
                final MainAct mainAct = (MainAct) getBaseUIAct();
                ArrayList<Record> images = FileTool.getRecords(getActivity(), getPV().getType(), getPV().timedu, new OnFinishListener() {
                    @Override
                    public void onFinish(Object o) {
                        Record record1 = (Record) o;
                        mainAct.deal("获取"+record1.getLocpath());
                    }
                });

                ArrayList<Record> rs = new ArrayList<>();
                if(v.getTag(R.id.data1)!=null){
                    for(int i=0;i<Math.min(100,images.size() );i++){
                        rs.add(images.get(i));
                    }
                }else{
                    rs.addAll(images);
                }
                v.setTag(R.id.data1,null);

                //上传本地未上传记录
                final ArrayList<Record> records1 = new ArrayList<>();
                getPV().getRecordDAOpe().updateRecordsStep(0, records1, RecordFrag.this, rs, new OnFinishListener() {
                    @Override
                    public void onFinish(Object o) {
                        mainAct.deal("更新记录"+o);
                        //上传记录完成
                        if(!(o instanceof String)){
                            //上传未上传的图片文件
                            getPV().getRecordDAOpe().uploadRecords(0, getActivity(), records1, new OnFinishListener() {
                                @Override
                                public void onFinish(Object o) {
                                    //上传完成
                                    if(o==null){
                                        //上传所有图片后 下载记录列表
                                        NetDataWork.Data.getAllRecords(getActivity(),getPV().getType() ,getPV().timedu , new NetAdapter<ArrayList<Record>>(getActivity()){
                                            @Override
                                            public void onSuccess(final ArrayList<Record> o) {
                                                super.onSuccess(o);
                                                mainAct.deal("已下载所有记录真正存储");
                                                //将下载的记录列表存在本地数据库
                                                DBTool.update(0,true,getPV().getType(),o, getPV().timedu, new OnFinishListener() {
                                                    @Override
                                                    public void onFinish(Object o) {
                                                        mainAct.deal("完成");
                                                        getPV().setIsdoing(false);
                                                        HandleUtil.getInstance().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                getPV().getOriRecords().clear();
                                                                initNow();
                                                            }
                                                        }, 1000);
                                                    }
                                                });
                                            }

                                            @Override
                                            public void onFail(boolean haveData, String msg) {
                                                super.onFail(haveData, msg);
                                                mainAct.deal("2"+msg);
                                                stopLoading();
                                                getPV().setIsdoing(false);
                                            }
                                        });
                                    }else{
                                        //上传了第X条
                                        Record record2 = (Record) o;
                                        mainAct.deal(""+record2.getName());
                                    }
                                }
                            });
                        }
                    }
                });
                break;
        }
    }

}
