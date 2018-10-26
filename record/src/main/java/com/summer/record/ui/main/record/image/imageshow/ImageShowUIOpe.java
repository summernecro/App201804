package com.summer.record.ui.main.record.image.imageshow;

//by summer on 2018-03-28.

import com.android.lib.GlideApp;
import com.android.lib.base.ope.BaseUIOpe;
import com.android.lib.util.ToastUtil;
import com.github.florent37.viewanimator.AnimationListener;
import com.summer.record.data.Record;
import com.summer.record.data.RecordURL;
import com.summer.record.databinding.FragMainImageImagedetailImageBinding;
import com.summer.record.tool.UrlTool;
import com.summer.record.tool.ViewTool;
import com.summer.record.ui.main.record.image.ImageDetailFrag;
import com.summer.record.ui.main.record.record.RecordDAOpe;
import com.summer.record.ui.main.record.image.NetAdapter;

import java.io.File;

public class ImageShowUIOpe extends BaseUIOpe<FragMainImageImagedetailImageBinding> {

    public void showImage(final Record image){
        getBind().tvDes.setText(image.getLocpath());
        File file = new File(image.getLocpath());
        if(file.exists()){
            if(image.getLocpath().toLowerCase().endsWith("gif")){
                GlideApp.with(getActivity()).asGif().centerInside().load(image.getLocpath()).into(getBind().ivImage);
            }else{
                GlideApp.with(getActivity()).asBitmap().centerInside().load(image.getLocpath()).into(getBind().ivImage);
            }
        }else{
            if(image.getLocpath().toLowerCase().endsWith("gif")){
                GlideApp.with(getActivity()).asGif().centerInside().load(RecordURL.getNetUrl(image.getNetpath())).into(getBind().ivImage);
            }else{
                GlideApp.with(getActivity()).asBitmap().centerInside().load(RecordURL.getNetUrl(image.getNetpath())).into(getBind().ivImage);
            }
            new RecordDAOpe().downLoadRecord(image,new NetAdapter(getFrag()){
                @Override
                public void onSuccess(Object o) {
                    super.onSuccess(o);
                    ToastUtil.getInstance().showShort(getActivity(), image.getLocpath());
                }
            });

        }

    }

    public void setVis(boolean is){
        ViewTool.setVisible(getBind().tvDes,is);
    }

    public void switcht(){
        ViewTool.switchView(getBind().tvDes);
    }

    public void setshow(ImageDetailFrag imageDetailFrag){
        imageDetailFrag.getPV().setShow(getBind().tvDes);
    }
}
