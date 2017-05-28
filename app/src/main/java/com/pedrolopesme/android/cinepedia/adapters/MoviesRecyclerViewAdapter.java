package com.pedrolopesme.android.cinepedia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pedrolopesme.android.cinepedia.R;
import com.pedrolopesme.android.cinepedia.clickListeners.MovieItemClickListener;

/**
 * Recycler View Adapater responsible to load new Movies to the
 * main grid
 */
public class MoviesRecyclerViewAdapter extends RecyclerView.Adapter<MoviesRecyclerViewAdapter.MovieViewHolder> {

    // Log tag description
    String logTag = this.getClass().getSimpleName();

    // Number of items
    private int mItemsNumber;

    // Click Listener
    private final MovieItemClickListener mOnClickListener;

    /**
     * MoviesRecyclerViewAdapter
     *
     * @param itemsNumber number of items
     * @param listener click listener
     */
    public MoviesRecyclerViewAdapter(int itemsNumber, MovieItemClickListener listener) {
        mItemsNumber = itemsNumber;
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
        Log.d(logTag, "MoviesRecyclerViewAdapter onBindViewHolder for position #" + position + " executed");
    }

    @Override
    public int getItemCount() {
        return mItemsNumber;
    }

    /**
     * Caches children views
     */
    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // Log tag description
        String logTag = this.getClass().getSimpleName();

        // Will display movie name
        TextView mMovieNameTextView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mMovieNameTextView = (TextView) itemView.findViewById(R.id.tv_movie_name);
            itemView.setOnClickListener(this);
            Log.d(logTag, "View Holder Created");
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onMovieItemClick("Some movie clicked " + getAdapterPosition());
            Log.d(logTag, "View Holder Clicked");
        }
    }

}
