package com.pedrolopesme.android.cinepedia.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pedrolopesme.android.cinepedia.R;
import com.pedrolopesme.android.cinepedia.activities.MovieDetailActivity;
import com.pedrolopesme.android.cinepedia.dao.DaoFactory;
import com.pedrolopesme.android.cinepedia.dao.ReviewDao;
import com.pedrolopesme.android.cinepedia.domain.Movie;

/**
 * Reviews async task
 */
final public class ReviewsAsyncTask extends AsyncTask<Movie, Void, Boolean> {

    // Log tag description
    private final static String LOG_TAG = ReviewsAsyncTask.class.getSimpleName();

    // Attached Activity
    private final MovieDetailActivity activity;

    // Reviews Dao
    private final ReviewDao reviewDao;

    public ReviewsAsyncTask(MovieDetailActivity activity, DaoFactory daoFactory) {
        this.activity = activity;
        this.reviewDao = daoFactory.getReviewDao();
    }

    @Override
    protected Boolean doInBackground(final Movie... params) {
        try {
            Log.d(LOG_TAG, "Refreshing reviews");
            Movie movie = params[0];
            refresh(movie);
            return true;
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Something bad has happened", ex);
            return false;
        }
    }

    /**
     * Refreshing Reviews
     *
     * @param movie Item
     */
    private void refresh(final Movie movie) {
        if (movie == null) {
            Log.e(LOG_TAG, "It's impossible to refresh movie reviews with a NULL movie");
            return;
        }

        Log.d(LOG_TAG, "Getting movie reviews");
        activity.refreshReviews(reviewDao.get(movie.getId()));
    }

    @Override
    protected void onPreExecute() {
        Log.d(LOG_TAG, "Executing ReviewsAsyncTask");
        activity.getProgressBar().setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(final Boolean result) {
        Log.d(LOG_TAG, "Finishing ReviewsAsyncTask execution");
        activity.getProgressBar().setVisibility(View.INVISIBLE);
        if (!result) {
            Context context = activity.getApplicationContext();
            if (activity.getToast() != null) {
                activity.getToast().cancel();
            }
            activity.setToast(Toast.makeText(context, R.string.error_conectivity, Toast.LENGTH_LONG));
            activity.getToast().show();
        }
    }
}


