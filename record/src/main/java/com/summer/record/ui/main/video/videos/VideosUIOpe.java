package com.summer.record.ui.main.video.videos;

//by summer on 2018-06-26.

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.widget.TextView;

import com.android.lib.GlideApp;
import com.android.lib.base.adapter.AppBasePagerAdapter2;
import com.android.lib.base.listener.BaseOnPagerChangeListener;
import com.android.lib.base.ope.BaseUIOpe;
import com.android.lib.util.StringUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.summer.record.R;
import com.summer.record.databinding.FragMainVideosBinding;
import com.summer.record.ui.main.image.image.ImageFrag;
import com.summer.record.ui.main.video.video.VideoFrag;

import java.util.ArrayList;
import java.util.List;

public class VideosUIOpe extends BaseUIOpe<FragMainVideosBinding> {

    public void initViewPager(FragmentManager fm, Context context, final List<Fragment> fragments){

        GlideApp.with(context).asBitmap()
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530017238105&di=242a09aac17052ed803ec7f3995d5ef6&imgtype=0&src=http%3A%2F%2Fimg18.3lian.com%2Fd%2Ffile%2F201708%2F25%2F200ffa94581a27c839d0c6fbe1c46ecd.jpg")
                .into(getBind().ivBg);
        getBind().viewpager.setAdapter(new AppBasePagerAdapter2(fm, context, fragments));
        getBind().viewpager.addOnPageChangeListener(new BaseOnPagerChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                updateTitle(((VideoFrag)fragments.get(position)).getP().getV().getTitleStr());
            }
        });
    }

    public void initFrag(VideosFrag videosFrag,ArrayList<String[]> strs, ArrayList<Fragment> videoFrags ){
        for(int i=0;i<strs.size();i++){
            VideoFrag videoFrag = VideoFrag.getInstance(strs.get(i),videosFrag);
            videoFrags.add(videoFrag);
        }
    }

    public VideoFrag getCurrentFrag(ArrayList<Fragment> videoFrags){
        return (VideoFrag) videoFrags.get(getBind().viewpager.getCurrentItem());
    }

    public void updateTitle(Object o){
        TextView textView = getView().findViewById(R.id.tv_lab);
        textView.setText(StringUtil.getStr(o));
    }
}
