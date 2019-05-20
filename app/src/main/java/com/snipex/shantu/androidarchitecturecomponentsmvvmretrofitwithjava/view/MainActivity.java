package com.snipex.shantu.androidarchitecturecomponentsmvvmretrofitwithjava.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.snipex.shantu.androidarchitecturecomponentsmvvmretrofitwithjava.R;
import com.snipex.shantu.androidarchitecturecomponentsmvvmretrofitwithjava.adapter.MovieArticleAdapter;
import com.snipex.shantu.androidarchitecturecomponentsmvvmretrofitwithjava.interfaces.ClickListner;
import com.snipex.shantu.androidarchitecturecomponentsmvvmretrofitwithjava.model.Article;
import com.snipex.shantu.androidarchitecturecomponentsmvvmretrofitwithjava.view_model.ArticleViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickListner {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView my_recycler_view;
    private ProgressBar progress_circular_movie_article;
    private LinearLayoutManager layoutManager;
    private MovieArticleAdapter adapter;
    private ArrayList<Article> articleArrayList = new ArrayList<>();
    ArticleViewModel articleViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();

        getMovieArticles();
    }

    /**
     * initialization of views and others
     *
     * @param @null
     */
    private void initialization() {
        progress_circular_movie_article = (ProgressBar) findViewById(R.id.progress_circular_movie_article);
        my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(MainActivity.this);
        my_recycler_view.setLayoutManager(layoutManager);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        my_recycler_view.setHasFixedSize(true);

        // adapter
        adapter = new MovieArticleAdapter(MainActivity.this, articleArrayList, this::onClick);
        my_recycler_view.setAdapter(adapter);

        // View Model
        articleViewModel = ViewModelProviders.of(this).get(ArticleViewModel.class);
    }

    /**
     * get movies articles from news api
     *
     * @param @null
     */
    private void getMovieArticles() {
        articleViewModel.getArticleResponseLiveData().observe(this, articleResponse -> {
            if (articleResponse != null) {

                progress_circular_movie_article.setVisibility(View.GONE);
                List<Article> articles = articleResponse.getArticles();
                articleArrayList.addAll(articles);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(Integer position) {
        Toast.makeText(this, "" + position, Toast.LENGTH_SHORT).show();
    }
}
