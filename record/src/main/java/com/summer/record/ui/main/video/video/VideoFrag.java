package com.summer.record.ui.main.video.video;

//by summer on 2018-03-27.

import android.view.View;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.interf.OnFinishWithObjI;
import com.android.lib.base.interf.OnLoadingAdapter;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.bean.BaseBean;
import com.android.lib.network.bean.res.BaseResBean;
import com.android.lib.network.news.UINetAdapter;
import com.android.lib.util.GsonUtil;
import com.android.lib.util.LogUtil;
import com.android.lib.util.ToastUtil;
import com.android.lib.util.fragment.two.FragManager2;
import com.summer.record.R;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.Records;
import com.summer.record.tool.DateUtil;
import com.summer.record.ui.main.image.image.ImageFrag;
import com.summer.record.ui.main.main.MainAct;
import com.summer.record.ui.main.main.MainValue;
import com.summer.record.ui.main.record.RecordDAOpe;
import com.summer.record.ui.main.video.videoplay.VideoPlayFrag;
import com.summer.record.ui.main.video.videos.VideosFrag;
import com.summer.record.ui.view.UpdateIndicator;

import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.Optional;

public class VideoFrag extends BaseUIFrag<VideoUIOpe,RecordDAOpe,VideoValue> implements ViewListener{


    public static VideoFrag getInstance(String[] time, VideosFrag videosFrag){
        VideoFrag videoFrag = new VideoFrag();
        videoFrag.getP().getV().getTimedu()[0] = time[0];
        videoFrag.getP().getV().getTimedu()[1] = time[1];
        videoFrag.getP().getV().setYear(DateUtil.toYear(time[0]));
        videoFrag.getP().getV().setVideosFrag(videosFrag);
        return videoFrag;
    }


    @Override
    public void initNow() {
        super.initNow();

        getP().getD().getVideos(getBaseAct(),getP().getV().getTimedu(), new OnLoadingAdapter(){
            @Override
            public void onStarLoading(Object o) {
                ((UpdateIndicator)getP().getV().getLoadUtil().getIndicator()).setContext(getContext());
                getP().getV().getLoadUtil().startLoadingDefault(getContext(), getBaseUIRoot(),getResources().getColor(R.color.color_red_500));
            }

            @Override
            public void onStopLoading(Object o) {
                getP().getV().getAllRecords().addAll((ArrayList<Record>) o);
                getP().getV().getRecords().addAll(getP().getV().getAllRecords());
                getP().getU().loadVideos(getP().getV().getRecords(),VideoFrag.this);
                getP().getV().getLoadUtil().stopLoading(getBaseUIRoot());
            }

            @Override
            public void onProgress(Object o) {
                super.onProgress(o);
                ((UpdateIndicator)getP().getV().getLoadUtil().getIndicator()).setProgress(o.toString());
            }
        });

        NetDataWork.Data.getRecordInfo(getBaseUIAct(), Record.ATYPE_VIDEO, getP().getV().getTimedu(),new UINetAdapter<Records>(getBaseUIFrag()) {
            @Override
            public void onSuccess(Records o) {
                super.onSuccess(o);
                getP().getD().setRecordsInfo(o);
                getP().getV().setTitleStr(o.getDoneNum()+"/"+o.getAllNum()+getP().getV().getYear());
                ( (MainAct)getBaseUIAct()).getP().getU().updateTitle(getP().getV().getTitleStr());
            }
        });

    }
    @Optional
    @OnClick({ R.id.tv_refresh,R.id.tv_down})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_refresh:
                getP().getD().updateRecordsStep(0,new ArrayList<Record>(),getBaseUIFrag(), getP().getD().getNoNullRecords(getP().getV().getRecords()), new OnFinishListener() {
                    @Override
                    public void onFinish(Object o) {
                        if(o instanceof ArrayList){
                            getP().getD().uploadRecords(0,getBaseUIAct(), (ArrayList<Record>) o, new OnFinishListener() {
                                @Override
                                public void onFinish(Object o) {
                                    if(o==null){
                                        NetDataWork.Data.getAllRecords(getBaseAct(), Record.ATYPE_VIDEO,getP().getV().getTimedu(),new UINetAdapter<ArrayList<Record>>(getBaseUIFrag(),UINetAdapter.Loading) {
                                            @Override
                                            public void onSuccess(ArrayList<Record> o) {
                                                super.onSuccess(o);
                                                getP().getV().getRecords().addAll(getP().getD().dealRecord(o));
                                                getP().getU().loadVideos(getP().getV().getRecords(),VideoFrag.this);
                                            }

                                            @Override
                                            public void onNetFinish(boolean haveData, String url, BaseResBean baseResBean) {
                                                super.onNetFinish(haveData, url, baseResBean);
                                                getP().getV().setTitleStr(baseResBean.getOther().toString()+getP().getV().getYear());
                                                ( (MainAct)getBaseUIAct()).getP().getU().updateTitle(getP().getV().getTitleStr());
                                            }
                                        });
                                    }else{
                                        Record record = (Record) o;
                                        //getP().getU().scrollToPos(getP().getV().getRecords(), record);
                                        if(getP().getD().getRecordsInfo()!=null){
                                            getP().getV().setTitleStr(record.getPos()+"/"+getP().getD().getRecordsInfo().getAllNum()+getP().getV().getYear());
                                            ( (MainAct)getBaseUIAct()).getP().getU().updateTitle(getP().getV().getTitleStr());
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
                NetDataWork.Data.getAllRecords(getBaseAct(), Record.ATYPE_VIDEO,getP().getV().getTimedu(),new UINetAdapter<ArrayList<Record>>(getBaseUIFrag(),UINetAdapter.Loading) {
                    @Override
                    public void onSuccess(ArrayList<Record> o) {
                        super.onSuccess(o);
                        getP().getV().getAllRecords().clear();
                        getP().getV().getAllRecords().addAll(getP().getD().dealRecord(o));
                        getP().getV().getRecords().addAll(getP().getV().getAllRecords());
                        getP().getU().loadVideos(getP().getV().getRecords(),VideoFrag.this);

                        getP().getD().downLoadRecords(0,getBaseUIFrag(),getP().getD().getUnDownloadRecords(getP().getD().getNoNullRecords(getP().getV().getRecords())), new OnFinishListener<Object>() {
                            @Override
                            public void onFinish(Object o) {
                                if(o!=null){
                                    Record record = (Record) o;
                                    ToastUtil.getInstance().showShort(getBaseAct(),record.getLocpath());
                                    //getP().getU().scrollToPos(record);
                                }
                            }
                        });

                    }

                    @Override
                    public void onNetFinish(boolean haveData, String url, BaseResBean baseResBean) {
                        super.onNetFinish(haveData, url, baseResBean);
                        getP().getV().setTitleStr(baseResBean.getOther().toString()+getP().getV().getYear());
                        ( (MainAct)getBaseUIAct()).getP().getU().updateTitle(getP().getV().getTitleStr());
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
}
