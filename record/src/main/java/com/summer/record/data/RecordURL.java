package com.summer.record.data;

public class RecordURL {
    protected static final String 奔溃日志 = "http://222.186.36.75:8888:8888/server/crash/sendCrash";
    protected static final String HTTP前缀 = "http://";
    protected static String 项目名 = "/record";
    protected static String 正式域名 = "222.186.36.75:8888";
    protected static String 测试域名 = "192.168.20.150:8079";
    protected static String 域名 = 正式域名;
    protected static String 正式文件路径;
    protected static String 测试文件路径;
    protected static String 文件路径="records";
    protected static boolean isOffice;

    public RecordURL() {
    }

    public static String 获取地址(String module) {
        return "http://" + 域名 + 项目名 + module;
    }

    public static String 获取文件路径(String module) {
        return "http://" + 域名 + 文件路径 + module;
    }

    public static void setIsOffice(boolean isOffice) {
        isOffice = isOffice;
        if(isOffice) {
            域名 = 正式域名;
        } else {
            域名 = 测试域名;
        }

    }

    public static void set域名(String 域名) {
        域名 = 域名;
    }

    public static String STARTSTR = "record\\";

    public static String NEWSTATSTR = "http://"+正式域名+"/";

    public static String getNetUrl(String neturl){

        if(neturl==null){
            return neturl;
        }
        int a = neturl.indexOf(STARTSTR);
        neturl = neturl.substring(a+STARTSTR.length(),neturl.length());
        neturl = neturl.replace("\\","/");
        neturl = NEWSTATSTR+文件路径+"/"+neturl;
        return neturl;
    }

    static {
        域名 = 正式域名;
        正式文件路径 = "";
        测试文件路径 = "";
        文件路径 = "records";
        isOffice = false;
    }
}