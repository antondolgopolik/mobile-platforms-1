package by.bsuir.mobileplatforms1.datasource.dao;

import static by.bsuir.mobileplatforms1.datasource.DbContract.*;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import by.bsuir.mobileplatforms1.datasource.DbHelper;
import by.bsuir.mobileplatforms1.entity.Result;

public class ResultDao {
    private final DbHelper dbHelper;
    private final UserDao userDao;

    public ResultDao(Context context) {
        dbHelper = new DbHelper(context);
        userDao = new UserDao(context);
    }

    public void create(int userId, int points) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ResultEntry.COLUMN_NAME_USER_ID, userId);
        values.put(ResultEntry.COLUMN_NAME_POINTS, points);
        db.insert(ResultEntry.TABLE_NAME, null, values);
    }

    public List<Result> read() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                ResultEntry.TABLE_NAME, null, null, null,
                null, null, null
        );
        List<Result> results = new ArrayList<>();
        while (cursor.moveToNext()) {
            int resultId = cursor.getInt(cursor.getColumnIndexOrThrow(ResultEntry._ID));
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow(ResultEntry.COLUMN_NAME_USER_ID));
            int points = cursor.getInt(cursor.getColumnIndexOrThrow(ResultEntry.COLUMN_NAME_POINTS));
            Timestamp createdAt = Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(ResultEntry.COLUMN_NAME_CREATED_AT)));
            Result result = new Result();
            result.setResultId(resultId);
            result.setUser(userDao.read(userId));
            result.setPoints(points);
            result.setCreatedAt(createdAt);
            results.add(result);
        }
        return results;
    }

    public Result readLastUserResult(int userId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = ResultEntry.COLUMN_NAME_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};
        String sortOrder = ResultEntry.COLUMN_NAME_CREATED_AT + " DESC";
        String limit = "1";
        Cursor cursor = db.query(
                ResultEntry.TABLE_NAME, null, selection, selectionArgs,
                null, null, sortOrder, limit
        );
        if (cursor.moveToNext()) {
            int resultId = cursor.getInt(cursor.getColumnIndexOrThrow(ResultEntry._ID));
            int points = cursor.getInt(cursor.getColumnIndexOrThrow(ResultEntry.COLUMN_NAME_POINTS));
            Timestamp createdAt = Timestamp.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(ResultEntry.COLUMN_NAME_CREATED_AT)));
            Result result = new Result();
            result.setResultId(resultId);
            result.setUser(userDao.read(userId));
            result.setPoints(points);
            result.setCreatedAt(createdAt);
            return result;
        } else {
            return null;
        }
    }
}
