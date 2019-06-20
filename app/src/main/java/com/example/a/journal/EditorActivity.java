package com.example.a.journal;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a.journal.data.JournalContract;
import com.example.a.journal.data.JournalDbHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText mPathToPhotoEditText;
    private EditText mFamilyEditText;
    private EditText mNameEditText;
    private EditText mOtchText;
    private EditText mGuestDatetime;
    private EditText mGuestText;
    private Spinner mGuestStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mPathToPhotoEditText = (EditText) findViewById(R.id.edit_guest_path_to_photo);
        mFamilyEditText = (EditText) findViewById(R.id.edit_guest_f);
        mNameEditText = (EditText) findViewById(R.id.edit_guest_i);
        mOtchText = (EditText) findViewById(R.id.edit_guest_o);
        mGuestDatetime = (EditText) findViewById(R.id.edit_guest_datetime);
        mGuestText = (EditText) findViewById(R.id.edit_guest_text);
        mGuestStatus = (Spinner) findViewById(R.id.spnr_status_request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_editor.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Save" menu option
            case R.id.action_save:
                insertPerson();
                // Закрываем активность
                finish();
                return true;
            // Respond to a click on the "Delete" menu option
            case R.id.action_delete:
                // Do nothing for now
                return true;
            // Respond to a click on the "Up" arrow button in the app bar
            case android.R.id.home:
                // Navigate back to parent activity (CatalogActivity)
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Сохраняем введенные данные в базе данных.
     */
    private void insertPerson() {
        // Считываем данные из текстовых полей
        String path_to_photo = mPathToPhotoEditText.getText().toString();
        String family = mFamilyEditText.getText().toString();
        String name = mNameEditText.getText().toString();
        String patronymic = mOtchText.getText().toString();
        String datetime = mGuestDatetime.getText().toString();
        String text = mGuestText.getText().toString();
        int status = Integer.parseInt(mGuestStatus.getSelectedItem().toString());

        JournalDbHelper mDbHelper = new JournalDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(JournalContract.GuestEntry.COLUMN_PATH_TO_PHOTO, path_to_photo);
        values.put(JournalContract.GuestEntry.COLUMN_FAMILY, family);
        values.put(JournalContract.GuestEntry.COLUMN_NAME, name);
        values.put(JournalContract.GuestEntry.COLUMN_PATRONYMIC, patronymic);
        values.put(JournalContract.GuestEntry.COLUMN_DATETIME, datetime);
        values.put(JournalContract.GuestEntry.COLUMN_TEXT, text);
        values.put(JournalContract.GuestEntry.COLUMN_STATUS, status);

        // Вставляем новый ряд в базу данных и запоминаем его идентификатор
        long newRowId = db.insert(JournalContract.GuestEntry.TABLE_NAME, null, values);

        // Выводим сообщение в успешном случае или при ошибке
        if (newRowId == -1) {
            // Если ID  -1, значит произошла ошибка
            Toast.makeText(this, "Ошибка при заведении гостя", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Гость заведён под номером: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }
}
