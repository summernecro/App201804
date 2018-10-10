package com.summer.record.ui.main.main;

//by summer on 2018-03-27.

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.ope.BaseValue;
import com.android.lib.view.bottommenu.BottomMenuBean;
import com.summer.record.R;
import com.summer.record.data.Record;
import com.summer.record.data.Tiplab;
import com.summer.record.ui.main.record.folder.FolderFrag;
import com.summer.record.ui.main.record.records.RecordsFrag;
import com.summer.record.ui.main.record.records.RecordsFrag;
import com.summer.record.ui.main.text.text.TextFrag;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainValue extends BaseValue{

    public static final String 视频 = Record.ATYPE_VIDEO;

    public static final String 图片 = Record.ATYPE_IMAGE;

    public static final String 文字 = Record.ATYPE_TEXT;

    public static final int 视频ID  = R.integer.id_video;

    public static final int 图片ID = R.integer.id_image;

    public static final int 文字ID = R.integer.id_text;

    public static final String[] 模块 = new String[]{视频,图片,文字};

    public static final int[] 模块ID = new int[]{视频ID,图片ID,文字ID};

    private ArrayList<BaseUIFrag> fragments = new ArrayList<>();

    private int pos = 0;

    private ArrayList<Tiplab> tiplabs = new ArrayList<>();

    ArrayList<BottomMenuBean> bottomMenuBeans = new ArrayList<>();

    public static ArrayList<String> sorts = new ArrayList<>();

    private MainDAOpe mainDAOpe = new MainDAOpe();


    public MainValue(){
        fragments.add(RecordsFrag.getInstance(Record.ATYPE_VIDEO));
        fragments.add(RecordsFrag.getInstance(Record.ATYPE_IMAGE));
        fragments.add(new TextFrag());
        sorts.clear();
        sorts.add("按日期排序");
        sorts.add("按标签排序");
        sorts.add("按文件夹排序");
    }



}
