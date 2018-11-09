package com.summer.record.ui.main.welcome;

import com.android.lib.GlideApp;
import com.android.lib.base.ope.BaseUIOpe;
import com.summer.record.data.Welcome;
import com.summer.record.databinding.ActWelBinding;

public class WelUIOpe extends BaseUIOpe<ActWelBinding> {

    public void load(String str){
        GlideApp.with(getActivity()).asBitmap().centerCrop().load(str).into(getBind().welImage);
    }
}
