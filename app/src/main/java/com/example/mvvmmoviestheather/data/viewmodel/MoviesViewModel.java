package com.example.mvvmmoviestheather.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.mvvmmoviestheather.data.MovieApiResponse;
import com.example.mvvmmoviestheather.data.repository.MovieApiRepository;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private MutableLiveData<List<MovieApiResponse.Result>> movies = new MutableLiveData<>();
    private MovieApiRepository movieApiRepository;

    public MoviesViewModel(@NonNull Application application) {
        super(application);

        movieApiRepository = new MovieApiRepository();


    }

    public LiveData<List<MovieApiResponse.Result>> getCurrentMoviesList(){
        return this.movies;
    }

    public void getMoviesByCategory(String category, Integer pageNum){
        movieApiRepository.getMoviesByCategory(category, pageNum, new OnMoviesReceivedCallback() {
            @Override
            public void onMoviesReceived(List<MovieApiResponse.Result> moviesFromRepository) {
                movies.setValue(moviesFromRepository);
            }
        });
    }

    public interface OnMoviesReceivedCallback{
        void onMoviesReceived(List<MovieApiResponse.Result> movies);
    }
}
