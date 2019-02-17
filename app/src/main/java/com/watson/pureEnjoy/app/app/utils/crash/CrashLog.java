package com.watson.pureEnjoy.app.app.utils.crash;

import java.io.Serializable;

public class CrashLog implements Serializable {
    private long mTime;
    private int type;
    private String timeStr;
    private String message;

    public CrashLog(String message) {
        this.message = message;
        mTime = System.currentTimeMillis();
        timeStr = CrashUtils.getLogTime();
    }

    public long getTime() {
        return mTime;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuffer bf = new StringBuffer(timeStr + "_" + mTime);
        bf.append("  ");
        bf.append(message);
        return bf.toString();
    }
}
