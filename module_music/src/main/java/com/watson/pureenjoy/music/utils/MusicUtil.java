package com.watson.pureenjoy.music.utils;

import android.content.Context;

import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.db.DBManager;

import es.dmoral.toasty.Toasty;

public class MusicUtil {

    public static boolean isSongMyLove(Context context, int musicId){
        return DBManager.getInstance(context).isSongMyLove(musicId);
    }

    public static void setSongLoveStatus(Context context, int musicId, boolean love){
        if (musicId == -1){
            Toasty.error(context, context.getString(R.string.music_song_not_exist));
            return;
        }
        DBManager.getInstance(context).setSongLoveStatus(musicId, love);
    }

}
