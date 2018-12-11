package com.summer.record.data;

//by summer on 2018-03-30.

import android.content.Context;

import com.android.lib.bean.BaseBean;
import com.android.lib.network.bean.req.BaseReqBean;
import com.android.lib.network.news.NetGet;
import com.android.lib.network.news.NetI;
import com.android.lib.util.GsonUtil;

import org.xutils.common.util.KeyValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NetDataWork {

    public static class Data{

        public static void updateRecords(Context context, BaseBean baseBean, NetI<ArrayList<Record>> adapter){
            NetGet.postData(context,RecordURL.获取地址("/record/updateRecords"),baseBean,adapter);
        }


        public static void updateRecords(Context context, ArrayList<Record> records, NetI<ArrayList<Record>> adapter){
            BaseReqBean baseReqBean = new BaseReqBean();
            baseReqBean.setData(GsonUtil.getInstance().toJson(records));
            NetGet.postData(context,RecordURL.获取地址("/record/updateRecords"),baseReqBean,adapter);
        }

        public static void getAllRecords(Context context,String atype, String[] timedu,NetI<ArrayList<Record>> adapter){
            Record record = new Record();
            record.setAtype(atype);
            record.setStartTime(timedu[0]+"000");
            record.setEndTime(timedu[1]+"000");
            NetGet.getData(context,RecordURL.获取地址("/record/getAllRecords"),record,adapter);
        }

        public static void getAllRecords(Context context,String atype,NetI<ArrayList<Record>> adapter){
            Record record = new Record();
            record.setAtype(atype);
            NetGet.getData(context,RecordURL.获取地址("/record/getAllRecords"),record,adapter);
        }

        public static void getRecordInfo(Context context,String atype,String[] timedu, NetI<Records> adapter){
            Record record = new Record();
            record.setAtype(atype);
            record.setStartTime(timedu[0]+"000");
            record.setEndTime(timedu[1]+"000");
            NetGet.getData(context,RecordURL.获取地址("/record/getRecordInfo"),record,adapter);
        }

        public static void getRecordInfo(Context context,String atype,  NetI<Records> adapter){
            Record record = new Record();
            record.setAtype(atype);
            NetGet.getData(context,RecordURL.获取地址("/record/getRecordInfo"),record,adapter);
        }


        public static void uploadRecords(Context context, List<KeyValue> list, NetI<BaseBean> adapter){
            NetGet.file(context,RecordURL.获取地址("/record/uploadRecords"),list,adapter);
        }

        public static void isRecordUploaded(Context context, Record record, NetI<Record> adapter){
            BaseReqBean baseReqBean = new BaseReqBean();
            baseReqBean.setData(GsonUtil.getInstance().toJson(record));
            NetGet.postData(context,RecordURL.获取地址("/record/isRecordUploaded"),baseReqBean,adapter);
        }

        public static void getMaxMinYear(Context context, String atype,NetI<int[]> adapter){
            Record record = new Record();
            record.setAtype(atype);
            NetGet.getData(context,RecordURL.获取地址("/record/getMaxMinYear"),record,adapter);
        }

    }

    public static class Tip{

        public static void addRecordTipsInfo(Context context, Record record, NetI<Record> adapter){
            BaseReqBean baseReqBean = new BaseReqBean();
            baseReqBean.setData(GsonUtil.getInstance().toJson(record));
            NetGet.postData(context,RecordURL.获取地址("/record/addRecordTipsInfo"),baseReqBean,adapter);
        }

        public static void addTextTipsInfo(Context context, Record record, NetI<Record> adapter){
            BaseReqBean baseReqBean = new BaseReqBean();
            baseReqBean.setData(GsonUtil.getInstance().toJson(record));
            NetGet.postData(context,RecordURL.获取地址("/record/addTextTipsInfo"),baseReqBean,adapter);
        }


        public static void getLikeTiplab(Context context,String content, NetI<ArrayList<Tiplab>> adapter){
            BaseReqBean baseReqBean = new BaseReqBean();
            Tiplab tiplab = new Tiplab();
            tiplab.setContent(content);
            baseReqBean.setData(GsonUtil.getInstance().toJson(tiplab));
            NetGet.postData(context,RecordURL.获取地址("/tip/getLikeTiplab"),baseReqBean,adapter);
        }

        public static void getRecordTips(Context context, Record record, NetI<ArrayList<Tiplab>> adapter){
            BaseReqBean baseReqBean = new BaseReqBean();
            baseReqBean.setData(GsonUtil.getInstance().toJson(record));
            NetGet.postData(context,RecordURL.获取地址("/tip/getRecordTips"),baseReqBean,adapter);
        }


        public static void getRecordsFromTip(Context context, Tiplab tiplab, NetI<ArrayList<Record>> adapter){
            BaseReqBean baseReqBean = new BaseReqBean();
            baseReqBean.setData(GsonUtil.getInstance().toJson(tiplab));
            NetGet.postData(context,RecordURL.获取地址("/tip/getRecordsFromTip"),baseReqBean,adapter);
        }


        public static void getImageRecordsFromTip(Context context, Tiplab tiplab, NetI<ArrayList<Record>> adapter){
            BaseReqBean baseReqBean = new BaseReqBean();
            baseReqBean.setData(GsonUtil.getInstance().toJson(tiplab));
            NetGet.postData(context,RecordURL.获取地址("/tip/getImageRecordsFromTip"),baseReqBean,adapter);
        }

        public static void getAllTipLabs(Context context, NetI<ArrayList<Tiplab>> adapter){
            BaseReqBean baseReqBean = new BaseReqBean();
            NetGet.getData(context,RecordURL.获取地址("/tip/getAllTipLabs"),baseReqBean,adapter);
        }

    }

    public static class Crash{

        public static void insert(Context context, com.summer.record.data.Crash crash, NetI<Boolean> adapter){
            BaseReqBean baseReqBean = new BaseReqBean();
            baseReqBean.setData(GsonUtil.getInstance().toJson(crash));
            NetGet.postData(context,RecordURL.获取地址("/record/crash"),baseReqBean,adapter);
        }
    }

    public static class Welcome{
        public static void getWelUrl(Context context, NetI<com.summer.record.data.Welcome> adapter){
            BaseReqBean baseReqBean = new BaseReqBean();
            NetGet.getData(context,RecordURL.获取地址("/welcome/getWelUrl"),baseReqBean,adapter);
        }
    }




}
