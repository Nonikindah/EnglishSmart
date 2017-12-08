package com.project.englishsmart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.content.ContentValues.TAG;

/**
 * Created by Iskandar Java on 26/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME="EnglishSmart.db";
    private static String DATABASE_PATH="";
    private SQLiteDatabase myDatabase;
    private final Context myContext;


    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
        this.myContext = context;
        DATABASE_PATH= myContext.getDatabasePath(DATABASE_NAME).toString();
        myContext.deleteDatabase(DATABASE_PATH);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        String sql = "CREATE TABLE " + TABLE_NAME + "(" + ID_VERB + " INTEGER PRIMARY KEY AUTOINCREMENT," + VERB1 + " TEXT," + VERB2 + " TEXT," + VERB3 + " TEXT,"+ VERBS +" TEXT,"+VERBING+" TEXT"+")";
//        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
//        onCreate(db);
    }

    public void createDatabase()throws IOException{

        if(!checkDataBase()){
            this.getWritableDatabase();

            try{
                copyDatabase();
            }
            catch(IOException e){
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase(){
        SQLiteDatabase checkDB = null;

        try{
            checkDB=SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch(SQLiteException e){
            Log.d(TAG,"error");
        }
        if(checkDB!=null){
            checkDB.close();
            return true;
        }
        else{
            return false;
        }
    }

    private void copyDatabase()throws IOException{
        InputStream input= myContext.getAssets().open(DATABASE_NAME);
        OutputStream output= new FileOutputStream(DATABASE_PATH);

        byte[] buffer = new byte[1024];
        int length;

        while((length= input.read(buffer))>0){
            output.write(buffer,0,length);
        }
        output.flush();
        output.close();
        input.close();
    }

    public void openDatabase()throws IOException{
        myDatabase = SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READONLY);
    }

    @Override
    public synchronized void close(){
        if(myDatabase!=null){
            myDatabase.close();
        }
        super.close();
    }
}
