package com.pedrolopesme.android.cinepedia.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pedrolopesme.android.cinepedia.R;
import com.pedrolopesme.android.cinepedia.adapters.ReviewRecyclerViewAdapter;
import com.pedrolopesme.android.cinepedia.adapters.TrailerRecyclerViewAdapter;
import com.pedrolopesme.android.cinepedia.asyncTasks.ReviewsAsyncTask;
import com.pedrolopesme.android.cinepedia.asyncTasks.TrailersAsyncTask;
import com.pedrolopesme.android.cinepedia.builders.TrailerUriBuilder;
import com.pedrolopesme.android.cinepedia.clickListeners.TrailerItemClickListener;
import com.pedrolopesme.android.cinepedia.dao.DaoFactory;
import com.pedrolopesme.android.cinepedia.dao.BaseDaoFactory;
import com.pedrolopesme.android.cinepedia.dao.FavoriteDao;
import com.pedrolopesme.android.cinepedia.domain.Movie;
import com.pedrolopesme.android.cinepedia.domain.Review;
import com.pedrolopesme.android.cinepedia.domain.Trailer;
import com.pedrolopesme.android.cinepedia.utils.DateUtil;
import com.pedrolopesme.android.cinepedia.utils.NumberUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity implements TrailerItemClickListener {

    // Log tag description
    private final static String LOG_TAG = MovieDetailActivity.class.getSimpleName();

    // DAO Factory
    private DaoFactory daoFactory;

    // Movie name
    @BindView(R.id.tv_movie_name)
    TextView mMovieNameTextView;

    // Movie poster
    @BindView(R.id.iv_movie_poster)
    ImageView mMoviePosterTextView;

    // Movie release date
    @BindView(R.id.tv_release_date)
    TextView mMovieReleaseDateTextView;

    // Movie rating
    @BindView(R.id.tv_ratings)
    TextView mMovieRatingTextView;

    // Movie Synopsis
    @BindView(R.id.tv_movie_synopsis)
    TextView mMovieSynopsisTextView;

    // Default loading
    @BindView(R.id.pb_loading_indicator)
    ProgressBar mLoadingProgressBar;

    // Trailers Recycler View
    @BindView(R.id.rc_trailers)
    RecyclerView mTrailersRecyclerView;

    // Reviews Recycler View
    @BindView(R.id.rc_reviews)
    RecyclerView mReviewsRecyclerView;

    // Trailers - No Items Message
    @BindView(R.id.tv_trailers_no_items)
    TextView mTrailersNoItemsTextView;

    // Reviews - No Items Message
    @BindView(R.id.tv_reviews_no_items)
    TextView mReviewNoItemsTextView;

    // Fav button
    @BindView(R.id.ib_favorite)
    ImageButton mFavoriteImageButton;

    // Trailer Recycler View Adapter
    TrailerRecyclerViewAdapter mTrailerRecyclerViewAdapter;

    // Review Recycler View Adapter
    ReviewRecyclerViewAdapter mReviewRecyclerViewAdapter;

    private Toast toast;

    private Movie movie;

    private List<Trailer> trailers;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Log.d(LOG_TAG, "Creating Movie Detail Activity!");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        renderTitle();

        ButterKnife.bind(this);

        String baseUrl = getString(R.string.moviedb_base_url);
        String apiKey = getString(R.string.moviedb_api_key);
        daoFactory = new BaseDaoFactory(baseUrl, apiKey, getContentResolver());

        LinearLayoutManager trailerLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTrailerRecyclerViewAdapter = new TrailerRecyclerViewAdapter(getApplicationContext(), this);
        mTrailersRecyclerView.setLayoutManager(trailerLayoutManager);
        mTrailersRecyclerView.setHasFixedSize(false);
        mTrailersRecyclerView.setNestedScrollingEnabled(false);
        mTrailersRecyclerView.setAdapter(mTrailerRecyclerViewAdapter);

        LinearLayoutManager reviewLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mReviewRecyclerViewAdapter = new ReviewRecyclerViewAdapter(getApplicationContext());
        mReviewsRecyclerView.setLayoutManager(reviewLayoutManager);
        mReviewsRecyclerView.setHasFixedSize(false);
        mReviewsRecyclerView.setNestedScrollingEnabled(false);
        mReviewsRecyclerView.setAdapter(mReviewRecyclerViewAdapter);

        movie = getMovie();
        renderActivity(movie);
        bindFavoriteCallback();

        Log.d(LOG_TAG, "Refreshing trailers");
        new TrailersAsyncTask(this, daoFactory).execute(movie);

        Log.d(LOG_TAG, "Refreshing reviews ");
        new ReviewsAsyncTask(this, daoFactory).execute(movie);

        Log.d(LOG_TAG, "Movie Detail created successfully!");
    }

    /**
     * Transforms title text and adding back arrow
     */
    private void renderTitle() {
        Log.d(LOG_TAG, "Rendering title");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(R.string.title_moviedetail);
    }

    /**
     * Apply movie info into Views
     *
     * @param movie selected
     */
    private void renderActivity(final Movie movie) {
        if (movie != null) {
            Log.d(LOG_TAG, "Applying movie info on Views");
            String rating = NumberUtil.stringify(movie.getVoteAverage()) + "/10";

            mMovieNameTextView.setText(movie.getOriginalTitle());
            mMovieReleaseDateTextView.setText(DateUtil.format(movie.getReleaseDate(), "yyyy"));
            mMovieRatingTextView.setText(rating);
            mMovieSynopsisTextView.setText(movie.getOverview());

            if (movie.getPoster() != null) {
                Picasso.with(getApplicationContext())
                        .load(movie.getPoster().getSmall())
                        .placeholder(R.drawable.ic_placeholder)
                        .error(R.drawable.ic_error)
                        .into(mMoviePosterTextView);
            }
            renderFavoriteIcon(movie);
        }
    }

    /**
     * Render favorite button icon
     *
     * @param movie to be checked
     */
    private void renderFavoriteIcon(final Movie movie) {
        final FavoriteDao favoriteDao = daoFactory.getFavoriteDao();
        if (favoriteDao.isFavorite(movie.getId()))
            mFavoriteImageButton.setBackgroundResource(R.drawable.ic_star_on);
        else
            mFavoriteImageButton.setBackgroundResource(R.drawable.ic_star_off);

        mFavoriteImageButton.setVisibility(View.VISIBLE);
    }

    /**
     * Binds Favorite Icon
     */
    private void bindFavoriteCallback() {
        mFavoriteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Movie movie = getMovie();
                final FavoriteDao favoriteDao = daoFactory.getFavoriteDao();
                String message;

                if (movie != null) {
                    if (favoriteDao.isFavorite(movie.getId())) {
                        favoriteDao.delete(movie.getId());
                        message = "Movie \"" + movie.getTitle() + "\" removed from favorites.";
                    } else {
                        favoriteDao.insert(movie);
                        message = "Movie \"" + movie.getTitle() + "\" added to favorites!";
                    }
                    renderFavoriteIcon(movie);
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Extracts selected Movie from intent
     *
     * @return Movie movie
     */
    private Movie getMovie() {
        Log.d(LOG_TAG, "Extracting movie from intent");
        Intent intent = getIntent();
        if (intent.hasExtra(Movie.class.getName())) {
            return (Movie) intent.getParcelableExtra(Movie.class.getName());
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.share_menu, menu);
        Log.d(LOG_TAG, "Share menu inflated successfully!");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item == null) {
            Log.d(LOG_TAG, "NULL item selected");
            return super.onOptionsItemSelected(null);
        }

        switch (item.getItemId()) {
            case R.id.action_share:
                Log.d(LOG_TAG, "Sharing movie trailer");
                shareMovieTrailer();
                break;
            default:
                Log.d(LOG_TAG, "Closing activity");
                finish();
                break;
        }
        return true;
    }

    /**
     * Refreshs the UI with the movie's trailers
     *
     * @param trailers list
     */
    public void refreshTrailers(final List<Trailer> trailers) {
        if (trailers == null) {
            Log.d(LOG_TAG, "No trailers has been found");
            mTrailersNoItemsTextView.setVisibility(View.VISIBLE);
            return;
        }

        this.trailers = trailers;

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "Refreshing recycler view with trailers found");
                mTrailerRecyclerViewAdapter.setTrailers(trailers);

                if (trailers.size() == 0)
                    mTrailersNoItemsTextView.setVisibility(View.VISIBLE);
                else
                    mTrailersNoItemsTextView.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Refreshs the UI with the movie's reviews
     *
     * @param reviews list
     */
    public void refreshReviews(final List<Review> reviews) {
        if (reviews == null) {
            Log.d(LOG_TAG, "No reviews has been found");
            mReviewNoItemsTextView.setVisibility(View.VISIBLE);
            return;
        }
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "Refreshing recycler view with reviews found");
                mReviewRecyclerViewAdapter.setReviews(reviews);

                if (reviews.size() == 0)
                    mReviewNoItemsTextView.setVisibility(View.VISIBLE);
                else
                    mReviewNoItemsTextView.setVisibility(View.GONE);
            }
        });
    }

    public View getProgressBar() {
        return mLoadingProgressBar;
    }

    public Toast getToast() {
        return toast;
    }

    public void setToast(Toast toast) {
        this.toast = toast;
    }

    @Override
    public void onTrailerItemClick(Trailer trailer) {
        Log.i(LOG_TAG, "Trailer clicked: " + trailer);
        Uri trailerLink = TrailerUriBuilder.build(trailer);
        if (trailerLink != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, trailerLink);
            startActivity(intent);
        }
    }

    /**
     * Share the first movie trailer
     */
    public void shareMovieTrailer() {
        if (trailers != null && trailers.size() > 0) {
            Trailer trailer = trailers.get(0);
            Uri trailerLink = TrailerUriBuilder.build(trailer);
            String shareBody = movie.getTitle() + " - " + trailerLink.toString();
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);

            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, movie.getTitle());
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_trailer)));
        } else {
            Toast.makeText(getApplicationContext(), "This movie has no trailers to share", Toast.LENGTH_LONG).show();
        }
    }
}
