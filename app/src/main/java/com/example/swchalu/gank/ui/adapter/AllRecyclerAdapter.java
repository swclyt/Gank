package com.example.swchalu.gank.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.swchalu.gank.App;
import com.example.swchalu.gank.R;
import com.example.swchalu.gank.entities.SearchResultEntity;
import com.example.swchalu.gank.ui.activities.WebActivity;

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
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_all, parent, false);
        AllHolder holder = new AllHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        AllHolder mHolder = (AllHolder) holder;
        if (items.size() > 0) {
            mHolder.tv_type.setText(items.get(position).getType());
            mHolder.tv_who.setText(items.get(position).getWho());
            mHolder.tv_desc.setText(items.get(position).getDesc());
            mHolder.tv_publishedAt.setText(items.get(position).getPublishedAt());
            if (items.get(position).getPic() != null) {
                mHolder.iv_show.setVisibility(View.VISIBLE);
                App.getPicasso().with(context).load(items.get(position).getPic()).into(mHolder.iv_show);
            }
            mHolder.ll_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra("url", items.get(position).getUrl());
                    context.startActivity(intent);
                }
            });
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
        private ImageView iv_show;
        private LinearLayout ll_item;

        public AllHolder(View itemView) {
            super(itemView);
            this.tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
            this.tv_publishedAt = (TextView) itemView.findViewById(R.id.tv_publishedAt);
            this.tv_type = (TextView) itemView.findViewById(R.id.tv_type);
            this.tv_who = (TextView) itemView.findViewById(R.id.tv_who);
            this.iv_show = (ImageView) itemView.findViewById(R.id.iv_show);
            this.ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
        }
    }
}
