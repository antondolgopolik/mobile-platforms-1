package by.bsuir.mobileplatforms1.datasource;

import android.provider.BaseColumns;

public final class DbContract {

    protected DbContract() {
    }

    public static class UserEntry implements BaseColumns {
        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_USERNAME = "username";
    }

    public static class ResultEntry implements BaseColumns {
        public static final String TABLE_NAME = "results";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_POINTS = "points";
        public static final String COLUMN_NAME_CREATED_AT = "created_at";
    }
}
