package com.summer.record.ui.main.text.textdeail;

//by summer on 2018-06-25.

import com.android.lib.base.ope.BaseValue;
import com.summer.record.data.Record;
import com.summer.record.data.Tiplab;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextDetailValue extends BaseValue {

    private Record record;

    private ArrayList<Tiplab> tiplabs = new ArrayList<>();
}
