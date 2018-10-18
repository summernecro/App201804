package com.summer.record.ui.main.main;

//by summer on 2018-09-26.

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.lib.base.adapter.AppsDataBindingAdapter;
import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.base.listener.ViewListener;
import com.android.lib.bean.AppViewHolder;
import com.android.lib.network.news.NetAdapter;
import com.android.lib.network.news.UINetAdapter;
import com.android.lib.util.NullUtil;
import com.android.lib.util.fragment.two.FragManager2;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.summer.record.BR;
import com.summer.record.R;
import com.summer.record.data.NetDataWork;
import com.summer.record.data.Record;
import com.summer.record.data.Tiplab;
import com.summer.record.databinding.ActMainABinding;
import com.summer.record.tool.TitleUtil;
import com.summer.record.ui.main.record.folder.FolderFrag;
import com.summer.record.ui.main.record.record.RecordFrag;
import com.summer.record.ui.main.record.record.RecordValue;
import com.summer.record.ui.main.record.records.RecordsFrag;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

public class AView extends RelativeLayout implements RefreshI,View.OnClickListener,OnFinishListener,TextView.OnEditorActionListener {

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
        TitleUtil.initSearhView((Activity) getContext(),this,actMainABinding.search);
        ButterKnife.bind(this);
    }

    public ActMainABinding getActMainABinding() {
        return actMainABinding;
    }


    public void showHideSort(boolean show, ArrayList<String> sorts, ViewListener listener){
        if(show){
            if(getActMainABinding().sortlist.getAdapter()==null){
                getActMainABinding().sortlist.setLayoutManager(new LinearLayoutManager(getContext()));
                getActMainABinding().sortlist.setAdapter(new AppsDataBindingAdapter(getContext(),R.layout.item_sort_text, BR.item_sort_text,sorts,listener){
                    @Override
                    public void onBindViewHolder(AppViewHolder holder, int position) {
                        super.onBindViewHolder(holder, position);
                        holder.viewDataBinding.setVariable(vari, list.get(position));
                        holder.viewDataBinding.executePendingBindings();//加一行，问题解决
                    }
                });
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
            case R.id.iv_search_back:
                TitleUtil.showHideSearch(getActMainABinding().search);
                //FragManager2.getInstance().setAnim(false).start(getAct(),getMoudle(),getMoudleid(), RecordsFrag.getInstance(getAct().getMoudle()));
                break;
            case R.id.tv_search:
                TitleUtil.showHideSearch(getActMainABinding().search);
                break;
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
                v.setTag(this);
                (FragManager2.getInstance().getCurrentFrag(getMoudle())).onClick(v);
                break;
        }
    }


    @Override
    public void refresh(ArrayList<Record> o){
        FragManager2.getInstance().setAnim(false).start(getAct(),getMoudle(),getMoudleid(), RecordFrag.getInstance(null,getMoudle(),null,o));
    }


    @Override
    public void onFinish(Object o) {
        if(NullUtil.isStrEmpty(o.toString())){
            return;
        }
        NetDataWork.Tip.getLikeTiplab(getContext(),o.toString(),new NetAdapter<ArrayList<Tiplab>>(getContext()){

            @Override
            public void onSuccess(final ArrayList<Tiplab> o) {
                super.onSuccess(o);
                TitleUtil.refreshList(getActMainABinding().search,getAct(),o, new ViewListener() {
                    @Override
                    public void onInterupt(final int i, final View view) {
                        int pos = (int) view.getTag(R.id.position);
                        NetDataWork.Tip.getRecordsFromTip(getContext(), o.get(pos), new UINetAdapter<ArrayList<Record>>(getContext()) {
                            @Override
                            public void onSuccess(ArrayList<Record> o) {
                                super.onSuccess(o);
                                TitleUtil.showHideSearch(getActMainABinding().search);
                                refresh(o);
                            }
                        });
                    }
                });
            }
        });
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


    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(actionId== EditorInfo.IME_ACTION_GO){
            //TitleUtil.showHideSearch(getActMainABinding().search);
            BaseUIFrag frag = FragManager2.getInstance().getCurrentFrag(getMoudle());
            if(frag instanceof AddTipI){
                AddTipI addTipI = (AddTipI) frag;
                addTipI.addTip(v.getText().toString());
            }
            return true;
        }
        return false;
    }
}
