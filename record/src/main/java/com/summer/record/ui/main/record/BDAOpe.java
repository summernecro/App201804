package com.summer.record.ui.main.record;

//by summer on 2018-04-08.

import com.android.lib.base.ope.BaseDAOpe;
import com.android.lib.util.LogUtil;

public class BDAOpe extends BaseDAOpe {

    RecordDAOpe recordDAOpe;


    public BDAOpe(){
        LogUtil.E("BDAOpe");
    }

    public void dddd(){
        if(recordDAOpe == null){
            recordDAOpe = new RecordDAOpe();
        }
    }
}
