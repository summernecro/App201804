package com.summer.record.tool;

//by summer on 2018-03-28.

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.android.lib.base.interf.OnFinishListener;
import com.android.lib.util.LogUtil;
import com.android.lib.util.NullUtil;
import com.android.lib.util.StringUtil;
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
            onFinishListener.onFinish(record);
            i++;
        }
        return  videos;
    }




    public static Record getLastRecord(Context context){
        ContentResolver contentResolver = context.getContentResolver();
        ArrayList<Record> videos = new ArrayList<>();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,null,null);
        cursor.moveToLast();
        File file = new File(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)));
        if(!file.exists()){
            return null;
        }
        Record record = new Record(Record.ATYPE_IMAGE,
                cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)),
                file.getPath(),
                cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED))*1000,
                cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED))*1000,
                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
        );
        record.init();
        return record;
    }

    public static ArrayList<Record> getRecords(Context context,String type,String[] timeduraion, OnFinishListener onFinishListener){
        switch (type){
            case Record.ATYPE_IMAGE:
                return getImages(context,timeduraion,onFinishListener);
            case Record.ATYPE_VIDEO:
                return getVideos(context,timeduraion,onFinishListener);
                default:
                    return null;
        }
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
            onFinishListener.onFinish(record);
            i++;
        }
        return  images;
    }

    public static ArrayList<Record> getImages(Context context,String startTime, OnFinishListener onFinishListener){
        return getImages(context,new String[]{startTime,(System.currentTimeMillis()/1000)+""},onFinishListener);
    }

    public static int[] getTime(Context context,String type){
        Long max = 0l,min=0l;
        ContentResolver contentResolver = context.getContentResolver();
        Uri uri = type.equals(Record.ATYPE_IMAGE)?MediaStore.Images.Media.EXTERNAL_CONTENT_URI:MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String add = type.equals(Record.ATYPE_IMAGE)?MediaStore.Images.Media.DATE_ADDED:MediaStore.Video.Media.DATE_ADDED;
        String modified = type.equals(Record.ATYPE_IMAGE)?MediaStore.Images.Media.DATE_MODIFIED:MediaStore.Video.Media.DATE_MODIFIED;
        Cursor cursor = contentResolver.query(uri,new String[]{"max("+add+")"},null,null,modified+" desc");
        while (cursor.moveToNext()){
            max = cursor.getLong(0);
        }
        Cursor cursor2 = contentResolver.query(uri,new String[]{"min("+add+")"},null,null,modified+" desc");
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


    public static void changeFileCreateDate(Record record){
        File file = new File(record.getLocpath());
        if(!file.exists()){
            return;
        }
        file.setLastModified(record.getUtime());
    }

    /**
     * 从路径中获取所在文件夹名字
     * @param path
     * @return
     */
    public static String getFolderName(String path){
        if(NullUtil.isStrEmpty(path)){
            return null;
        }
        File file = new File(path);
        return file.getParentFile().getName();
    }
}
