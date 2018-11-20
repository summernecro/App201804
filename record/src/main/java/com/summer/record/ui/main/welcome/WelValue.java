package com.summer.record.ui.main.welcome;

import android.icu.util.ULocale;

import com.android.lib.base.ope.BaseValue;
import com.android.lib.util.NullUtil;
import com.android.lib.util.SPUtil;
import com.android.lib.util.StringUtil;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WelValue extends BaseValue {

    public String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1541743855288&di=b93470b8669e1022e8e348af98abb8b1&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fmobile%2F2018-09-17%2F5b9f602936f11.jpg%3Fdown"
    ;

    public static final String welurl = "welurl";

    @Override
    public void initValue() {
        super.initValue();
        String url  = SPUtil.getInstance().getStr(welurl);
        if(!NullUtil.isStrEmpty(url)){
            this.url = url;
        }
    }
}
