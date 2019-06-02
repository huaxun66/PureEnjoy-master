package com.watson.pureenjoy.music.http.entity.local;

import android.os.Parcel;
import android.os.Parcelable;

public class MusicSheetInfo implements Parcelable {
    private int id;
    private String name;
    private int count;

    public MusicSheetInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public static final Creator<MusicSheetInfo> CREATOR = new Creator<MusicSheetInfo>() {
        @Override
        public MusicSheetInfo createFromParcel(Parcel source) {
            return new MusicSheetInfo(source);
        }

        @Override
        public MusicSheetInfo[] newArray(int size) {
            return new MusicSheetInfo[size];
        }
    };


    protected MusicSheetInfo(Parcel in) {
        this.id = in.readInt();
        this.count = in.readInt();
        this.name = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.count);
        dest.writeString(this.name);
    }
}
