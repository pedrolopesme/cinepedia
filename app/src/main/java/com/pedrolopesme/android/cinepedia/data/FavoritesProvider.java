package com.pedrolopesme.android.cinepedia.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.pedrolopesme.android.cinepedia.data.FavoritesContract.FavoriteEntry;

public class FavoritesProvider extends ContentProvider {

    private final static String LOG_TAG = FavoritesProvider.class.getSimpleName();

    public static final int CODE_FAVORITES = 100;
    public static final int CODE_FAVORITES_WITH_ID = 101;

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private FavoritesDBHelper dbHelper;

    public static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FavoritesContract.CONTENT_AUTHORITY;

        /* This URI is content://com.pedrolopesme.android.cinepedia/favorites/ */
        matcher.addURI(authority, FavoritesContract.PATH_FAVORITES, CODE_FAVORITES);

        /* This URI is content://com.pedrolopesme.android.cinepedia/favorites/1 */
        matcher.addURI(authority, FavoritesContract.PATH_FAVORITES + "/#", CODE_FAVORITES_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        dbHelper = new FavoritesDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        Cursor cursor;

        switch (sUriMatcher.match(uri)) {
            case CODE_FAVORITES:
                cursor = dbHelper.getReadableDatabase().query(
                        FavoriteEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case CODE_FAVORITES_WITH_ID:
                String normalizedUtcDateString = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{normalizedUtcDateString};

                cursor = dbHelper.getReadableDatabase().query(
                        FavoriteEntry.TABLE_NAME,
                        projection,
                        FavoriteEntry._ID + " = ?",
                        selectionArguments,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        throw new RuntimeException("Type isn't implemented.");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {
            case CODE_FAVORITES:
                db.beginTransaction();
                try {
                    final long id = db.insert(FavoriteEntry.TABLE_NAME, null, values);
                    if (id != -1) {
                        return FavoriteEntry.buildUriWithId(id);
                    }
                } catch (Exception ex) {
                    Log.e(LOG_TAG, "It was impossible to insert values", ex);
                } finally {
                    db.endTransaction();
                }
                break;
            default:
                throw new RuntimeException("Something went wrong.");
        }
        throw new RuntimeException("Value not inserted.");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;

        switch (sUriMatcher.match(uri)) {
            case CODE_FAVORITES:
                String normalizedUtcDateString = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{normalizedUtcDateString};

                numRowsDeleted = dbHelper.getWritableDatabase().delete(
                        FavoriteEntry.TABLE_NAME,
                        FavoriteEntry._ID + " = ?",
                        selectionArguments);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsUpdated;

        switch (sUriMatcher.match(uri)) {
            case CODE_FAVORITES_WITH_ID:
                String normalizedUtcDateString = uri.getLastPathSegment();
                String[] selectionArguments = new String[]{normalizedUtcDateString};

                numRowsUpdated = dbHelper.getWritableDatabase().update(
                        FavoriteEntry.TABLE_NAME,
                        values,
                        FavoriteEntry._ID + " = ?",
                        selectionArguments);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        /* If we actually deleted any rows, notify that a change has occurred to this URI */
        if (numRowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsUpdated;
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        dbHelper.close();
        super.shutdown();
    }
}
