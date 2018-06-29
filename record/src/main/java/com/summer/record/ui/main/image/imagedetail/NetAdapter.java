//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.summer.record.ui.main.image.imagedetail;

import android.content.Context;
import com.android.lib.base.fragment.BaseUIFrag;
import com.android.lib.network.bean.res.BaseResBean;
import com.android.lib.network.news.NetGet;
import com.android.lib.network.news.NetI;
import com.android.lib.util.GsonUtil;
import com.android.lib.util.LogUtil;
import com.android.lib.util.NetWorkUtil;
import com.android.lib.util.NullUtil;
import com.android.lib.util.SPUtil;
import com.android.lib.util.StringUtil;
import com.android.lib.util.ToastUtil;
import com.android.lib.view.bottommenu.MessageEvent;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class NetAdapter<A> implements NetI<A> {
    public static boolean cache = false;
    protected Context context;
    protected String url;
    protected boolean showtoast = false;
    protected BaseUIFrag baseUIFrag;

    public NetAdapter(Context context) {
        this.context = context;
    }

    public NetAdapter(BaseUIFrag baseUIFrag) {
        this.baseUIFrag = baseUIFrag;
        this.context = baseUIFrag.getActivity();
    }

    public NetAdapter(BaseUIFrag baseUIFrag, boolean isshow) {
        this.baseUIFrag = baseUIFrag;
        this.context = baseUIFrag.getActivity();
        this.showtoast = isshow;
    }

    public NetAdapter(Context context, boolean isshow) {
        this.context = context;
        this.showtoast = isshow;
    }

    public boolean onNetStart(String url, String gson) {
        this.url = url;
        boolean isNetOk = NetWorkUtil.getInstance().getNetisAvailable(this.context);
        if(NetGet.test) {
            return true;
        } else {
            if(!isNetOk) {
                ToastUtil.getInstance().showShort(this.context, "无网络");
            }

            return isNetOk;
        }
    }

    public void onNetFinish(boolean haveData, String url, BaseResBean baseResBean) {
        if(!haveData) {
            if(cache) {
                this.onResult(true, baseResBean.getErrorMessage(), null);
            } else {
                this.onResult(false, baseResBean.getErrorMessage(),  null);
                if(!NullUtil.isStrEmpty(baseResBean.getMessage()) && this.showtoast) {
                    ToastUtil.getInstance().showShort(this.context.getApplicationContext(), StringUtil.getStr(baseResBean.getMessage()) + StringUtil.getStr(baseResBean.getErrorMessage()));
                }
            }
        } else if(cache) {
            if(this.showtoast) {
                ToastUtil.getInstance().showShort(this.context, "当前为无网络测试环境");
            }

            BaseResBean resBean = (BaseResBean)GsonUtil.getInstance().fromJson(SPUtil.getInstance().getStr(url), BaseResBean.class);
            if(resBean == null) {
                resBean = new BaseResBean();
                resBean.setCode("000");
            }

            this.deal(haveData, url, resBean);
        } else {
            if(!NullUtil.isStrEmpty(baseResBean.getMessage()) && this.showtoast) {
                ToastUtil.getInstance().showShort(this.context.getApplicationContext(), StringUtil.getStr(baseResBean.getMessage()) + StringUtil.getStr(baseResBean.getErrorMessage()));
            }

            if(cache) {
                SPUtil.getInstance().saveStr(url, GsonUtil.getInstance().toJson(baseResBean));
            }

            this.deal(haveData, url, baseResBean);
        }

    }

    private void deal(boolean haveData, String url, BaseResBean baseResBean) {
        boolean isobject = false;
        boolean isarray = false;
        Type type = this.getClass().getGenericSuperclass();
        if(type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            A aa = null;
            String json = GsonUtil.getInstance().toJson(baseResBean.getResult());

            try {
                Object o = (new JSONTokener(json)).nextValue();

                try {
                    isobject = o instanceof JSONObject;
                    if(!isobject) {
                        try {
                            isarray = o instanceof JSONArray;
                        } catch (Exception var17) {
                            var17.printStackTrace();
                        }
                    }
                } catch (Exception var18) {
                    var18.printStackTrace();
                }

                if(isobject) {
                    Class<A> a = (Class)parameterizedType.getActualTypeArguments()[0];
                    aa = GsonUtil.getInstance().fromJson(json, a);
                } else if(isarray) {
                    TypeToken<?> typeToken = TypeToken.get(parameterizedType.getActualTypeArguments()[0]);
                    aa = GsonUtil.getInstance().fromJson(json, typeToken.getType());
                } else {
                    LogUtil.E(baseResBean.getResult());
                }
            } catch (Exception var19) {
                var19.printStackTrace();
            } finally {
                if(!"200".equals(baseResBean.getCode())) {
                    this.onResult(false, StringUtil.getStr(baseResBean.getMessage()) + StringUtil.getStr(baseResBean.getErrorMessage()), aa);
                } else {
                    this.onResult(true, StringUtil.getStr(baseResBean.getMessage()) + StringUtil.getStr(baseResBean.getErrorMessage()), aa);
                }

            }
        }

    }

    public void onResult(boolean success, String msg, A o) {
        if(msg != null && msg.toLowerCase().contains("Unauthorized".toLowerCase())) {
            MessageEvent messageEvent = new MessageEvent();
            messageEvent.sender = "net";
            messageEvent.dealer = "main";
            EventBus.getDefault().post(messageEvent);
        }

        if(success) {
            this.onSuccess(o);
        } else {
            this.onFail(o == null, msg);
        }

    }

    public void onProgress(long total, long current) {
    }

    public void onSuccess(A o) {
    }

    public void onFail(boolean haveData, String msg) {
    }
}
