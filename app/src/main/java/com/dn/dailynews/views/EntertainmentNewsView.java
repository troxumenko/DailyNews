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

public class EntertainmentNewsView extends BaseView {

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    @BindView(R.id.list_news)
    RecyclerView recyclerView;

    public EntertainmentNewsView(@NonNull Context context) {
        this(context, null);
    }

    public EntertainmentNewsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EntertainmentNewsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateView(R.layout.content_main);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();


        initArticles();

        swipeContainer.setColorSchemeResources(android.R.color.holo_red_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
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
        subscriptions.add(
                apiService.getArticlesEntertainment()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(answer -> {
                            List<Article> articles = answer.getArticles();
                            recyclerView.setAdapter(new ArticleAdapter(getContext(), articles));
                            recyclerView.smoothScrollToPosition(0);
                            swipeContainer.setRefreshing(false);
                        }, throwable -> {
                            Log.d("Error", throwable.getMessage());
                        }));
    }
}
