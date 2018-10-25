package com.summer.record.ui.main.main;

//by summer on 2018-03-27.

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.lib.base.activity.BaseUIActivity;
import com.android.lib.base.adapter.AppsDataBindingAdapter;
import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.fragment.FragUtil;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.interf.view.OnAppItemSelectListener;
import com.android.lib.base.listener.BaseTextWather;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.base.ope.BaseUIOpe;
import com.android.lib.util.ScreenUtil;
import com.android.lib.util.StringUtil;
import com.android.lib.view.bottommenu.BottomMenuBean;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.summer.record.BR;
import com.summer.record.R;
import com.summer.record.data.Tiplab;
import com.summer.record.databinding.ActMainBinding;
import com.summer.record.databinding.ItemRecordTitleSearchBinding;
import com.summer.record.tool.TitleUtil;
import com.transitionseverywhere.utils.AnimatorUtils;
import com.transitionseverywhere.utils.ViewUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.ButterKnife;

public class MainUIOpe extends BaseUIOpe<ActMainBinding>{

    ArrayList<BottomMenuBean> bottomMenuBeans = new ArrayList<>();

    @Override
    public void initUI() {
        super.initUI();
        bottomMenuBeans.add(new BottomMenuBean("视频", R.drawable.drawable_record_main_bottom_video,null,getBind().containVideo,getActivity().getResources().getColorStateList(R.color.color_white_black)));
        bottomMenuBeans.add(new BottomMenuBean("图片", R.drawable.drawable_record_main_bottom_image,null,getBind().containImage,getActivity().getResources().getColorStateList(R.color.color_white_black)));
        bottomMenuBeans.add(new BottomMenuBean("文字", R.drawable.drawable_record_main_bottom_text,null,getBind().containText,getActivity().getResources().getColorStateList(R.color.color_white_black)));
        getBind().bottommenu.initItems(bottomMenuBeans);
        if(getActivity() instanceof OnAppItemSelectListener){
            getBind().bottommenu.setOnAppItemClickListener((OnAppItemSelectListener)getActivity());
            getBind().bottommenu.setIndex(0);
        }

        getBind().containVideo.setMoudleid(MainValue.视频ID);
        getBind().containVideo.setMoudle(MainValue.视频);
        getBind().containVideo.getActMainABinding().amain.setId(MainValue.视频ID);
        getBind().containImage.setMoudleid(MainValue.图片ID);
        getBind().containImage.setMoudle(MainValue.图片);
        getBind().containImage.getActMainABinding().amain.setId(MainValue.图片ID);
        getBind().containText.setMoudleid(MainValue.文字ID);
        getBind().containText.setMoudle(MainValue.文字);
        getBind().containText.getActMainABinding().amain.setId(MainValue.文字ID);
    }

    public void showView(int pos,int beforepos){
        if(beforepos==pos){
            return;
        }
       for(int i=0;i<bottomMenuBeans.size();i++){
           if(i==pos){
               bottomMenuBeans.get(i).getContainerView().setVisibility(View.VISIBLE);
               ViewAnimator.animate(((AView)bottomMenuBeans.get(i).getContainerView()).getActMainABinding().amain).duration(300).fadeIn().start();
           }else{
               final int finalI = i;
               bottomMenuBeans.get(finalI).getContainerView().setVisibility(View.GONE);
           }
       }
    }

    public void initFrag(BaseUIActivity activity, ArrayList<BaseUIFrag> fragments, int[] 模块ID, String[] 模块){
        if(fragments.size()==0){
            return;
        }
        for(int i=0;i<fragments.size();i++){
            FragUtil.getInstance().start(activity,null,模块[i],模块ID[i],fragments.get(i));
        }
        showView(0,-1);
    }


    public void updateTitle(Object o){
        //getBind().recordtitle.tvLab.setText(StringUtil.getStr(o));
    }

    public void setTitleAndBottomVisible(boolean visible){
        getBind().bottommenu.setVisibility(visible?View.VISIBLE:View.GONE);
        getBind().containImage.getActMainABinding().title.getRoot().setVisibility(visible?View.VISIBLE:View.GONE);
    }

    public void swithTitleAndBottomVisible(){
            setTitleAndBottomVisible(getBind().bottommenu.getVisibility()==View.VISIBLE?false:true);
    }

    public void changeRightImage2(int res){
       // getBind().recordtitle.tvRefresh.setBackgroundResource(res);
    }

    public void changeRightImage3(int res){
       // getBind().recordtitle.tvDown.setBackgroundResource(res);
    }



}
