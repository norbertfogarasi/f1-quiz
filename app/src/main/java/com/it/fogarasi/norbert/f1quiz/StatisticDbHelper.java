package com.it.fogarasi.norbert.f1quiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StatisticDbHelper extends SQLiteOpenHelper {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + StatisticContract.StatisticEntry.TABLE_NAME + " (" +
                    StatisticContract.StatisticEntry._ID + " INTEGER PRIMARY KEY," +
                    StatisticContract.StatisticEntry.COLUMN_NAME_NUMBER_OF_QUESTIONS + INTEGER_TYPE + COMMA_SEP +
                    StatisticContract.StatisticEntry.COLUMN_NAME_TIME + TEXT_TYPE + COMMA_SEP +
                    StatisticContract.StatisticEntry.COLUMN_NAME_CORRECT + INTEGER_TYPE + COMMA_SEP +
                    StatisticContract.StatisticEntry.COLUMN_NAME_INCORRECT + INTEGER_TYPE + COMMA_SEP +
                    StatisticContract.StatisticEntry.COLUMN_NAME_PERCENTAGE +
                    " )";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + StatisticContract.StatisticEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "Statistic.db";

    public StatisticDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
