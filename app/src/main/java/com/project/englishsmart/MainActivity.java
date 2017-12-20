package com.project.englishsmart;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        FragmentTransaction fragmentTransaction;
        DatabaseHelper MyDb;
        private static ArrayAdapter<String> adapter;
        private ArrayList<String> verb;
        private static ListView listView;
        LinkedHashMap<String,String> namelist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDb= new DatabaseHelper(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment() ).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fetch();

        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,verb);

//
        //ListView listView = (ListView) findViewById(R.id.list);
        //listView.setAdapter(adapter);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_exercise) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new ExerciseFragment()).commit();
            getSupportActionBar().setTitle("Exercise");
            item.setChecked(true);
        } else if (id == R.id.edit) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new MenuEdit()).commit();
//            fragmentTransaction.commit();
            getSupportActionBar().setTitle("Edit Questions");
            item.setChecked(true);

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        Cursor cursor = sd.rawQuery("SELECT * FROM verb_table",null);
        ii=cursor.getColumnIndex("VERB1");
        verb=new ArrayList<String>();
        Log.d(TAG,"first");
        if (cursor.moveToFirst()) {
            do {
                verb.add(cursor.getString(ii));


            } while (cursor.moveToNext());
        }
        Toast.makeText(MainActivity.this, "jumlah "+verb.size(), Toast.LENGTH_SHORT).show();
    }
    public void RefreshList(){

    }

    public void exercise1(View view)
    {
        Intent intent = new Intent(getApplicationContext(), ExerciseActivity.class);
        //Toast.makeText(getActivity(), "Exercise", Toast.LENGTH_SHORT).show();
        startActivity(intent);

    }
}
