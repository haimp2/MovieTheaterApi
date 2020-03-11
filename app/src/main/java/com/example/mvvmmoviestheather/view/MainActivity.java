package com.example.mvvmmoviestheather.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.mvvmmoviestheather.MovieDetailsActivity;
import com.example.mvvmmoviestheather.MoviesAdapter;
import com.example.mvvmmoviestheather.R;
import com.example.mvvmmoviestheather.data.MovieApiResponse;
import com.example.mvvmmoviestheather.data.repository.MovieApiRepository;
import com.example.mvvmmoviestheather.data.viewmodel.MoviesViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    //const variables declaration:
    public static final String MOVIE_NAME = "movie name";
    public static final String MOVIE_OVERVIEW = "movie overview";
    public static final String MOVIE_RATING = "movie rating";
    public static final String MOVIE_IMG_URL = "image url";


    MoviesAdapter moviesAdapter;
    MoviesViewModel moviesViewModel;
    List<MovieApiResponse.Result> movies;
    int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentPage = 1;

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);

        moviesAdapter = new MoviesAdapter();
        recyclerView.setAdapter(moviesAdapter);

        moviesAdapter.setMoviesApiListener(new MoviesAdapter.MoviesApiListener() {
            @Override
            public void onClickListener(MovieApiResponse.Result result) {
                Intent intent = new Intent(MainActivity.this, MovieDetailsActivity.class);
                intent.putExtra(MOVIE_NAME, result.getTitle());
                intent.putExtra(MOVIE_OVERVIEW, result.getOverview());
                intent.putExtra(MOVIE_RATING, String.valueOf(result.getVoteAverage()));
                intent.putExtra(MOVIE_IMG_URL, MoviesAdapter.imageUrl
                + result.getPosterPath());
                startActivity(intent);
            }
        });

        moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
        moviesViewModel.getCurrentMoviesList().observe(this, new Observer<List<MovieApiResponse.Result>>() {
            @Override
            public void onChanged(List<MovieApiResponse.Result> results) {
                moviesAdapter.setMovies(results);
            }
        });




        Button loadMovies = findViewById(R.id.load_movies_btn);
        loadMovies.setOnClickListener(v -> {
            loadMovies.setText("Next Page");
            moviesViewModel.getMoviesByCategory("popular", currentPage++);
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                moviesAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
