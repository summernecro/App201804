package com.summer.record.ui.main.image.image;

//by summer on 2018-03-27.

import android.support.annotation.ArrayRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

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
import com.android.lib.util.fragment.two.FragManager2;
import com.summer.record.R;
import com.summer.record.data.Crash;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.Records;
import com.summer.record.tool.FileTool;
import com.summer.record.ui.main.image.imagedetail.ImageDetailFrag;
import com.summer.record.ui.main.image.imagedetail.NetAdapter;
import com.summer.record.ui.main.image.images.ImagesFrag;
import com.summer.record.ui.main.main.MainValue;
import com.summer.record.ui.main.record.RecordDAOpe;
import com.summer.record.ui.view.UpdateIndicator;

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.Optional;

public class ImageFrag  extends BaseUIFrag<ImageUIOpe,ImageDAOpe,ImageValue> implements ViewListener{


    public static ImageFrag getInstance(String[] time, ImagesFrag imagesFrag){
        ImageFrag imageFrag = new ImageFrag();
        imageFrag.getP().getV().getTimedu()[0] = time[0];
        imageFrag.getP().getV().getTimedu()[1] = time[1];
        imageFrag.getP().getV().setImagesFrag(imagesFrag);
        return imageFrag;
    }

    @Override
    public void initNow() {
        super.initNow();
        getP().getD().getImages(getBaseAct(),getP().getV().getTimedu(), new OnLoadingAdapter() {
            @Override
            public void onStarLoading(Object o) {
                ((UpdateIndicator)getP().getV().getLoadUtil().getIndicator()).setContext(getContext());
                getP().getV().getLoadUtil().startLoadingDefault(getContext(), getBaseUIRoot(),getResources().getColor(R.color.color_red_500));
            }

            @Override
            public void onStopLoading(Object o) {
                getP().getV().getAllRecords().addAll((ArrayList<Record>) o);
                getP().getV().getRecords().addAll((ArrayList<Record>) o);
                getP().getU().loadImages(getP().getV().getRecords(),ImageFrag.this);
                getP().getV().getLoadUtil().stopLoading(getBaseUIRoot());
            }

            @Override
            public void onProgress(Object o) {
                super.onProgress(o);
                ((UpdateIndicator)getP().getV().getLoadUtil().getIndicator()).setProgress(o.toString());
            }
        });


        NetDataWork.Data.getRecordInfo(getBaseUIAct(), Record.ATYPE_IMAGE,getP().getV().getTimedu(), new UINetAdapter<Records>(getBaseUIFrag()) {
            @Override
            public void onSuccess(Records o) {
                super.onSuccess(o);
                getP().getD().setRecordsInfo(o);
                getP().getV().setTitleStr(o.getDoneNum()+"/"+o.getAllNum());
                getP().getV().getImagesFrag().getP().getU().updateTitle(getP().getV().getTitleStr());
            }
        });


        ArrayList<String> list = new ArrayList<>();
        //list.get(1).toLowerCase();
    }
    @Optional
    @OnClick({ R.id.tv_refresh,R.id.tv_down,R.id.tv_search})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_refresh:
                ArrayList<Record>  list = getP().getD().getNoNullRecords(getP().getV().getRecords());
                final ArrayList<Record> records = new ArrayList<>();
                getP().getD().updateRecordsStep(0,records,getBaseUIFrag(), list, new OnFinishListener() {
                    @Override
                    public void onFinish(Object o) {
                        if(!(o instanceof String)){
                            getP().getD().uploadRecords(0,getBaseUIAct(),records , new OnFinishListener() {
                                @Override
                                public void onFinish(Object o) {
                                    if(o==null){
                                        NetDataWork.Data.getAllRecords(getBaseAct(), Record.ATYPE_IMAGE,getP().getV().getTimedu(),new UINetAdapter<ArrayList<Record>>(getBaseUIFrag(),UINetAdapter.Loading) {
                                            @Override
                                            public void onSuccess(ArrayList<Record> o) {
                                                super.onSuccess(o);
                                                getP().getV().getRecords().clear();
                                                getP().getV().getRecords().addAll(getP().getD().dealRecord(o));
                                                getP().getU().loadImages(getP().getV().getRecords(),ImageFrag.this);
                                            }

                                            @Override
                                            public void onNetFinish(boolean haveData, String url, BaseResBean baseResBean) {
                                                super.onNetFinish(haveData, url, baseResBean);
                                                getP().getV().setTitleStr(baseResBean.getOther().toString());
                                                getP().getV().getImagesFrag().getP().getU().updateTitle(getP().getV().getTitleStr());
                                            }
                                        });
                                    }else{
                                        Record record = (Record) o;
//                                    getP().getU().scrollToPos(getP().getD().getRecords(), record);
                                        if(getP().getD().getRecordsInfo()!=null){
                                            getP().getV().setTitleStr(record.getPos()+"/"+getP().getD().getRecordsInfo().getAllNum());
                                            getP().getV().getImagesFrag().getP().getU().updateTitle(getP().getV().getTitleStr());
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
                NetDataWork.Data.getAllRecords(getBaseAct(), Record.ATYPE_IMAGE,getP().getV().getTimedu(),new UINetAdapter<ArrayList<Record>>(getBaseUIFrag(),UINetAdapter.Loading) {
                    @Override
                    public void onSuccess(ArrayList<Record> o) {
                        super.onSuccess(o);
                        getP().getV().getRecords().clear();
                        getP().getV().getRecords().addAll(getP().getD().dealRecord(o));
                        getP().getU().loadImages(getP().getV().getRecords(),ImageFrag.this);

                        getP().getD().downLoadRecords(0,getBaseUIFrag(),getP().getD().getUnDownloadRecords(getP().getD().getNoNullRecords(getP().getV().getRecords())), new OnFinishListener<Object>() {
                            @Override
                            public void onFinish(Object o) {
                                if(o!=null){
                                    Record record = (Record) o;
                                    getP().getU().scrollToPos(record);
                                }
                            }
                        });

                    }

                    @Override
                    public void onNetFinish(boolean haveData, String url, BaseResBean baseResBean) {
                        super.onNetFinish(haveData, url, baseResBean);
                        getP().getV().setTitleStr(baseResBean.getOther().toString());
                        getP().getV().getImagesFrag().getP().getU().updateTitle(getP().getV().getTitleStr());
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
                FragManager2.getInstance().start(getBaseUIAct(), MainValue.图片,ImageDetailFrag.getInstance(getP().getD().getNoNullRecords(getP().getV().getRecords()),record.getPos() ));
                break;
        }
    }
}
