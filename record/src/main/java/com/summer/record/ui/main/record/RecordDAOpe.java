package com.summer.record.ui.main.record;

//by summer on 2018-03-27.

import android.content.Context;
import android.os.AsyncTask;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.interf.OnFinishWithObjI;
import com.android.lib.base.interf.OnLoadingInterf;
import com.android.lib.base.ope.BaseDAOpe;
import com.android.lib.bean.BaseBean;
import com.android.lib.network.bean.req.BaseReqBean;
import com.android.lib.network.bean.res.BaseResBean;
import com.android.lib.network.news.NetAdapter;
import com.android.lib.network.news.NetGet;
import com.android.lib.network.news.NetI;
import com.android.lib.network.news.UINetAdapter;
import com.android.lib.util.GsonUtil;
import com.android.lib.util.LogUtil;
import com.android.lib.util.NullUtil;
import com.android.lib.util.data.DateFormatUtil;
import com.google.gson.reflect.TypeToken;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.RecordURL;
import com.summer.record.data.Records;
import com.summer.record.tool.FileTool;
import com.yalantis.ucrop.util.FileUtils;

import org.xutils.common.util.KeyValue;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RecordDAOpe extends BaseDAOpe {

    ArrayList<Record> records;

    private Records recordsInfo;

    BDAOpe bdaOpe ;

    public RecordDAOpe(){
        LogUtil.E("RecordDAOpe");
    }


    public BDAOpe getBDAOpe(){
        if(bdaOpe==null){
            bdaOpe = new BDAOpe();
        }
       return bdaOpe;
    }


    public void getVideos(final Context context, final OnLoadingInterf i){
        i.onStarLoading(context);
        getBDAOpe().dddd();
        new AsyncTask<String, String, ArrayList<Record>>() {
            @Override
            protected ArrayList<Record> doInBackground(String... strings) {
                ArrayList<Record> videos =  FileTool.getVideos(context);
                return dealRecord(videos);
            }

            @Override
            protected void onPostExecute(ArrayList<Record> videos) {
                super.onPostExecute(videos);
                i.onStopLoading(videos);
            }
        }.execute();
    }

    public void getImages(final Context context, final OnLoadingInterf i){
        i.onStarLoading(context);
        new AsyncTask<String, String, ArrayList<Record>>() {
            @Override
            protected ArrayList<Record> doInBackground(String... strings) {
                ArrayList<Record> videos =  FileTool.getImages(context);
                return dealRecord(videos);
            }

            @Override
            protected void onPostExecute(ArrayList<Record> videos) {
                super.onPostExecute(videos);
                i.onStopLoading(videos);
            }
        }.execute();
    }



    public static ArrayList<Record> dealRecord(ArrayList<Record> videos){
        HashMap<String,ArrayList<Record>> map = new HashMap<>();
        for(int i=0;i<videos.size();i++){
            videos.get(i).init();
            String date = videos.get(i).getDateStr();
            if(map.get(date)==null){
                map.put(date,new ArrayList<Record>());
            }
            map.get(date).add(videos.get(i));
        }
        String[] strs = new String[map.keySet().size()];
        strs = map.keySet().toArray(strs);
        for(int i=0;i<strs.length;i++){
            ArrayList<Record> videos1 = map.get(strs[i]);
            if(videos1.size()%4!=0){
                int left = videos1.size()%4;
                for(int j=0;j<4-left;j++){
                    map.get(strs[i]).add(new Record("",-1,null,0l,0l,map.get(strs[i]).get(0).getUtime(),null));
                }
            }
        }
        ArrayList<Record> v = new ArrayList<>();
        strs = map.keySet().toArray(strs);
        ArrayList<Long> timestr = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat(DateFormatUtil.YYYY_MM_DD);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        for(int i=0;i<strs.length;i++){
            try {
                timestr.add(format.parse(strs[i]).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        for(int i=0;i<timestr.size();i++){
            for(int j=0;j<timestr.size()-1-i;j++){
                if(timestr.get(j)>timestr.get(j+1)){
                    long l = timestr.get(j+1);
                    timestr.set(j+1,timestr.get(j));
                    timestr.set(j,l);
                }
            }
        }
        ArrayList<String> ss = new ArrayList<>();
        for(int i=timestr.size()-1;i>=0;i--){
            Date d=new Date(timestr.get(i));
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            ss.add(format.format(d));
        }

        for(int i=0;i<ss.size();i++){
            for(int j=0;j<4;j++){
                map.get(ss.get(i)).get(j).setFrist(1);
            }
            v.addAll(map.get(ss.get(i)));
        }
        int j=0;
        for(int i =0;i<v.size();i++){
            v.get(i).setId(i);
            if(v.get(i).getLocpath()!=null){
                v.get(i).setPos(j);
                j++;
            }
        }
        map=null;
        return v;
    }

    public void downLoadRecords(final int index, final BaseUIFrag frag, final ArrayList<Record> records, final OnFinishListener onFinishListener){
        downLoadRecord(records.get(index),new NetAdapter(frag.getBaseUIAct()){
            @Override
            public void onNetFinish(boolean haveData, String url, BaseResBean baseResBean) {
                super.onNetFinish(haveData, url, baseResBean);
                int i = index+1;
                if(i>=records.size()){
                    onFinishListener.onFinish(null);
                    return;
                }else{
                    onFinishListener.onFinish(records.get(index));
                }
                downLoadRecords(i,frag,records,onFinishListener);
            }
        });
    }


    public void downLoadRecord(Record record,NetI adapter){
        String netpath = record.getNetpath();
        if(netpath.startsWith("E:")){
            netpath = netpath.substring(2,netpath.length());
        }

        NetGet.downLoadFile(RecordURL.获取文件路径(record.getNetpath().replace("\\","//")),record.locpath,adapter);
    }


    public void updateRecords(BaseUIFrag baseUIFrag, ArrayList<Record> videos, NetI<ArrayList<Record>> adapter){
        if(videos==null){
            return;
        }
        BaseReqBean baseReqBean = new BaseReqBean();
        baseReqBean.setData(GsonUtil.getInstance().toJson(videos));
        NetDataWork.Data.updateRecords(baseUIFrag.getBaseUIAct(),baseReqBean,adapter);
    }

    private int pagesize = 100;

    public void updateRecordsStep(final int index, final ArrayList<Record> records, final BaseUIFrag baseUIFrag, final ArrayList<Record> videos, final OnFinishListener adapter){
        final int start = index*pagesize;
        int end = index*pagesize+pagesize;
        if(start>=videos.size()){
            adapter.onFinish(records);
            return;
        }
        if(end>=videos.size()){
            end = videos.size();
        }
        ArrayList<Record> list = new ArrayList<>();
        for(int i=start;i<end;i++){
            list.add(videos.get(i));
        }
        final int finalEnd = end;

        updateRecords(baseUIFrag, list, new UINetAdapter<ArrayList<Record>>(baseUIFrag,UINetAdapter.Loading) {
            @Override
            public void onNetFinish(boolean haveData, String url, BaseResBean baseResBean) {
                super.onNetFinish(haveData, url, baseResBean);
                adapter.onFinish(start+""+ finalEnd);
                int i = index+1;
                ArrayList<Record> list1 = GsonUtil.getInstance().fromJson(GsonUtil.getInstance().toJson(baseResBean.getData()),new TypeToken<ArrayList<Record>>(){}.getType());
                if(list1!=null&&list1.size()!=0){
                    records.addAll(list1);
                }
                updateRecordsStep(i,records,baseUIFrag, videos, adapter);
            }
        });
    }




    public void uploadRecord(Context context, Record record, NetI<BaseBean> adapter){
        if(record==null){
            return;
        }
        List<KeyValue> list = new ArrayList<>();
        list.add(new KeyValue("file",new File(record.getLocpath())));
        list.add(new KeyValue("locpath",record.getLocpath()));
        NetDataWork.Data.uploadRecords(context,list,adapter);
    }

    public void uploadRecords(final int index , final Context context, final ArrayList<Record> list, final OnFinishListener listener){
        if(list==null||list.size()<=index){
            listener.onFinish(null);
            return;
        }
        final Record record = list.get(index);

        NetDataWork.Data.isRecordUploaded(context, record, new UINetAdapter<Record>(context) {
            @Override
            public void onResult(boolean success, String msg, Record o) {
                super.onResult(success, msg, o);
                if(!o.isUploaded()){
                    uploadRecord(context,record,new NetAdapter<BaseBean>(context){
                        @Override
                        public void onNetFinish(boolean haveData, String url, BaseResBean baseResBean) {
                            int i= index+1;
                            listener.onFinish(record);
                            uploadRecords(i,context, list, listener);
                        }
                    });
                }else{
                    int i= index+1;
                    listener.onFinish(record);
                    uploadRecords(i,context, list, listener);
                }
            }
        });

    }

    public ArrayList<Record> getNoNullRecords(ArrayList<Record> list){
        ArrayList<Record> records = new ArrayList<>();
        for(int i = 0; list!=null&&i< list.size(); i++){
            if(!NullUtil.isStrEmpty(list.get(i).getAtype())){
                records.add(list.get(i));
            }
        }
        for(int i=0;i<records.size();i++){
            records.get(i).setPos(i);
        }
        return records;
    }

    public ArrayList<Record> getUnDownloadRecords(ArrayList<Record> list){
        ArrayList<Record> records = new ArrayList<>();
        for(int i = 0; list!=null&&i< list.size(); i++){
            File file = new File(list.get(i).locpath);
            if(!file.exists()&&!NullUtil.isStrEmpty(list.get(i).netpath)){
                records.add(list.get(i));
            }
        }
        return records;
    }

}
