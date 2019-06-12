package com.watson.pureenjoy.music.utils;

import com.watson.pureenjoy.music.http.entity.local.LocalAlbumInfo;
import com.watson.pureenjoy.music.http.entity.local.LocalFolderInfo;
import com.watson.pureenjoy.music.http.entity.local.LocalMusicInfo;
import com.watson.pureenjoy.music.http.entity.local.LocalSingerInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicUtil {


    //按歌手分组
    public static ArrayList<LocalSingerInfo> groupBySinger(ArrayList list) {
        Map<String, List<LocalMusicInfo>> musicMap = new HashMap<>();
        ArrayList<LocalSingerInfo> singerInfoList = new ArrayList<>();
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

        for (Map.Entry<String,List<LocalMusicInfo>> entry : musicMap.entrySet()) {
            LocalSingerInfo singerInfo = new LocalSingerInfo();
            singerInfo.setName(entry.getKey());
            singerInfo.setCount(entry.getValue().size());
            singerInfoList.add(singerInfo);
        }
        return singerInfoList;
    }


    //按专辑分组
    public static ArrayList<LocalAlbumInfo> groupByAlbum(ArrayList list) {
        Map<String, List<LocalMusicInfo>> musicMap = new HashMap<>();
        ArrayList<LocalAlbumInfo> albumInfoList = new ArrayList<>();
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

        for (Map.Entry<String,List<LocalMusicInfo>> entry : musicMap.entrySet()) {
            LocalAlbumInfo albumInfo = new LocalAlbumInfo();
            albumInfo.setName(entry.getKey());
            albumInfo.setSinger(entry.getValue().get(0).getSinger());
            albumInfo.setCount(entry.getValue().size());
            albumInfoList.add(albumInfo);
        }

        return albumInfoList;
    }

    //按文件夹分组
    public static ArrayList<LocalFolderInfo> groupByFolder(ArrayList list) {
        Map<String, List<LocalMusicInfo>> musicMap = new HashMap<>();
        ArrayList<LocalFolderInfo> folderInfoList = new ArrayList<>();
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

        for (Map.Entry<String,List<LocalMusicInfo>> entry : musicMap.entrySet()) {
            LocalFolderInfo folderInfo = new LocalFolderInfo();
            File file = new File(entry.getKey());
            folderInfo.setName(file.getName());
            folderInfo.setPath(entry.getKey());
            folderInfo.setCount(entry.getValue().size());
            folderInfoList.add(folderInfo);
        }

        return folderInfoList;
    }

}
