package com.summer.record.ui.main.image.images;

//by summer on 2018-06-25.

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.lib.GlideApp;
import com.android.lib.base.adapter.AppBasePagerAdapter2;
import com.android.lib.base.adapter.AppsDataBindingAdapter;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.listener.BaseOnPagerChangeListener;
import com.android.lib.base.listener.BaseTextWather;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.base.ope.BaseUIOpe;
import com.android.lib.util.StringUtil;
import com.summer.record.BR;
import com.summer.record.R;
import com.summer.record.data.Tiplab;
import com.summer.record.databinding.FragMainImagesBinding;
import com.summer.record.databinding.ItemRecordTitleSearchBinding;
import com.summer.record.ui.main.image.image.ImageFrag;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ImagesUIOpe extends BaseUIOpe<FragMainImagesBinding> {


    @Override
    public void initUI() {
        super.initUI();
        addSearhView();
//        GlideApp.with(getActivity()).asBitmap()
//                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530075649656&di=dd57d7701506ea32e336a6c2424c26eb&imgtype=0&src=http%3A%2F%2Fpic1.win4000.com%2Fmobile%2Fd%2F53917b6fcaa98_200_300.jpg")
//                .into(getBind().ivBg);
        getBind().ivBg.setBackgroundColor(getActivity().getResources().getColor(R.color.color_grey_200));
    }

    public void initViewPager(FragmentManager fm, Context context, final List<Fragment> fragments){
        getBind().viewpager.setAdapter(new AppBasePagerAdapter2(fm, context, fragments));
        getBind().viewpager.addOnPageChangeListener(new BaseOnPagerChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateTitle(((ImageFrag)fragments.get(position)).getP().getV().getTitleStr());
            }
        });
    }

    public void initFrag(ImagesFrag imagesFrag,ArrayList<String[]> strs,ArrayList<Fragment> imageFrags ){
        for(int i=0;i<strs.size();i++){
            ImageFrag imageFrag = ImageFrag.getInstance(strs.get(i),imagesFrag);
            imageFrags.add(imageFrag);
        }
    }

    public ImageFrag getCurrentFrag(ArrayList<Fragment> imageFrags){
        return (ImageFrag) imageFrags.get(getBind().viewpager.getCurrentItem());
    }

    public void updateTitle(Object o){
        TextView textView = getView().findViewById(R.id.tv_lab);
        textView.setText(StringUtil.getStr(o));
    }

    public void addSearhView(){
        ViewGroup viewGroup = (ViewGroup) getView();
        ItemRecordTitleSearchBinding itemRecordTitleSearchBinding = ItemRecordTitleSearchBinding.inflate(LayoutInflater.from(getActivity()));
        itemRecordTitleSearchBinding.getRoot().setVisibility(View.GONE);
        viewGroup.addView(itemRecordTitleSearchBinding.getRoot());
        ButterKnife.bind(getFrag(),getView());
        itemRecordTitleSearchBinding.recycleTips.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(getFrag() instanceof TextView.OnEditorActionListener){
            itemRecordTitleSearchBinding.edtSearch.setOnEditorActionListener((TextView.OnEditorActionListener) getFrag());
        }

        if(getFrag() instanceof OnFinishListener){
            final OnFinishListener listener = (OnFinishListener) getFrag();
            itemRecordTitleSearchBinding.edtSearch.addTextChangedListener(new BaseTextWather(){
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    super.onTextChanged(s, start, before, count);
                    listener.onFinish(s.toString());
                }
            });
        }
    }

    public int showHideSearch(){
        ItemRecordTitleSearchBinding itemRecordTitleSearchBinding = DataBindingUtil.bind(((ViewGroup)getView()).getChildAt( ((ViewGroup) getView()).getChildCount()-1));
        if(itemRecordTitleSearchBinding.getRoot().getVisibility()==View.GONE){
            itemRecordTitleSearchBinding.getRoot().setVisibility(View.VISIBLE);
        }else{
            itemRecordTitleSearchBinding.getRoot().setVisibility(View.GONE);
        }
        return itemRecordTitleSearchBinding.getRoot().getVisibility();
    }

    public void refreshList(ArrayList<Tiplab> tiplabs, ViewListener listener){
        ItemRecordTitleSearchBinding itemRecordTitleSearchBinding = DataBindingUtil.bind(((ViewGroup)getView()).getChildAt( ((ViewGroup) getView()).getChildCount()-1));
        if(itemRecordTitleSearchBinding.recycleTips.getAdapter()==null){
            itemRecordTitleSearchBinding.recycleTips.setAdapter(new AppsDataBindingAdapter(getActivity(), R.layout.item_tiplab_text, BR.item_tiplab_text,tiplabs,listener));
        }else{
            itemRecordTitleSearchBinding.recycleTips.getAdapter().notifyDataSetChanged();
        }
    }

}
