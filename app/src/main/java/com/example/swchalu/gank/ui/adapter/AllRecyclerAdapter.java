package com.example.swchalu.gank.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.swchalu.gank.R;
import com.example.swchalu.gank.entities.SearchResultEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swchalu on 2016/6/28.
 */
public class AllRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<SearchResultEntity> items = new ArrayList<SearchResultEntity>();
    private Context context;
    private LayoutInflater inflater;

    public AllRecyclerAdapter(List<SearchResultEntity> items, Context context) {
        this.items = items;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_all, parent, false);
        AllHolder holder = new AllHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AllHolder mHolder = (AllHolder) holder;
        if (items.size() > 0) {
            mHolder.tv_type.setText(items.get(position).getType());
            mHolder.tv_who.setText(items.get(position).getWho());
            mHolder.tv_desc.setText(items.get(position).getDesc());
            mHolder.tv_publishedAt.setText(items.get(position).getPublishedAt());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class AllHolder extends RecyclerView.ViewHolder {

        private TextView tv_desc;
        private TextView tv_publishedAt;
        private TextView tv_type;
        private TextView tv_who;

        public AllHolder(View itemView) {
            super(itemView);
            this.tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
            this.tv_publishedAt = (TextView) itemView.findViewById(R.id.tv_publishedAt);
            this.tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            this.tv_who = (TextView) itemView.findViewById(R.id.tv_who);
        }
    }
}
