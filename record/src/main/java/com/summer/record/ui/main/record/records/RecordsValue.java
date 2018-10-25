package com.summer.record.ui.main.record.records;

//by summer on 2018-06-25.

import android.support.v4.app.Fragment;

import com.android.lib.base.ope.BaseValue;
import com.summer.record.data.Tiplab;
import com.summer.record.ui.main.record.folder.FolderFrag;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class RecordsValue extends BaseValue {

    public String type;

    private ArrayList<Fragment> imageFrags = new ArrayList<>();


    ArrayList<Tiplab> tiplabs = new ArrayList<>();

    private ArrayList<Integer> pos = new ArrayList<>();

    private ArrayList<String> sorts = new ArrayList<>();

    ArrayList<FolderFrag> folderFrags = new ArrayList<>();

    RecordsDAOpe recordsDAOpe = new RecordsDAOpe();

    public RecordsValue() {
        sorts.add("按日期排序");
        sorts.add("按标签排序");
        sorts.add("按文件夹排序");
        pos.add(0,0);
    }

    public FolderFrag getFolderFrag() {
        if(folderFrags.size()==0){
            folderFrags.add(new FolderFrag());
        }
        return folderFrags.get(0);
    }
}
