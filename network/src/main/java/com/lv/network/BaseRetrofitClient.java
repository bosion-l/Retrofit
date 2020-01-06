package com.lv.network;

import android.content.Context;
import android.text.TextUtils;

import com.lv.network.cookie.CookieJarImpl;
import com.lv.network.cookie.store.PersistentCookieStore;
import com.lv.network.interceptor.BaseInterceptor;
import com.lv.network.interceptor.CacheInterceptor;
import com.lv.network.interceptor.logging.Level;
import com.lv.network.interceptor.logging.LoggingInterceptor;
import com.lv.network.utils.HttpsUtils;
import com.lv.network.utils.LLog;
import com.lv.network.utils.ContextUtils;

import java.io.File;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitClient，处理HTTP请求，为了处理多个baseURL的情况，需要通过{@link #initRetrofit(String, Map)}手动设置。
 * 初始化需调用{@link #initRetrofit(String, Map)}
 * 具体实例可以参考demo{@linkplain } ApiService.class
 */
public abstract class BaseRetrofitClient {
    //超时时间
    private static final int DEFAULT_TIMEOUT = 20;
    //缓存时间
    private static final int CACHE_TIMEOUT = 10 * 1024 * 1024;

    private static Context mContext = ContextUtils.getContext();

    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    private Cache cache = null;
    private File httpCacheDirectory;

    public Retrofit initRetrofit(String url, Map<String, String> headers) {

        if (TextUtils.isEmpty(url)) {
            LLog.e("baseurl is empty!");
        }

        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(mContext.getCacheDir(), "lv_cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, CACHE_TIMEOUT);
            }
        } catch (Exception e) {
            LLog.e("Could not create http cache", e);
        }
        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory();
        okHttpClient = new OkHttpClient.Builder()
                .cookieJar(new CookieJarImpl(new PersistentCookieStore(mContext)))
//                .cache(cache)
                .addInterceptor(new BaseInterceptor(headers))
                .addInterceptor(new CacheInterceptor(mContext))
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .addInterceptor(new LoggingInterceptor
                        .Builder()//构建者模式
                        .loggable(BuildConfig.DEBUG) //是否开启日志打印
                        .setLevel(Level.BASIC) //打印的等级
                        .log(Platform.INFO) // 打印类型
                        .request("Request") // request的Tag
                        .response("Response")// Response的Tag
                        .addHeader("header", "request header.") // 添加打印头, 注意 key 和 value 都不能是中文
                        .build()
                )
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(8, 15, TimeUnit.SECONDS))
                // 这里你可以根据自己的机型设置同时连接的个数和时间，我这里8个，和每个保持时间为10s
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();

        return retrofit;
    }
}
