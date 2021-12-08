package com.sunmi.msrtests.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.sunmi.msrtests.data.dao.FDRCTestCaseDao;
import com.sunmi.msrtests.data.dao.TestCaseStatusDao;
import com.sunmi.msrtests.data.entity.FDRCTestCase;
import com.sunmi.msrtests.data.entity.TestCaseStatus;

@Database(
        entities = {FDRCTestCase.class, TestCaseStatus.class},
        version=1,
        exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase singleton;

    public static final String DATABASE_NAME = "fdrcTestCase.db";

    public static AppDatabase getInstance(final Context context) {
        if (singleton == null) {
            if(context == null) {
                return null;
            }

            synchronized (AppDatabase.class) {
                if (singleton == null) {
                    singleton = createDatabase(context);
                }
            }
        }

        return singleton;
    }

    private static AppDatabase createDatabase(Context context) {
        AppDatabase database = null;

        try {
            database = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        catch(Exception ex) {

        }

        return database;
    }

    public abstract FDRCTestCaseDao fdrcTestCaseDao();

    public abstract TestCaseStatusDao testCaseStatusDao();

    public void teardown(Context ctx) {

    }

    public static void dispose(Context ctx) {
        if(singleton != null){
            singleton.teardown(ctx);
            singleton = null;
        }
    }
}