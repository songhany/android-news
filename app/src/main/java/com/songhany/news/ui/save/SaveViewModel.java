package com.songhany.news.ui.save;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.songhany.news.model.Article;
import com.songhany.news.repository.NewsRepository;

import java.util.List;

public class SaveViewModel extends ViewModel {

    private final NewsRepository repository;

    public SaveViewModel(NewsRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<Article>> getAllSavedArticles() {  // getAllSavedArticles gets all the saved articles from the database
        return repository.getAllSavedArticles();
    }

    public void deleteSavedArticle(Article article) {
        repository.deleteSavedArticle(article);
    }
}
