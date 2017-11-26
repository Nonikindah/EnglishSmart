package com.project.englishsmart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Iskandar Java on 26/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static  final String DATABASE_NAME="EnglishSmart.db";
    public static final String TABLE_NAME="verb_table";
    public static final String ID_VERB="ID";
    public static final String VERB1="VERB1";
    public static final String VERB2="VERB2";
    public static final String VERB3="VERB3";
    public static final String VERBS="VERBS";
    public static final String VERBING="VERBING";

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" + ID_VERB + " INTEGER PRIMARY KEY AUTOINCREMENT," + VERB1 + " TEXT," + VERB2 + " TEXT," + VERB3 + " TEXT,"+ VERBS +" TEXT,"+VERBING+" TEXT"+")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

}
