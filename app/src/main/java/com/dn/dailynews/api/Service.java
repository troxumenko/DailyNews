package com.dn.dailynews.api;

import com.dn.dailynews.model.ArticleResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {
    @GET("v2/everything?apiKey=970f8ae98fd84b83a1f92a193b0305fb")
    Single<ArticleResponse> getArticlesWorldNews(
            @Query("page") int page,
            @Query("pageSize") int pageSize,
            @Query("q") String search
    );

    @GET("v2/top-headlines?apiKey=970f8ae98fd84b83a1f92a193b0305fb")
    Single<ArticleResponse> getArticlesNews(
            @Query("country") String country,
            @Query("category") String category,
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );
}
