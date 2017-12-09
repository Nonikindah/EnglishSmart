package com.project.englishsmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class FormEdit extends AppCompatActivity {

    EditText edit;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_edit);
        //edit = (EditText)findViewById(R.id.editText1);
        //db= new DatabaseHelper(this);
    }

    public void save(View view)
    {
        //db.insertSentence(edit.getText().toString());
    }

}
