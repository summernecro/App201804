package com.summer.record.ui.main.record.video;

//by summer on 2018-03-28.

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.android.lib.base.ope.BaseUIOpe;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.summer.record.R;
import com.summer.record.data.Record;
import com.summer.record.databinding.FragMainVideoVideoplayBinding;
import com.summer.record.tool.TitleUtil;

import java.io.File;

public class VideoPlayUIOpe extends BaseUIOpe<FragMainVideoVideoplayBinding> {


    public void play(Record video) {
        getBind().tvDes.setText(video.getLocpath());
        getBind().videoplayer.setUp(video.getLocpath(), false, new File(""), "视频播放");
        //外部辅助的旋转，帮助全屏
        final OrientationUtils orientationUtils = new OrientationUtils((Activity) getActivity(), getBind().videoplayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        getBind().videoplayer.setIsTouchWiget(true);
        getBind().videoplayer.setIsTouchWigetFull(false);
        getBind().videoplayer.getBackButton().setVisibility(View.GONE);
        getBind().videoplayer.getTitleTextView().setVisibility(View.GONE);

        ImageView imageView = new ImageView(getActivity());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        getBind().videoplayer.setThumbImageView(imageView);
        Glide.with(getActivity()).setDefaultRequestOptions(
                new RequestOptions()
                        .frame(3000000)
                        .centerCrop())
                .load(video.getLocpath())
                .into(imageView);

        //关闭自动旋转
        getBind().videoplayer.setRotateViewAuto(false);
        getBind().videoplayer.setLockLand(true);
        getBind().videoplayer.setShowFullAnimation(true);
        getBind().videoplayer.setNeedLockFull(true);
        getBind().videoplayer.setSeekRatio(1);
        getBind().videoplayer.setRotateWithSystem(true);
        //detailPlayer.setOpenPreView(false);
        getBind().videoplayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                getBind().videoplayer.startWindowFullscreen(getActivity(), true, true);
            }
        });
        //bind.videoplayer.setUp(videoBean.getFile(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, videoBean.getCreated());
        //bind.videoplayer.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");

    }
}
