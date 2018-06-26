package com.summer.record.tool;

//by summer on 2018-03-28.

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.util.LogUtil;
import com.summer.record.data.Record;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FileTool {

    public static ArrayList<Record> getVideos(Context context,String[] timeduraion, OnFinishListener onFinishListener){
        ContentResolver contentResolver = context.getContentResolver();
        ArrayList<Record> videos = new ArrayList<>();
        Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,null,MediaStore.Video.Media.DATE_ADDED+">=? and "+MediaStore.Images.Media.DATE_ADDED+"< ? ",timeduraion,MediaStore.Video.Media.DATE_MODIFIED+" desc");
        int i=0;
        while (cursor.moveToNext()){
            File file = new File(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));
            if(!file.exists()){
                continue;
            }
            Record record = new Record(Record.ATYPE_VIDEO,
                    cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID)),
                    file.getPath(),
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED))*1000,
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_MODIFIED))*1000,
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))

            );
            record.init();
            videos.add(record);
            onFinishListener.onFinish(i);
            i++;
        }
        return  videos;
    }

    public static ArrayList<Record> getImages(Context context,String[] timeduraion, OnFinishListener onFinishListener){
        ContentResolver contentResolver = context.getContentResolver();
        ArrayList<Record> images = new ArrayList<>();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,MediaStore.Images.Media.DATE_ADDED+">=? and "+MediaStore.Images.Media.DATE_ADDED+"< ? ",timeduraion,MediaStore.Images.Media.DATE_MODIFIED+" desc");
        int i=0;
        while (cursor.moveToNext()){
            File file = new File(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
            if(!file.exists()){
                continue;
            }
            Record record = new Record(Record.ATYPE_IMAGE,
                    cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)),
                    file.getPath(),
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED))*1000,
                    cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED))*1000,
                    cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
            );
            record.init();
            images.add(record);
            onFinishListener.onFinish(i);
            i++;
        }
        return  images;
    }

    public static int[] getTime(Context context){
        Long max = 0l,min=0l;
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,new String[]{"max("+MediaStore.Images.Media.DATE_ADDED+")"},null,null,MediaStore.Images.Media.DATE_MODIFIED+" desc");
        while (cursor.moveToNext()){
            max = cursor.getLong(0);
        }
        Cursor cursor2 = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,new String[]{"min("+MediaStore.Images.Media.DATE_ADDED+")"},null,null,MediaStore.Images.Media.DATE_MODIFIED+" desc");
        while (cursor2.moveToNext()){
            min = cursor2.getLong(0);
        }
        int ma =0,mi=0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date(max*1000));
        ma = calendar.get(Calendar.YEAR);
        calendar.setTime(new Date(min*1000));
        mi = calendar.get(Calendar.YEAR);
        return new int[]{mi,ma};
    }
}
