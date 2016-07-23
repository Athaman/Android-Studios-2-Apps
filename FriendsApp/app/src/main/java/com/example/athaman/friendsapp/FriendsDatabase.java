package com.example.athaman.friendsapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by keone on 7/22/2016.
 */
public class FriendsDatabase extends SQLiteOpenHelper{

    private static final String TAG = FriendsDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "friends.db";
    private static final int DATABASE_VERSION = 2;
    private final Context mContext;

    interface Tables{
        String FRIENDS = "friends";
    }

    public FriendsDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + Tables.FRIENDS + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + FriendsContract.FriendsColumns.FRIENDS_NAME + " TEXT NOT NULL,"
                + FriendsContract.FriendsColumns.FRIENDS_EMAIL + " TEXT NOT NULL,"
                + FriendsContract.FriendsColumns.FRIENDS_PHONE + " TEXT NOT NULL)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int current) {

        if(old == 1){
            // add in any new table columns
            old = current;
        }
        if(old != DATABASE_VERSION){
            //something has gone pretty wrong
            db.execSQL("DROP TABLE IF EXISTS " + Tables.FRIENDS);
            onCreate(db);
        }
    }

    public static void deleteDatabase(Context context){
        context.deleteDatabase(DATABASE_NAME);
    }
}
