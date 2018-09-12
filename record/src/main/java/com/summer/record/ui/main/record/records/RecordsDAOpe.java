package com.summer.record.ui.main.record.records;

//by summer on 2018-06-25.

import android.content.Context;

import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.ope.BaseDAOpe;
import com.summer.record.data.NetDataWork;
import com.summer.record.tool.DBTool;
import com.summer.record.tool.FileTool;
import com.summer.record.ui.main.record.image.NetAdapter;

import java.util.ArrayList;
import java.util.Calendar;

public class RecordsDAOpe extends BaseDAOpe {

    /**
     * 根据类型获取记录表最大最小两个年数值
     * @param context
     * @param type
     * @param listener
     */
    public void getMaxMinYear(Context context, final String type, final OnFinishListener listener){
        NetDataWork.Data.getMaxMinYear(context, type,new NetAdapter<int[]>(context){
            @Override
            public void onSuccess(int[] o) {
                super.onSuccess(o);
                listener.onFinish(getYears(context,type,o));
            }

            @Override
            public void onFail(boolean haveData, String msg) {
                super.onFail(haveData, msg);
                listener.onFinish(getYears(context,type, DBTool.getMaxMinYear(type)));
            }
        });
    }


    public ArrayList<String[]> getYears(Context context,String type,int[] o){
        int[] y = FileTool.getTime(context,type);
        int start = y[1];
        int end = y[0];
        if(o!=null&&o[0]!=0&&o[1]!=0){
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
