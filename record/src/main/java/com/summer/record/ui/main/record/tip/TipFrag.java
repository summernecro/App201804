package com.summer.record.ui.main.record.tip;

import android.view.View;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.fragment.FragUtil;
import com.android.lib.network.news.UINetAdapter;
import com.android.lib.network.newsf.UIFNetAdapter;
import com.android.lib.util.LoadUtil;
import com.summer.record.R;
import com.summer.record.data.Folder;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.Tiplab;
import com.summer.record.ui.main.record.folder.FolderFrag;
import com.summer.record.ui.main.record.record.RecordFrag;
import com.summer.record.ui.view.UpdateIndicator;

import java.util.ArrayList;

public class TipFrag extends BaseUIFrag<TipUIOpe,TipValue> {

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
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.item_record_tiplab:
                LoadUtil.getInstance().startLoading(getContext(), getBaseUIRoot(),"");
                Tiplab tiplab = (Tiplab) v.getTag(R.id.data);
                NetDataWork.Tip.getImageRecordsFromTip(getBaseUIAct(), tiplab, new UIFNetAdapter<ArrayList<Record>>(getBaseUIFrag()) {

                    @Override
                    public boolean onNetStart(String url, String gson) {
                        return super.onNetStart(url, gson);
                    }

                    @Override
                    public void onResult(boolean success, String msg, ArrayList<Record> o) {
                        super.onResult(success, msg, o);
                        LoadUtil.getInstance().stopLoading(getBaseUIRoot());
                        if(success){
                            FragUtil.getInstance().start(getBaseUIAct(),TipFrag.this,Record.ATYPE_IMAGE,RecordFrag.getInstance(null,Record.ATYPE_IMAGE,null, o));
                        }
                    }
                });
                break;
        }
    }
}
