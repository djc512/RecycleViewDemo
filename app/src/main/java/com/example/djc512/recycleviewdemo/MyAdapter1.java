package com.example.djc512.recycleviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by DjC512 on 2017-6-3.
 */

public class MyAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TITLE_TYPE = 0;
    private static final int CONTENT_TYPE = 1;

    private Context ctx;
    private List<DataBean.ResultsBean> datas;

    public MyAdapter1(Context ctx, List<DataBean.ResultsBean> datas) {
        this.ctx = ctx;
        this.datas = datas;
    }

    public void update(List<DataBean.ResultsBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
        System.out.print("哈哈哈哈哈");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        holder = new ContentHolder(LayoutInflater.from(ctx).inflate(R.layout.content, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ContentHolder) holder).tv_content.setText(datas.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ContentHolder extends RecyclerView.ViewHolder {

        private final TextView tv_content;

        public ContentHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
