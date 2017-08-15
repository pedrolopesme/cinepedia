package com.pedrolopesme.android.cinepedia.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.pedrolopesme.android.cinepedia.data.FavoritesContract.*;


public final class FavoritesDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "favorites.db";

    private static final int DATABASE_VERSION = 1;

    public FavoritesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_WEATHER_TABLE =
                "CREATE TABLE " + FavoriteEntry.TABLE_NAME + " (" +
                        FavoriteEntry._ID + " INTEGER PRIMARY KEY, " +
                        FavoriteEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                        FavoriteEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                        FavoriteEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT, " +
                        FavoriteEntry.COLUMN_ADULT + " INT, " +
                        FavoriteEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                        FavoriteEntry.COLUMN_POPULARITY + " REAL, " +
                        FavoriteEntry.COLUMN_VOTE_AVERAGE + " REAL, " +
                        FavoriteEntry.COLUMN_VOTE_COUNT + " INT, " +
                        FavoriteEntry.COLUMN_VIDEO + " INT, " +
                        FavoriteEntry.COLUMN_RELEASED_AT + " INTEGER);";

        db.execSQL(SQL_CREATE_WEATHER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
