package com.summer.record.ui.main.record.tip;

import android.view.View;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.network.news.UINetAdapter;
import com.android.lib.network.newsf.UIFNetAdapter;
import com.android.lib.util.fragment.two.FragManager2;
import com.summer.record.R;
import com.summer.record.data.Folder;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.Tiplab;
import com.summer.record.ui.main.record.folder.FolderFrag;
import com.summer.record.ui.main.record.record.RecordFrag;
import com.summer.record.ui.view.UpdateIndicator;

import java.util.ArrayList;

public class TipFrag extends BaseUIFrag<TipUIOpe,TipValue> implements ViewListener {

    public static TipFrag getInstance(String atype){
        TipFrag tipFrag = new TipFrag();
        tipFrag.getArguments().putString("atype",atype);
        return tipFrag;
    }

    @Override
    public void initNow() {
        super.initNow();
        NetDataWork.Tip.getAllTipLabs(getBaseUIAct(), new UINetAdapter<ArrayList<Tiplab>>(this) {
            @Override
            public boolean onNetStart(String url, String gson) {
                return super.onNetStart(url, gson);
            }

            @Override
            public void onResult(boolean success, String msg, ArrayList<Tiplab> o) {
                super.onResult(success, msg, o);
                if(success){
                    getPU().loadRecordByFolder(o,TipFrag.this);
                }
            }
        });
    }

    @Override
    public void onInterupt(int i, View view) {
        switch (i){
            case  ViewListener.TYPE_ONCLICK:
                ((UpdateIndicator)getPV().getLoadUtil().getIndicator()).setContext(getContext());
                getPV().getLoadUtil().startLoadingDefault(getContext(), getBaseUIRoot(),getResources().getColor(R.color.color_red_500));
                Tiplab tiplab = (Tiplab) view.getTag(R.id.data);
                NetDataWork.Tip.getImageRecordsFromTip(getBaseUIAct(), tiplab, new UIFNetAdapter<ArrayList<Record>>(getBaseUIFrag()) {

                    @Override
                    public boolean onNetStart(String url, String gson) {
                        return super.onNetStart(url, gson);
                    }

                    @Override
                    public void onResult(boolean success, String msg, ArrayList<Record> o) {
                        super.onResult(success, msg, o);
                        getPV().getLoadUtil().stopLoading(getBaseUIRoot());
                        if(success){
                            FragManager2.getInstance().setAnim(false).start(getBaseUIAct(),Record.ATYPE_IMAGE,RecordFrag.getInstance(null,Record.ATYPE_IMAGE,null, o));
                        }
                    }
                });
                break;
        }
    }
}
