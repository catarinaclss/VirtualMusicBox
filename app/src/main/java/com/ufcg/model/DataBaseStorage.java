package com.ufcg.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by st on 27/03/2016.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.database.Cursor;
import android.util.Log;


public class DataBaseStorage implements  StorageSystem {
    /**
     * Database of object Song
     */
    public static final String TABLE_NAME = "SONG";
    public static final String ID = "_id";
    public static final String TITLE = "TITLE";
    public static final String ARTIST = "ARTIST";
    public static final String VOTES = "VOTES";

    private static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + "  ("
            + ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + TITLE + " TEXT NOT NULL,"
            + ARTIST + " TEXT NOT NULL,"
            + VOTES + " INTEGER NOT NULL);";
    /**
     * Create database
     */
    private static final String TAG = "DataBaseStorage";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    private static final String DB_NAME = "DBP";
    private static final int DATABASE_VERSION = 1;
    private final Context mCtx;

    public static class DatabaseHelper extends SQLiteOpenHelper {
        @Override
        public void onOpen(SQLiteDatabase db) {
            super.onOpen(db);
        }

        DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    public DataBaseStorage(Context ctx) {
        this.mCtx = ctx;
        open();
    }

    public void create(){
        mDb.execSQL(CREATE_TABLE);
    }

    public void recreate(){
        mDb.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public DataBaseStorage open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        mDbHelper.close();
        mDb.close();
    }

    public DatabaseHelper getMDBHelper(){
        return mDbHelper;
    }

    public SQLiteDatabase getMdb(){
        return mDb;
    }

    @Override
    public void add(Song song) {
        ContentValues values = new ContentValues();
        values = songContentValues(song);
        mDb.insert(TABLE_NAME, null, values);
    }

    @Override
    public void delete(Integer id) {
        mDb.delete(TABLE_NAME, ID + "=" + id, null);

    }
    public void edit(Song song) {
        ContentValues values = songContentValues(song);
        mDb.update(TABLE_NAME, values, ID + "=" + song.getId(), null);
    }

    private ContentValues songContentValues(Song song) {
        ContentValues values = new ContentValues();

        values.put(TITLE, song.getTitle());
        values.put(ARTIST, song.getArtist());
        values.put(VOTES, song.getVotes());

        return values;
    }

    @Override
    public Song getSong(int key) {
        Song song  = null;

        try{
            Cursor mCursor = mDb.query(true, TABLE_NAME, new String[]{ID, TITLE, ARTIST, VOTES}, ID + "=?", new String[]{String.valueOf(key)}, null, null, null, null);;

            if (mCursor != null) {
                mCursor.moveToFirst();
            }

            Integer id = mCursor.getInt(mCursor.getColumnIndex(ID));
            String title = mCursor.getString(mCursor.getColumnIndex(TITLE));
            String artist = mCursor.getString(mCursor.getColumnIndex(ARTIST));
            int votes = mCursor.getInt(mCursor.getColumnIndex(VOTES));

            song = new Song(id, title, artist, votes);
            song.setId(id);
        }catch(SQLException e){
            e.printStackTrace();
        }
        
        return song;
    }

    @Override
    public List<Song> getPlaylist() {

        List<Song> mPlayList = new ArrayList<Song>();
        Song mSong;
        int id;
        String title;
        String artist;
        int votes;

        try
        {
            Cursor mCursor = mDb.query(true, TABLE_NAME, new String[] { ID, TITLE, ARTIST, VOTES},null,null, null, null, null, null);;

            if(mCursor != null)
            {
                mCursor.moveToFirst();
                while(mCursor.isAfterLast() == false)
                {
                    id = mCursor.getInt(mCursor.getColumnIndex(ID));
                    title = mCursor.getString(mCursor.getColumnIndex(TITLE));
                    Log.i("teste",mCursor.getColumnIndex(TITLE) + "");
                    artist = mCursor.getString(mCursor.getColumnIndex(ARTIST));
                    votes = mCursor.getInt(mCursor.getColumnIndex(VOTES));

                    mSong = new Song(id, title, artist, votes);
                    mPlayList.add(mSong);
                    mCursor.moveToNext();
                }
            }
            mCursor.close();

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        Collections.reverse(mPlayList);
        return mPlayList;

    }

}
