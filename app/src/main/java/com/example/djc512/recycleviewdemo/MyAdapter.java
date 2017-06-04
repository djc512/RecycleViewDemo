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

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TITLE_TYPE = 0;
    private static final int CONTENT_TYPE = 1;

    private Context ctx;
    private List<DataBean.ResultsBean> datas;

    public MyAdapter(Context ctx, List<DataBean.ResultsBean> datas) {
        this.ctx = ctx;
        this.datas = datas;
    }

    public void update(List<DataBean.ResultsBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        if (viewType == TITLE_TYPE) {
            holder = new TitleHolder(LayoutInflater.from(ctx).inflate(R.layout.title, parent, false));
        } else if (viewType == CONTENT_TYPE) {
            holder = new ContentHolder(LayoutInflater.from(ctx).inflate(R.layout.content, parent, false));
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if (type == TITLE_TYPE) {
            if (holder instanceof TitleHolder) {
                ((TitleHolder) holder).title.setText(datas.get(position).getCreatedAt().split("T")[0]);
                ((TitleHolder) holder).content.setText(datas.get(position).getDesc());
            }
        } else if (type == CONTENT_TYPE) {
            if (holder instanceof ContentHolder) {
                ((ContentHolder) holder).tv_content.setText(datas.get(position).getDesc());
            }
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TITLE_TYPE;
        }
        String dateStr = datas.get(position).getCreatedAt();
        String[] split = dateStr.split("T");
        String date = split[0];

        int previewIndex = position - 1;
        String datePre = datas.get(previewIndex).getCreatedAt();
        String[] splitPre = datePre.split("T");
        String date1 = splitPre[0];

        boolean isSame = date.equals(date1);

        return isSame ? CONTENT_TYPE : TITLE_TYPE;
    }

    public class TitleHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView content;

        public TitleHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }

    public class ContentHolder extends RecyclerView.ViewHolder {

        private final TextView tv_content;

        public ContentHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
