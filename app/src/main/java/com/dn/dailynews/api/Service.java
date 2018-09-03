package com.dn.dailynews.api;

import com.dn.dailynews.model.ArticleResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface Service {
    @GET("v2/top-headlines?country=ua&apiKey=970f8ae98fd84b83a1f92a193b0305fb")
    Single<ArticleResponse> getArticles();

    @GET("v2/top-headlines?country=ua&category=technology&apiKey=970f8ae98fd84b83a1f92a193b0305fb")
    Single<ArticleResponse> getArticlesTechnology();

    @GET("v2/top-headlines?country=ua&category=business&apiKey=970f8ae98fd84b83a1f92a193b0305fb")
    Single<ArticleResponse> getArticlesBusiness();

    @GET("v2/top-headlines?country=ua&category=sports&apiKey=970f8ae98fd84b83a1f92a193b0305fb")
    Single<ArticleResponse> getArticlesSports();

    @GET("v2/top-headlines?country=ua&category=entertainment&apiKey=970f8ae98fd84b83a1f92a193b0305fb")
    Single<ArticleResponse> getArticlesEntertainment();

    @GET("v2/top-headlines?country=ua&category=health&apiKey=970f8ae98fd84b83a1f92a193b0305fb")
    Single<ArticleResponse> getArticlesHealth();
}
