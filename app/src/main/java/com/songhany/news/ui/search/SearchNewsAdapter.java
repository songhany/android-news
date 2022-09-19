package com.songhany.news.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.songhany.news.R;
import com.songhany.news.databinding.SearchNewsItemBinding;
import com.songhany.news.model.Article;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SearchNewsAdapter extends RecyclerView.Adapter<SearchNewsAdapter.SearchNewsViewHolder> {

    interface ItemCallback {  // We usually put interface definition at the top, then private variables, lastly functions.
        void onOpenDetails(Article article);  // onOpenDetails is to be implemented for opening a new fragment for article details.
    }

    private ItemCallback itemCallback;

    public void setItemCallback(ItemCallback itemCallback) {
        this.itemCallback = itemCallback;
    }


    // 1. Supporting data:
    private List<Article> articles = new ArrayList<>();

    public void setArticles(List<Article> newsList) {
        articles.clear();
        articles.addAll(newsList);
        notifyDataSetChanged();  // every time a new list is set, we call notifyDataSetChanged to let the adapter refresh and re-render the data.

    }


    // 2. Adapter overrides:
    // onCreateViewHolder is for providing the generated item views;
    // onBindViewHolder is for binding the data with a view;
    // getItemCount is for providing the current data collection size
    @NonNull
    @Override
    public SearchNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_news_item, parent, false);
        return new SearchNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchNewsViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.itemTitleTextView.setText(article.title);
        if (article.urlToImage != null) {
            Picasso.get().load(article.urlToImage).resize(200, 200).into(holder.itemImageView);
        }
        holder.itemView.setOnClickListener(v -> itemCallback.onOpenDetails(article));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }


    // 3. SearchNewsViewHolder:
    public static class SearchNewsViewHolder extends RecyclerView.ViewHolder {

        ImageView itemImageView;
        TextView itemTitleTextView;

        public SearchNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            SearchNewsItemBinding binding = SearchNewsItemBinding.bind(itemView);
            itemImageView = binding.searchItemImage;
            itemTitleTextView = binding.searchItemTitle;
        }
    }
}
