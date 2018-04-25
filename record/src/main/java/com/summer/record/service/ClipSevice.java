package com.summer.record.service;

//by summer on 2018-03-29.

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.android.lib.service.main.AppService;
import com.android.lib.util.LogUtil;
import com.android.lib.view.bottommenu.MessageEvent;
import com.google.android.exoplayer2.metadata.emsg.EventMessage;
import com.summer.record.data.Record;
import com.summer.record.data.text.Text;
import com.summer.record.ui.main.text.TextFrag;

import org.greenrobot.eventbus.EventBus;

public class ClipSevice extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        final ClipboardManager clipboarmanager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        clipboarmanager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                for(int i=0;i<clipboarmanager.getPrimaryClip().getItemCount();i++){
                    ClipData.Item item = clipboarmanager.getPrimaryClip().getItemAt(i);
                    LogUtil.E( i+"ClipData"+item.getText());
                    Record record = new Record();
                    record.atype = Record.ATYPE_TEXT;
                    record.content = item.getText().toString();
                    record.ctime = System.currentTimeMillis();
                    record.save();
                }
                EventBus.getDefault().post(new MessageEvent(ClipSevice.class.getName(), TextFrag.class.getName(),""));
            }
        });
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
