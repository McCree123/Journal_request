package com.example.a.journal.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class JournalDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = JournalDbHelper.class.getSimpleName();

    /**
     * Имя файла базы данных
     */
    private static final String DATABASE_NAME = "journal_request.db";

    /**
     * Версия базы данных. При изменении схемы увеличить на единицу
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Конструктор {@link JournalDbHelper}.
     *
     * @param context Контекст приложения
     */
    public JournalDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Вызывается при создании базы данных
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Строка для создания таблицы
        String SQL_CREATE_GUESTS_TABLE = "CREATE TABLE " + JournalContract.GuestEntry.TABLE_NAME + " ("
                + JournalContract.GuestEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + JournalContract.GuestEntry.COLUMN_PATH_TO_PHOTO + " TEXT NOT NULL, "
                + JournalContract.GuestEntry.COLUMN_FAMILY + " TEXT NOT NULL, "
                + JournalContract.GuestEntry.COLUMN_NAME + " TEXT NOT NULL, "
                + JournalContract.GuestEntry.COLUMN_PATRONYMIC + " TEXT NOT NULL, "
                + JournalContract.GuestEntry.COLUMN_DATETIME + " TEXT NOT NULL, "
                + JournalContract.GuestEntry.COLUMN_TEXT + " TEXT NOT NULL, "
                + JournalContract.GuestEntry.COLUMN_STATUS + " INTEGER);";

        // Запускаем создание таблицы
        db.execSQL(SQL_CREATE_GUESTS_TABLE);
    }

    /**
     * Вызывается при обновлении схемы базы данных
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

