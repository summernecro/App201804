package com.summer.record.ui.main.record.folder;

//by summer on 2018-09-25.

import com.android.lib.base.ope.BaseValue;
import com.android.lib.util.LoadUtil;
import com.summer.record.data.Folder;
import com.summer.record.data.Record;
import com.summer.record.ui.view.UpdateIndicator;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FolderValue extends BaseValue {

    ArrayList<Record> OriRecords = new ArrayList<>();
    ArrayList<Folder> folders = new ArrayList<>();
    String atype ;
    FolderDAOpe folderDAOpe = new FolderDAOpe();

    UpdateIndicator updateIndicator = new UpdateIndicator();

    private ArrayList<String> sorts = new ArrayList<>();


    public FolderValue() {
        sorts.add("按日期排序");
        sorts.add("按标签排序");
        sorts.add("按文件夹排序");
    }

    public UpdateIndicator getUpdateIndicator() {
        return updateIndicator;
    }

    public void setUpdateIndicator(UpdateIndicator updateIndicator) {
        this.updateIndicator = updateIndicator;
    }
}
