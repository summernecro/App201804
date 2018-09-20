package com.summer.record.tool;

//by summer on 2018-09-17.

import android.view.View;

public class ViewTool {

    public static boolean switchView(View v){
        if(v.getVisibility()!=View.VISIBLE){
            v.setVisibility(View.VISIBLE);
            return true;
        }else{
            v.setVisibility(View.GONE);
            return false;
        }
    }

    public static void setVisible(View v,boolean visible){
        v.setVisibility(visible?View.VISIBLE:View.GONE);
    }
}
