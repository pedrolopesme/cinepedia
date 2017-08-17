package com.pedrolopesme.android.cinepedia.dao.contentProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.pedrolopesme.android.cinepedia.dao.FavoriteDao;
import com.pedrolopesme.android.cinepedia.domain.Movie;
import com.pedrolopesme.android.cinepedia.domain.MovieImage;
import com.pedrolopesme.android.cinepedia.utils.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.pedrolopesme.android.cinepedia.data.FavoritesContract.FavoriteEntry;

public final class ContentProviderFavoriteDao extends ContentProviderBaseDao implements FavoriteDao {

    public ContentProviderFavoriteDao(ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    public void insert(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(FavoriteEntry._ID, movie.getId());
        values.put(FavoriteEntry.COLUMN_TITLE, movie.getTitle());
        values.put(FavoriteEntry.COLUMN_ORIGINAL_TITLE, movie.getOriginalTitle());
        values.put(FavoriteEntry.COLUMN_ORIGINAL_LANGUAGE, movie.getOriginalLanguage());
        values.put(FavoriteEntry.COLUMN_OVERVIEW, movie.getOverview());
        values.put(FavoriteEntry.COLUMN_RELEASED_AT, DateUtil.encode(movie.getReleaseDate()));
        values.put(FavoriteEntry.COLUMN_POPULARITY, movie.getPopularity());
        values.put(FavoriteEntry.COLUMN_VOTE_COUNT, movie.getVoteCount());
        values.put(FavoriteEntry.COLUMN_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(FavoriteEntry.COLUMN_VIDEO, movie.isVideo());
        values.put(FavoriteEntry.COLUMN_ADULT, movie.isAdult());
        values.put(FavoriteEntry.COLUMN_POSTER_IMAGE, movie.getPosterImage().getPath());
        values.put(FavoriteEntry.COLUMN_BACKDROP_IMAGE, movie.getBackdrop().getPath());
        getContentResolver().insert(FavoriteEntry.CONTENT_URI, values);
    }

    @Override
    public void delete(long movieId) {
        Uri uri = FavoriteEntry.CONTENT_URI.buildUpon().appendPath(Long.toString(movieId)).build();
        getContentResolver().delete(uri, null, null);
    }

    @Override
    public List<Movie> getMovies() {
        final Cursor cursor = getContentResolver().query(
                FavoriteEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        List<Movie> movies = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                long id = cursor.getLong(cursor.getColumnIndex(FavoriteEntry._ID));
                String title = cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_TITLE));
                String originalTitle = cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_ORIGINAL_TITLE));
                String originalLanguage = cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_ORIGINAL_LANGUAGE));
                String backdropPath = cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_BACKDROP_IMAGE));
                String posterPath = cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_POSTER_IMAGE));
                String overview = cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_OVERVIEW));
                Date releaseAt = DateUtil.decode(cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_RELEASED_AT)));
                Double popularity = cursor.getDouble(cursor.getColumnIndex(FavoriteEntry.COLUMN_POPULARITY));
                int voteCount = cursor.getInt(cursor.getColumnIndex(FavoriteEntry.COLUMN_VOTE_COUNT));
                Double average = cursor.getDouble(cursor.getColumnIndex(FavoriteEntry.COLUMN_VOTE_AVERAGE));
                boolean adult = Boolean.valueOf(cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_ADULT)));
                boolean video = Boolean.valueOf(cursor.getString(cursor.getColumnIndex(FavoriteEntry.COLUMN_VIDEO)));

                Movie movie = new Movie();
                movie.setId(id);
                movie.setTitle(title);
                movie.setOriginalTitle(title);
                movie.setOriginalTitle(originalTitle);
                movie.setOriginalLanguage(originalLanguage);
                movie.setOverview(overview);
                movie.setReleaseDate(releaseAt);
                movie.setPopularity(popularity);
                movie.setVoteCount(voteCount);
                movie.setVoteAverage(average);
                movie.setAdult(adult);
                movie.setVideo(video);
                movie.setPoster(new MovieImage(posterPath));
                movie.setBackdrop(new MovieImage(backdropPath));
                movies.add(movie);
            }
            cursor.close();
        }
        return movies;
    }

    @Override
    public boolean isFavorite(long movieId) {
        String[] selectionArguments = new String[]{String.valueOf(movieId)};
        final Cursor query = getContentResolver().query(
                FavoriteEntry.CONTENT_URI,
                null,
                FavoriteEntry._ID + " = ? ",
                selectionArguments,
                null
        );
        boolean isFavorite = false;
        if (query != null) {
            isFavorite = query.getCount() > 0;
            query.close();
        }
        return isFavorite;
    }
}
