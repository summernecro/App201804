package com.summer.record.ui.main.video.videos;

//by summer on 2018-06-26.

import android.support.v4.app.Fragment;

import com.android.lib.base.ope.BaseValue;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class VideosValue extends BaseValue {

    @Getter
    @Setter
    private ArrayList<Fragment> videoFrags = new ArrayList<>();
}
