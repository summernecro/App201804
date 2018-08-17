package com.summer.record.ui.main.image.images;

//by summer on 2018-06-25.

import android.content.Context;

import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.ope.BaseDAOpe;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.tool.FileTool;
import com.summer.record.ui.main.image.imagedetail.NetAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class ImagesDAOpe extends BaseDAOpe {

    public void getMaxMinYear(Context context,final OnFinishListener listener){
        NetDataWork.Data.getMaxMinYear(context, Record.ATYPE_IMAGE,new NetAdapter<int[]>(context){
            @Override
            public void onSuccess(int[] o) {
                super.onSuccess(o);
                listener.onFinish(getYears(context,o));
            }

            @Override
            public void onFail(boolean haveData, String msg) {
                super.onFail(haveData, msg);
                listener.onFinish(getYears(context,null));
            }
        });
    }


    public ArrayList<String[]> getYears(Context context,int[] o){
        int[] y = FileTool.getTime(context);
        int start = y[1];
        int end = y[0];
        if(o!=null){
            start = Math.max(y[1],o[1]);
            end = Math.min(y[0],o[0]);
        }
        ArrayList<Integer> years = new ArrayList<>();
        while (start>=end){
            int a = start;
            years.add(a);
            --start;
        }
        ArrayList<String[]> strs = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for(int i=0;i<years.size();i++){
            calendar.set(Calendar.YEAR,years.get(i));
            calendar.set(Calendar.MONTH,Calendar.JANUARY);
            calendar.set(Calendar.DAY_OF_MONTH,1);


            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(Calendar.YEAR,years.get(i)+1);
            calendar2.set(Calendar.MONTH,Calendar.JANUARY);
            calendar2.set(Calendar.DAY_OF_MONTH,0);

            strs.add(new String[]{calendar.getTime().getTime()/1000+"",calendar2.getTime().getTime()/1000+""});
        }
        return strs;
    }



}
