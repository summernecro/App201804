package com.summer.record.ui.main.record.folder;

//by summer on 2018-09-25.

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.fragment.FragUtil;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.interf.OnLoadingAdapter;
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

public class FolderFrag extends BaseUIFrag<FolderUIOpe,FolderValue>{


    public static FolderFrag getInstance(String atype){
        FolderFrag folderFrag = new FolderFrag();
        folderFrag.getArguments().putString("atype",atype);
        return folderFrag;
    }

    @Override
    public void initNow() {
        super.initNow();
        getPV().setAtype(getArguments().getString("atype"));
        getPV().getFolderDAOpe().getImages(getBaseUIAct(),getPV().getAtype(), new OnLoadingAdapter() {
            @Override
            public void onStarLoading(Object o) {
                ((UpdateIndicator)getPV().getLoadUtil().getIndicator()).setContext(getContext());
                getPV().getLoadUtil().startLoadingDefault(getContext(), getBaseUIRoot(),getResources().getColor(R.color.color_red_500));
            }

            @Override
            public void onStopLoading(Object o) {
                getPV().setOriRecords((ArrayList<Record>) o);
                getPV().getFolderDAOpe().sortRecordByFolder(getPV().getOriRecords(), new OnFinishListener() {
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
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.item_record_folder:
            Folder folder = (Folder) v.getTag(R.id.data);
                FragUtil.getInstance().start(getBaseUIAct(),FolderFrag.this,getPV().getAtype(),RecordFrag.getInstance(null,getPV().getAtype(),null, folder.getRecords()));

            break;
        }
    }
}
