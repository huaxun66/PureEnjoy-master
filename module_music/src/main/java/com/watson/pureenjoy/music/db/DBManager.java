package com.watson.pureenjoy.music.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.github.promeg.pinyinhelper.Pinyin;
import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.http.entity.local.LocalMusicInfo;
import com.watson.pureenjoy.music.http.entity.local.MusicSheetInfo;

import java.util.ArrayList;
import java.util.List;

import me.jessyan.armscomponent.commonsdk.utils.SharedPreferenceUtil;

import static com.watson.pureenjoy.music.db.DatabaseHelper.ID_COLUMN;
import static com.watson.pureenjoy.music.db.DatabaseHelper.LOVE_COLUMN;
import static com.watson.pureenjoy.music.db.DatabaseHelper.MUSIC_ID_COLUMN;

public class DBManager {

    private static final String TAG = DBManager.class.getName();
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private static DBManager instance = null;


    /* 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,mFactory);
     * 需要一个context参数 ,所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
     */
    public DBManager(Context context) {
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    public static synchronized DBManager getInstance(Context context) {
        if (instance == null) {
            instance = new DBManager(context);
        }
        return instance;
    }

    // 获取音乐表歌曲数量
    public int getMusicCount(int table) {
        int musicCount = 0;
        Cursor cursor = null;
        switch (table) {
            case MusicConstants.LIST_ALLMUSIC:
                cursor = db.query(DatabaseHelper.MUSIC_TABLE, null, null, null, null, null, null);
                break;
            case MusicConstants.LIST_LASTPLAY:
                cursor = db.query(DatabaseHelper.LAST_PLAY_TABLE, null, null, null, null, null, null);
                break;
            case MusicConstants.LIST_MYLOVE:
                cursor = db.query(DatabaseHelper.MUSIC_TABLE, null, LOVE_COLUMN + " = ? ", new String[]{"" + 1}, null, null, null);
                break;
            case MusicConstants.LIST_MYPLAY:
                cursor = db.query(DatabaseHelper.SHEET_TABLE, null, null, null, null, null, null);
                break;
        }
        if (cursor.moveToFirst()) {
            musicCount = cursor.getCount();
        }
        if (cursor != null) {
            cursor.close();
        }
        return musicCount;
    }

    public List<LocalMusicInfo> getAllMusicFromMusicTable() {
        Log.d(TAG, "getAllMusicFromMusicTable: ");
        List<LocalMusicInfo> musicInfoList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try {
            cursor = db.query(DatabaseHelper.MUSIC_TABLE, null, null, null, null, null, null);
            musicInfoList = cursorToMusicList(cursor);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            if (cursor!=null){
                cursor.close();
            }
        }
        return musicInfoList;
    }

    public LocalMusicInfo getSingleMusicFromMusicTable(int id) {
        Log.i(TAG, "getSingleMusicFromMusicTable: ");
        List<LocalMusicInfo> musicInfoList = null;
        LocalMusicInfo musicInfo = null;
        Cursor cursor = null;
        db.beginTransaction();
        try {
            cursor = db.query(DatabaseHelper.MUSIC_TABLE, null, ID_COLUMN + " = ?", new String[]{"" + id}, null, null, null);
            musicInfoList = cursorToMusicList(cursor);
            musicInfo = musicInfoList.get(0);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            if (cursor!=null){
                cursor.close();
            }
        }
        return musicInfo;
    }


    public List<LocalMusicInfo> getAllMusicFromTable(int playList) {
        Log.d(TAG, "getAllMusicFromTable: ");
        List<Integer> idList = getMusicList(playList);
        List<LocalMusicInfo> musicList = new ArrayList<>();
        for (int id : idList) {
            musicList.add(getSingleMusicFromMusicTable(id));
        }
        return musicList;
    }

    public List<MusicSheetInfo> getMySheet() {
        List<MusicSheetInfo> musicSheetInfos = new ArrayList<>();
        Cursor cursor = db.query(DatabaseHelper.SHEET_TABLE, null, null, null, null, null, null);
        Cursor cursorCount = null;
        while (cursor.moveToNext()) {
            MusicSheetInfo musicSheetInfo = new MusicSheetInfo();
            int id = Integer.valueOf(cursor.getString(cursor.getColumnIndex(ID_COLUMN)));
            musicSheetInfo.setId(id);
            musicSheetInfo.setName(cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME_COLUMN)));
            cursorCount = db.query(DatabaseHelper.SHEET_SONG_TABLE,null, ID_COLUMN + " = ?", new String[]{"" + id}, null,null,null);
            musicSheetInfo.setCount(cursorCount.getCount());
            musicSheetInfos.add(musicSheetInfo);
        }
        if (cursor!=null){
            cursor.close();
        }
        if (cursorCount!=null){
            cursorCount.close();
        }
        return musicSheetInfos;
    }


    public void createSheet(String name) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.NAME_COLUMN, name);
        db.insert(DatabaseHelper.SHEET_TABLE, null, values);
    }

    public List<LocalMusicInfo> getMusicListBySinger(String singer){
        List<LocalMusicInfo> musicInfoList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try{
            String sql = "select * from "+DatabaseHelper.MUSIC_TABLE+" where "+ DatabaseHelper.SINGER_COLUMN+" = ? ";
            cursor = db.rawQuery(sql,new String[]{singer});
            musicInfoList = cursorToMusicList(cursor);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
            if (cursor !=null)
                cursor.close();
        }
        return musicInfoList;
    }

    public List<LocalMusicInfo> getMusicListByAlbum(String album){
        List<LocalMusicInfo> musicInfoList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try{
            String sql = "select * from "+DatabaseHelper.MUSIC_TABLE+" where "+ DatabaseHelper.ALBUM_COLUMN+" = ? ";
            cursor = db.rawQuery(sql,new String[]{album});
            musicInfoList = cursorToMusicList(cursor);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
            if (cursor !=null)
                cursor.close();
        }
        return musicInfoList;
    }

    public List<LocalMusicInfo> getMusicListByFolder(String folder){
        List<LocalMusicInfo> musicInfoList = new ArrayList<>();
        Cursor cursor = null;
        db.beginTransaction();
        try{
            String sql = "select * from "+DatabaseHelper.MUSIC_TABLE+" where "+ DatabaseHelper.PARENT_PATH_COLUMN+" = ? ";
            cursor = db.rawQuery(sql,new String[]{folder});
            musicInfoList = cursorToMusicList(cursor);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
            if (cursor !=null)
                cursor.close();
        }
        return musicInfoList;
    }

    public ArrayList<Integer> getMusicIdListByPlaylist(int playlistId){
        Cursor cursor = null;
        db.beginTransaction();
        ArrayList<Integer> list = new ArrayList<Integer>();
        try{
            String sql = "select * from "+DatabaseHelper.SHEET_SONG_TABLE+" where "+ ID_COLUMN+" = ? ";
            cursor = db.rawQuery(sql,new String[]{""+playlistId});
            while (cursor.moveToNext()) {
                int musicId = cursor.getInt(cursor.getColumnIndex(MUSIC_ID_COLUMN));
                list.add(musicId);
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
            if (cursor !=null)
                cursor.close();
        }
        return list;
    }

    public List<LocalMusicInfo> getMusicListByPlaylist(int playlistId){
        List<LocalMusicInfo> musicInfoList = new ArrayList<>();
        Cursor cursor = null;
        int id;
        db.beginTransaction();
        try{
            String sql = "select * from "+DatabaseHelper.SHEET_SONG_TABLE+" where "+ ID_COLUMN+" = ? ORDER BY "+ ID_COLUMN;
            cursor = db.rawQuery(sql,new String[]{""+playlistId});
            while (cursor.moveToNext()){
                LocalMusicInfo musicInfo = new LocalMusicInfo();
                id =  cursor.getInt(cursor.getColumnIndex(MUSIC_ID_COLUMN));
                musicInfo = getSingleMusicFromMusicTable(id);
                musicInfoList.add(musicInfo);
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
            if (cursor !=null)
                cursor.close();
        }
        return musicInfoList;
    }





    public void insertMusicListToMusicTable(List<LocalMusicInfo> musicInfoList) {
        Log.d(TAG, "insertMusicListToMusicTable: ");
        for (LocalMusicInfo musicInfo : musicInfoList) {
            insertMusicInfoToMusicTable(musicInfo);
        }
    }

    //添加歌曲到音乐表
    public void insertMusicInfoToMusicTable(LocalMusicInfo musicInfo) {
        ContentValues values;
        Cursor cursor = null;
        int id = 1;
        try {
            values = musicInfoToContentValues(musicInfo);
            String sql = "select max(id) from " + DatabaseHelper.MUSIC_TABLE + ";";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                //设置新添加的ID为最大ID+1
                id = cursor.getInt(0) + 1;
            }
            values.put(ID_COLUMN, id);
            db.insert(DatabaseHelper.MUSIC_TABLE, null, values);
        } catch (Exception e) {
            e.printStackTrace();
            if (cursor!=null){
                cursor.close();
            }
        }
    }

    //添加音乐到歌单
    public void addToPlaylist(int playlistId,int musicId){
        ContentValues values = new ContentValues();
        values.put(ID_COLUMN,playlistId);
        values.put(MUSIC_ID_COLUMN,musicId);
        db.insert(DatabaseHelper.SHEET_SONG_TABLE,null,values);
    }

    //检索音乐是否已经存在歌单中
    public boolean isExistPlaylist(int playlistId,int musicId){
        boolean result = false;
        Cursor cursor = db.query(DatabaseHelper.SHEET_SONG_TABLE,null,ID_COLUMN + " = ? and "+ MUSIC_ID_COLUMN + " = ? ",
                new String[]{""+playlistId,""+musicId},null,null,null);
        if (cursor.moveToFirst()){
            result= true;
        }
        if (cursor!=null){
            cursor.close();
        }
        return  result;
    }

    public void updateAllMusic(List<LocalMusicInfo> musicInfoList) {
        db.beginTransaction();
        try {
            deleteAllTable();
            insertMusicListToMusicTable(musicInfoList);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }


    //删除数据库中所有的表
    public void deleteAllTable() {
        db.execSQL("PRAGMA foreign_keys=ON");
        db.delete(DatabaseHelper.MUSIC_TABLE, null, null);
        db.delete(DatabaseHelper.LAST_PLAY_TABLE, null, null);
        db.delete(DatabaseHelper.SHEET_TABLE, null, null);
        db.delete(DatabaseHelper.SHEET_SONG_TABLE, null, null);
    }

    //删除指定音乐
    public void deleteMusic(int id) {
        db.execSQL("PRAGMA foreign_keys=ON");
        db.delete(DatabaseHelper.MUSIC_TABLE, ID_COLUMN + " = ? ", new String[]{"" + id});
        db.delete(DatabaseHelper.LAST_PLAY_TABLE, ID_COLUMN + " = ? ", new String[]{"" + id});
    }

    public void deletePlaylist(int id) {
        db.delete(DatabaseHelper.SHEET_TABLE, ID_COLUMN + " = ? ", new String[]{"" + id});
    }

    //根据从哪个activity中发出的移除歌曲指令判断
    public void removeMusic(int id, int witchActivity) {
        db.execSQL("PRAGMA foreign_keys=ON");
        switch (witchActivity) {
            case MusicConstants.ACTIVITY_LOCAL:
                db.delete(DatabaseHelper.MUSIC_TABLE, ID_COLUMN + " = ? ", new String[]{"" + id});
                break;
            case MusicConstants.ACTIVITY_RECENTPLAY:
                db.delete(DatabaseHelper.LAST_PLAY_TABLE, ID_COLUMN + " = ? ", new String[]{"" + id});
                break;
            case MusicConstants.ACTIVITY_MYLOVE:
                ContentValues values = new ContentValues();
                values.put(LOVE_COLUMN, 0);
                db.update(DatabaseHelper.MUSIC_TABLE, null, ID_COLUMN + " = ? ", new String[]{"" + id});
                break;
        }
    }

    //根据从哪个activity中发出的移除歌曲指令判断
    public int removeMusicFromPlaylist(int musicId, int playlistId) {
        db.execSQL("PRAGMA foreign_keys=ON");
        int ret = 0;
        try {
            ret = db.delete(DatabaseHelper.SHEET_SONG_TABLE, ID_COLUMN + " = ? and " + MUSIC_ID_COLUMN
                    + " = ? ", new String[]{"" + playlistId, musicId + ""});
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    // 获取歌曲路径
    public String getMusicPath(int id) {
        Log.d(TAG, "getMusicPath id = " + id);
        if (id == -1) {
            return null;
        }
        String path = null;
        Cursor cursor = null;
        setLastPlay(id);        //每次播放一首新歌前都需要获取歌曲路径，所以可以在此设置最近播放
        try {
            cursor = db.query(DatabaseHelper.MUSIC_TABLE, null, ID_COLUMN + " = ?", new String[]{"" + id}, null, null, null);
            Log.i(TAG, "getCount: " + cursor.getCount());
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PATH_COLUMN));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return path;
    }

    //获取音乐表中的第一首音乐的ID
    public int getFirstId(int listNumber) {
        Cursor cursor = null;
        int id = -1;
        try {
            switch (listNumber) {
                case MusicConstants.LIST_ALLMUSIC:
                    cursor = db.rawQuery("select min(id) from " + DatabaseHelper.MUSIC_TABLE, null);
                    break;
                default:
                    Log.i(TAG, "getFirstId: default");
                    break;
            }
            if (cursor.moveToFirst()) {
                id = cursor.getInt(0);
                Log.d(TAG, "getFirstId min id = " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return id;
    }


    // 获取下一首歌曲(id)
    public int getNextMusic(ArrayList<Integer> musicList, int id, int playMode) {
        if (id == -1) {
            return -1;
        }
        //找到当前id在列表的第几个位置（i+1）
        int index = musicList.indexOf(id);
        if (index == -1) {
            return -1;
        }
        // 如果当前是最后一首
        switch (playMode) {
            case MusicConstants.PLAYMODE_SEQUENCE:
                if ((index + 1) == musicList.size()) {
                    id = musicList.get(0);
                } else {
                    ++index;
                    id = musicList.get(index);
                }
                break;
            case MusicConstants.PLAYMODE_SINGLE_REPEAT:
                break;
            case MusicConstants.PLAYMODE_RANDOM:
                id = getRandomMusic(musicList, id);
                break;
        }
        return id;
    }

    // 获取上一首歌曲(id)
    public int getPreMusic(ArrayList<Integer> musicList, int id, int playMode) {
        if (id == -1) {
            return -1;
        }
        //找到当前id在列表的第几个位置（i+1）
        int index = musicList.indexOf(id);
        if (index == -1) {
            return -1;
        }
        // 如果当前是第一首则返回最后一首
        switch (playMode) {
            case MusicConstants.PLAYMODE_SEQUENCE:
                if (index == 0) {
                    id = musicList.get(musicList.size() - 1);
                } else {
                    --index;
                    id = musicList.get(index);
                }
                break;
            case MusicConstants.PLAYMODE_SINGLE_REPEAT:
                break;
            case MusicConstants.PLAYMODE_RANDOM:
                id = getRandomMusic(musicList, id);
                break;
        }
        return id;
    }

    // 获取歌单列表
    public ArrayList<Integer> getMusicList(int playList) {
        Cursor cursor = null;
        ArrayList<Integer> list = new ArrayList<Integer>();
        int musicId;
        switch (playList) {
            case MusicConstants.LIST_ALLMUSIC:
                cursor = db.query(DatabaseHelper.MUSIC_TABLE, null, null, null, null, null, null);
                break;
            case MusicConstants.LIST_LASTPLAY:
                cursor = db.rawQuery("select * from "+DatabaseHelper.LAST_PLAY_TABLE+" ORDER BY "+ ID_COLUMN,null);
                break;
            case MusicConstants.LIST_MYLOVE:
                cursor = db.query(DatabaseHelper.MUSIC_TABLE, null, LOVE_COLUMN + " = ?", new String[]{"" + 1}, null, null, null);
                break;
            case MusicConstants.LIST_PLAYLIST:
                int listId = (int)SharedPreferenceUtil.getData(MusicConstants.KEY_LIST_ID, -1);
                list = getMusicIdListByPlaylist(listId);
                break;
            default:
                Log.e(TAG, "getMusicList default");
                break;
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                musicId = cursor.getInt(cursor.getColumnIndex("id"));
                list.add(musicId);
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    // 获取歌曲详细信息
    public ArrayList<String> getMusicInfo(int id) {
        if (id == -1) {
            return null;
        }
        Cursor cursor = null;
        ArrayList<String> musicInfo = new ArrayList<String>();
        cursor = db.query(DatabaseHelper.MUSIC_TABLE, null, ID_COLUMN + " = ?", new String[]{"" + id}, null, null, null);
        if (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                musicInfo.add(i, cursor.getString(i));
            }
        } else {
            musicInfo.add("0");
            musicInfo.add("听听音乐");
            musicInfo.add("好音质");
            musicInfo.add("0");
            musicInfo.add("0");
            musicInfo.add("0");
            musicInfo.add("0");
            musicInfo.add("0");
        }
        if (cursor != null) {
            cursor.close();
        }
        return musicInfo;
    }

    //获取随机歌曲
    public int getRandomMusic(ArrayList<Integer> list, int id) {
        int musicId;
        if (id == -1) {
            return -1;
        }
        if (list.isEmpty()) {
            return -1;
        }
        if (list.size() == 1) {
            return id;
        }
        do {
            int count = (int) (Math.random() * list.size());
            musicId = list.get(count);
        } while (musicId == id);

        return musicId;

    }

    //保留最近的20首
    public void setLastPlay(int id) {
        Log.i(TAG, "setLastPlay: id = " + id);
        if (id == -1 || id == 0) {
            return;
        }
        ContentValues values = new ContentValues();
        ArrayList<Integer> lastList = new ArrayList<Integer>();
        Cursor cursor = null;
        lastList.add(id);
        db.beginTransaction();
        try {
            cursor = db.rawQuery("select id from " + DatabaseHelper.LAST_PLAY_TABLE, null);
            while (cursor.moveToNext()) {
                if (cursor.getInt(0) != id) {
                    lastList.add(cursor.getInt(0));
                }
            }
            db.delete(DatabaseHelper.LAST_PLAY_TABLE, null, null);
            if (lastList.size() < 20) {
                for (int i = 0; i < lastList.size(); i++) {
                    values.put(ID_COLUMN, lastList.get(i));
                    db.insert(DatabaseHelper.LAST_PLAY_TABLE, null, values);
                }
            } else {
                for (int i = 0; i < 20; i++) {
                    values.put(ID_COLUMN, lastList.get(i));
                    db.insert(DatabaseHelper.LAST_PLAY_TABLE, null, values);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    //检索音乐是否我喜爱
    public boolean isSongMyLove(int musicId){
        boolean result = false;
        Cursor cursor = db.query(DatabaseHelper.MUSIC_TABLE,null,ID_COLUMN + " = ? and "+ LOVE_COLUMN + " = 1 ",
                new String[]{"" + musicId},null,null,null);
        if (cursor.moveToFirst()){
            result= true;
        }
        if (cursor!=null){
            cursor.close();
        }
        return  result;
    }

    public void setSongLoveStatus(int id, boolean love) {
        ContentValues values = new ContentValues();
        values.put(LOVE_COLUMN, love ? 1 : 0);
        db.update(DatabaseHelper.MUSIC_TABLE, values, ID_COLUMN + " = ? ", new String[]{"" + id});
    }

    //把MusicInfo对象转为ContentValues对象
    public ContentValues musicInfoToContentValues(LocalMusicInfo musicInfo) {
        ContentValues values = new ContentValues();
        try {
//            values.put(DatabaseHelper.ID_COLUMN, musicInfo.getId());
            values.put(DatabaseHelper.NAME_COLUMN, musicInfo.getName());
            values.put(DatabaseHelper.SINGER_COLUMN, musicInfo.getSinger());
            values.put(DatabaseHelper.ALBUM_COLUMN, musicInfo.getAlbum());
            values.put(DatabaseHelper.DURATION_COLUMN, musicInfo.getDuration());
            values.put(DatabaseHelper.PATH_COLUMN, musicInfo.getPath());
            values.put(DatabaseHelper.PARENT_PATH_COLUMN, musicInfo.getParentPath());
            values.put(DatabaseHelper.LOVE_COLUMN, musicInfo.getLove());
            values.put(DatabaseHelper.FIRST_LETTER_COLUMN, "" + Pinyin.toPinyin(musicInfo.getName().charAt(0)).toUpperCase().charAt(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return values;
    }

    //把Cursor对象转为List<MusicInfo>对象
    public List<LocalMusicInfo> cursorToMusicList(Cursor cursor) {
        List<LocalMusicInfo> list = null;
        try {
            if (cursor != null) {
                list = new ArrayList<>();
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex(ID_COLUMN));
                    String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.NAME_COLUMN));
                    String singer = cursor.getString(cursor.getColumnIndex(DatabaseHelper.SINGER_COLUMN));
                    String album = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ALBUM_COLUMN));
                    String duration = cursor.getString(cursor.getColumnIndex(DatabaseHelper.DURATION_COLUMN));
                    String path = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PATH_COLUMN));
                    String parentPath = cursor.getString(cursor.getColumnIndex(DatabaseHelper.PARENT_PATH_COLUMN));
                    int love = cursor.getInt(cursor.getColumnIndex(LOVE_COLUMN));
                    String firstLetter = cursor.getString(cursor.getColumnIndex(DatabaseHelper.FIRST_LETTER_COLUMN));

                    LocalMusicInfo musicInfo = new LocalMusicInfo();
                    musicInfo.setId(id);
                    musicInfo.setName(name);
                    musicInfo.setSinger(singer);
                    musicInfo.setAlbum(album);
                    musicInfo.setPath(path);
                    musicInfo.setParentPath(parentPath);
                    musicInfo.setLove(love);
                    musicInfo.setDuration(duration);
                    musicInfo.setFirstLetter(firstLetter);
                    list.add(musicInfo);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


}
