package com.project.englishsmart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class FormEdit extends AppCompatActivity {

    EditText edit;
    DatabaseHelper db;
    Bundle extras;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_edit);
        edit = (EditText)findViewById(R.id.editText1);
        delete = (Button)findViewById(R.id.button_delete);


        String newString;
        if (savedInstanceState == null) {
            extras = getIntent().getExtras();
            if(extras == null) {
                newString= null;
                delete.setVisibility(View.GONE);
            } else {
                newString= extras.getString("STUDY");
            }
        } else {
            newString= (String) savedInstanceState.getSerializable("STUDY");
        }

        edit.setText(newString);
        db= new DatabaseHelper(this);
    }
    public void save(View view){
        if(extras == null){
            db.insertSentence(edit.getText().toString());
        } else {
            db.updateSentence(extras.getString("STUDY"),edit.getText().toString());
        }
        finish();
    }
    public void delete(View view){
        db.deleteSentence(edit.getText().toString());
        finish();
    }

}
