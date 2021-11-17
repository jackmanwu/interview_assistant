package com.jackmanwu.interview.assistant.ui.home;


import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_SETTLING;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public abstract class LoadMoreListener extends RecyclerView.OnScrollListener {
    private boolean isScrolled = false;
    private int count;
    private int last;

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        isScrolled = newState == SCROLL_STATE_DRAGGING || newState == SCROLL_STATE_SETTLING;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            count = layoutManager.getItemCount();
            last = ((LinearLayoutManager) layoutManager).findLastCompletelyVisibleItemPosition();
        }
        if (isScrolled && count != last && last == count - 1) {
            onLoading(count, last);
        }
    }

    public abstract void onLoading(int count, int last);
}
