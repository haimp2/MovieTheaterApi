package com.example.mvvmmoviestheather.data.repository;

import android.net.DnsResolver;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mvvmmoviestheather.data.MovieApiResponse;
import com.example.mvvmmoviestheather.data.TheMovieApi;
import com.example.mvvmmoviestheather.data.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieApiRepository {

    //declaring final variables:
    private static final String BASE_URL = "https://api.themoviedb.org/";
    private static final String API_KEY = "245f3dd16731fd07be4f9e1df9bf4925";
    private static final String LNG = "en-US";

    public TheMovieApi theMovieApi;
    public Retrofit retrofit;

    public MovieApiRepository() {

        retrofit = new Retrofit.Builder().
            baseUrl(BASE_URL).
            addConverterFactory(GsonConverterFactory.create()).
            build();

        theMovieApi = retrofit.create(TheMovieApi.class);
    }


    public void getMoviesByCategory(String category, Integer page, final MoviesViewModel.OnMoviesReceivedCallback callback){


        Call<MovieApiResponse> movieApiResponseCall = theMovieApi.
                getMoviesListByCategory(category, API_KEY, LNG, page);

        movieApiResponseCall.enqueue(new Callback<MovieApiResponse>() {
            @Override
            public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                if(!response.isSuccessful()){

                    return;
                }
                else {
                    MovieApiResponse movieApiResponse = response.body();
                    assert movieApiResponse != null;
                    List<MovieApiResponse.Result> movies = movieApiResponse.getMoviesList();
                    Log.d("NAME OF MOVIE", String.valueOf(movies.size()));
                    callback.onMoviesReceived(movies);
                }
            }

            @Override
            public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                Log.d("" , Objects.requireNonNull(t.getMessage()));
            }
        });

    }
}
