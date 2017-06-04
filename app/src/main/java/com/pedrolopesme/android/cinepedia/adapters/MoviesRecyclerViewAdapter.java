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
 * Recycler View Adapter responsible to load new MoviesDao to the
 * main grid
 */
public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.MovieViewHolder> {

    // Log tag description
    private final static String LOG_TAG = MoviesRecyclerViewAdapter.class.getSimpleName();

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
    public MoviesRecyclerViewAdapter(final Context context, final MovieItemClickListener listener) {
        this.context = context;
        mOnClickListener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        Log.d(LOG_TAG, "Creating view holder ...");
        Context context = parent.getContext();
        int movieItemLayoutId = R.layout.movie_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(movieItemLayoutId, parent, false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        Log.d(LOG_TAG, "onCreateViewHolder created!");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        Log.d(LOG_TAG, "Executing onBindViewHolder for position #" + position);
        Movie movie = movies.get(position);
        holder.renderPoster(movie);
    }

    @Override
    public int getItemCount() {
        return movies == null ? 0 : movies.size();
    }

    public void setMovies(final List<Movie> movies) {
        Log.d(LOG_TAG, "Refreshing movies list");
        this.movies = movies;
        notifyDataSetChanged();
    }

    /**
     * Caches children views
     */
    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Log tag description
        final String LOG_TAG = this.getClass().getSimpleName();

        // Will display movie image
        final ImageView mMovieImage;

        MovieViewHolder(final View itemView) {
            super(itemView);
            mMovieImage = (ImageView) itemView.findViewById(R.id.iv_movie_poster);
            itemView.setOnClickListener(this);
            Log.d(LOG_TAG, "View Holder Created");
        }

        @Override
        public void onClick(final View v) {
            if (movies != null) {
                Movie movie = movies.get(getAdapterPosition());
                if (movie != null) {
                    mOnClickListener.onMovieItemClick(movie);
                    Log.d(LOG_TAG, "View Holder Clicked on movie " + movie);
                }
            }
        }

        /**
         * Renders view poster
         *
         * @param movie selected
         */
        void renderPoster(final Movie movie) {
            Picasso.with(context)
                    .load(movie.getPoster().getSmall())
                    .fit()
                    .centerCrop()
                    .into(mMovieImage);
        }
    }

}
