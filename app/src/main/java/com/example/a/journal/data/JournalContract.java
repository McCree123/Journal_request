package com.example.a.journal.data;

import android.provider.BaseColumns;

public final class JournalContract {

    private JournalContract() {
    }

    public static final class GuestEntry implements BaseColumns {
        public final static String TABLE_NAME = "journal";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_PATH_TO_PHOTO = "path_to_photo";
        public final static String COLUMN_FAMILY = "family";
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_PATRONYMIC = "patronymic";
        public final static String COLUMN_DATETIME = "datetime";
        public final static String COLUMN_TEXT = "text";
        public final static String COLUMN_STATUS = "status";
    }
}

