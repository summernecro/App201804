package com.summer.record.ui.main.video.videos;

//by summer on 2018-06-26.

import android.content.Context;

import com.android.lib.base.ope.BaseDAOpe;
import com.summer.record.tool.FileTool;

import java.util.ArrayList;
import java.util.Calendar;

public class VideosDAOpe extends BaseDAOpe {

    public ArrayList<String[]> getYears(Context context){
        int[] y = FileTool.getTime(context);
        int start = y[0];
        ArrayList<Integer> years = new ArrayList<>();
        while (start<=y[1]){
            int a = start;
            years.add(a);
            ++start;
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
