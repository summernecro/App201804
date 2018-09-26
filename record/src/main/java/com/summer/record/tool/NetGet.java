package com.summer.record.tool;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.lib.bean.BaseBean;
import com.android.lib.constant.ValueConstant;
import com.android.lib.network.bean.res.BaseResBean;
import com.android.lib.network.news.NetI;
import com.android.lib.util.GsonUtil;
import com.android.lib.util.LogUtil;
import com.android.lib.util.SPUtil;
import com.google.gson.reflect.TypeToken;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.common.util.KeyValue;
import org.xutils.http.RequestParams;
import org.xutils.http.body.MultipartBody;
import org.xutils.http.cookie.DbCookieStore;
import org.xutils.x;

import java.io.File;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * Created by ${viwmox} on 2016-04-26.
 */
public class NetGet {


    private static Deal deal;

    public static void setDeal(Deal d) {
        deal = d;
    }

    public interface Deal{
        public void onSuccess(BaseResBean baseResBean);
        public void onError(Throwable ex, boolean isOnCallback);
        public void onCancelled(Callback.CancelledException cex);
        public void onFinished();
    }

    public static boolean test = false;

    private NetGet() {

    }

    public static void postDataGetCookie(final Context context, final String url, final BaseBean reqBean, final NetI netI) {
        final String jsonstr = GsonUtil.getInstance().toJson(reqBean);
        if(test){
            netI.onNetFinish(false, url+reqBean.getUniqueID(), new BaseResBean());
            return;
        }
        LogUtil.E("input-->" + url);
        LogUtil.E("input-->" + jsonstr);
        if (!netI.onNetStart(url, jsonstr)) {
            BaseResBean res = new BaseResBean();
            res.setErrorCode(ValueConstant.ERROR_CODE_NET_NO_CONNETCT);
            res.setErrorMessage(ValueConstant.ERROR_STR_NET_NO_CONNETCT);
            // res.setData(jsonstr);
            netI.onNetFinish(false, url+reqBean.getUniqueID(), res);
            return;
        }

        RequestParams requestParams = new RequestParams(url);

        Map<String, String> map = GsonUtil.getInstance(). fromJson(jsonstr,new TypeToken<Map<String, String>>() {}.getType());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            requestParams.addBodyParameter(entry.getKey(), entry.getValue());
        }
        requestParams.isUseCookie();
        requestParams.setUseCookie(true);

//        requestParams.setReadTimeout(5000);
//        requestParams.setConnectTimeout(5000);

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtil.E("output-->" + response);
                DbCookieStore dbCookieStore = DbCookieStore.INSTANCE;
                ArrayList<String> strings = new ArrayList<>();
                for (HttpCookie cookie : dbCookieStore.getCookies()) {
                    StringBuffer sb = new StringBuffer();
                    if(cookie.getName().equals("token")){
                        sb.append(cookie.getName()).append("=").append("\"")
                                .append(cookie.getValue()).append("\"")
                                .append("; path=").append(cookie.getPath())
                                .append("; domain=")
                                .append(cookie.getDomain())
                                .append(";");
                    }else{
                        sb.append(cookie.getName()).append("=")
                                .append(cookie.getValue())
                                .append("; path=").append(cookie.getPath())
                                .append("; domain=")
                                .append(cookie.getDomain())
                                .append(";");
                    }
                    strings.add(sb.toString());
                }
                SPUtil.getInstance().saveStr(ValueConstant.cookieFromResponse, GsonUtil.getInstance().toJson(strings));
                LogUtil.E(GsonUtil.getInstance().toJson(strings));
                if (response == null) {
                    BaseResBean res = new BaseResBean();
                    res.setErrorCode(ValueConstant.ERROR_CODE_RES_NULL);
                    res.setErrorMessage(ValueConstant.ERROR_STR_RES_NULL);
                    if(deal!=null){
                        deal.onSuccess(res);
                    }
                    netI.onNetFinish(false, url+reqBean.getUniqueID(), res);
                } else {
                    BaseResBean baseResBean = GsonUtil.getInstance().fromJson(response,BaseResBean.class);
                    if(deal!=null){
                        deal.onSuccess(baseResBean);
                    }
                    netI.onNetFinish(true, url+reqBean.getUniqueID(), baseResBean);
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                BaseResBean baseResBean = new BaseResBean();
                baseResBean.setErrorCode(ValueConstant.ERROR_CODE_VOLLEY_FAIL);
                baseResBean.setErrorMessage(ex.getMessage() == null ? "" : ex.getMessage());
                baseResBean.setException(true);
                if(deal!=null){
                    deal.onError(ex,isOnCallback);
                }

                netI.onNetFinish(false, url+reqBean.getUniqueID(), baseResBean);
                LogUtil.E(ex == null ? "Throwable" : "Throwable-->" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                if(deal!=null){
                    deal.onCancelled(cex);
                }
                LogUtil.E("onCancelled-->" + cex);
            }

            @Override
            public void onFinished() {
                if(deal!=null){
                    deal.onFinished();
                }
                LogUtil.E("onFinished-->");
            }
        });
    }



    public static void postData(final Context context, final String url, final BaseBean reqBean, final NetI netI) {
        final String jsonstr = GsonUtil.getInstance().toJson(reqBean);
        if(test){
            netI.onNetFinish(false, url+reqBean.getUniqueID(), new BaseResBean());
            return;
        }
        LogUtil.E("input-->" + url);
        LogUtil.E("input-->" + jsonstr);
        if (!netI.onNetStart(url, jsonstr)) {
            BaseResBean res = new BaseResBean();
            res.setErrorCode(ValueConstant.ERROR_CODE_NET_NO_CONNETCT);
            res.setErrorMessage(ValueConstant.ERROR_STR_NET_NO_CONNETCT);
            // res.setData(jsonstr);
            netI.onNetFinish(false, url+reqBean.getUniqueID(), res);
            return;
        }

        RequestParams requestParams = new RequestParams(url);
        requestParams.setUseCookie(false);

//        requestParams.setReadTimeout(5000);
//        requestParams.setConnectTimeout(5000);

        ArrayList<String> strings = GsonUtil.getInstance().fromJson(SPUtil.getInstance().getStr(ValueConstant.cookieFromResponse),new TypeToken<ArrayList<String>>(){}.getType());
        for(int i=0;strings!=null&& i<strings.size();i++){
            requestParams.addHeader("Cookie",strings.get(i));
            LogUtil.E(strings.get(i));
        }
        Map<String, String> map = GsonUtil.getInstance().fromJson(jsonstr,new TypeToken<Map<String, String>>() {}.getType());
        for (Map.Entry<String, String> entry : map.entrySet()) {
            requestParams.addBodyParameter(entry.getKey(), entry.getValue());

        }

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtil.E("output-->" + response);
                if (response == null) {
                    BaseResBean res = new BaseResBean();
                    res.setErrorCode(ValueConstant.ERROR_CODE_RES_NULL);
                    res.setErrorMessage(ValueConstant.ERROR_STR_RES_NULL);
                    if(deal!=null){
                        deal.onSuccess(res);
                    }
                    netI.onNetFinish(false, url+reqBean.getUniqueID(), res);
                } else {
                    BaseResBean baseResBean = GsonUtil.getInstance().fromJson(response,BaseResBean.class);
                    if(deal!=null){
                        deal.onSuccess(baseResBean);
                    }
                    netI.onNetFinish(true, url+reqBean.getUniqueID(), baseResBean);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                BaseResBean baseResBean = new BaseResBean();
                baseResBean.setErrorCode(ValueConstant.ERROR_CODE_VOLLEY_FAIL);
                baseResBean.setErrorMessage(ex.getMessage() == null ? "" : ex.getMessage());
                baseResBean.setException(true);
                if(deal!=null){
                    deal.onError(ex,isOnCallback);
                }
                netI.onNetFinish(false, url+reqBean.getUniqueID(), baseResBean);
                LogUtil.E(ex == null ? "Throwable" : "Throwable-->" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.E("onCancelled-->" + cex);
                if(deal!=null){
                    deal.onCancelled(cex);
                }
            }

            @Override
            public void onFinished() {
                LogUtil.E("onFinished-->");
                if(deal!=null){
                    deal.onFinished();
                }
            }
        });
    }



    public static void getData(final Context context, final String url, final BaseBean reqBean, final NetI netI) {

        new AsyncTask<String, String, RequestParams>() {
            @Override
            protected RequestParams doInBackground(String... strs) {
                final String jsonstr = GsonUtil.getInstance().toJson(reqBean);
                if(test){
                    netI.onNetFinish(false, url+reqBean.getUniqueID(), new BaseResBean());
                    return null;
                }
                LogUtil.E("input-->" + url);
                LogUtil.E("input-->" + jsonstr);
                if (!netI.onNetStart(url, jsonstr)) {
                    BaseResBean res = new BaseResBean();
                    res.setErrorCode(ValueConstant.ERROR_CODE_NET_NO_CONNETCT);
                    res.setErrorMessage(ValueConstant.ERROR_STR_NET_NO_CONNETCT);
                    // res.setData(jsonstr);
                    netI.onNetFinish(false, url+reqBean.getUniqueID(), res);
                    return null;
                }

                RequestParams requestParams = new RequestParams(url);
                requestParams.setUseCookie(false);

//        requestParams.setReadTimeout(5000);
//        requestParams.setConnectTimeout(5000);

                ArrayList<String> strings = GsonUtil.getInstance().fromJson(SPUtil.getInstance().getStr(ValueConstant.cookieFromResponse),new TypeToken<ArrayList<String>>(){}.getType());
                for(int i=0;strings!=null&& i<strings.size();i++){
                    requestParams.addHeader("Cookie",strings.get(i));
                    LogUtil.E(strings.get(i));
                }

                Map<String, String> map = GsonUtil.getInstance(). fromJson(jsonstr,new TypeToken<Map<String, String>>() {}.getType());
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    requestParams.addBodyParameter(entry.getKey(), entry.getValue());
                }
                return requestParams;
            }

            @Override
            protected void onPostExecute(RequestParams s) {
                super.onPostExecute(s);
                if(s==null){
                    return;
                }
                x.http().get(s, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        LogUtil.E("output-->" + response);
                        if (response == null) {
                            BaseResBean res = new BaseResBean();
                            res.setErrorCode(ValueConstant.ERROR_CODE_RES_NULL);
                            res.setErrorMessage(ValueConstant.ERROR_STR_RES_NULL);
                            if(deal!=null){
                                deal.onSuccess(res);
                            }
                            netI.onNetFinish(false, url+reqBean.getUniqueID(), res);
                        } else {
                            BaseResBean baseResBean = GsonUtil.getInstance().fromJson(response,BaseResBean.class);
                            LogUtil.E(baseResBean.getResult());
                            if(deal!=null){
                                deal.onSuccess(baseResBean);
                            }
                            netI.onNetFinish(true, url+reqBean.getUniqueID(), baseResBean);
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        BaseResBean baseResBean = new BaseResBean();
                        baseResBean.setErrorCode(ValueConstant.ERROR_CODE_VOLLEY_FAIL);
                        baseResBean.setErrorMessage(ex.getMessage() == null ? "" : ex.getMessage());
                        baseResBean.setException(true);
                        if(deal!=null){
                            deal.onError(ex,isOnCallback);
                        }
                        netI.onNetFinish(false, url+reqBean.getUniqueID(), baseResBean);
                        LogUtil.E(ex == null ? "Throwable" : "Throwable-->" + ex.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                        if(deal!=null){
                            deal.onCancelled(cex);
                        }
                        LogUtil.E("onCancelled-->" + cex);
                    }

                    @Override
                    public void onFinished() {
                        if(deal!=null){
                            deal.onFinished();
                        }
                        LogUtil.E("onFinished-->");
                    }
                });
            }
        }.execute();
    }



    public static void file(Context context, final String url, List<KeyValue> list, final NetI netI) {
        final String jsonstr = GsonUtil.getInstance().toJson(list);
        if(test){
            netI.onNetFinish(false, url+jsonstr, new BaseResBean());
            return;
        }
        LogUtil.E(url);
        LogUtil.E(jsonstr);
        if (!netI.onNetStart(url, jsonstr)) {
            BaseResBean res = new BaseResBean();
            res.setErrorCode(ValueConstant.ERROR_CODE_NET_NO_CONNETCT);
            res.setErrorMessage(ValueConstant.ERROR_STR_NET_NO_CONNETCT);
            // res.setData(jsonstr);
            netI.onNetFinish(false, url+jsonstr, res);
            return;
        }

        LogUtil.E(url + "---" + ValueConstant.cookieFromResponse);
        RequestParams requestParams = new RequestParams(url);
        requestParams.setUseCookie(false);

        ArrayList<String> strings = GsonUtil.getInstance().fromJson(SPUtil.getInstance().getStr(ValueConstant.cookieFromResponse),new TypeToken<ArrayList<String>>(){}.getType());
        for(int i=0;strings!=null&& i<strings.size();i++){
            requestParams.addHeader("Cookie",strings.get(i));
            LogUtil.E(strings.get(i));
        }


        MultipartBody body = new MultipartBody(list, "UTF-8");
        requestParams.setRequestBody(body);
        requestParams.setMultipart(true);

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String response) {
                LogUtil.E("gson", "" + response);
                if (response == null) {
                    BaseResBean res = new BaseResBean();
                    res.setErrorCode(ValueConstant.ERROR_CODE_RES_NULL);
                    res.setErrorMessage(ValueConstant.ERROR_STR_RES_NULL);
                    if(deal!=null){
                        deal.onSuccess(res);
                    }
                    netI.onNetFinish(false, url+jsonstr, res);
                } else {
                    BaseResBean baseResBean = GsonUtil.getInstance().fromJson(response.toString(), BaseResBean.class);
                    if(deal!=null){
                        deal.onSuccess(baseResBean);
                    }
                    netI.onNetFinish(true, url+jsonstr, baseResBean);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                BaseResBean baseResBean = new BaseResBean();
                baseResBean.setErrorCode(ValueConstant.ERROR_CODE_VOLLEY_FAIL);
                baseResBean.setErrorMessage(ex.getMessage() == null ? "" : ex.getMessage());
                baseResBean.setException(true);
                if(deal!=null){
                    deal.onError(ex,isOnCallback);
                }
                netI.onNetFinish(false, url+jsonstr, baseResBean);
                LogUtil.E(ex == null ? "" : ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                if(deal!=null){
                    deal.onCancelled(cex);
                }
                LogUtil.E(cex);
            }

            @Override
            public void onFinished() {
                if(deal!=null){
                    deal.onFinished();
                }
                LogUtil.E("");
            }
        });
    }

    public static void downLoadFile(final String netpath, String locpath, final NetI netI){
        LogUtil.E(netpath);
        if (!netI.onNetStart(netpath, locpath)) {
            BaseResBean res = new BaseResBean();
            res.setErrorCode(ValueConstant.ERROR_CODE_NET_NO_CONNETCT);
            res.setErrorMessage(ValueConstant.ERROR_STR_NET_NO_CONNETCT);
            // res.setData(jsonstr);
            netI.onNetFinish(false, netpath, res);
            return;
        }


        //设置请求参数
        RequestParams params = new RequestParams(netpath);
        params.setAutoResume(true);//设置是否在下载是自动断点续传
        params.setAutoRename(false);//设置是否根据头信息自动命名文件
        params.setSaveFilePath(locpath);
        params.setExecutor(new PriorityExecutor(2, true));//自定义线程池,有效的值范围[1, 3], 设置为3时, 可能阻塞图片加载.
        params.setCancelFast(true);//是否可以被立即停止.
        //下面的回调都是在主线程中运行的,这里设置的带进度的回调

        Callback.Cancelable cancelable = x.http().get(params, new Callback.ProgressCallback<File>() {
            @Override
            public void onCancelled(CancelledException arg0) {
                Log.i("tag", "取消"+Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {
                Log.i("tag", "onError: 失败"+Thread.currentThread().getName());
            }

            @Override
            public void onFinished() {
                netI.onNetFinish(true, netpath, new BaseResBean());
                Log.i("tag", "完成,每次取消下载也会执行该方法"+Thread.currentThread().getName());
            }

            @Override
            public void onSuccess(File arg0) {
                Log.i("tag", "下载成功的时候执行"+Thread.currentThread().getName());
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                if (isDownloading) {
                    Log.i("tag", "下载中,会不断的进行回调:"+Thread.currentThread().getName());
                }
            }

            @Override
            public void onStarted() {
                Log.i("tag", "开始下载的时候执行"+Thread.currentThread().getName());
            }

            @Override
            public void onWaiting() {
                Log.i("tag", "等待,在onStarted方法之前执行"+Thread.currentThread().getName());
            }
        });
    }

}
