package com.dn.dailynews.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import com.dn.dailynews.R;
import com.dn.dailynews.adapter.ArticleAdapter;
import com.dn.dailynews.api.Client;
import com.dn.dailynews.api.Service;
import com.dn.dailynews.model.Article;

import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BusinessNewsView extends BaseView {

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.list_news)
    RecyclerView recyclerView;

    private boolean isLoading = false;
    private int page = 1;
    private ArticleAdapter adapter;
    private OnScrollListener listener = new OnScrollListener();

    public BusinessNewsView(@NonNull Context context) {
        this(context, null);
    }

    public BusinessNewsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BusinessNewsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateView(R.layout.content_main);
        adapter = new ArticleAdapter(getContext());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        recyclerView.addOnScrollListener(listener);
        recyclerView.setAdapter(adapter);
        initArticles();

        swipeContainer.setColorSchemeResources(android.R.color.holo_red_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                loadJSON();
            }
        });
    }

    private void initArticles() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.smoothScrollToPosition(0);
        loadJSON();
    }

    private void loadJSON() {
        Service apiService = Client.getClient();
        if (isLoading) return;
        isLoading = true;
        subscriptions.add(
                apiService.getArticlesNews("ua", "business", page, 5)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(answer -> {
                            List<Article> articles = answer.getArticles();
                            adapter.setData(articles, false);
                            swipeContainer.setRefreshing(false);
                            page++;
                            isLoading = false;
                        }, throwable -> {
                            Log.d("Error", throwable.getMessage());
                        }));
    }

    private class OnScrollListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            LinearLayoutManager mLayoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
            int visibleItemCount = mLayoutManager.getChildCount();
            int totalItemCount = mLayoutManager.getItemCount();
            int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
            if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                loadJSON();
            }
        }
    }
}
