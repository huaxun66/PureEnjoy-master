package com.watson.pureenjoy.music.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;

import com.jess.arms.integration.EventBusManager;
import com.watson.pureenjoy.music.R;
import com.watson.pureenjoy.music.app.MusicConstants;
import com.watson.pureenjoy.music.db.DBManager;
import com.watson.pureenjoy.music.event.MusicRefreshEvent;
import com.watson.pureenjoy.music.http.entity.local.LocalAlbumInfo;
import com.watson.pureenjoy.music.http.entity.local.LocalFolderInfo;
import com.watson.pureenjoy.music.http.entity.local.LocalMusicInfo;
import com.watson.pureenjoy.music.http.entity.local.LocalSingerInfo;
import com.watson.pureenjoy.music.mvp.service.MusicPlayerService;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import es.dmoral.toasty.Toasty;
import me.jessyan.armscomponent.commonsdk.utils.SharedPreferenceUtil;

public class MusicUtil {
    private final String TAG = "MusicUtil";

    //按歌手分组
    public static List<LocalSingerInfo> groupBySinger(ArrayList list) {
        Map<String, List<LocalMusicInfo>> musicMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            LocalMusicInfo musicInfo = (LocalMusicInfo) list.get(i);
            if (musicMap.containsKey(musicInfo.getSinger())) {
                ArrayList singerList = (ArrayList) musicMap.get(musicInfo.getSinger());
                singerList.add(musicInfo);
            } else {
                ArrayList temp = new ArrayList();
                temp.add(musicInfo);
                musicMap.put(musicInfo.getSinger(), temp);
            }
        }
        List<LocalSingerInfo> singerInfoList = musicMap.entrySet().stream().map(entry -> new LocalSingerInfo(entry.getKey(), entry.getValue().size())).collect(Collectors.toList());
        return singerInfoList;
    }


    //按专辑分组
    public static List<LocalAlbumInfo> groupByAlbum(ArrayList list) {
        Map<String, List<LocalMusicInfo>> musicMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            LocalMusicInfo musicInfo = (LocalMusicInfo) list.get(i);
            if (musicMap.containsKey(musicInfo.getAlbum())) {
                ArrayList albumList = (ArrayList) musicMap.get(musicInfo.getAlbum());
                albumList.add(musicInfo);
            } else {
                ArrayList temp = new ArrayList();
                temp.add(musicInfo);
                musicMap.put(musicInfo.getAlbum(), temp);
            }
        }
        List<LocalAlbumInfo> albumInfoList = musicMap.entrySet().stream().map(entry -> new LocalAlbumInfo(entry.getKey(), entry.getValue().get(0).getSinger(), entry.getValue().get(0).getAlbumThumbs(), entry.getValue().size())).collect(Collectors.toList());
        return albumInfoList;
    }

    //按文件夹分组
    public static List<LocalFolderInfo> groupByFolder(ArrayList list) {
        Map<String, List<LocalMusicInfo>> musicMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            LocalMusicInfo musicInfo = (LocalMusicInfo) list.get(i);
            if (musicMap.containsKey(musicInfo.getParentPath())) {
                ArrayList folderList = (ArrayList) musicMap.get(musicInfo.getParentPath());
                folderList.add(musicInfo);
            } else {
                ArrayList temp = new ArrayList();
                temp.add(musicInfo);
                musicMap.put(musicInfo.getParentPath(), temp);
            }
        }
        List<LocalFolderInfo> folderInfoList = musicMap.entrySet().stream().map(entry -> new LocalFolderInfo(new File(entry.getKey()).getName(), entry.getKey(), entry.getValue().size())).collect(Collectors.toList());
        return folderInfoList;
    }

    //从contentProvider删除
    public static void deleteMediaDB(File file, Context context) {
        String filePath = file.getPath();
        context.getContentResolver().delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                MediaStore.Audio.Media.DATA + "= \"" + filePath + "\"",
                null);
    }

    //切换播放模式
    public static int updatePlayMode() {
        int playMode = (int)SharedPreferenceUtil.getData(MusicConstants.KEY_MODE, MusicConstants.PLAY_MODE_SEQUENCE);
        int modeAfter = playMode == 2 ? 0 : ++playMode;
        SharedPreferenceUtil.putData(MusicConstants.KEY_MODE, modeAfter);
        return modeAfter;
    }

    //播放单个音乐
    public static void playMusic(Context context, LocalMusicInfo musicInfo, boolean addToRecent) {
        DBManager dbManager = DBManager.getInstance(context);
        if (addToRecent) {
            dbManager.addMusicToRecentPlayList(musicInfo);
            SharedPreferenceUtil.putData(MusicConstants.KEY_LIST, MusicConstants.LIST_RECENT_PLAY);
        }
        EventBusManager.getInstance().post(new MusicRefreshEvent());
        SharedPreferenceUtil.putData(MusicConstants.KEY_ID, musicInfo.getId());
        Intent intent = new Intent();
        intent.putExtra(MusicConstants.COMMAND, MusicConstants.COMMAND_PLAY);
        intent.setClass(context, MusicPlayerService.class);
        context.startService(intent);
    }

    //播放列表音乐
    public static void playMusic(Context context, LocalMusicInfo musicInfo, int type, String other) {
        SharedPreferenceUtil.putData(MusicConstants.KEY_LIST, type);
        SharedPreferenceUtil.putData(MusicConstants.KEY_OTHER, other == null ? "" : other);
        SharedPreferenceUtil.putData(MusicConstants.KEY_ID, musicInfo.getId());
        EventBusManager.getInstance().post(new MusicRefreshEvent());
        Intent intent = new Intent();
        intent.putExtra(MusicConstants.COMMAND, MusicConstants.COMMAND_PLAY);
        intent.setClass(context, MusicPlayerService.class);
        context.startService(intent);
    }

    //开始音乐
    public static void resumeMusic(Context context) {
        Intent intent = new Intent();
        intent.putExtra(MusicConstants.COMMAND, MusicConstants.COMMAND_RESUME);
        intent.setClass(context, MusicPlayerService.class);
        context.startService(intent);
    }

    //暂停音乐
    public static void pauseMusic(Context context) {
        Intent intent = new Intent();
        intent.putExtra(MusicConstants.COMMAND, MusicConstants.COMMAND_PAUSE);
        intent.setClass(context, MusicPlayerService.class);
        context.startService(intent);
    }

    //停止音乐
    public static void stopMusic(Context context) {
        Intent intent = new Intent();
        intent.putExtra(MusicConstants.COMMAND, MusicConstants.COMMAND_STOP);
        intent.setClass(context, MusicPlayerService.class);
        context.startService(intent);
    }

    //拖动音乐
    public static void seekMusic(Context context, int progress) {
        Intent intent = new Intent();
        intent.putExtra(MusicConstants.COMMAND, MusicConstants.COMMAND_PROGRESS);
        intent.putExtra(MusicConstants.KEY_CURRENT, progress);
        intent.setClass(context, MusicPlayerService.class);
        context.startService(intent);
    }


    //播放下一首
    public static void playNextMusic(Context context) {
        DBManager dbManager = DBManager.getInstance(context);
        int currentMusicId = (int)SharedPreferenceUtil.getData(MusicConstants.KEY_ID, -1);
        int playMode = (int)SharedPreferenceUtil.getData(MusicConstants.KEY_MODE, MusicConstants.PLAY_MODE_SEQUENCE);
        int playList = (int)SharedPreferenceUtil.getData(MusicConstants.KEY_LIST, MusicConstants.LIST_RECENT_PLAY);
        List<LocalMusicInfo> playMusicList = dbManager.getMusicList(playList);
        //播放列表为空
        if (playMusicList.isEmpty()) {
            Toasty.error(context, context.getString(R.string.music_play_list_empty)).show();
            SharedPreferenceUtil.putData(MusicConstants.KEY_ID, -1);
            Intent intent = new Intent();
            intent.putExtra(MusicConstants.COMMAND, MusicConstants.COMMAND_PAUSE);
            intent.setClass(context, MusicPlayerService.class);
            context.startService(intent);
            return;
        }
        List<Integer> playMusicIds = playMusicList.stream().map(info -> info.getId()).collect(Collectors.toList());
        //找不到取第一首
        if (currentMusicId == -1) {
            currentMusicId = playMusicList.get(0).getId();
        }
        //找到当前id在列表的位置
        int index = playMusicIds.indexOf(currentMusicId);
        switch (playMode) {
            case MusicConstants.PLAY_MODE_SEQUENCE:
                if (index == playMusicList.size() - 1) {
                    currentMusicId = playMusicList.get(0).getId();
                } else {
                    currentMusicId = playMusicList.get(++index).getId();
                }
                break;
            case MusicConstants.PLAY_MODE_SINGLE_REPEAT:
                break;
            case MusicConstants.PLAY_MODE_RANDOM:
                int randomIndex = (int)(Math.random() * playMusicList.size());
                currentMusicId = playMusicList.get(randomIndex).getId();
                break;
        }
        SharedPreferenceUtil.putData(MusicConstants.KEY_ID, currentMusicId);
        EventBusManager.getInstance().post(new MusicRefreshEvent());
        Intent intent = new Intent();
        intent.putExtra(MusicConstants.COMMAND, MusicConstants.COMMAND_PLAY);
        intent.setClass(context, MusicPlayerService.class);
        context.startService(intent);
    }

    //播放上一首
    public static void playPreMusic(Context context){
        DBManager dbManager = DBManager.getInstance(context);
        int currentMusicId = (int)SharedPreferenceUtil.getData(MusicConstants.KEY_ID, -1);
        int playMode = (int)SharedPreferenceUtil.getData(MusicConstants.KEY_MODE, MusicConstants.PLAY_MODE_SEQUENCE);
        int playList = (int)SharedPreferenceUtil.getData(MusicConstants.KEY_LIST, MusicConstants.LIST_RECENT_PLAY);
        List<LocalMusicInfo> playMusicList = dbManager.getMusicList(playList);
        //播放列表为空
        if (playMusicList.isEmpty()) {
            Toasty.error(context, context.getString(R.string.music_play_list_empty)).show();
            SharedPreferenceUtil.putData(MusicConstants.KEY_ID, -1);
            Intent intent = new Intent();
            intent.putExtra(MusicConstants.COMMAND, MusicConstants.COMMAND_PAUSE);
            intent.setClass(context, MusicPlayerService.class);
            context.startService(intent);
            return;
        }
        List<Integer> playMusicIds = playMusicList.stream().map(info -> info.getId()).collect(Collectors.toList());
        //找不到取第一首
        if (currentMusicId == -1) {
            currentMusicId = playMusicList.get(0).getId();
        }
        //找到当前id在列表的位置
        int index = playMusicIds.indexOf(currentMusicId);
        switch (playMode) {
            case MusicConstants.PLAY_MODE_SEQUENCE:
                if (index == 0) {
                    currentMusicId = playMusicList.get(playMusicList.size()-1).getId();
                } else {
                    currentMusicId = playMusicList.get(--index).getId();
                }
                break;
            case MusicConstants.PLAY_MODE_SINGLE_REPEAT:
                break;
            case MusicConstants.PLAY_MODE_RANDOM:
                int randomIndex = (int)(Math.random() * playMusicList.size());
                currentMusicId = playMusicList.get(randomIndex).getId();
                break;
        }
        SharedPreferenceUtil.putData(MusicConstants.KEY_ID, currentMusicId);
        EventBusManager.getInstance().post(new MusicRefreshEvent());
        Intent intent = new Intent();
        intent.putExtra(MusicConstants.COMMAND, MusicConstants.COMMAND_PLAY);
        intent.setClass(context, MusicPlayerService.class);
        context.startService(intent);
    }

    //获取当前播放歌曲
    public static LocalMusicInfo getCurrentPlayInfo(Context context) {
        int currentMusicId = (int)SharedPreferenceUtil.getData(MusicConstants.KEY_ID, -1);
        return DBManager.getInstance(context).getMusicById(currentMusicId);
    }


    //获取当前播放列表
    public static List<LocalMusicInfo> getCurrentPlayList(Context context) {
        int playList = (int)SharedPreferenceUtil.getData(MusicConstants.KEY_LIST, MusicConstants.LIST_RECENT_PLAY);
        return DBManager.getInstance(context).getMusicList(playList);
    }


    public static String getTimeString(int milliSecs) {
        StringBuffer sb = new StringBuffer();
        int m = milliSecs / (60 * 1000);
        sb.append(m < 10 ? "0" + m : m);
        sb.append(":");
        int s = (milliSecs % (60 * 1000)) / 1000;
        sb.append(s < 10 ? "0" + s : s);
        return sb.toString();
    }



}
