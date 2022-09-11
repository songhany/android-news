package com.songhany.news.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.songhany.news.model.Article;
import com.songhany.news.model.NewsResponse;
import com.songhany.news.repository.NewsRepository;

public class HomeViewModel extends ViewModel {

    private final NewsRepository repository;
    private final MutableLiveData<String> countryInput = new MutableLiveData<>();

    public HomeViewModel(NewsRepository newsRepository) {
        this.repository = newsRepository;
    }

    // event
    public void setCountryInput(String country) {
        // data store change logic
        countryInput.setValue(country);
    }

    // event
    public void setFavoriteArticleInput(Article article) {  // the API to favorite an article
        repository.favoriteArticle(article);
    }


    public LiveData<NewsResponse> getTopHeadlines() {
        // countryInput livedata -> switch/translate -> top headline livedata
        return Transformations.switchMap(countryInput, repository::getTopHeadlines);
    }
}
