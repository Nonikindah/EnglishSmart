package com.project.englishsmart;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

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
        //myContext.deleteDatabase(DATABASE_PATH);
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

    public void insertSentence(String sentence)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] words=sentence.split("\\s+");
        boolean checkWords=true;
        String Query = "SELECT * FROM sentence WHERE sentence = '" + sentence + "' COLLATE NOCASE ";
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() > 0)
        {
            checkWords=false;

        }

        if(checkWords)
        {
            for (int i = 0; i < words.length; i++) {
                words[i] = words[i].replace(".", "");
                words[i] = words[i].replace(",", "");
                words[i] = words[i].replace(":", "");
                words[i] = words[i].replace("\"", "");
                words[i] = words[i].replace("/", "");
                Query = "SELECT * FROM verb_table WHERE VERB1 = '" + words[i] + "' COLLATE NOCASE OR VERB2 = '" + words[i] + "' COLLATE NOCASE OR VERB3 = '" + words[i] + "' COLLATE NOCASE OR VERBING = '" + words[i] + "' COLLATE NOCASE OR VERBS = '" + words[i] + "' COLLATE NOCASE ";
                cursor = db.rawQuery(Query, null);
                if (cursor.getCount() > 0) {
                    checkWords = true;
                    break;
                }
                else{
                    checkWords=false;
                }
            }
            cursor.close();
        }

        db.close();
        if(checkWords==true)
        {
            db = this.getWritableDatabase();
            db.execSQL("INSERT INTO sentence (sentence)" + "VALUES (" + "\""+sentence + "\""+");");
            Toast.makeText(myContext, "Sentence Saved", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(myContext, "your Sentence has no supported word in database", Toast.LENGTH_SHORT).show();
        }
        db.execSQL("INSERT INTO verb_table (VERB1, VERB2, VERB3)" + "VALUES (" + "\""+words[0] + "\","+"\""+words[1] + "\","+"\""+words[2] + "\""+");");
    }

    public void updateSentence(String from, String sentence) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE sentence SET sentence = '" + sentence + "' WHERE sentence='"+from+"' ");
        Toast.makeText(myContext, "Sentence Saved", Toast.LENGTH_SHORT).show();
    }
    public void deleteSentence( String sentence){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM sentence WHERE sentence = '" +sentence+"'");
        Toast.makeText(myContext, "Sentence Deleted", Toast.LENGTH_SHORT).show();
    }
}