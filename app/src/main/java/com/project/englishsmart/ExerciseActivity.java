package com.project.englishsmart;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class ExerciseActivity extends AppCompatActivity {
    DatabaseHelper MyDb;
    ArrayList <String> sentence;
    TextView sentenceText;
    int i=0;
    //FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MyDb= new DatabaseHelper(this);
        sentenceText= (TextView) findViewById(R.id.sentence);
        fetch();
        sentenceText.setText(sentence.get(0));
    }

    public void next(View view)
    {
        sentenceText.setText(sentence.get(++i));
    }


    public void fetch(){
        MyDb =new DatabaseHelper(this);
        try {

            MyDb.createDatabase();
            MyDb.openDatabase();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        //namelist=new LinkedHashMap<>();
        int ii;
        SQLiteDatabase sd = MyDb.getReadableDatabase();
        Cursor cursor = sd.rawQuery("SELECT * FROM sentence",null);
        ii=cursor.getColumnIndex("sentence");
        sentence=new ArrayList<String>();
        Log.d(TAG,"first");
        if (cursor.moveToFirst()) {
            do {
                sentence.add(cursor.getString(ii));


            } while (cursor.moveToNext());
        }
        //Toast.makeText(ExerciseActivity.this, "jumlah "+verb.size(), Toast.LENGTH_SHORT).show();
    }

}
