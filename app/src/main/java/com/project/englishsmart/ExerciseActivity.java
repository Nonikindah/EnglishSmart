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
import java.util.Random;

import static android.content.ContentValues.TAG;

public class ExerciseActivity extends AppCompatActivity {

    DatabaseHelper MyDb;
    ArrayList <String> sentence;
    TextView sentenceText;
    int number;
    Random rand;

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
        rand= new Random();
        number= rand.nextInt(sentence.size());

        words(sentence.get(number));
        sentenceText.setText(sentence.get(number));
    }

    public void next(View view)
    {
        number= rand.nextInt(sentence.size()-1);
        //Toast.makeText(ExerciseActivity.this, ""+sentence.size(), Toast.LENGTH_SHORT).show();
        words(sentence.get(number));
        sentenceText.setText(sentence.get(number));
    }

    public void words(String s)
    {
        String[] words=s.split("\\s+|\\s*,\\s*|\\s*\\.\\s+");
        String[] words1=s.split("\\s+");
        String word;
        word=generateWord(words, words1);
        //Toast.makeText(ExerciseActivity.this, ""+words1[1], Toast.LENGTH_SHORT).show();
    }

    public String generateWord(String[] s, String[] s1)
    {
        int index;
        int verb;
        String word="";
        String word1="";
        String sentence=s1[0];
        int count=0;
        SQLiteDatabase sd = MyDb.getReadableDatabase();
        Cursor cursor;
        while(count==0)
        {
            index= rand.nextInt(s.length);
            word=s1[index];
            word="wear";
            word=word.replace(".","");
            word=word.replace(",","");
            word=word.replace(":","");
            word=word.replace("\"","");
            word=word.replace("/","");
            cursor = sd.rawQuery("SELECT * FROM verb_table WHERE VERB1 LIKE '"+word+"' OR VERB2 LIKE '"+word+"' OR VERB3 LIKE '"+word+"' OR VERBS LIKE '"+word+"' OR VERBING LIKE '"+word+"' ",null);
            count=cursor.getCount();

            if (cursor.moveToFirst()) {
                do {
                    do{
                        verb= rand.nextInt(3);
                        verb+=1;
                        word1=cursor.getString(verb);
                    }while(word1.equalsIgnoreCase(word));


                } while (cursor.moveToNext());
            }
            Toast.makeText(ExerciseActivity.this, ""+word1, Toast.LENGTH_SHORT).show();
            //count=1;
        }

        for(int i=1; i<s1.length; i++)
        {
            sentence=sentence+" "+s1[i];
        }

        //Toast.makeText(ExerciseActivity.this, ""+sentence, Toast.LENGTH_SHORT).show();

        return sentence;
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
