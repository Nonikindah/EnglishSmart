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
        edit = (EditText)findViewById(R.id.editText1);

        String newString;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
            } else {
                newString= extras.getString("STUDY");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("STUDY");
        }

        edit.setText(newString);
        db= new DatabaseHelper(this);
    }

    public void save(View view)
    {
        db.insertSentence(edit.getText().toString());
        db.updateSentence(edit.getText().toString());
    }

}
