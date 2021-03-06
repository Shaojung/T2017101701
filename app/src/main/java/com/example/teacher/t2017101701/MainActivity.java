package com.example.teacher.t2017101701;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class MainActivity extends AppCompatActivity {
    EditText ed1, ed2, ed3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed1 = (EditText) findViewById(R.id.edName);
        ed2 = (EditText) findViewById(R.id.edTel);
        ed3 = (EditText) findViewById(R.id.edAddr);
    }
    public void clickCopy(View v) {
        InputStream is = getResources().openRawResource(R.raw.student);
        try {
            OutputStream os = new FileOutputStream(getFilesDir() + File.separator + "student.sqlite");
            int i = 0;
            while(i != -1) {
                i = is.read();
                os.write(i);
            }
            is.close();
            os.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void clickCopy2(View v)
    {
        File f = new File(getFilesDir() + File.separator + "student2.sqlite");
        if (!f.exists())
        {
            InputStream is = getResources().openRawResource(R.raw.student);
            URI uri = URI.create("file://" + getFilesDir().getAbsolutePath() + File.separator + "student2.sqlite");
            Path p = Paths.get(uri);
            try {
                Files.copy(is, p, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void clickReadDB(View v)
    {
        String path = getFilesDir().getAbsolutePath() + File.separator + "student2.sqlite";
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);
        Cursor c = db.query("phone", new String[] {"id", "name", "tel", "addr"}, null, null, null, null, null);
        c.moveToFirst();
        do {
            Log.d("DB", String.valueOf(c.getInt(0)) + "," + c.getString(1));
        }while(c.moveToNext());
        c.close();
        db.close();

    }
    public void clickInsert(View v)
    {
        String path = getFilesDir().getAbsolutePath() + File.separator + "student2.sqlite";
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, 0);
        ContentValues cv = new ContentValues();
        cv.put("name", ed1.getText().toString());
        cv.put("tel", ed2.getText().toString());
        cv.put("addr", ed3.getText().toString());

        db.insert("phone", null, cv);
        db.close();
    }
    public void clickDelete(View v)
    {
        String path = getFilesDir().getAbsolutePath() + File.separator + "student2.sqlite";
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        db.delete("phone", "id=?", new String[] {"2"});
        db.close();
    }
    public void clickUpdate(View v)
    {
        String path = getFilesDir().getAbsolutePath() + File.separator + "student2.sqlite";
        SQLiteDatabase db = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues cv = new ContentValues();
        cv.put("name", "AAAA");
        db.update("phone", cv, "id=?", new String[] {"1"});
        db.close();
    }

}
