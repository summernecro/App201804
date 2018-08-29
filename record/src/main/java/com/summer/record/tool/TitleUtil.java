package com.summer.record.tool;

//by summer on 2018-08-29.

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.android.lib.util.ScreenUtil;

public class TitleUtil {


    public void initTitle(Context context, View view){
        view.setPadding(0, (int) ScreenUtil.getInstance().getStatusBarHeight(context),0,0);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = (int) (ScreenUtil.getInstance().getStatusBarHeight(context)+ScreenUtil.最小DIMEN*38);
        view.setLayoutParams(params);
    }
}
