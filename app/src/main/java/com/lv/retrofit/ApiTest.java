package com.lv.retrofit;


import com.lv.network.BaseRetrofitClient;

/**
 * 具体的http调用demo，具体实现参考{@linkplain BaseRetrofitClient}
 * 采用的是干货集中营的测试接口
 *
 * @see<a href="http://gank.io/api">gank.io</a>
 * 本实例可以处理不同baseURL的情况，demo中以两个URL为例
 * 其中一个可以通过调用{@link #getInstance()}
 * <p>
 * 例如
 * <pre><code>
 *     ApiTest.getInstance().doSomeThing();
 * </code></pre>
 * </p>
 * 可参考{@linkplain MainActivity}里的例子
 * 另一个可以通过调用{@link #getInstanceOther()}
 */
public class ApiTest extends BaseRetrofitClient {

    //service  url
    private static String BASE_URL = "http://gank.io/api/";
    private static String BASE_URL_OTHER = "http://gank.io/api/";

    /**
     * 静态内部类单例
     */
    private static class ApiHolder {
        private static ApiTest apiTest = new ApiTest();
        private final static ApiService apiService = apiTest.initRetrofit(BASE_URL, null)
                .create(ApiService.class);
        private final static ApiService apiServiceOther = apiTest.initRetrofit(BASE_URL_OTHER, null)
                .create(ApiService.class);

    }

    public static ApiService getInstance() {
        return ApiHolder.apiService;
    }

    public static ApiService getInstanceOther() {
        return ApiHolder.apiServiceOther;
    }

}
