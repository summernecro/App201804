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
import com.summer.record.ui.main.main.MainAct;
import com.summer.record.ui.main.video.video.VideoFrag;

import java.util.ArrayList;
import java.util.List;

public class VideosUIOpe extends BaseUIOpe<FragMainVideosBinding> {

    @Override
    public void initUI() {
        super.initUI();
//        GlideApp.with(getActivity()).asBitmap()
//                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1530075352053&di=0ad5544e96bc8475275e1317205c2329&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F01d65e5555b6830000009af077ef70.jpg")
//                .into(getBind().ivBg);
        getBind().ivBg.setBackgroundColor(getActivity().getResources().getColor(R.color.color_grey_200));
    }

    public void initViewPager(FragmentManager fm, Context context, final List<Fragment> fragments){

        getBind().viewpager.setAdapter(new AppBasePagerAdapter2(fm, context, fragments));
        getBind().viewpager.addOnPageChangeListener(new BaseOnPagerChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                ((MainAct)getActivity()).getP().getU().updateTitle(((VideoFrag)fragments.get(position)).getP().getV().getTitleStr());
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

}
