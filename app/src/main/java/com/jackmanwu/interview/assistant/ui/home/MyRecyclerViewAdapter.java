package com.jackmanwu.interview.assistant.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jackmanwu.interview.assistant.R;
import com.jackmanwu.interview.assistant.domain.Article;

import java.util.ArrayList;
import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int TYPE_NORMAL = 1000;
    public static final int TYPE_FOOTER = 1001;
    private final List<Article> dataList = new ArrayList<>();

    @Override
    public int getItemViewType(int position) {
        if (position == dataList.size()) {
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int resourceId;
        if (viewType == TYPE_FOOTER) {
            resourceId = R.layout.fragment_home_footer;
        } else {
            resourceId = R.layout.fragment_home_item;
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(resourceId, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_NORMAL) {
            Article article = dataList.get(position);
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.title.setText(article.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size() + 1;
    }

    public List<Article> getDataList() {
        return dataList;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.article_title);
        }
    }
}
