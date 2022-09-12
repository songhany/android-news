package com.songhany.news.ui.save;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.songhany.news.R;
import com.songhany.news.databinding.FragmentSaveBinding;
import com.songhany.news.model.Article;
import com.songhany.news.repository.NewsRepository;
import com.songhany.news.repository.NewsViewModelFactory;

public class SaveFragment extends Fragment {

    private FragmentSaveBinding binding;
    private SaveViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentSaveBinding.inflate(inflater, container, false);  // a reference to the binding. We do not need to use findViewById for each view. Any views with an @+id tag will have a binding automatically. We will refer to any views from the binding directly.
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SavedNewsAdapter savedNewsAdapter = new SavedNewsAdapter();
        binding.newsSavedRecyclerView.setAdapter(savedNewsAdapter);
        binding.newsSavedRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        NewsRepository repository = new NewsRepository();
        viewModel = new ViewModelProvider(this, new NewsViewModelFactory(repository)).get(SaveViewModel.class);
        viewModel
                .getAllSavedArticles()
                .observe(
                        getViewLifecycleOwner(),
                        savedArticles -> {
                            if (savedArticles != null) {
                                Log.d("SaveFragment", savedArticles.toString());
                                savedNewsAdapter.setArticles(savedArticles);
                            }
                        });


        savedNewsAdapter.setItemCallback(new SavedNewsAdapter.ItemCallback() {
            @Override
            public void onOpenDetails(Article article) {
                // TODO
                Log.d("onOpenDetails", article.toString());
            }

            @Override
            public void onRemoveFavorite(Article article) {
                viewModel.deleteSavedArticle(article);
            }
        });
    }
}