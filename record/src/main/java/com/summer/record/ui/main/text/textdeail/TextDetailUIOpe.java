package com.summer.record.ui.main.text.textdeail;

//by summer on 2018-04-26.

import com.android.lib.base.ope.BaseUIOpe;
import com.summer.record.data.Record;
import com.summer.record.databinding.FragTextTextdetailBinding;

public class TextDetailUIOpe extends BaseUIOpe<FragTextTextdetailBinding>{


    public void initText(Record record){
        getBind().ivText.setText(record.getContent());
    }
}
