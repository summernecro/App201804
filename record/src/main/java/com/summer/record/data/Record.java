package com.summer.record.data;

import android.net.Uri;

import com.android.lib.util.NullUtil;
import com.android.lib.util.data.DateFormatUtil;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.summer.record.base.BaseBean;
import com.summer.record.data.db.RecordDataBase;


import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(database = RecordDataBase.class)
public class Record extends BaseBean {

    @PrimaryKey(autoincrement = true)
    public Integer id;

    public Integer pos;
    @Column
    public String locpath;
    @Column
    public String netpath;
    @Column
    public Long ctime;
    @Column
    public Long utime;
    @Column
    public String atype;
    @Column
    public String btype;
    @Column
    public String address;
    @Column
    public Long duration;
    @Column
    public String name;
    @Column
    public String content;

    public Uri uri;

    public String dateStr;

    public int frist = 0;

    public int isUploaded = 0;

    public int isDoing = 0;

    public String startTime;

    public String endTime;

    public static final int 本地有服务器无=1;

    public static final int 本地有服务器有=2;

    public static final int 本地无服务器有=3;

    public static final int 其他情况=0;

    public static final String ATYPE_VIDEO = "video";

    public static final String ATYPE_IMAGE = "image";

    public static final String ATYPE_TEXT = "text";

    public ArrayList<Tiplab> tiplabs;


    public Record() {
    }

    public Record(String atype,Integer id, String locpath, Long ctime, Long utime, Long duration, String name) {
        this.atype = atype;
        this.id = id;
        this.locpath = locpath;
        this.ctime = ctime;
        this.utime = utime;
        this.duration = duration;
        this.name = name;
    }

    public Record(String atype,Integer id, String locpath, Long ctime, Long utime, String name) {
        this.atype = atype;
        this.id = id;
        this.locpath = locpath;
        this.netpath = netpath;
        this.ctime = ctime;
        this.utime = utime;
        this.name = name;
    }

    public void init(){
        if(locpath!=null){
            uri = Uri.fromFile(new File(locpath));
        }
        Date d=new Date((ctime));
        DateFormat df=new SimpleDateFormat(DateFormatUtil.YYYY_MM_DD);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateStr = df.format(d);
    }

    public boolean isNull(){
        if(NullUtil.isStrEmpty(getAtype())){
            return true;
        }
        return false;
    }

    public int getStatus(){
        if(NullUtil.isStrEmpty(getLocpath())){
            return 本地无服务器有;
        }
        if(NullUtil.isStrEmpty(getNetpath())&&!NullUtil.isStrEmpty(getLocpath())){
            return 本地有服务器无;
        }
        if(NullUtil.isStrEmpty(getNetpath())&&NullUtil.isStrEmpty(getLocpath())){
            return 本地有服务器有;
        }
        return 其他情况;
    }

    public String getShortContent(){
        if(content!=null&&content.length()>99){
            return content.substring(0,100);
        }
        return content;
    }

    public boolean isFrist() {
        return frist==1;
    }

    public boolean isUploaded() {
        return isUploaded==1;
    }

    public boolean isDoing() {
        return isDoing==1;
    }

    public Uri getUri() {
        if(uri==null){
            init();
        }
        return uri;
    }

    @Override
    public String getUniqueID() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("atype")
                .append(getAtype())
                .append("startTime")
                .append(getStartTime())
                .append("endTime")
                .append(getEndTime());
        return buffer.toString();
    }
}