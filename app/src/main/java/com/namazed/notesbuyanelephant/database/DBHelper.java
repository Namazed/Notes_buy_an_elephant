package com.namazed.notesbuyanelephant.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.namazed.notesbuyanelephant.model.ModelTask;

public class DBHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;

	public static final String DATABASE_NAME = "notes_database";

	public static final String TASKS_TABLE = "tasks_table";

	public static final String TASK_TITLE_COLUMN = "task_title";
	public static final String TASK_DATE_COLUMN = "task_date";
	public static final String TASK_PRIORITY_COLUMN = "task_priority";
	public static final String TASK_STATUS_COLUMN = "task_status";
	public static final String TASK_TIME_STAMP_COLUMN = "task_time_stamp";

	private static final String TASKS_TABLE_CREATE_SCRIPT = "CREATE TABLE "
			+ TASKS_TABLE + " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ TASK_TITLE_COLUMN + " TEXT NOT NULL, " + TASK_DATE_COLUMN + " LONG, "
			+ TASK_PRIORITY_COLUMN + " INTEGER, " + TASK_STATUS_COLUMN + " INTEGER, "
			+ TASK_TIME_STAMP_COLUMN + " LONG);";

	public static final String SELECTION_STATUS = DBHelper.TASK_STATUS_COLUMN + " = ?";

	private DBQueryManager mQueryManager;
	private DBUpdateManager mUpdateManager;

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mQueryManager = new DBQueryManager(getReadableDatabase());
		mUpdateManager = new DBUpdateManager(getWritableDatabase());
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL(TASKS_TABLE_CREATE_SCRIPT);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
		sqLiteDatabase.execSQL("DROP TABLE " + TASKS_TABLE);
		onCreate(sqLiteDatabase);
	}

	public void saveTask(ModelTask task) {
		ContentValues newValues = new ContentValues();

		newValues.put(TASK_TITLE_COLUMN, task.getTitle());
		newValues.put(TASK_DATE_COLUMN, task.getDate());
		newValues.put(TASK_STATUS_COLUMN, task.getStatus());
		newValues.put(TASK_PRIORITY_COLUMN, task.getPriority());
		newValues.put(TASK_TIME_STAMP_COLUMN, task.getTimeStamp());

		getWritableDatabase().insert(TASKS_TABLE, null, newValues);
	}

	public DBQueryManager getQueryManager() {
		return mQueryManager;
	}

	public DBUpdateManager getUpdateManager() {
		return mUpdateManager;
	}
}
