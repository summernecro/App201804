package com.summer.record.ui.main.record.image;

//by summer on 2018-06-25.

import com.android.lib.base.ope.BaseValue;
import com.summer.record.data.Record;
import com.summer.record.data.Tiplab;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ImageDetailValue extends BaseValue {

    private int[] currentPos = new int[1];

    private ArrayList<Record> images;

    ArrayList<Tiplab> tiplabs = new ArrayList<>();
}
