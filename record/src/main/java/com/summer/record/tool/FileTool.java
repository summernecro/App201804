package com.summer.record.tool;

//by summer on 2018-03-28.

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import com.summer.record.data.Record;

import java.io.File;
import java.util.ArrayList;

public class FileTool {

    public static ArrayList<Record> getVideos(Context context){
        ContentResolver contentResolver = context.getContentResolver();
        ArrayList<Record> videos = new ArrayList<>();
        Cursor cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Video.Media.DATE_MODIFIED+" desc");
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
        }
        return  videos;
    }

    public static ArrayList<Record> getImages(Context context){
        ContentResolver contentResolver = context.getContentResolver();
        ArrayList<Record> images = new ArrayList<>();
        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Images.Media.DATE_MODIFIED+" desc");
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
        }
        return  images;
    }
}
