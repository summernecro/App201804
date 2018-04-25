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
import java.util.List;

public class NetDataWork {

    public static class Data{

        public static void updateRecords(Context context, BaseBean baseBean, NetI<ArrayList<Record>> adapter){
            NetGet.postData(context,RecordURL.获取地址("/record/updateRecords"),baseBean,adapter);
        }

        public static void getAllRecords(Context context,String atype, NetI<ArrayList<Record>> adapter){
            Record record = new Record();
            record.setAtype(atype);
            NetGet.getData(context,RecordURL.获取地址("/record/getAllRecords"),record,adapter);
        }

        public static void getRecordInfo(Context context,String atype, NetI<Records> adapter){
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

    }




}
