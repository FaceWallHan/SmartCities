package com.our.smart.net;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringDef;
import androidx.core.util.Consumer;

import com.google.gson.Gson;
import com.our.smart.bean.BaseBean;
import com.our.smart.bean.post.Login;
import com.our.smart.utils.LocalKeyUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HttpUtil {
    private HttpUtil() {}
    private static volatile HttpUtil single;
    private String contentType="application/json";
    private final StringBuilder builderGetUrl=new StringBuilder();

    /**
     * 随便用了个线程池
     * */
    private static final ExecutorService service= Executors.newFixedThreadPool(50);

    /**
     * 默认为GET请求
     * */
    private boolean isPost=false;
    private String endUrl="";
    private String postMsg;


    public static HttpUtil getInstance() {
        if (single==null){
            synchronized (HttpUtil.class){
                if (single==null){
                    single=new HttpUtil();
                }
            }
        }
        return single;
    }

    /**
     * get请求的信息
     * */
    public HttpUtil inflateGetMap(@Nullable Map<String,String> map) {
        builderGetUrl.setLength(0);
        if (map!=null && !map.isEmpty()){
            builderGetUrl.append("?");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                builderGetUrl.append(entry).append("&");
            }
            builderGetUrl.deleteCharAt(builderGetUrl.length() - 1);
        }
        isPost=false;
        return this;
    }

    /**
     * post请求的信息
     * */
    public HttpUtil inflatePostMsg(Object postMsg) {
        this.postMsg = new Gson().toJson(postMsg);
        isPost=true;
        return this;
    }


    /**
     * 需要拼接的具体url,{@link EndUrlUtil}<br>
     * 此处应更严格的限制类型，比如枚举,{@link StringDef}注解等
     * */
    public HttpUtil inflateEndUrl(String endUrl) {
        this.endUrl = endUrl;
        return this;
    }

    public HttpUtil inflateContentTypeUrl(){
        contentType="application/x-www-form-urlencoded";
        return this;
    }

    public HttpUtil inflateContentTypeJSON(){
        contentType="application/json";
        return this;
    }


    /**
     * 这样判断有很大的局限，lifecycle我觉得更好<br></>
     * @param action Void为没用的泛型，懒得找/写接口了
     * */
    private void actionActivity(Activity activity, Consumer<Void> action){
        if (!activity.isFinishing()){
            activity.runOnUiThread(() -> action.accept(null));
        }
    }


    public <T extends BaseBean> void startRealRequest(Activity activity, Class<T> clazz, NetStateListener<T> listener){
        service.submit(() -> startRequest(activity,clazz,listener));
    }

    private  <T> void startRequest(Activity activity,Class<T> clazz, NetStateListener<T> listener){
        if (endUrl.isEmpty()){
            return;
        }
        (isPost?startPostRequest():startGetRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                actionActivity(activity, unused -> listener.onFailure(e));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    String body= Objects.requireNonNull(response.body()).string();
                    T value= new Gson().fromJson(body, clazz);
                    if (value==null){
                        actionActivity(activity, unused -> listener.onFailure(new IOException("数据解析失败")));
                    }else {
                        actionActivity(activity, unused -> listener.onSuccess(value));
                    }
                }catch (Exception e){
                    actionActivity(activity, unused -> listener.onFailure(e));
                }
            }
        });
    }

    private Request.Builder getRequestBuilder(){
        return new Request.Builder()
                .addHeader("Authorization", LocalKeyUtil.getToken())
                .addHeader("Content_Type", contentType)
                .url(EndUrlUtil.BaseUrl + endUrl+builderGetUrl);
    }

    /**
     * get请求就自己拼接地址吧
     * */
    private Call startGetRequest(){
        OkHttpClient client = new OkHttpClient();
        Request request = getRequestBuilder().get().build();
        return client.newCall(request);
    }

    /**
     * post请求
     * */
    private Call startPostRequest(){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(MediaType.parse(contentType), postMsg);
        Request request = getRequestBuilder().post(body).build();
        return client.newCall(request);
    }
}
