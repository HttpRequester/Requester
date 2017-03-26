package ir.bpadashi.requester.database;

import java.io.File;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class DatabaseObj extends SQLiteOpenHelper {

    private static DatabaseObj sInstance;

    public static final String DATABASE_NAME = "datareq.db";
    public static final String TABLE = "ser";
    private static DatabaseObj context;
    public static int VERSION = 1;
    public static String FILE_DIR = "Android/data";


    private static Object mutex = new Object();

    private static DatabaseObj aDatabaseObj = null;


    public static DatabaseObj getInstance(Context context) {
        if (aDatabaseObj == null)
            synchronized (mutex) {
                if (aDatabaseObj == null) {
                    aDatabaseObj = new DatabaseObj(context.getApplicationContext());
                } else {
                    return aDatabaseObj;
                }

            }
        return aDatabaseObj;
    }

    private DatabaseObj(Context context) {
        super(context,
                Environment.getExternalStorageDirectory() + File.separator + FILE_DIR + File.separator + context.getPackageName() + File.separator + DATABASE_NAME,
                null, VERSION);
        DatabaseObj.context = this;
    }


    public void onCreate(SQLiteDatabase db) {

        DatabaseObj.context = this;
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE + " (_id  integer primary key autoincrement, name TEXT, obj BLOB)");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }




}
