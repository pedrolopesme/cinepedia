package com.pedrolopesme.android.cinepedia.asyncTasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.pedrolopesme.android.cinepedia.R;
import com.pedrolopesme.android.cinepedia.activities.MoviesActivity;
import com.pedrolopesme.android.cinepedia.dao.DaoFactory;
import com.pedrolopesme.android.cinepedia.dao.MoviesDao;
import com.pedrolopesme.android.cinepedia.domain.Sorting;

/**
 * Movies fetcher async task
 */
public class MoviesAsyncTask extends AsyncTask<Sorting, Void, Boolean> {

    // Log tag description
    private final static String LOG_TAG = MoviesAsyncTask.class.getSimpleName();

    // Attached Activity
    private final MoviesActivity activity;

    // Movies Dao
    private final MoviesDao moviesDao;

    public MoviesAsyncTask(MoviesActivity activity, DaoFactory daoFactory) {
        this.activity = activity;
        this.moviesDao = daoFactory.getMoviesDao();
    }

    @Override
    protected Boolean doInBackground(final Sorting... params) {
        try {
            Log.d(LOG_TAG, "Refreshing movies");
            Sorting sorting = params[0];
            refresh(sorting);
            return true;
        } catch (Exception ex) {
            Log.e(LOG_TAG, "Something bad has happened", ex);
            return false;
        }
    }

    /**
     * Get movies according to the sorting
     *
     * @param sorting strategy
     */
    private void refresh(final Sorting sorting) {
        if (sorting == null) {
            Log.e(LOG_TAG, "Really, needs to refresh using NULL sorter?");
            return;
        }

        switch (sorting) {
            case POPULAR:
                Log.d(LOG_TAG, "Getting popular movies");
                activity.refreshMovies(moviesDao.getPopular());
                break;
            case TOP_RATED:
                Log.d(LOG_TAG, "Getting top rated movies");
                activity.refreshMovies(moviesDao.getTopRated());
                break;
        }
    }

    @Override
    protected void onPreExecute() {
        Log.d(LOG_TAG, "Executing MoviesAsyncTask");
        activity.getProgressBar().setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(final Boolean result) {
        Log.d(LOG_TAG, "Finishing MoviesAsyncTask execution");
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


