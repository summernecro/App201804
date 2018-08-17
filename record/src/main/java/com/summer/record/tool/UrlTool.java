package com.summer.record.tool;

//by summer on 2018-08-15.

public class UrlTool {

    public static String STARTSTR = "record\\";

    public static String NEWSTATSTR = "http://www.summernecro.com/";

    public static String getNetUrl(String neturl){

        if(neturl==null){
            return neturl;
        }
        int a = neturl.indexOf(STARTSTR);
        neturl = neturl.substring(a+STARTSTR.length(),neturl.length());
        neturl = neturl.replace("\\","/");
        neturl = NEWSTATSTR+neturl;
        return neturl;
    }

}
