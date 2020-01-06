package com.lv.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.lv.network.entity.BaseResponse;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 测试activity
 * 拉取服务器数据参考{@link #getDataFromServer()}
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDataFromServer();
            }
        });
    }

    private void getDataFromServer() {
        //Observer

//        ApiTest.getInstance().testGet2(1)
//                .subscribeOn(Schedulers.io())//指定网络请求在io后台线程中进行
//                .observeOn(AndroidSchedulers.mainThread())//指定observer回调在UI主线程中进行
//                .subscribe(new Observer<BaseResponse<List<TestBean>>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(BaseResponse<List<TestBean>> listBaseResponse) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });

        //Call
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
    }

    private void showToast(String text) {
        Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
    }
}
