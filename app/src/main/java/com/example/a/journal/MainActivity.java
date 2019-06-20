package com.example.a.journal;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.a.journal.data.JournalContract;
import com.example.a.journal.data.JournalDbHelper;

public class MainActivity extends AppCompatActivity {

    private JournalDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new JournalDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_new_data:
                insertPerson();
                displayDatabaseInfo();
                return true;
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void displayDatabaseInfo() {
        // Создадим и откроем для чтения базу данных
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Зададим условие для выборки - список столбцов
        String[] projection = {
                JournalContract.GuestEntry._ID,
                JournalContract.GuestEntry.COLUMN_PATH_TO_PHOTO,
                JournalContract.GuestEntry.COLUMN_FAMILY,
                JournalContract.GuestEntry.COLUMN_NAME,
                JournalContract.GuestEntry.COLUMN_PATRONYMIC,
                JournalContract.GuestEntry.COLUMN_DATETIME,
                JournalContract.GuestEntry.COLUMN_TEXT,
                JournalContract.GuestEntry.COLUMN_STATUS,
        };

        // Делаем запрос
        Cursor cursor = db.query(
                JournalContract.GuestEntry.TABLE_NAME,   // таблица
                projection,            // столбцы
                null,                  // столбцы для условия WHERE
                null,                  // значения для условия WHERE
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order

        TextView displayTextView = (TextView) findViewById(R.id.text_view_info);

        try {
            displayTextView.setText("Таблица содержит " + cursor.getCount() + " гостей.\n\n");
            displayTextView.append(JournalContract.GuestEntry._ID + " - " +
                    JournalContract.GuestEntry.COLUMN_PATH_TO_PHOTO + " - " +
                    JournalContract.GuestEntry.COLUMN_FAMILY + " - " +
                    JournalContract.GuestEntry.COLUMN_NAME + " - " +
                    JournalContract.GuestEntry.COLUMN_PATRONYMIC + " - " +
                    JournalContract.GuestEntry.COLUMN_DATETIME + " - " +
                    JournalContract.GuestEntry.COLUMN_TEXT + " - " +
                    JournalContract.GuestEntry.COLUMN_STATUS + "\n");

            // Узнаем индекс каждого столбца
            int idColumnIndex = cursor.getColumnIndex(JournalContract.GuestEntry._ID);
            int pathToPhotoColumnIndex = cursor.getColumnIndex(JournalContract.GuestEntry.COLUMN_PATH_TO_PHOTO);
            int familyColumnIndex = cursor.getColumnIndex(JournalContract.GuestEntry.COLUMN_FAMILY);
            int nameColumnIndex = cursor.getColumnIndex(JournalContract.GuestEntry.COLUMN_NAME);
            int patronymicColumnIndex = cursor.getColumnIndex(JournalContract.GuestEntry.COLUMN_PATRONYMIC);
            int datetimeColumnIndex = cursor.getColumnIndex(JournalContract.GuestEntry.COLUMN_DATETIME);
            int textColumnIndex = cursor.getColumnIndex(JournalContract.GuestEntry.COLUMN_TEXT);
            int statusColumnIndex = cursor.getColumnIndex(JournalContract.GuestEntry.COLUMN_STATUS);

            // Проходим через все ряды
            while (cursor.moveToNext()) {
                // Используем индекс для получения строки или числа
                int currentID = cursor.getInt(idColumnIndex);
                String currentPathToPhoto = cursor.getString(pathToPhotoColumnIndex);
                String currentFamily = cursor.getString(familyColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentPatronymic = cursor.getString(patronymicColumnIndex);
                String currentDatetime = cursor.getString(datetimeColumnIndex);
                String currentText = cursor.getString(textColumnIndex);
                int currentStatus = cursor.getInt(statusColumnIndex);
                // Выводим значения каждого столбца
                displayTextView.append(("\n" + currentID + " - " +
                        currentPathToPhoto + " - " +
                        currentFamily + " - " +
                        currentName + " - " +
                        currentPatronymic + " - " +
                        currentDatetime + " - " +
                        currentText + " - " +
                        currentStatus));
            }
        } finally {
            // Всегда закрываем курсор после чтения
            cursor.close();
        }
    }

    /**
     * Вспомогательный метод для вставки записи
     */
    private void insertPerson() {

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Создаем объект ContentValues, где имена столбцов ключи,
        // а информация о госте является значениями ключей
        ContentValues values = new ContentValues();
        values.put(JournalContract.GuestEntry.COLUMN_PATH_TO_PHOTO, "content://lalala/75873");
        values.put(JournalContract.GuestEntry.COLUMN_FAMILY, "Мурзиков");
        values.put(JournalContract.GuestEntry.COLUMN_NAME, "Мурзик");
        values.put(JournalContract.GuestEntry.COLUMN_PATRONYMIC, "Мурзикович");
        values.put(JournalContract.GuestEntry.COLUMN_DATETIME, "02.02.2019 00:09");
        values.put(JournalContract.GuestEntry.COLUMN_TEXT, "мяумяумяу");
        values.put(JournalContract.GuestEntry.COLUMN_STATUS, 1);

        long newRowId = db.insert(JournalContract.GuestEntry.TABLE_NAME, null, values);

//        Uri newUri = getContentResolver().insert(GuestEntry.CONTENT_URI, values);
    }
}
