package com.summer.record.ui.main.image.images;

//by summer on 2018-06-25.

import android.support.v4.app.Fragment;

import com.android.lib.base.ope.BaseValue;
import com.summer.record.data.Tiplab;
import com.summer.record.ui.main.image.image.ImageFrag;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ImagesValue extends BaseValue {


    private ArrayList<Fragment> imageFrags = new ArrayList<>();


    ArrayList<Tiplab> tiplabs = new ArrayList<>();


}
