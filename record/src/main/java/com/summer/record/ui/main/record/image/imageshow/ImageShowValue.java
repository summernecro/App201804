package com.summer.record.ui.main.record.image.imageshow;

//by summer on 2018-06-25.

import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.ope.BaseValue;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class ImageShowValue extends BaseValue {

    ArrayList<BaseUIFrag> baseUIFrags = new ArrayList<>();

    public void initFrag(BaseUIFrag baseUIFrag){
        if(baseUIFrags.size()==0){
            baseUIFrags.add(baseUIFrag);
        }
    }

    public BaseUIFrag getBaseUIFrag() {
        return baseUIFrags.get(0);
    }
}
