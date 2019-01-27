package com.watson.pureenjoy.music.http.entity.rank;

import com.watson.pureenjoy.music.http.entity.ResponseCode;

import java.util.List;

public class RankDetailResponse extends ResponseCode {

    private List<RankSongInfo> song_list;
    private RankInfo billboard;

    public List<RankSongInfo> getSong_list() {
        return song_list;
    }

    public RankInfo getBillboard() {
        return billboard;
    }
}
