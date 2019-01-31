package com.watson.pureenjoy.music.http.entity.recommendSong;

import java.util.List;

public class RecommendInfo {
    private String title;
    private List<RecommendSongInfo> song_list;

    public String getTitle() {
        return title;
    }

    public List<RecommendSongInfo> getSong_list() {
        return song_list;
    }
}
