package com.summer.record.ui.main.record.tip;

import com.android.lib.base.ope.BaseValue;
import com.android.lib.util.LoadUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TipValue extends BaseValue {

    private String atype;

    LoadUtil loadUtil= new LoadUtil();
}