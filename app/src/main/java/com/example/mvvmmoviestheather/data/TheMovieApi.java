package com.example.mvvmmoviestheather.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TheMovieApi {

    @GET("3/movie/{category}")
    Call<MovieApiResponse> getMoviesListByCategory(
            @Path("category") String category,
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") Integer pageNumber
    );

}
