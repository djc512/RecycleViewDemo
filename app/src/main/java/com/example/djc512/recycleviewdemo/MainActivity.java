package com.example.djc512.recycleviewdemo;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rv;
    private List<DataBean.ResultsBean> datas = new ArrayList<>();
    private Context ctx;
    private MyAdapter adapter;
    private Button btn;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctx = this;
        initView();
    }

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/data/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        request(retrofit);
    }

    private void request(Retrofit retrofit) {
        Service service = retrofit.create(Service.class);
        service.getData()
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DataBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd = new ProgressDialog(ctx);
                                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                pd.setMessage("努力加载中...");
                                pd.show();
                            }
                        });
                    }

                    @Override
                    public void onNext(@NonNull DataBean dataBean) {
                        pd.dismiss();
                        if (null != dataBean) {
                            datas.clear();
                            datas = dataBean.getResults();
                            Log.d("datas", "datas:" + datas.toString());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.update(datas);
                                }
                            });
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        pd.dismiss();
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void initView() {
        rv = (RecyclerView) findViewById(R.id.rv);
        btn = (Button) findViewById(R.id.btn);

        adapter = new MyAdapter(ctx, datas);
        rv.setLayoutManager(new LinearLayoutManager(ctx));
        rv.setAdapter(adapter);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initData();
            }
        });
    }
}
