package com.summer.record.ui.main.main;

//by summer on 2018-09-26.

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.lib.base.adapter.AppsDataBindingAdapter;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.util.fragment.two.FragManager2;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.summer.record.BR;
import com.summer.record.R;
import com.summer.record.databinding.ActMainABinding;
import com.summer.record.tool.TitleUtil;
import com.summer.record.ui.main.record.folder.FolderFrag;
import com.summer.record.ui.main.record.records.RecordsFrag;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class AView extends RelativeLayout {

    ActMainABinding actMainABinding;

    private int moudleid;

    private String moudle;

    public AView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        actMainABinding = ActMainABinding.inflate(LayoutInflater.from(getContext()));
        addView(actMainABinding.getRoot(),new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        TitleUtil.initTitle(getContext(),actMainABinding.title.getRoot());
        TitleUtil.initSearhView((Activity) getContext(),actMainABinding.search);
        ButterKnife.bind(this);
    }

    public ActMainABinding getActMainABinding() {
        return actMainABinding;
    }


    public void showHideSort(boolean show, ArrayList<String> sorts, ViewListener listener){
        if(show){
            if(getActMainABinding().sortlist.getAdapter()==null){
                getActMainABinding().sortlist.setLayoutManager(new LinearLayoutManager(getContext()));
                getActMainABinding().sortlist.setAdapter(new AppsDataBindingAdapter(getContext(),R.layout.item_sort_text, BR.item_sort_text,sorts,listener));
            }
            ViewAnimator.animate( getActMainABinding().sortlist).translationY(-getActMainABinding().sortlist.getHeight(),0).duration(500).onStart(new AnimationListener.Start() {
                @Override
                public void onStart() {
                    getActMainABinding().sortlist.setVisibility(View.VISIBLE);
                }
            }).start();
        }else{
            ViewAnimator.animate(getActMainABinding().sortlist).translationY(0,-getActMainABinding().sortlist.getHeight()).duration(500).onStop(new AnimationListener.Stop() {
                @Override
                public void onStop() {
                    getActMainABinding().sortlist.setVisibility(View.GONE);
                }
            }).start();
        }
    }

    public void switchSort(int id,ArrayList<String> sorts, ViewListener listener){
        showHideSort(getActMainABinding().sortlist.getVisibility()!=View.VISIBLE,sorts,listener);
    }

    @Optional
    @OnClick({ R.id.tv_refresh,R.id.tv_upload,R.id.tv_down,R.id.tv_search,R.id.tv_sort,R.id.iv_search_back})
    public void onClick(final View v) {
        switch (v.getId()){
            case R.id.tv_sort:
                switchSort(v.getId(),MainValue.sorts, new ViewListener() {
                    @Override
                    public void onInterupt(int i, View view) {
                        switchSort(v.getId(),MainValue.sorts, null) ;
                        switch (i){
                            case ViewListener.TYPE_ONCLICK:
                                FragManager2.getInstance().clear(getAct(),getMoudle());
                                switch ((int)view.getTag(R.id.position)){
                                    //按日期排序
                                    case 0:
                                        FragManager2.getInstance().setAnim(false).start(getAct(),getMoudle(),getMoudleid(), RecordsFrag.getInstance(getAct().getMoudle()));
                                        break;
                                    //按文件夹排序
                                    case 2:
                                        FragManager2.getInstance().setAnim(false).start(getAct(),getMoudle(),getMoudleid(), FolderFrag.getInstance(getAct().getMoudle()));
                                        break;
                                }
                                break;
                        }
                    }
                });
                break;
            default:
                (FragManager2.getInstance().getCurrentFrag(getMoudle())).onClick(v);
                break;
        }
    }

    public MainAct getAct(){
        return (MainAct) getContext();
    }

    public int getMoudleid() {
        return moudleid;
    }

    public void setMoudleid(int moudleid) {
        this.moudleid = moudleid;
    }

    public String getMoudle() {
        return moudle;
    }

    public void setMoudle(String moudle) {
        this.moudle = moudle;
    }
}
