package com.watson.pureenjoy.music.http.entity;

import com.watson.pureenjoy.music.app.MusicConstants;

public class ResponseCode {
    private String error_code;

    public String getError_code() {
        return error_code;
    }

    public boolean isSuccess() {
        return (MusicConstants.REQUEST_SUCCESS.equals(getError_code()));
    }
}
