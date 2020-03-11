package com.example.mvvmmoviestheather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mvvmmoviestheather.view.MainActivity;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_deatails);

        TextView movieTitleTextView = findViewById(R.id.movie_details_name);
        TextView movieOverviewTextView = findViewById(R.id.overview);
        TextView movieRatingTextView = findViewById(R.id.rating);
        ImageView moviePoster = findViewById(R.id.movie_details_image);

        Intent intent = getIntent();
        String movieTitle = intent.getStringExtra(MainActivity.MOVIE_NAME);
        String movieOverview = intent.getStringExtra(MainActivity.MOVIE_OVERVIEW);
        String movieRating = intent.getStringExtra(MainActivity.MOVIE_RATING);
        String moviePosterUrl = intent.getStringExtra(MainActivity.MOVIE_IMG_URL);

        movieTitleTextView.setText(movieTitle);
        movieRatingTextView.setText(movieRating);
        movieOverviewTextView.setText(movieOverview);
        Picasso.get().load(moviePosterUrl).into(moviePoster);

    }
}
