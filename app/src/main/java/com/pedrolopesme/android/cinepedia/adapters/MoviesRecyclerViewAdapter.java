package com.pedrolopesme.android.cinepedia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pedrolopesme.android.cinepedia.R;
import com.pedrolopesme.android.cinepedia.clickListeners.MovieItemClickListener;
import com.pedrolopesme.android.cinepedia.domain.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Recycler View Adapater responsible to load new MoviesDao to the
 * main grid
 */
public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.MovieViewHolder> {

    // Log tag description
    private final String logTag = this.getClass().getSimpleName();

    // Number of items
    private List<Movie> movies = null;

    // Click Listener
    private final MovieItemClickListener mOnClickListener;

    // Activity Context
    private final Context context;

    /**
     * MoviesRecyclerViewAdapter
     *
     * @param context  Activity Context
     * @param listener MovieItemClickListener
     */
    public MoviesRecyclerViewAdapter(Context context, MovieItemClickListener listener) {
        this.context = context;
        mOnClickListener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int movieItemLayoutId = R.layout.movie_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachImmediatelyToParent = false;

        View view = inflater.inflate(movieItemLayoutId, parent, shouldAttachImmediatelyToParent);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        Log.d(logTag, "MoviesRecyclerViewAdapter onCreateViewHolder executed");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
        Log.d(logTag, "MoviesRecyclerViewAdapter onBindViewHolder for position #" + position + " executed");
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    /**
     * Caches children views
     */
    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Log tag description
        String logTag = this.getClass().getSimpleName();

        // Will display movie image
        ImageView mMovieImage;

        MovieViewHolder(View itemView) {
            super(itemView);
            mMovieImage = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
            Log.d(logTag, "View Holder Created");
        }

        @Override
        public void onClick(View v) {
            if (movies != null) {
                Movie movie = movies.get(getAdapterPosition());
                if (movie != null) {
                    mOnClickListener.onMovieItemClick(movie);
                    Log.d(logTag, "View Holder Clicked on movie " + movie);
                }
            }
        }

        void bind(Movie movie) {
            Picasso.with(context)
                    .load(movie.getPoster().getSmall())
                    .fit()
                    .centerCrop()
                    .into(mMovieImage);
        }
    }

}
