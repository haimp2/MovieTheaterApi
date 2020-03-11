package com.example.mvvmmoviestheather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mvvmmoviestheather.data.MovieApiResponse;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> implements Filterable {

    public static final String imageUrl = "https://image.tmdb.org/t/p/w500";
    private MoviesApiListener moviesApiListener;
    private List<MovieApiResponse.Result> movies = new ArrayList<>();
    private List<MovieApiResponse.Result> fullMovieList =  new ArrayList<>(movies);


    public interface MoviesApiListener{
        void onClickListener(MovieApiResponse.Result result);
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder{

        ImageView movieImage;
        TextView movieName;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);

            movieImage = itemView.findViewById(R.id.movie_image);
            movieName = itemView.findViewById(R.id.movie_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(moviesApiListener!=null){
                        moviesApiListener.onClickListener(movies.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card_layout, parent, false);
        MoviesViewHolder moviesViewHolder = new MoviesViewHolder(view);

        return moviesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {

        MovieApiResponse.Result movie = movies.get(position);

        Picasso.get().load(imageUrl + movie.getPosterPath()).into(holder.movieImage);
        holder.movieName.setText(movie.getTitle());

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public void setMovies(List<MovieApiResponse.Result> movies){
        this.movies = movies;
        fullMovieList =  new ArrayList<>(movies);
        notifyDataSetChanged();
    }

    public void setMoviesApiListener(MoviesApiListener moviesApiListener){
        this.moviesApiListener = moviesApiListener;
    }

    @Override
    public Filter getFilter() {
        return movieNameFilter;
    }

    private Filter movieNameFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<MovieApiResponse.Result> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0){
                filteredList.addAll(fullMovieList);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(MovieApiResponse.Result result : fullMovieList){
                    if (result.getTitle().toLowerCase().contains(filterPattern)){
                        filteredList.add(result);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            movies.clear();
            movies.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };
}

