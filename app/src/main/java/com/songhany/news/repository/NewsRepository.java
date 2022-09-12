package com.songhany.news.repository;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.songhany.news.TinNewsApplication;
import com.songhany.news.database.TinNewsDatabase;
import com.songhany.news.model.Article;
import com.songhany.news.model.NewsResponse;
import com.songhany.news.network.NewsApi;
import com.songhany.news.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private final NewsApi newsApi;
    private final TinNewsDatabase database;

    public NewsRepository() {
        newsApi = RetrofitClient.newInstance().create(NewsApi.class);
        database = TinNewsApplication.getDatabase();
    }

    public LiveData<NewsResponse> getTopHeadlines(String country) {  // get Top headlines
        MutableLiveData<NewsResponse> topHeadlinesLiveData = new MutableLiveData<>();
        newsApi.getTopHeadlines(country)
                .enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful()) {
                            topHeadlinesLiveData.setValue(response.body());
                            Log.d("getTopHeadlines", response.body().toString());
                        } else {
                            topHeadlinesLiveData.setValue(null);
                            Log.d("getTopHeadlines", response.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        topHeadlinesLiveData.setValue(null);
                    }
                });
        return topHeadlinesLiveData;
    }

    public LiveData<NewsResponse> searchNews(String query) {   // search news
        MutableLiveData<NewsResponse> everyThingLiveData = new MutableLiveData<>();
        newsApi.getEverything(query, 40)
                .enqueue(
                        new Callback<NewsResponse>() {
                            @Override
                            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                                if (response.isSuccessful()) {
                                    everyThingLiveData.setValue(response.body());
                                } else {
                                    everyThingLiveData.setValue(null);
                                }
                            }

                            @Override
                            public void onFailure(Call<NewsResponse> call, Throwable t) {
                                everyThingLiveData.setValue(null);
                            }
                        });
        return everyThingLiveData;
    }

    public LiveData<List<Article>> getAllSavedArticles() {
        return database.articleDao().getAllArticles();
    }

    public void deleteSavedArticle(Article article) {
        AsyncTask.execute(() -> database.articleDao().deleteArticle(article));
    }

    public LiveData<Boolean> favoriteArticle(Article article) {
        MutableLiveData<Boolean> resultLiveData = new MutableLiveData<>();
        new FavoriteAsyncTask(database, resultLiveData).execute(article);
        return resultLiveData;
    }


    // I/O & networking -> background thread
    private static class FavoriteAsyncTask extends AsyncTask<Article, Void, Boolean> {

        private final TinNewsDatabase database;
        private final MutableLiveData<Boolean> liveData;

        private FavoriteAsyncTask(TinNewsDatabase database, MutableLiveData<Boolean> liveData) {
            this.database = database;
            this.liveData = liveData;
        }

        @Override
        protected Boolean doInBackground(Article... articles) {
            Article article = articles[0];
            try {
                database.articleDao().saveArticle(article);
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            liveData.setValue(success);
        }
    }

}


