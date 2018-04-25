package com.summer.record.ui.main.main;

//by summer on 2018-03-27.

import android.view.View;

import com.android.lib.base.interf.view.OnAppItemSelectListener;
import com.android.lib.base.ope.BaseUIOpe;
import com.android.lib.view.bottommenu.BottomMenuBean;
import com.summer.record.R;
import com.summer.record.databinding.ActMainBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainUIOpe extends BaseUIOpe<ActMainBinding>{

    ArrayList<BottomMenuBean> bottomMenuBeans = new ArrayList<>();


    @Override
    public void initUI() {
        super.initUI();

        bottomMenuBeans.add(new BottomMenuBean("视频", R.drawable.drawable_record_main_bottom_video,null,getBind().containVideo,getActivity().getResources().getColorStateList(R.color.color_white_blue)));
        bottomMenuBeans.add(new BottomMenuBean("图片", R.drawable.drawable_record_main_bottom_image,null,getBind().containImage,getActivity().getResources().getColorStateList(R.color.color_white_blue)));
        bottomMenuBeans.add(new BottomMenuBean("文字", R.drawable.drawable_record_main_bottom_text,null,getBind().containText,getActivity().getResources().getColorStateList(R.color.color_white_blue)));
        bottomMenuBeans.add(new BottomMenuBean("设置", R.drawable.drawable_record_main_bottom_setting,null,getBind().containSetting,getActivity().getResources().getColorStateList(R.color.color_white_blue)));
        getBind().bottommenu.initItems(bottomMenuBeans);
        if(getActivity() instanceof OnAppItemSelectListener){
            getBind().bottommenu.setOnAppItemClickListener((OnAppItemSelectListener)getActivity());
        }
    }

    public void showView(int pos){
       for(int i=0;i<bottomMenuBeans.size();i++){
           if(i==pos){
               bottomMenuBeans.get(i).getContainerView().setVisibility(View.VISIBLE);
           }else{
               bottomMenuBeans.get(i).getContainerView().setVisibility(View.GONE);
           }
       }
    }
}
