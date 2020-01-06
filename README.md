# 以Retrofit和RxJava为基础的网络请求库
## 1.目的
   满足日常网络请求的基本需求，包括拦截器interceptor、SSL证书校验、缓存cookie等，满足不同baseURL的情况，后续会添加下载部分
## 2.如何使用
###   引用方式：
   在对应的build.gradle中的dependencies中添加：
   ```
   implementation 'com.github.bosion-l:Retrofit:1.0'
   ```
   在对应Project下的build.gradle中添加：
   ```
   allprojects {
       repositories {
        ...
        maven { url 'https://jitpack.io' }
      }
   }
   ```
###  使用方式：
#### 1st，在YourApplicaton中的onCreate方法中添加初始化代码
```
 //获取应用context
 ContextUtils.init(this);
 //日志初始化开关
 LLog.init(BuildConfig.DEBUG);
 ```
#### 2nd，建立请求接口
```
public interface ApiService {

    @GET("xiandu/data/id/appinn/count/1/page/{pageNum}")
    Call<BaseResponse<List<TestBean>>> testGet1(@Path("pageNum") int pageNum);

    @GET("xiandu/data/id/appinn/count/1/page/{pageNum}")
    Observable<BaseResponse<List<TestBean>>> testGet2(@Path("pageNum") int pageNum);

}
```
#### 3rd，实现自己的RetrofitClient
```
 /**
 * 具体的http调用demo，具体实现参考{@linkplain BaseRetrofitClient}
 * 采用的是干货集中营的测试接口
 * @see<a href="http://gank.io/api">gank.io</a>
 *
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
```
#### 4th，调用
Call
```
ApiTest.getInstance().testGet1(2).enqueue(new Callback<BaseResponse<List<TestBean>>>() {
            @Override
            public void onResponse(Call<BaseResponse<List<TestBean>>> call, Response<BaseResponse<List<TestBean>>> response) {
                List<TestBean> testBeanList = response.body().getResult();
                if (testBeanList.size() > 0) {
                    showToast("第一条的title==" + testBeanList.get(0).getTitle());
                } else showToast("还没有数据哦");
            }

            @Override
            public void onFailure(Call<BaseResponse<List<TestBean>>> call, Throwable t) {

            }
        });        
```
Observer
```
ApiTest.getInstance().testGet2(1)
                .subscribeOn(Schedulers.io())//指定网络请求在io后台线程中进行
                .observeOn(AndroidSchedulers.mainThread())//指定observer回调在UI主线程中进行
                .subscribe(new Observer<BaseResponse<List<TestBean>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseResponse<List<TestBean>> listBaseResponse) {
                          //doNext here.
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
```
#### 5th，结果
![logcat](https://raw.githubusercontent.com/bosion-l/Retrofit/master/image/logcat.png)
![demo](https://raw.githubusercontent.com/bosion-l/Retrofit/master/image/demo.png)
