package com.example.athaman.learnsql;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SQLiteDatabase sqLiteDatabase = getBaseContext().openOrCreateDatabase("SQLite Test 1.db", MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("DROP TABLE contacts;");
        sqLiteDatabase.execSQL("CREATE TABLE contacts(name TEXT, phone INTEGER, email TEXT);");
        sqLiteDatabase.execSQL("INSERT INTO contacts VALUES('Keone', 12345, 'keone@email.com');");
        sqLiteDatabase.execSQL("INSERT INTO contacts VALUES('Thomas', 67890, 'Thomas@email.com');");
        Cursor query = sqLiteDatabase.rawQuery("SELECT * from contacts", null);
        if(query.moveToFirst()){
            //do stuff to records, if will put us at first record if it exists
            do {
                String name = query.getString(0);
                int phone = query.getInt(1);
                String email = query.getString(2);
                Toast.makeText(getBaseContext(), "name = " + name + " phone = " + phone + " email = " + email, Toast.LENGTH_SHORT).show();
            }while(query.moveToNext());

        }else{
            Toast.makeText(getBaseContext(), "It broked", Toast.LENGTH_SHORT).show();
        }

        sqLiteDatabase.close();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
