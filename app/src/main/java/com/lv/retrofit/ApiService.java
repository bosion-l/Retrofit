package com.lv.retrofit;

import com.lv.network.entity.BaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {

    @GET("xiandu/data/id/appinn/count/1/page/{pageNum}")
    Call<BaseResponse<List<TestBean>>> testGet1(@Path("pageNum") int pageNum);

    @GET("xiandu/data/id/appinn/count/1/page/{pageNum}")
    Observable<BaseResponse<List<TestBean>>> testGet2(@Path("pageNum") int pageNum);

}
