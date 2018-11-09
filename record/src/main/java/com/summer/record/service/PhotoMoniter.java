package com.summer.record.service;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;

import com.android.lib.base.activity.BaseUIActivity;
import com.android.lib.base.interf.OnFinishListener;

public class PhotoMoniter extends ContentObserver {

    OnFinishListener listener;


    public PhotoMoniter(Handler handler,OnFinishListener listener) {
        super(handler);
        this.listener = listener;
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        super.onChange(selfChange, uri);
        listener.onFinish(uri);
    }

    public OnFinishListener getListener() {
        return listener;
    }

    public static PhotoMoniter registerContentObserver(BaseUIActivity activity,OnFinishListener onFinishListener){
        Handler handler = new Handler();
        PhotoMoniter photoMoniter = new PhotoMoniter(handler, onFinishListener);
        ContentResolver resolver = activity.getContentResolver();
        resolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false, photoMoniter);
        return  photoMoniter;
    }

    public static void unregisterContentObserver(BaseUIActivity activity,PhotoMoniter photoMoniter){
        ContentResolver resolver = activity.getContentResolver();
        resolver.unregisterContentObserver(photoMoniter);
    }

}
