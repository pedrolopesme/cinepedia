package com.pedrolopesme.android.cinepedia.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pedrolopesme.android.cinepedia.R;
import com.pedrolopesme.android.cinepedia.domain.Review;

import java.util.List;

/**
 * Recycler View Adapter responsible to load new Reviews
 */
public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewRecyclerViewAdapter.ReviewViewHolder> {

    // Log tag description
    private final static String LOG_TAG = ReviewRecyclerViewAdapter.class.getSimpleName();

    // List of items
    private List<Review> reviews = null;

    // Activity Context
    private final Context context;

    /**
     * ReviewRecyclerViewAdapter
     *
     * @param context Activity Context
     */
    public ReviewRecyclerViewAdapter(final Context context) {
        this.context = context;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        Log.d(LOG_TAG, "Creating view holder ...");
        Context context = parent.getContext();
        int reviewLaoutId = R.layout.review_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(reviewLaoutId, parent, false);
        ReviewViewHolder viewHolder = new ReviewViewHolder(view);
        Log.d(LOG_TAG, "onCreateViewHolder created!");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder holder, int position) {
        Log.i(LOG_TAG, reviews.get(position).toString());
        Review review = reviews.get(position);
        if (review != null) {
            holder.render(review);
        }
    }

    @Override
    public int getItemCount() {
        return reviews == null ? 0 : reviews.size();
    }

    public void setReviews(final List<Review> reviews) {
        Log.d(LOG_TAG, "Refreshing review list");
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    /**
     * Caches children views
     */
    class ReviewViewHolder extends RecyclerView.ViewHolder {

        // Log tag description
        final String LOG_TAG = this.getClass().getSimpleName();

        final TextView mReviewContent;
        final TextView mReviewAuthor;

        ReviewViewHolder(final View itemView) {
            super(itemView);
            mReviewContent = (TextView) itemView.findViewById(R.id.tv_review_content);
            mReviewAuthor = (TextView) itemView.findViewById(R.id.tv_review_author);
            Log.d(LOG_TAG, "View Holder Created");
        }

        void render(Review review) {
            mReviewContent.setText(review.getContent());
            mReviewAuthor.setText(review.getAuthor());
        }

    }

}
