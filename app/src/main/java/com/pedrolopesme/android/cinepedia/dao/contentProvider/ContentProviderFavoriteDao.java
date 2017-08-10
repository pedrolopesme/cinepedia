package com.pedrolopesme.android.cinepedia.dao.contentProvider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.pedrolopesme.android.cinepedia.dao.FavoriteDao;
import com.pedrolopesme.android.cinepedia.data.FavoritesContract;
import com.pedrolopesme.android.cinepedia.domain.Movie;
import com.pedrolopesme.android.cinepedia.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;

import static com.pedrolopesme.android.cinepedia.data.FavoritesContract.*;

public final class ContentProviderFavoriteDao extends ContentProviderBaseDao implements FavoriteDao {

    public ContentProviderFavoriteDao(ContentResolver contentResolver) {
        super(contentResolver);
    }

    @Override
    public void insert(Movie movie) {
        ContentValues values = new ContentValues();
        values.put(FavoriteEntry._ID, movie.getId());
        values.put(FavoriteEntry.COLUMN_TITLE, movie.getTitle());
        values.put(FavoriteEntry.COLUMN_SYNOPSIS, movie.getOverview());
        values.put(FavoriteEntry.COLUMN_RELEASED_AT, DateUtil.convert(movie.getReleaseDate()));
        values.put(FavoriteEntry.COLUMN_USER_RATING, movie.getVoteAverage());
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

                Movie movie = new Movie();
                movie.setTitle(title);
                movie.setId(id);
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
