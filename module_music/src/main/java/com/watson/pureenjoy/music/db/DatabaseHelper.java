package com.watson.pureenjoy.music.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String TAG = DatabaseHelper.class.getName();
	//数据库名
	private static final String DATABASE_NAME = "MusicDatabase.db";

	//音乐表
	public static final String MUSIC_TABLE = "music_table";

	public static final String ID_COLUMN = "id";
	public static final String MUSIC_ID_COLUMN = "music_id";
	public static final String NAME_COLUMN = "name";
	public static final String SINGER_COLUMN = "singer";
	public static final String DURATION_COLUMN = "duration";
	public static final String ALBUM_COLUMN = "album";
	public static final String PATH_COLUMN = "path";
	public static final String PARENT_PATH_COLUMN = "parent_path";
	public static final String FIRST_LETTER_COLUMN = "first_letter";
	public static final String LOVE_COLUMN = "love";


	//最近播放表
	public static final String LAST_PLAY_TABLE = "last_play_table";

	//歌单表
	public static final String SHEET_TABLE = "sheet_table";
	//歌单歌曲表
	public static final String SHEET_SONG_TABLE = "sheet_song_table";


	//数据库版本号
	private static final int VERSION = 1;

	//音乐表建表语句
	private String createMusicTable = "create table if not exists " + MUSIC_TABLE + "("
			+ ID_COLUMN +" integer PRIMARY KEY ,"
			+ NAME_COLUMN +" text,"
			+ SINGER_COLUMN +" text,"
			+ ALBUM_COLUMN + " text,"
			+ DURATION_COLUMN + " long,"
			+ PATH_COLUMN + " text,"
			+ PARENT_PATH_COLUMN + " text,"
			+ LOVE_COLUMN + " integer,"
			+ FIRST_LETTER_COLUMN + " text );";

	//创建播放历史表
	private String createLastPlayTable = "create table if not exists " + LAST_PLAY_TABLE +" ("
			+ ID_COLUMN +" integer,"
			+ "FOREIGN KEY(id) REFERENCES "+ MUSIC_TABLE + " (id) ON DELETE CASCADE);";


	//创建歌单表
	private String createSheetTable = "create table if not exists " + SHEET_TABLE + " ("
			+ ID_COLUMN +" integer PRIMARY KEY autoincrement,"
			+ NAME_COLUMN + " text);";

	//创建歌单歌曲表
	private String createSheetSongTable = "create table if not exists " + SHEET_SONG_TABLE +" ("
			+ ID_COLUMN + " integer,"
			+ MUSIC_ID_COLUMN + " integer,"
			+ "FOREIGN KEY(id) REFERENCES " + SHEET_TABLE + "(id) ON DELETE CASCADE,"
			+ "FOREIGN KEY(music_id) REFERENCES "+ MUSIC_TABLE + " (id) ON DELETE CASCADE) ;";


	public DatabaseHelper(Context context) {
		// 数据库实际被创建是在getWritableDatabase()或getReadableDatabase()方法调用时
		super(context, DATABASE_NAME, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "onCreate");
		db.execSQL(createMusicTable);			//创建音乐表
		db.execSQL(createLastPlayTable);		//创建播放历史表
		db.execSQL(createSheetTable);		    //创建歌单表
		db.execSQL(createSheetSongTable);		//创建歌单歌曲表

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.e(TAG, "onUpgrade: oldVersion ="+oldVersion );
		Log.e(TAG, "onUpgrade: newVersion ="+newVersion );
		if (oldVersion < VERSION){
			db.execSQL("drop table if exists "+MUSIC_TABLE);
			db.execSQL("drop table if exists "+LAST_PLAY_TABLE);
			db.execSQL("drop table if exists "+ SHEET_TABLE);
			db.execSQL("drop table if exists "+ SHEET_SONG_TABLE);
			onCreate(db);
		}
	}


}
