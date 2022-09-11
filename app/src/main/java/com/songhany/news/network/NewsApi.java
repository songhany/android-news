package com.songhany.news.network;

import com.songhany.news.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApi {

    @GET("everything")   // get baseURL/v2/everything?q={query}&pageSize={pageSize}
    Call<NewsResponse> getEverything(
            @Query("q") String query, @Query("pageSize") int pageSize);

    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(
            @Query("country") String country);

}
