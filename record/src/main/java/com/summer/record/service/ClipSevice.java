package com.summer.record.service;

//by summer on 2018-03-29.

import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.android.lib.network.news.NetAdapter;
import com.android.lib.util.LogUtil;
import com.android.lib.view.bottommenu.Msg;
import com.summer.record.data.Record;
import com.summer.record.ui.main.record.record.RecordDAOpe;
import com.summer.record.ui.main.text.text.TextFrag;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import androidx.annotation.Nullable;

public class ClipSevice extends Service {

    long time = 0;

    RecordDAOpe recordDAOpe = new RecordDAOpe();

    @Override
    public void onCreate() {
        super.onCreate();
        final ClipboardManager clipboarmanager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        clipboarmanager.addPrimaryClipChangedListener(new ClipboardManager.OnPrimaryClipChangedListener() {
            @Override
            public void onPrimaryClipChanged() {
                if(System.currentTimeMillis()-time<200){
                    time = System.currentTimeMillis();
                    return;
                }
                time = System.currentTimeMillis();
                for(int i=0;i<clipboarmanager.getPrimaryClip().getItemCount();i++){
                    ClipData.Item item = clipboarmanager.getPrimaryClip().getItemAt(i);
                    LogUtil.E( i+"ClipData"+item.getText());
                    Record record = new Record();
                    record.atype = Record.ATYPE_TEXT;
                    record.content = item.getText().toString();
                    record.ctime = System.currentTimeMillis();
                    record.save();

                    ArrayList<Record> rs = new ArrayList<>();
                    rs.add(record);
                    recordDAOpe.updateRecords(getBaseContext(),rs,new NetAdapter<ArrayList<Record>>(getApplicationContext()));

                    EventBus.getDefault().post(new Msg(ClipSevice.class.getName(), TextFrag.class.getName(),record));
                }
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
