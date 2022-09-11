package com.songhany.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.songhany.news.model.NewsResponse;
import com.songhany.news.network.NewsApi;
import com.songhany.news.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();   // open all opage in nav_graph.xml
        // navController.navigate(R.id.navigation_search);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController);

        // sample on using retrofit
        NewsApi api = RetrofitClient.newInstance().create(NewsApi.class);
        // endpoint:  baseUrl/top-headlines/?q=tesla&pageSize=10
        Call<NewsResponse> apiResponseCall = api.getTopHeadlines("US");
        apiResponseCall.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                // success
                if (response.isSuccessful()) {
                    Log.d("getTopHeadlines", response.body().toString());
                } else {
                    Log.d("getTopHeadlines", response.toString());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                // failure
                Log.d("getTopHeadlines", t.toString());
            }
        });

        // new a task, make the call<NewsResponse>
        // add task to queue
        // while(true) { retrofit keep check the queue }
        // if queue has 'task', retrofit do task: call endpoint, parse json ... etc
        // once retrofit finish the task
        // callback.onResponse(response)
        // if (task success) newsResponseCallback.onResponse()
        // else newsResponseCallback.onFailure()
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }
}