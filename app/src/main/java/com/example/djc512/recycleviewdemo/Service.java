package com.example.djc512.recycleviewdemo;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by DjC512 on 2017-6-3.
 */

public interface Service {
    @GET("Android/10/1")
    Observable<DataBean> getData();
}
