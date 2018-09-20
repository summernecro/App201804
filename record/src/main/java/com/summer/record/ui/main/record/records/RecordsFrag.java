package com.summer.record.ui.main.record.records;

//by summer on 2018-06-25.

import android.view.View;

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
import com.summer.record.tool.DBTool;
import com.summer.record.tool.FileTool;
import com.summer.record.tool.TitleUtil;
import com.summer.record.ui.main.record.record.RecordDAOpe;
import com.summer.record.ui.main.record.record.RecordFrag;
import com.summer.record.ui.main.main.RefreshI;
import com.summer.record.ui.main.record.record.RecordValue;

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.Optional;

public class RecordsFrag extends BaseUIFrag<RecordsUIOpe,RecordsDAOpe,RecordsValue> implements RefreshI,View.OnClickListener,OnFinishListener {


    public static BaseUIFrag getInstance(String type){
        RecordsFrag recordsFrag = new RecordsFrag();
        recordsFrag.getPV().setType(type);
        return recordsFrag;
    }

    @Override
    public void initNow() {
        super.initNow();
        getPD().getMaxMinYear(getContext(),getPV().getType(), new OnFinishListener() {
            @Override
            public void onFinish(Object o) {
                getPU().initFrag(RecordsFrag.this,getPV().getType(), (ArrayList<String[]>) o,getPV().getImageFrags());
                getPU().initViewPager(getChildFragmentManager(),getContext(),getPV().getImageFrags(),getPV().getPos());
            }
        });
    }

    @Optional
    @OnClick({ R.id.tv_upload,R.id.tv_down,R.id.tv_refresh,R.id.tv_search,R.id.iv_search_back,R.id.tv_sort})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.iv_search_back:
                TitleUtil.showHideSearch(this);
                refresh(null);
                break;
            case R.id.tv_search:
                TitleUtil.showHideSearch(this);
                break;
            case R.id.tv_sort:
                getPU().switchSort(getPV().getSorts());
                break;
            case R.id.tv_refresh:
                ArrayList<Record> all = FileTool.getRecords(getBaseAct(), getPV().getType(),new String[]{DBTool.getLastReCordCTime(getPV().getType())+"",""+(System.currentTimeMillis()/1000)}, new OnFinishListener() {
                    @Override
                    public void onFinish(Object o) {

                    }
                });
                final ArrayList<Record> records = new ArrayList<>();
                new RecordDAOpe().updateRecordsStep(0, records, RecordsFrag.this, new RecordDAOpe().getNoNullRecords( all), new OnFinishListener() {
                    @Override
                    public void onFinish(Object o) {
                        if(!(o instanceof String)){
                            NetDataWork.Data.getAllRecords(getBaseAct(), getPV().getType(),new String[]{DBTool.getLastReCordCTime(getPV().getType())+"",""+(System.currentTimeMillis()/1000)},new UINetAdapter<ArrayList<Record>>(getBaseUIFrag(),UINetAdapter.Loading) {
                                @Override
                                public void onSuccess(ArrayList<Record> o) {
                                    super.onSuccess(o);
                                    DBTool.saveRecords(o, new OnFinishListener() {
                                        @Override
                                        public void onFinish(Object o) {
                                            getPU().initFrag(RecordsFrag.this,getPV().getType(),getPD().getYears(getBaseUIAct(),getPV().getType(),DBTool.getMaxMinYear(getPV().getType())),getPV().getImageFrags());
                                            getPU().initViewPager(getChildFragmentManager(),getContext(),getPV().getImageFrags(),getPV().getPos());
                                        }
                                    });
                                }
                            });
                        }
                    }
                });
                break;
            default:
                RecordFrag recordFrag = (RecordFrag) getPV().getImageFrags().get(getPV().getPos()[0]);
                recordFrag.onClick(v);
                break;
        }


    }


    @Override
    public void refresh(ArrayList<Record> o){
        RecordFrag recordFrag = (RecordFrag) getPV().getImageFrags().get(0);
        if(o==null){
            recordFrag.getPV().reAddUsedRecord(recordFrag.getPV().getOriRecords());
            recordFrag.getPD().initRecords(recordFrag.getPV().getUsedRecords());
            recordFrag.getPU().loadImages(o, recordFrag);
            return;
        }
        ArrayList<Record> records = new ArrayList<>();
        for(int i=0;o!=null&&i<o.size();i++){
            if(o.get(i).getAtype().equals(getPV().getType())){
                records.add(o.get(i));
            }
        }
        if(records.size()==0){
            return;
        }
        recordFrag.getPV().reAddUsedRecord(recordFrag.getPD().dealRecord(records, RecordValue.num));
        recordFrag.getPD().initRecords(recordFrag.getPV().getUsedRecords());
        recordFrag.getPU().loadImages(o, recordFrag);
    }


    @Override
    public void onFinish(Object o) {
        if(NullUtil.isStrEmpty(o.toString())){
            return;
        }
        NetDataWork.Tip.getLikeTiplab(getBaseAct(),o.toString(),new NetAdapter<ArrayList<Tiplab>>(getBaseAct()){

            @Override
            public void onSuccess(ArrayList<Tiplab> o) {
                super.onSuccess(o);
                getPV().getTiplabs().clear();
                if(o!=null&&o.size()>0){
                    getPV().getTiplabs().addAll(o);
                }
                TitleUtil.refreshList(RecordsFrag.this,getPV().getTiplabs(), new ViewListener() {
                    @Override
                    public void onInterupt(final int i, final View view) {
                        int pos = (int) view.getTag(R.id.position);
                        NetDataWork.Tip.getRecordsFromTip(getBaseUIAct(), getPV().getTiplabs().get(pos), new UINetAdapter<ArrayList<Record>>(getBaseUIAct()) {
                            @Override
                            public void onSuccess(ArrayList<Record> o) {
                                super.onSuccess(o);
                                TitleUtil.showHideSearch(RecordsFrag.this);
                                refresh(o);
                            }
                        });
                    }
                });
            }
        });
    }
}
