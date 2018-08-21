package com.summer.record.ui.main.main;

//by summer on 2018-03-27.

import android.support.v4.app.Fragment;

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.ope.BaseValue;
import com.android.lib.util.LoadUtil;
import com.summer.record.R;
import com.summer.record.data.Tiplab;
import com.summer.record.ui.main.image.images.ImagesFrag;
import com.summer.record.ui.main.sett.SettFrag;
import com.summer.record.ui.main.text.text.TextFrag;
import com.summer.record.ui.main.video.videos.VideosFrag;
import com.summer.record.ui.view.UpdateIndicator;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MainValue extends BaseValue{

    public static final String 视频 = "视频";

    public static final String 图片 = "图片";

    public static final String 文字 = "文字";

    public static final String 设置 = "设置";

    public static final int 视频ID  = R.id.contain_video;

    public static final int 图片ID = R.id.contain_image;

    public static final int 文字ID = R.id.contain_text;

    public static final int 设置ID = R.id.contain_setting;

    public static final String[] 模块 = new String[]{视频,图片,文字,设置};

    public static final int[] 模块ID = new int[]{视频ID,图片ID,文字ID,设置ID};

    private ArrayList<BaseUIFrag> fragments = new ArrayList<>();

    private int pos = 0;

    private ArrayList<Tiplab> tiplabs = new ArrayList<>();

    public MainValue(){
        fragments.add(new VideosFrag());
        fragments.add(new ImagesFrag());
        fragments.add(new TextFrag());
        //fragments.add(new SettFrag());
    }




}
