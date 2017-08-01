package com.pedrolopesme.android.cinepedia.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pedrolopesme.android.cinepedia.R;
import com.pedrolopesme.android.cinepedia.activities.MovieDetailActivity;
import com.pedrolopesme.android.cinepedia.dao.DaoFactory;
import com.pedrolopesme.android.cinepedia.dao.TrailerDao;
import com.pedrolopesme.android.cinepedia.domain.Movie;

/**
 * Trailers fetcher async task
 */
public class TrailersAsyncTask extends AsyncTask<Movie, Void, Boolean> {

    // Log tag description
    private final static String LOG_TAG = TrailersAsyncTask.class.getSimpleName();

    // Attached Activity
    private final MovieDetailActivity activity;

    // Movies Dao
    private final TrailerDao trailerDao;

    public TrailersAsyncTask(MovieDetailActivity activity, DaoFactory daoFactory) {
        this.activity = activity;
        this.trailerDao = daoFactory.getTrailerDao();
    }

    @Override
    protected Boolean doInBackground(final Movie... params) {
        try {
            Log.d(LOG_TAG, "Refreshing trailers");
            Movie movie = params[0];
            refresh(movie);
            return true;
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Something bad has happened", ex);
            return false;
        }
    }

    /**
     * Refreshing Trailers
     *
     * @param movie Item
     */
    private void refresh(final Movie movie) {
        if (movie == null) {
            Log.e(LOG_TAG, "It's impossible to refresh movie trailers with a NULL movie");
            return;
        }

        Log.d(LOG_TAG, "Getting movie trailers");
        activity.refreshTrailers(trailerDao.get(movie.getId()));
    }

    @Override
    protected void onPreExecute() {
        Log.d(LOG_TAG, "Executing TrailersAsyncTask");
        activity.getProgressBar().setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(final Boolean result) {
        Log.d(LOG_TAG, "Finishing TrailersAsyncTask execution");
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


