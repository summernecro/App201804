package com.summer.record.ui.main.record.folder;

//by summer on 2018-09-25.

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.interf.OnLoadingAdapter;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.util.fragment.two.FragManager2;
import com.summer.record.R;
import com.summer.record.data.Folder;
import com.summer.record.data.Record;
import com.summer.record.tool.DateUtil;
import com.summer.record.tool.TitleUtil;
import com.summer.record.ui.main.main.MainValue;
import com.summer.record.ui.main.record.image.ImageDetailFrag;
import com.summer.record.ui.main.record.record.RecordFrag;
import com.summer.record.ui.main.record.record.RecordValue;
import com.summer.record.ui.main.record.records.RecordsFrag;
import com.summer.record.ui.view.UpdateIndicator;

import java.util.ArrayList;

import butterknife.OnClick;
import butterknife.Optional;

public class FolderFrag extends BaseUIFrag<FolderUIOpe,FolderDAOpe,FolderValue> implements ViewListener{


    public static FolderFrag getInstance(String atype){
        FolderFrag folderFrag = new FolderFrag();
        folderFrag.getArguments().putString("atype",atype);
        return folderFrag;
    }

    @Override
    public void initNow() {
        super.initNow();
        getPV().setAtype(getArguments().getString("atype"));
        getPD().getImages(getBaseAct(),getPV().getAtype(), new OnLoadingAdapter() {
            @Override
            public void onStarLoading(Object o) {
                ((UpdateIndicator)getPV().getLoadUtil().getIndicator()).setContext(getContext());
                getPV().getLoadUtil().startLoadingDefault(getContext(), getBaseUIRoot(),getResources().getColor(R.color.color_red_500));
            }

            @Override
            public void onStopLoading(Object o) {
                getPV().setOriRecords((ArrayList<Record>) o);
                getP().getD().sortRecordByFolder(getPV().getOriRecords(), new OnFinishListener() {
                    @Override
                    public void onFinish(Object o) {
                        getPV().setFolders((ArrayList<Folder>) o);
                        getPU().loadRecordByFolder(getPV().getFolders(),FolderFrag.this);
                        getPV().getLoadUtil().stopLoading(getBaseUIRoot());
                    }
                });
            }

            @Override
            public void onProgress(Object o) {
                super.onProgress(o);
                ((UpdateIndicator)getPV().getLoadUtil().getIndicator()).setProgress(o.toString());
            }
        });
    }

    @Override
    public void onInterupt(int i, View view) {
        switch (i){
            case  ViewListener.TYPE_ONCLICK:
                Folder folder = (Folder) view.getTag(R.id.data);
                FragManager2.getInstance().setAnim(false).start(getBaseUIAct(),getPV().getAtype(),RecordFrag.getInstance(null,getPV().getAtype(),null, folder.getRecords()));
                break;
        }
    }
}
