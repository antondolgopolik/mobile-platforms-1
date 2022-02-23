package by.bsuir.mobileplatforms1.datasource;

import static by.bsuir.mobileplatforms1.datasource.DbContract.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public final class DbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "app.db";
    private static final int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // users
        db.execSQL("create table " + UserEntry.TABLE_NAME + "\n" +
                "(\n" +
                "    " + UserEntry._ID + "  integer\n" +
                "        constraint users_pk\n" +
                "            primary key autoincrement,\n" +
                "    " + UserEntry.COLUMN_NAME_USERNAME + " varchar(255) not null\n" +
                ")");
        db.execSQL("create unique index users_username_uindex\n" +
                "    on " + UserEntry.TABLE_NAME + " (" + UserEntry.COLUMN_NAME_USERNAME + ")");
        // results
        db.execSQL("create table " + ResultEntry.TABLE_NAME + "\n" +
                "(\n" +
                "    " + ResultEntry._ID + "  integer\n" +
                "        constraint results_pk\n" +
                "            primary key autoincrement,\n" +
                "    " + ResultEntry.COLUMN_NAME_USER_ID + "    integer\n" +
                "        constraint results_users_user_id_fk\n" +
                "            references " + UserEntry.TABLE_NAME + "\n" +
                "            on update cascade on delete cascade,\n" +
                "    " + ResultEntry.COLUMN_NAME_POINTS + "     integer not null,\n" +
                "    " + ResultEntry.COLUMN_NAME_CREATED_AT + " timestamp default CURRENT_TIMESTAMP not null\n" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + ResultEntry.TABLE_NAME);
        db.execSQL("drop table if exists " + UserEntry.TABLE_NAME);
        onCreate(db);
    }
}
